/**
 * Created by mahesh.govind on 2/5/16.
 */

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;

import java.net.InetAddress;


public class IncommingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {


    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        final int rcvPktLength = buf.readableBytes();
        final byte[] rcvPktBuf = new byte[rcvPktLength];
        buf.readBytes(rcvPktBuf);
        try {
            System.out.printf("Inside incomming packet handler %d %d %d\n", buf.getInt(0) ,buf.getInt(4),buf.getByte(8));
        }catch (Exception e){

        e.printStackTrace();

        }

    }
}
