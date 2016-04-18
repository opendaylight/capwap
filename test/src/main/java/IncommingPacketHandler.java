/**
 * Created by mahesh.govind on 2/5/16.
 */

import akka.actor.ActorRef;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;


public class IncommingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    ActorRef msgProcessor = null;
    IncommingPacketHandler(){

    }

    public IncommingPacketHandler(ActorRef msgProcessorActor) {
        this.msgProcessor = msgProcessorActor;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        try {
            long longOut  = buf.readUnsignedInt();
            System.out.printf("\nReceived  Message %x ", longOut);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
