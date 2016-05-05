/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
/**
 * Created by mahesh.govind on 2/5/16.
 */

import akka.actor.ActorRef;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.ODLCapwapMessageFactory;
import org.opendaylight.capwap.fsm.CapwapEvent;
import org.opendaylight.capwap.fsm.CapwapEventType;
import org.opendaylight.capwap.utils.CapwapMessageCreator;

import java.net.InetAddress;


public class IncommingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    ActorRef msgProcessor = null;
    ActorRef decoder = null;
    ActorRef discoveryActor = null;
    public IncommingPacketHandler(){

    }

    public IncommingPacketHandler(ActorRef msgProcessorActor) {
        this.msgProcessor = msgProcessorActor;
    }

    public IncommingPacketHandler(ActorRef packetProcessorActor, ActorRef decoder, ActorRef discoveryActor) {

        this.msgProcessor = packetProcessorActor;
        this.decoder = decoder;
        this.discoveryActor = discoveryActor;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        try {

            CapwapEvent event = new CapwapEvent(CapwapEventType.DECODE);
            event.setPacket(packet);
            buf.retain();
            event.setIncomingBuf(buf);
            event.setCtx(channelHandlerContext);;
            msgProcessor.tell(event,this.msgProcessor);




        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
          public void channelReadComplete(ChannelHandlerContext ctx) {
        System.out.println("Channel Read Complete");
              ctx.flush();
          }

       @Override
       public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
           cause.printStackTrace();
           // We don't close the channel because we can keep serving requests.
       }

}

