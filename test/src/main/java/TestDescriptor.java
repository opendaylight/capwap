/*
 * Copyright (c) 2016 Brunda R Rajagopala and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

import com.sun.deploy.association.AssociationService;
import org.junit.Test;
import org.junit.Assert;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.*;
import org.opendaylight.capwap.binding_802_11.AddWlan;
import org.opendaylight.capwap.binding_802_11.UpdateWlan;
import org.opendaylight.capwap.binding_802_11.DeleteWlan;
import org.opendaylight.capwap.binding_802_11.WTP_Radio_Information;
import org.opendaylight.capwap.msgelements.*;
import org.opendaylight.capwap.msgelements.subelem.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;
//import java.util.logging.Logger;


public class TestDescriptor{
private static final Logger LOG = LoggerFactory.getLogger(TestDescriptor.class);

   public TestDescriptor ()
    {



    }
    
	@Test
 	public void deleteWlanTester ()
 	{

    	StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

     	ODLCapwapMessage msg = null;
     	ODLCapwapMessage n = null;

     	msg = new ODLCapwapMessage();
     	DeleteWlan deleteWlan = new DeleteWlan();
     	System.out.println("DeleteWlanTester: type = " +deleteWlan.getType());
     	deleteWlan.setRadioId((byte)2);
     	deleteWlan.setWlanId((byte)12);
     	msg.ctrlMsg.addMessageElement(deleteWlan);

     	msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
     	msg.ctrlMsg.setSeqNo((short) 1);
     	ByteBuf buf = Unpooled.buffer();
    	msg.header.encodeHeader(buf);
     	msg.ctrlMsg.encode(buf);

     	n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
     	sender(buf);
     	Assert.assertEquals(msg,n);
 	}

	@Test
	public void updateWlanTester ()
	{
    	StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

    	ODLCapwapMessage msg = null;
    	ODLCapwapMessage n = null;

    	msg = new ODLCapwapMessage();
    	UpdateWlan updateWlan = new UpdateWlan();
    	System.out.println("UpdateWlanTester: type = " +updateWlan.getType());
    	updateWlan.setRadioId((byte)2);
    	updateWlan.setWlanId((byte)12);
    	//addWlan.setCapability(343);
    	updateWlan.setCapabilityEbit();
    	System.out.println("Capability = " + updateWlan.getCapability());
    	System.out.println("Capability set = " + updateWlan.isCapabilityEbitSet());

    	updateWlan.unsetCapabilityEbit();
    	System.out.println("Capability = " + updateWlan.getCapability());
    	System.out.println("Capability set = " + updateWlan.isCapabilityEbitSet());

	    updateWlan.setCapabilityAbit();
    	updateWlan.setCapabilityBbit();
    	updateWlan.setCapabilityCbit();
    	updateWlan.setCapabilityDbit();
    	updateWlan.setCapabilityEbit();
    	updateWlan.setCapabilityFbit();
    	updateWlan.setCapabilityIbit();
    	updateWlan.setCapabilityKbit();
    	updateWlan.setCapabilityLbit();
    	updateWlan.setCapabilityMbit();
    	updateWlan.setCapabilityObit();
    	updateWlan.setCapabilityPbit();
    	updateWlan.setCapabilityQbit();
    	updateWlan.setCapabilitySbit();
    	updateWlan.setCapabilityTbit();
    	updateWlan.setCapabilityVbit();
    	System.out.println("Capability = " + updateWlan.getCapability());
    	updateWlan.setKeyIndex((byte)78);
    	updateWlan.setKeyStatus((byte)1);
    	System.out.println("keyStatus = " + updateWlan.getKeyStatus());
    	byte [] key = new byte[4];
   		key[0] = 1;
    	key[1] = 2;
    	key[2] = 3;
    	key[3] = 4;
    	updateWlan.setKey(key) ;

    	msg.ctrlMsg.addMessageElement(updateWlan);

    	msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
    	msg.ctrlMsg.setSeqNo((short) 1);
    	ByteBuf buf = Unpooled.buffer();
    	msg.header.encodeHeader(buf);
    	msg.ctrlMsg.encode(buf);

    	n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
    	sender(buf);
    	Assert.assertEquals(msg,n);
	}
   
    @Test
    public void AddWlanTester ()
    {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();
        AddWlan addWlan = new AddWlan();
        System.out.println("AddWlanTester: type = " +addWlan.getType());
        addWlan.setRadioId((byte)2);
        addWlan.setWlanId((byte)12);
        //addWlan.setCapability(343);
        addWlan.setCapabilityEbit();
        System.out.println("Capability = " + addWlan.getCapability());
        System.out.println("Capability set = " + addWlan.isCapabilityEbitSet());

        addWlan.unsetCapabilityEbit();
        System.out.println("Capability = " + addWlan.getCapability());
        System.out.println("Capability set = " + addWlan.isCapabilityEbitSet());



        addWlan.setCapabilityAbit();
        addWlan.setCapabilityBbit();
        addWlan.setCapabilityCbit();
        addWlan.setCapabilityDbit();
        addWlan.setCapabilityEbit();
        addWlan.setCapabilityFbit();
        addWlan.setCapabilityIbit();
        addWlan.setCapabilityKbit();
        addWlan.setCapabilityLbit();
        addWlan.setCapabilityMbit();
        addWlan.setCapabilityObit();
        addWlan.setCapabilityPbit();
        addWlan.setCapabilityQbit();
        addWlan.setCapabilitySbit();
        addWlan.setCapabilityTbit();
        addWlan.setCapabilityVbit();
        System.out.println("Capability = " + addWlan.getCapability());
        addWlan.setKeyIndex((byte)78);
        addWlan.setKeyStatus((byte)1);
        byte [] key = new byte[4];
        key[0] = 1;
        key[1] = 2;
        key[2] = 3;
        key[3] = 4;
        addWlan.setKey(key) ;
        byte [] groupTsc = new byte[6];
        groupTsc[0] = 1;
        groupTsc[1] = 2;
        groupTsc[2] = 3;
        groupTsc[3] = 4;
        groupTsc[4] = 5;
        groupTsc[5] = 6;
        addWlan.setGroupTsc(groupTsc);
        addWlan.setQos((byte)2);
        addWlan.setAuthType((byte)1);
        addWlan.setMacMode((byte)0);
        addWlan.setTunnelMode((byte)2);
        addWlan.setSuppressSSID((byte)1);
        byte [] ssId = new byte [4];
        ssId[0] = 100;
        ssId[1] = 101;
        ssId[2] = 102;
        ssId[3] = 104;
        addWlan.setSsId(ssId);
        msg.ctrlMsg.addMessageElement(addWlan);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
    }

    @Test
public void SessionIdTester ()
{
    StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

    ODLCapwapMessage msg = null;
    ODLCapwapMessage n = null;

    msg = new ODLCapwapMessage();

    SessionID sess = new SessionID();
    byte[] address = new byte[16];
    address[12] = 32;
    address[1] = 32;
    address[5] = 32;
    address[2] = 32;
    address[8] = 62;
    address[7] = 32;
    sess.setSessionid(address);

    msg.ctrlMsg.addMessageElement(sess);

    msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
    msg.ctrlMsg.setSeqNo((short) 1);
    ByteBuf buf = Unpooled.buffer();
    msg.header.encodeHeader(buf);
    msg.ctrlMsg.encode(buf);

    n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
    sender(buf);
    Assert.assertEquals(msg,n);
}

@Test
public void  wtpNameTester() {
    StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

    ODLCapwapMessage msg = null;
    ODLCapwapMessage n = null;

    msg = new ODLCapwapMessage();

    WTPName name = new WTPName();
    byte[] address = new byte[16];
    address[12] = 32;
    address[1] = 32;
    address[5] = 32;
    address[2] = 32;
    address[8] = 62;
    address[7] = 32;
    name.setName(address);
    msg.ctrlMsg.addMessageElement(name);

    msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
    msg.ctrlMsg.setSeqNo((short) 1);
    ByteBuf buf = Unpooled.buffer();
    msg.header.encodeHeader(buf);
    msg.ctrlMsg.encode(buf);

    n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
    sender(buf);
    Assert.assertEquals(msg,n);
}    
/*
    @Test
    public void wtpRadioInfoTester() {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();
        WTP_Radio_Information ri = new WTP_Radio_Information();
        ri.setRadioType((byte) 2);
        ri.set802_11g();
        msg.ctrlMsg.addMessageElement(ri);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
}
*/

     
       @Test

    public void WtpDesciptorTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        msg.ctrlMsg.addMessageElement(discoveryType);

        //create WtpDescriptor
        WtpDescriptor wtpDescriptor = new WtpDescriptor();
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

        msg.ctrlMsg.addMessageElement(wtpDescriptor);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
    }

    @Test
    public void frameTunnelModeTester() {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();
        WtpFrameTunnelModeMsgElem ft = new WtpFrameTunnelModeMsgElem();
        ft.setnBit();
        msg.ctrlMsg.addMessageElement(ft);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);

    }


