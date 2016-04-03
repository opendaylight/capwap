/*
 * Copyright (c) 2015 Navin Agrawal and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetAddress;
import java.net.SocketException;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.usc.plugin.UscPluginUdp;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapAcRoot;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.capwap.ac.DiscoveredWtps;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.capwap.ac.DiscoveredWtpsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.capwap.ac.DiscoveredWtpsKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.discovered.wtp.Descriptor;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.discovered.wtp.DescriptorBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;


public class ODLCapwapACServer implements ODLCapwapACBaseServer,Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ODLCapwapACServer.class);
    
    public static enum SecurityType {
        NONE, DTLS, USC
    }

    private static final SecurityType security = SecurityType.DTLS;

    private class CapwapPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {

            final InetAddress srcAddr = packet.sender().getAddress();
            final ByteBuf buf = packet.content();
            final int rcvPktLength = buf.readableBytes();
            final byte[] rcvPktBuf = new byte[rcvPktLength];
            buf.readBytes(rcvPktBuf);

            rcvPktProcessing(rcvPktBuf, rcvPktLength, srcAddr);
        }
    }
    
    private final int port;
	private final NioEventLoopGroup group = new NioEventLoopGroup();

    private DataBroker dataProvider;

    private DiscoveredWtps localDiscoveredWtps;
    private Descriptor localDescriptor;

    private InstanceIdentifier<DiscoveredWtps> WTPS_IID;

    public ODLCapwapACServer(DataBroker dataProvider) throws SocketException {
        this.port = 5246;
	this.dataProvider = dataProvider;
    }

    public void run() {
	try {
		start();
	} catch (Exception e) {System.out.println (e);}
    }

    @Override
    public void start() throws Exception {

        final Bootstrap b = new Bootstrap();
        b.group(group);
        b.channel(NioDatagramChannel.class);
        b.option(ChannelOption.SO_BROADCAST, true);
        b.handler(new ChannelInitializer<NioDatagramChannel>() {
            @Override
            public void initChannel(final NioDatagramChannel ch) throws Exception {

                ChannelPipeline p = ch.pipeline();
                if (security == SecurityType.DTLS) {
                    p.addLast(new LoggingHandler("CapwapACServer Log 2", LogLevel.TRACE));
                    ChannelHandler dtlsHandler = UscPluginUdp.getSecureServerHandler(ch);
                    p.addLast(dtlsHandler);
                }
                p.addLast(new LoggingHandler("CapwapACServer Log 1", LogLevel.TRACE));
                p.addLast(new CapwapPacketHandler());
            }
        });
        b.bind(port).sync().channel().closeFuture().await();
    }

    @Override
    public void close() throws Exception {
		group.shutdownGracefully();
    }

    private Descriptor generateWtpDescriptor ( int Index ) {
	DescriptorBuilder descriptorBuilder = new DescriptorBuilder ();
        return descriptorBuilder.setMaxRadios((short)Index)
                                 .build();
    }


    private void addWTPEntryInDataStore ( String ipv4Addr, int Index ) {

        localDescriptor = generateWtpDescriptor (Index);

        DiscoveredWtpsBuilder wtpsBuilder = new DiscoveredWtpsBuilder();
        localDiscoveredWtps = wtpsBuilder.setIpv4Addr(ipv4Addr)
                                         .setKey(new DiscoveredWtpsKey(ipv4Addr))
                                         .setDescriptor(localDescriptor)
                                         .build();

        WTPS_IID = InstanceIdentifier.builder(CapwapAcRoot.class)
                                     .child(DiscoveredWtps.class, new DiscoveredWtpsKey(ipv4Addr))
                                     .build();

        WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
        tx.put (LogicalDatastoreType.OPERATIONAL, WTPS_IID, localDiscoveredWtps);

        Futures.addCallback( tx.submit(), new FutureCallback<Void>() {
            @Override
            public void onSuccess( final Void result ) {
            }

            @Override
            public void onFailure( final Throwable t ) {
                LOG.error( "WTP Add Failed", t );
            }
        } );
    }

    private void rcvPktProcessing ( byte pktBuf[], int pktLength, InetAddress srcAddr ) {
	byte buf[] = pktBuf;
	int index;
	int hlen;

	//System.out.print("rcvPktProcessing, pktLength: " + pktLength + "\n");

	/* Basic packet validation */

	/* Smaller than capwap header */
	if (pktLength < 8) return;

	/* Packet length smaller than header length specified in the capwap header */
	hlen = (buf[1] >> 3) * 4;
	//System.out.print("rcvPktProcessing, hlen: " + hlen + "\n");
	if (pktLength < hlen) return;

	addWTPEntryInDataStore (srcAddr.getHostAddress(), 0);

    }
}

