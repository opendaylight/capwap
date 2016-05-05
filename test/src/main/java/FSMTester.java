/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import org.opendaylight.capwap.fsm.CapwapMsgProcActor;

import java.net.InetAddress;

/**
 * Discards any incoming data.
 */
public class FSMTester {

    private int port;

    public FSMTester(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        final NioEventLoopGroup group = new NioEventLoopGroup();
        //Create Actor System
        final ActorSystem system = ActorSystem.create("CapwapFSMActorSystem");
        final ActorRef PacketProcessorActor = system.actorOf(Props.create(CapwapMsgProcActor.class,"MessageProcessorActor"),"MessageProcessorActor");
        try {
            final Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new ChannelInitializer<NioDatagramChannel>() {
                @Override
                public void initChannel(final NioDatagramChannel ch) throws Exception {

                    ChannelPipeline p = ch.pipeline();
                    p.addLast(new IncommingPacketFSMHandler(PacketProcessorActor));
                }
            });

            // Bind and start to accept incoming connections.
            Integer pPort = port;
            InetAddress address  = InetAddress.getLoopbackAddress();
            System.out.printf("\n\nwaiting for message %s %s\n\n",String.format(pPort.toString()),String.format( address.toString()));
            b.bind(address,port).sync().channel().closeFuture().await();

        } finally {
        System.out.print("In Server Finally");
        }
    }

    public static void main(String[] args) throws Exception {
        int port =5246;
        new UdpServer(port).run();
    }
}
