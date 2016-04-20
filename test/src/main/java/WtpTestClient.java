import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.binding_802_11.WTP_Radio_Information;
import org.opendaylight.capwap.msgelements.*;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

/**
 * Created by flat on 17/04/16.
 */
public class WtpTestClient {


    public static ODLCapwapMessage createDiscoveryRequest(int discType){//Discovery or primary Discovery
        ODLCapwapMessage m = null;
        DiscoveryType discoveryType = null;
        WtpBoardDataMsgElem wtpBoardData = null;
        WtpDescriptor wtpDescriptor = null;
        WtpFrameTunnelModeMsgElem wtpFrameTunnelMode = null;
        WtpMacTypeMsgElem wtpMacType = null;
        WTP_Radio_Information wtp_radio_information = null;
        VendorSpecificPayload vsa =null;
        MtuDisPadding mtuDisPadding = null;

        m = new ODLCapwapMessage();

        //create Header
        m.header.setRid((byte)1);
        //create ctrlMsg
        m.ctrlMsg.setMsgType(discType );
        //create Msg Elements
        //Discovery Type, see Section 4.6.21
        discoveryType = new DiscoveryType();
        //WTP Board Data, see Section 4.6.40
        wtpBoardData = new WtpBoardDataMsgElem();
        //WTP Descriptor, see Section 4.6.41
        wtpDescriptor = new WtpDescriptor();
        //WTP Frame Tunnel Mode, see Section 4.6.43
        wtpFrameTunnelMode = new WtpFrameTunnelModeMsgElem();
        //WTP MAC Type, see Section 4.6.44
        wtpMacType = new WtpMacTypeMsgElem();
        wtp_radio_information = new WTP_Radio_Information();
        //WTP Radio Information message element(s) that the WTP supports;
        //These are defined by the individual link layer CAPWAP Binding
        //Protocols (see Section 2.1).
        //****Optional ones****
        //MTU Discovery Padding, see Section 4.6.32
        mtuDisPadding = new MtuDisPadding();
        //Vendor Specific Payload, see Section 4.6.39
        vsa = new VendorSpecificPayload();


        return null;
    }


    public ODLCapwapMessage createDiscoveryResponse(ODLCapwapMessage inRequest){
        ODLCapwapMessage outMsg = null;
        DiscoveryType discoveryType = null;
        WtpBoardDataMsgElem wtpBoardData = null;
        WtpDescriptor wtpDescriptor = null;
        WtpFrameTunnelModeMsgElem wtpFrameTunnelMode = null;
        WtpMacTypeMsgElem wtpMacTypeMsgElem = null;
        WTP_Radio_Information wtp_radio_information = null;

        outMsg = new ODLCapwapMessage();

        //create Header
        outMsg.header.setRid((byte)1);
        //create ctrlMsg
        outMsg.ctrlMsg.setMsgType(inRequest.ctrlMsg.getMsgType()+1 );//Response is always 1+ request
        //create Msg Elements
        /*
                o  Discovery Type, see Section 4.6.21
                o  WTP Board Data, see Section 4.6.40
                o  WTP Descriptor, see Section 4.6.41
                o  WTP Frame Tunnel Mode, see Section 4.6.43
                o  WTP MAC Type, see Section 4.6.44
                o  WTP Radio Information message element(s) that the WTP supports;
                    These are defined by the individual link layer CAPWAP Binding
                    Protocols (see Section 2.1).
                   ****Optional ones****
                o  MTU Discovery Padding, see Section 4.6.32
                o  Vendor Specific Payload, see Section 4.6.39

         */

        return null;
    }


    public void sender(ByteBuf buf) {

        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress address = null;
        try {
            address = InetAddress.getLoopbackAddress();
            System.out.println(address);
        } catch (Exception e) {
            e.printStackTrace();
        }

        {
            System.out.printf("Capacity of ByteBuf  %d", buf.writableBytes());
            System.out.printf("Writer Index %d", buf.writerIndex());
            int packet_size = buf.writerIndex();
            System.out.printf("packetsize %d", packet_size);
            byte[] array = buf.array();
            DatagramPacket packet = new DatagramPacket(array, packet_size, address, 5246);
            try {
                socket.send(packet);
                System.out.printf("\nSending Capwap Message  ");
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static void main(String args[]) {


        WtpTestClient tester = new WtpTestClient();
        ByteBuf buf = null;

    }
}
