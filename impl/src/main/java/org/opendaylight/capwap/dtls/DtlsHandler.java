/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.dtls;

import io.netty.channel.Channel;
import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import io.netty.channel.socket.DatagramPacket;
import org.bouncycastle.crypto.tls.DTLSTransport;
//import org.opendaylight.capwap.dtls.DtlsEngine;
//import org.opendaylight.capwap.dtls.DtlsPacket;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class DtlsHandler extends ChannelDuplexHandler {

    //private static final Logger log = LoggerFactory.getLogger(org.opendaylight.capwap.dtls.DtlsHandler.class);
    private static final Logger log = LoggerFactory.getLogger(DtlsHandler.class);
    int counter = 0;


    Channel channel=null;

    public static class ChannelContext {
        public final ChannelHandlerContext ctx;
        public final ChannelPromise promise;

        public ChannelContext(ChannelHandlerContext ctx, ChannelPromise promise) {
            super();
            this.ctx = ctx;
            this.promise = promise;
        }
    }

    //private final ExecutorService executor = Executors.newSingleThreadExecutor();
    //protected final DtlsHandlerTransport rawTransport = new DtlsHandlerTransport();
    //private final org.opendaylight.capwap.dtls.DtlsEngine engine = new DtlsEngine(rawTransport);
    //private final DtlsEngine engine = new DtlsEngine(channel);
    //private final ConcurrentLinkedQueue<ChannelContext> writeCtxQueue = new ConcurrentLinkedQueue<>();

    HashMap <InetSocketAddress, DtlsEngine> ctxMap= new HashMap<>();

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);

        channel = ctx.channel();

        /*
        executor.submit(new Runnable() {
            @Override
            public void run() {
                //while (true) {
                    try {
                        log.error(getName() + " init start ");

                        final DTLSTransport encTransport = getDtlsTransport();
                        engine.initialize(encTransport);
                        log.trace(getName() + " init end ");
                    } catch (IOException | InterruptedException | ExecutionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                //}
            }
        });
        */
    }

    private DtlsEngine findDtlsEngine(InetSocketAddress sender) {
        return ctxMap.get(sender);
    }

    private DtlsEngine createDtlsEngine(InetSocketAddress sender) {
        DtlsEngine engine = new DtlsEngine("Engine-"+counter, channel);
        engine.start();

        ctxMap.put(sender, engine);
        return engine;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (obj instanceof DatagramPacket) {
            DatagramPacket msg = (DatagramPacket) obj;
            InetSocketAddress peer = msg.sender();
            DtlsEngine engine = null;

            log.trace(getName() + " channelRead ");

            //Find DTLS Engine
            if ((engine = findDtlsEngine(peer)) == null) {
                log.trace(getName() + "Unable to find peer for IP=" +  peer.getHostString() + " and port=" + peer.getPort());
                //Create DTLS engine
                engine = createDtlsEngine(peer);
                if (engine == null) {
                    log.error("Unable to create DtlsEngine for peer IP=" + peer.getHostString() + " and port=" + peer.getPort());
                    return;
                }
                engine.setRemoteAddress(peer);
            } else {
                log.trace(getName() + "Found DtlsEngine for peer IP=" +  peer.getHostString() + " and port=" + peer.getPort());
            }

            // send packet to underlying transport for consumption
            ArrayList<DatagramPacket> packets = engine.read(msg);
            for (DatagramPacket packet : packets) {
                log.trace(getName() + "Got packet" + packet);
                super.channelRead(ctx, packet);
            }

        } else {
            log.trace(getName() + " Not a DatagramPacket ");
            super.channelRead(ctx, obj);
        }
    }

    @Override
    public void write(ChannelHandlerContext ctx, Object obj, ChannelPromise promise) throws Exception {
        DtlsEngine engine = null;
        if (obj instanceof DatagramPacket) {
            // this is the unencryped data written by the app
            DatagramPacket msg = (DatagramPacket) obj;

            log.trace(getName() + " write " + msg);

            InetSocketAddress dest = msg.recipient();
            if ((engine = findDtlsEngine(dest)) == null) {
                log.error(getName() + "Unable to find DtlsEngine");
                return;
            }

            // flush the queue when channel initialized
            if (engine.isInitialized()) {
                // assume messages are one-to-one between raw and encrypted
                engine.setContext(new ChannelContext(ctx, promise));
            }
            engine.write(msg);
        } else if (obj instanceof DtlsPacket) {
            // used to passthrough the data for handshake packets
            log.trace(getName() + " write DtlsPacket" );

            // this is the underlying traffic written by this handler
            DtlsPacket msg = (DtlsPacket) obj;

            InetSocketAddress dest = msg.packet.recipient();
            if ((engine = findDtlsEngine(dest)) == null) {
                log.error(getName() + "Unable to find DtlsEngine");
                return;
            }
            ChannelContext context = engine.getContext();
            //ChannelContext context = writeCtxQueue.poll();
            if (context != null) {
                log.trace(getName() + " write context" + context.promise);
                super.write(context.ctx, msg.packet, context.promise);
            } else {
                log.trace(getName() + " write NULL context" );
                super.write(ctx, msg.packet, promise);
            }
        } else {
            super.write(ctx, obj, promise);
        }
    }

    protected String getName() {
        return this.getClass().toString();
    }

    protected abstract DTLSTransport getDtlsTransport() throws IOException;

}