@Test
public void ACDescriptorTest() {
    StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;
        ACName acname = null;

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        msg.ctrlMsg.addMessageElement(discoveryType);

        //create AcDescriptor element
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
        msg.ctrlMsg.addMessageElement(acDescriptor);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

    acname = new ACName();
    acname.setName(new byte[] {'T','h','i','s',' ', 'i','s',',','m','y','n','a','m','e'});
    //acname.setName("k1");
    acname.setLength(2);
    msg.ctrlMsg.addMessageElement(acname);


        ByteBuf buf = Unpooled.buffer();
    msg.header.encodeHeader(buf);
    msg.ctrlMsg.encode(buf);

        ODLCapwapMessage n = null;
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
    //boolean m = false;
                //Assert.assertEquals(msg,m);

    sender(buf);
    Assert.assertEquals(msg,n);



    }


    @Test
    public void WtpReootStatisticsTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;


        WTPRebootStatistics rebootStatistics = new WTPRebootStatistics();

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        rebootStatistics.setLastFailureType((short) 1);
        rebootStatistics.setSoftwareFailure(2);
        rebootStatistics.setAcInitiated(3);
        rebootStatistics.setLinkFailure(4);
        rebootStatistics.setUnKnownFailure(5);
        rebootStatistics.setRebootCount(6);
        rebootStatistics.setOtherFailure(7);
        rebootStatistics.setHwFailure(8);

        msg.ctrlMsg.addMessageElement(rebootStatistics);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
            //LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

    }   
 // */
            /*
    @Test
    public void  IPV4ControlDescriptorTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        CapwapControlIPV4Addr capwapControlIPV4Addr = new CapwapControlIPV4Addr();

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        capwapControlIPV4Addr.setIpv4(new byte []{1,2,3,4});
        capwapControlIPV4Addr.setWtpCount(3);
        msg.ctrlMsg.addMessageElement(capwapControlIPV4Addr);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf =  Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals (msg,n);

    }
    @Test
    public void  localIPV4DescriptorTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        CapwapLocalIPV4Address capwapLocalIPV4Address = new CapwapLocalIPV4Address();

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        capwapLocalIPV4Address.setAddress(new byte []{1,2,3,4});
        msg.ctrlMsg.addMessageElement(capwapLocalIPV4Address);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);

    }

    @Test
    public void  IPV4ListDescriptorTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        IPV4AddrList ipv4AddrList = new IPV4AddrList();

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        ipv4AddrList.addAddress(new byte []{1,2,3,4});
        ipv4AddrList.addAddress(new byte []{5,2,3,4});
        msg.ctrlMsg.addMessageElement(ipv4AddrList);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        Assert.assertEquals(msg,n);

    }

    @Test

    public void resultCodeTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;


        ResultCode resultCode = new ResultCode();

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        resultCode.setResultCode(2345);
        msg.ctrlMsg.addMessageElement(resultCode);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
                                     sender(buf);
        Assert.assertEquals(msg,n);

    }    */
    /*
    @Test
    public void  ECNTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;


        ECNSupport ecnSupport = new ECNSupport();

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        ecnSupport.setEcn((byte) 2);
        msg.ctrlMsg.addMessageElement(ecnSupport);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
    }

    @Test
    public void DiscoveryDescriptorTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        msg.ctrlMsg.addMessageElement(discoveryType);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);

    }
    @Test
    public void  WtpMacTypeDescriptorTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;
        WtpMacTypeMsgElem macType = null;
        msg = new ODLCapwapMessage();

        //create  Discovery Type
        macType = new WtpMacTypeMsgElem();
        macType.setTypeLocal();
        msg.ctrlMsg.addMessageElement(macType);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
     }        
             /*
     @Test
    public void WtpBoardDataDescriptorTest() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;
        DiscoveryType discoveryType = null;
        WtpBoardDataMsgElem wtpBoard = null;

        msg = new ODLCapwapMessage();

        //create  Discovery Type
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        msg.ctrlMsg.addMessageElement(discoveryType);


        //create a board data message elem
        wtpBoard = new WtpBoardDataMsgElem();
        BoardDataSubElem bs = new BoardDataSubElem(12, 64);

        wtpBoard.setVendorId(120);
        wtpBoard.addBoardData(bs);
        wtpBoard.addBoardData(bs);
        msg.ctrlMsg.addMessageElement(wtpBoard);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf  buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);


        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
    }          */




                /*








    @Test
    public void CapwapProtocolTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();

        CapwapTransportProtocol protocol = new CapwapTransportProtocol();
        protocol.setProtocol((byte) 2);
        msg.ctrlMsg.addMessageElement(protocol);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
     }
                  */










    /*
     @Test
    public void imageIdentifierTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();

        ImageIdentifier id = new ImageIdentifier();
        byte[] dummy = new byte[1024];
        id.setData(dummy);
        id.setVendorID(5);
        msg.ctrlMsg.addMessageElement(id);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
    }

      */
      
    @Test
    public void maxLenTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();

        MaxMsgLength l = new MaxMsgLength();
        l.setMaxLength(32000);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
    }   
    /*
    @Test
    public void VendorSpecificPayloadTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();
        VendorSpecificPayload vsa = new VendorSpecificPayload();
        vsa.setVendorId(119);
        vsa.setElementID(120);
        byte[] address = new byte[512];
        address[12] = 32;
        address[1] = 32;
        address[5] = 32;
        address[2] = 32;
        address[8] = 62;
        address[7] = 32;

        vsa.setData(address);

        msg.ctrlMsg.addMessageElement(vsa);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);

    }
             @Test
    public void statisticsTimerTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();
        StatisticsTimer timer = new StatisticsTimer();
        timer.setTimer(320);

        msg.ctrlMsg.addMessageElement(timer);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        ByteBuf buf = Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);
    }



   @Test
    public void messageHeaderTester() {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;

        msg = new ODLCapwapMessage();
        WtpFallBack fb = new WtpFallBack();
        fb.setMode(2);
        msg.ctrlMsg.addMessageElement(fb);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_JOIN_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        //

        msg.header.setMbit();
        msg.header.setFragId(2);
        msg.header.setFragOffset(8190);
        MacAddress mac = new MacAddress();
        byte[] address = new byte[6];
        address[0] = 12;
        address[1] = 52;
        address[2] = 32;
        address[3] = 42;
        address[4] = 72;
        address[5] = 92;

        mac.setAddress(address);
        msg.header.setRadioMacAddress(mac);

        WsiInfo wsi = new WsiInfo();
        msg.header.setWbit();

        byte[] wsiData = new byte[8];
        address[0] = 12;
        address[1] = 52;
        address[2] = 32;
        address[3] = 42;
        address[4] = 72;
        address[5] = 92;

        wsi.setData(wsiData);
        msg.header.setWsiInfo(wsi);

        ByteBuf buf= Unpooled.buffer();
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        ODLCapwapMessage n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        sender(buf);
        Assert.assertEquals(msg,n);

    }      */

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
}
