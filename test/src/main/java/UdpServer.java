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
import org.opendaylight.capwap.fsm.CapwapDecoderActor;
import org.opendaylight.capwap.fsm.CapwapDiscoveryServiceActor;
import org.opendaylight.capwap.fsm.CapwapMsgProcActor;

import java.net.InetAddress;

/**
 * Discards any incoming data.
 */
public class UdpServer {

    private int port;

    public UdpServer(int port) {
        this.port = port;
    }

    void createActorAndHandler(ChannelPipeline p){
        final ActorSystem system = ActorSystem.create("CapwapFSMActorSystem");
        ActorRef discoveryActor= system.actorOf(Props.create(CapwapDiscoveryServiceActor.class,"discoveryActor"),"discoveryActor");
        ActorRef decoder= system.actorOf(Props.create(CapwapDecoderActor.class,"MessageDecoderActor",discoveryActor),"MessageDecoderActor");
        final ActorRef PacketProcessorActor = system.actorOf(Props.create(CapwapMsgProcActor.class,"MessageProcessorActor",decoder),"MessageProcessorActor");
        p.addLast( new IncommingPacketHandler(PacketProcessorActor,decoder,discoveryActor));
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
                    createActorAndHandler(p);
                    //p.addLast(new IncommingPacketHandler(PacketProcessorActor));
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
