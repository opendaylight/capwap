import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.binding_802_11.WTP_Radio_Information;
import org.opendaylight.capwap.msgelements.*;
import org.opendaylight.capwap.msgelements.subelem.ACInformationSubElement;
import org.opendaylight.capwap.msgelements.subelem.BoardDataSubElem;
import org.opendaylight.capwap.msgelements.subelem.DescriptorSubElement;
import org.opendaylight.capwap.msgelements.subelem.EncryptionSubElement;
import org.opendaylight.capwap.utils.CapwapMessageCreator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

/**
 * Created by flat on 17/04/16.
 */
public class WtpTestClient {


    public  ODLCapwapMessage createDiscoveryRequest(int discType){//Discovery or primary Discovery

        ODLCapwapMessage m = null;
        DiscoveryType discoveryType = null;
        WtpBoardDataMsgElem wtpBoard = null;
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
        discoveryType.setDhcp();
        m.ctrlMsg.addMessageElement(discoveryType);
        //WTP Board Data, see Section 4.6.40

        wtpBoard = new WtpBoardDataMsgElem();
        BoardDataSubElem bs = new BoardDataSubElem(12, 64);

        wtpBoard.setVendorId(120);
        wtpBoard.addBoardData(bs);
        wtpBoard.addBoardData(bs);
        m.ctrlMsg.addMessageElement(wtpBoard);


        //WTP Descriptor, see Section 4.6.41
        wtpDescriptor = new WtpDescriptor();
        wtpDescriptor.setMaxRadios((short) 12).
                setRadioInUse((short) 4);
        EncryptionSubElement e = new EncryptionSubElement((byte) 1, 12);
        EncryptionSubElement e1 = new EncryptionSubElement((byte) 2, 10);

        wtpDescriptor.addEncryptSubElement(e);
        wtpDescriptor.addEncryptSubElement(e1);

        DescriptorSubElement d = new DescriptorSubElement(128);
        d.setVendorId(120).setDescType(13);

        DescriptorSubElement d1 = new DescriptorSubElement(64);
        d1.setVendorId(110).setDescType(10);

        wtpDescriptor.addDescriptorSubElm(d);
        wtpDescriptor.addDescriptorSubElm(d1);

        m.ctrlMsg.addMessageElement(wtpDescriptor);


        //WTP Frame Tunnel Mode, see Section 4.6.43
        wtpFrameTunnelMode = new WtpFrameTunnelModeMsgElem();
        wtpFrameTunnelMode.setnBit();
        m.ctrlMsg.addMessageElement(wtpFrameTunnelMode);



        //WTP MAC Type, see Section 4.6.44
        wtpMacType = new WtpMacTypeMsgElem();
        wtpMacType.setTypeLocal();
        m.ctrlMsg.addMessageElement(wtpMacType);

        //WTP Radio Information message element(s) that the WTP supports;
        //These are defined by the individual link layer CAPWAP Binding
        //Protocols (see Section 2.1).
        WTP_Radio_Information ri = new WTP_Radio_Information();
        ri.setRadioType((byte) 2);
        ri.set802_11g();
        m.ctrlMsg.addMessageElement(ri);
        return m;
    }

    public ODLCapwapMessage createResponse(ODLCapwapMessage inRequest){
        ODLCapwapMessage m = null;
        m = new ODLCapwapMessage();
        m.ctrlMsg.setMsgType(inRequest.ctrlMsg.getMsgType()+1 );//Response is always 1+ request
        return m;
    }

