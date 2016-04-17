/**
 * Created by mahesh.govind on 2/5/16.
 */

import akka.actor.ActorRef;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapHeader;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.ODLCapwapMessageFactory;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.CapwapHeader;

import java.net.InetAddress;


public class IncommingPacketHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    public IncommingPacketHandler() {
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        try {
            System.out.printf("Inside incomming packet handler wtpId \n");
            ODLCapwapMessage msg = ODLCapwapMessageFactory.decodeFromByteArray(buf);
            ODLCapwapHeader hdr = ODLCapwapHeader.decodeHeader(buf);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
