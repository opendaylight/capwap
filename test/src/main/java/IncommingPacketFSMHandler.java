/**
 * Created by mahesh.govind on 2/5/16.
 */

import akka.actor.ActorRef;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.fsm.CapwapEvent;
import org.opendaylight.capwap.fsm.CapwapEventType;

import java.net.InetAddress;


public class IncommingPacketFSMHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    ActorRef msgProcessor = null;
    public IncommingPacketFSMHandler(){

    }

    public IncommingPacketFSMHandler(ActorRef msgProcessorActor) {
        this.msgProcessor = msgProcessorActor;
    }

    @Override
    protected void messageReceived(ChannelHandlerContext channelHandlerContext, DatagramPacket packet) throws Exception {
        final InetAddress srcAddr = packet.sender().getAddress();
        final ByteBuf buf = packet.content();
        try {
            System.out.printf("Inside incomming packet handler %s wtpId %d \n", ODLCapwapConsts.msgTypetoString( buf.getInt(0)) ,buf.getInt(4));
            CapwapEvent event = new CapwapEvent(CapwapEventType.DECODE);
            int wtpId = buf.getInt(4);
            Integer WTPID = new Integer(wtpId);
            event.setWtpID("wtpID"+WTPID.toString());
            msgProcessor.tell(event,this.msgProcessor);

        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