    public ODLCapwapMessage createDiscoveryResponse(ODLCapwapMessage inRequest){
        ODLCapwapMessage m = null;
        DiscoveryType discoveryType = null;
        WtpBoardDataMsgElem wtpBoard = null;
        WtpDescriptor wtpDescriptor = null;
        WtpFrameTunnelModeMsgElem wtpFrameTunnelMode = null;
        WtpMacTypeMsgElem wtpMacTypeMsgElem = null;
        WTP_Radio_Information wtp_radio_information = null;
        ACDescriptor acDescriptor = null;
        ACName acName = null;

        m = new ODLCapwapMessage();

        //create Header
        m.header.setRid((byte)1);
        //create ctrlMsg
        m.ctrlMsg.setMsgType(inRequest.ctrlMsg.getMsgType()+1 );//Response is always 1+ request

        //Discovery Type, see Section 4.6.21
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        m.ctrlMsg.addMessageElement(discoveryType);

        //Set AC Descriptor
        acDescriptor = new ACDescriptor();
        acDescriptor.setStations(2).
                setActiveWtps(2).
                setLimit(12).
                setMaxWtps(3).
                setSecuritySbit().
                setRmac((byte) 2).
                setDtlsPolicyDbit();
        //Now create Information Sub Element
        ACInformationSubElement e = new ACInformationSubElement(64);
        byte[] dummy = new byte[64];
        e.setAcInfoVendorId(12);
        e.setAcInfoType(35);
        e.setAcInfoData(dummy);
        acDescriptor.addAcInformationSubElem(e);
        ACInformationSubElement e1 = new ACInformationSubElement(128);
        byte[] dummy1 = new byte[128];
        e1.setAcInfoVendorId(102);
        e1.setAcInfoType(3);
        e1.setAcInfoData(dummy1);
        acDescriptor.addAcInformationSubElem(e1);
        //Add message element to Capwap Message
        m.ctrlMsg.addMessageElement(acDescriptor);


        //Set AC name
        acName = new ACName();
        acName.setName(new byte[] {'T','h','i','s',' ', 'i','s',',','m','y','n','a','m','e'});
        m.ctrlMsg.addMessageElement(acName);

        //WTP Board Data, see Section 4.6.40

        wtpBoard = new WtpBoardDataMsgElem();
        BoardDataSubElem bs = new BoardDataSubElem(12, 64);
        wtpBoard.setVendorId(120);
        wtpBoard.addBoardData(bs);
        wtpBoard.addBoardData(bs);
        m.ctrlMsg.addMessageElement(wtpBoard);


        //WTP Descriptor, see Section 4.6.41
        wtpDescriptor = new WtpDescriptor();
        wtpDescriptor.setMaxRadios((short) 12).
                setRadioInUse((short) 4);
        EncryptionSubElement enc = new EncryptionSubElement((byte) 1, 12);
        EncryptionSubElement enc1 = new EncryptionSubElement((byte) 2, 10);
        wtpDescriptor.addEncryptSubElement(enc);
        wtpDescriptor.addEncryptSubElement(enc1);
        DescriptorSubElement d = new DescriptorSubElement(128);
        d.setVendorId(120).setDescType(13);
        DescriptorSubElement d1 = new DescriptorSubElement(64);
        d1.setVendorId(110).setDescType(10);
        wtpDescriptor.addDescriptorSubElm(d);
        wtpDescriptor.addDescriptorSubElm(d1);
        m.ctrlMsg.addMessageElement(wtpDescriptor);


        //WTP Frame Tunnel Mode, see Section 4.6.43
        wtpFrameTunnelMode = new WtpFrameTunnelModeMsgElem();
        wtpFrameTunnelMode.setnBit();
        m.ctrlMsg.addMessageElement(wtpFrameTunnelMode);


        //WTP Radio Information message element(s) that the WTP supports;
        //These are defined by the individual link layer CAPWAP Binding
        //Protocols (see Section 2.1).
        WTP_Radio_Information ri = new WTP_Radio_Information();
        ri.setRadioType((byte) 2);
        ri.set802_11g();
        m.ctrlMsg.addMessageElement(ri);
        return m;

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
            int packet_size = buf.writerIndex();
            byte[] array = buf.array();
            int port = 5246;
            DatagramPacket packet = new DatagramPacket(array, packet_size, address, port);
            try {
                socket.send(packet);
                System.out.printf("\nSending Capwap Message of size->%d to %s:%d ",packet_size,address.toString(),port);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    public static void main(String args[]) {


        WtpTestClient tester = new WtpTestClient();
        ODLCapwapMessage msg = null;
        int input = 0;
        Scanner sc=new Scanner(System.in);


        while(input != -1) {
            ByteBuf buf = Unpooled.buffer();
            System.out.printf("\nEnter the CAPWAP Message Number , enter \"-1\" to exit\n");
            input = sc.nextInt();
            if (input == -1) break;
            System.out.printf("\nInput Choice %d\n",input);
            switch (input){
                case ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);  //later each of the functions needs to be changd with custom methods
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_PRIMARY_DISCOVERY_REQUEST:
                    msg = tester.createDiscoveryRequest(input);
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_JOIN_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_ECHO_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_CONFIG_STATUS_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_CHANGE_STATE_EVENT_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_CLEAR_CONFIG_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_IMAGE_DATA_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);
                    break;
                case ODLCapwapConsts.ODL_CAPWAP_WTP_EVENT_REQUEST:
                    msg = CapwapMessageCreator.createRequest(input);
                    break;


                default:

            }//end of switch(input)
            msg.header.encodeHeader(buf);
            msg.ctrlMsg.encode(buf);
            tester.sender(buf);

            //encode the message and send the message to AC
        } //end of while

    }
}
