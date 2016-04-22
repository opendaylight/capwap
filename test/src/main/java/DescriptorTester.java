/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */


import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.*;
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

/**
 * Created by flat on 17/04/16.
 */
public class DescriptorTester {

    private static final Logger LOG = LoggerFactory.getLogger(DescriptorTester.class);

    public boolean ACDescriptorTest(ByteBuf buf) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

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

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        ODLCapwapMessage n = null;
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }


    public boolean  sessionIdTester(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }

    public boolean  wtpNameTester(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }



    public boolean wtpRadioInfoTester(ByteBuf buf) {

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


        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }




    public boolean WtpDesciptorTester(ByteBuf buf) {
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

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }


    public boolean frameTunnelModeTester(ByteBuf buf) {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();
        WtpFrameTunnelModeMsgElem ft = new WtpFrameTunnelModeMsgElem();
        ft.setnBit();
        msg.ctrlMsg.addMessageElement(ft);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);


        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }

    public boolean AcNameDescriptorTest(ByteBuf buf) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        ACName acName = null;

        msg = new ODLCapwapMessage();

        //create  Discovery Type

        acName = new ACName();
        acName.setName(new byte[] {'T','h','i','s',' ', 'i','s',',','m','y','n','a','m','e'});
        msg.ctrlMsg.addMessageElement(acName);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }



        return true;
    }

    public boolean resultCodeTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }



        return true;
    }
    public boolean ECNTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }



        return true;
    }

    public boolean WtpReootStatisticsTeaster(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }



        return true;
    }


    public boolean  IPV4ControlDescriptorTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }


        return true;
    }

    public boolean  localIPV4DescriptorTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }


        return true;
    }

    public boolean  IPV4ListDescriptorTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }


        return true;
    }


    public boolean DiscoveryDescriptorTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }


        return true;
    }

    public boolean WtpMacTypeDescriptorTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }


        return true;
    }

    public boolean WtpBoardDataDescriptorTest(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);


        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }


        return true;
    }


    public ByteBuf ACDescriptorTest() {
        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        ByteBuf buf = Unpooled.buffer();
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

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }


    public ByteBuf WtpBoardDataTester() {

        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        //create a Board data Dec
        WtpBoardDataMsgElem e = new WtpBoardDataMsgElem();
        e.setVendorId(12);
        BoardDataSubElem se = new BoardDataSubElem(12, 64);
        e.addBoardData(se);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DeleteMacAclEntryTester() {

        ODLCapwapMessage msg = null;
        DiscoveryType discoveryType = null;
        ACDescriptor acDescriptor = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        DeleteMacAclEntry e = new DeleteMacAclEntry();
        e.setMacAddrLength((short) 6);
        MacAddress mac = new MacAddress((short) 6);
        e.addMacAddress(mac);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DeleteStationTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        MacAddress mac = new MacAddress((short) 8);
        mac.address[0] = 12;
        mac.address[1] = 15;
        mac.address[2] = 12;
        mac.address[3] = 17;
        mac.address[4] = 12;
        mac.address[7] = 12;
        DeleteStation e = new DeleteStation((short) 12, mac);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }


    public ByteBuf DecryptionErrorReportTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        MacAddress mac = new MacAddress((short) 8);
        mac.address[0] = 12;
        mac.address[1] = 15;
        mac.address[2] = 12;
        mac.address[3] = 17;
        mac.address[4] = 12;
        mac.address[7] = 12;
        DecryptionErrorReport e = new DecryptionErrorReport();
        e.setRadioId((short) 1);
        e.addMacAddress(mac);

        msg.ctrlMsg.addMessageElement(e);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf ControlIPV4Tester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        IPV4Address ipv4 = new IPV4Address();
        byte[] address = new byte[]{1, 2, 3, 4};
        ipv4.setAddress(address);

        CapwapControlIPV4Addr ctrl = new CapwapControlIPV4Addr();
        ctrl.setIpv4(ipv4);
        ctrl.setWtpCount(23333);


        msg.ctrlMsg.addMessageElement(ctrl);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf LocalIPV4Tester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        IPV4Address ipv4 = new IPV4Address();
        byte[] address = new byte[]{1, 2, 3, 4};
        ipv4.setAddress(address);

        CapwapLocalIPV4Address ctrl = new CapwapLocalIPV4Address();
        ctrl.setAddress(address);

        msg.ctrlMsg.addMessageElement(ctrl);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf LocalIPV6Tester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();


        IPV6Address ipv6 = new IPV6Address();
        byte[] address = new byte[]{0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        ipv6.setAddress(address);

        CapwapLocalIPV6Address ctrl = new CapwapLocalIPV6Address();
        ctrl.setAddress(address);

        msg.ctrlMsg.addMessageElement(ctrl);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf CapwapTimerTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        CapwapTimers timers = new CapwapTimers();
        timers.setDiscovery((short) 255).setEchoReq((short) 0);
        msg.ctrlMsg.addMessageElement(timers);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public boolean CapwapProtocolTester(ByteBuf buf) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();

        CapwapTransportProtocol protocol = new CapwapTransportProtocol();
        protocol.setProtocol((byte) 2);
        msg.ctrlMsg.addMessageElement(protocol);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }

    public ByteBuf DataTransferDataTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DataTransferData data = new DataTransferData();
        data.setDataMode((short) 2).setDataType((short) 5);
        byte[] dummy = new byte[128];
        data.setData(dummy);
        msg.ctrlMsg.addMessageElement(data);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DataTransferModeTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DataTransferMode mode = new DataTransferMode();
        mode.setMode((byte) 2);
        msg.ctrlMsg.addMessageElement(mode);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DecryptionErrorReportPeriodTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DecryptionErrorPeriod period = new DecryptionErrorPeriod();
        period.setRadioId((short) 12).setReportInterval(36);
        msg.ctrlMsg.addMessageElement(period);


        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf DuplicateIPV4Tester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        DuplicateIPV4Addr duplicateIPV4Addr = new DuplicateIPV4Addr();
        //set IPV4 Adddress
        IPV4Address ipv4 = new IPV4Address();
        byte[] address = new byte[]{1, 2, 3, 4};
        ipv4.setAddress(address);

        //set
        MacAddress mac = new MacAddress((short) 8);
        mac.address[0] = 12;
        mac.address[1] = 15;
        mac.address[2] = 12;
        mac.address[3] = 17;
        mac.address[4] = 12;
        mac.address[7] = 12;
        duplicateIPV4Addr.setIpv4(ipv4).setMacAddress(mac).setStatus((short) 1);
        msg.ctrlMsg.addMessageElement(duplicateIPV4Addr);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf IdleTimeOutTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        IdleTimeOut timeOut = new IdleTimeOut();
        timeOut.setTimout(12345);

        msg.ctrlMsg.addMessageElement(timeOut);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf EcnTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ECNSupport ecn = new ECNSupport();
        ecn.setEcn((byte) 1);
        msg.ctrlMsg.addMessageElement(ecn);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf imageDataTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ImageData img = new ImageData();
        byte[] dummy = new byte[1024];
        img.setData(dummy);
        img.setDataType((byte) 5);
        msg.ctrlMsg.addMessageElement(img);
        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public boolean imageIdentifierTester(ByteBuf buf) {
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

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }

        return true;
    }

    public ByteBuf imageInfoTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ImageInformation id = new ImageInformation();
        byte[] dummy = new byte[16];
        id.setFileSize(12345);
        byte[] address = new byte[]{0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        id.setHash(address);
        msg.ctrlMsg.addMessageElement(id);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf initiateDownloadTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        InitiateDownload in = new InitiateDownload();
        msg.ctrlMsg.addMessageElement(in);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf locationDataTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        LocationData l = new LocationData();
        byte[] address = new byte[]{0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        l.setLocationData(address);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public boolean maxLenTester(ByteBuf buf) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();

        MaxMsgLength l = new MaxMsgLength();
        l.setMaxLength(32000);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }
        return true;
    }

    public ByteBuf radioAdminStateTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        RadioAdministrativeState l = new RadioAdministrativeState();
        l.setRadioId((short) 1);
        l.setAdminState((short) 2);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);

        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf radioOperationalStateTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        RadioOperationalState l = new RadioOperationalState();
        l.setRadioId((short) 1);
        l.setCause((short) 7);
        l.setState((short) 5);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }


    public ByteBuf resultCodeTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ResultCode l = new ResultCode();
        l.setResultCode(12345);

        msg.ctrlMsg.addMessageElement(l);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public ByteBuf returnedMsgElementTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();

        ReturnedMessageElement r = new ReturnedMessageElement();
        r.setReason((short) 1);
        byte[] address = new byte[]{0, 1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        r.setMsgElement(address);

        msg.ctrlMsg.addMessageElement(r);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }



    public boolean VendorSpecificPayloadTester(ByteBuf buf) {
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
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }



        return true;
    }

    public boolean statisticsTimerTester(ByteBuf buf) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage msg = null;
        ODLCapwapMessage n = null;

        msg = new ODLCapwapMessage();
        StatisticsTimer timer = new StatisticsTimer();
        timer.setTimer(320);

        msg.ctrlMsg.addMessageElement(timer);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short) 1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS for {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }



        return true;
    }


    public ByteBuf fallBackModeTester() {

        ODLCapwapMessage msg = null;

        ByteBuf buf = Unpooled.buffer();
        msg = new ODLCapwapMessage();
        WtpFallBack fb = new WtpFallBack();
        fb.setMode(2);
        msg.ctrlMsg.addMessageElement(fb);
        msg.ctrlMsg.addMessageElement(fb);
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


        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

    public boolean messageHederTester(ByteBuf buf) {
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


        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);

        ODLCapwapMessage n = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(msg, n)) {
            LOG.info("Decoding  SUCCESS {}:", getFunctionName(bTop));
            //encodeDecodeTester(msg,n);

        } else {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

            return false;
        }


        return true;
    }

    public void encodeDecodeTester(ODLCapwapMessage o, ODLCapwapMessage n) {

        //encode new message
        //decode new message
        //compare it new new message with old message
        ByteBuf buf = Unpooled.buffer();
        n.header.encodeHeader(buf);
        n.ctrlMsg.encode(buf);
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        ODLCapwapMessage nn = ODLCapwapMessageFactory.decodeFromByteArray(buf);
        if (compareMessage(o, nn)) {
            LOG.info("Decoding  SUCCESS {}:", getFunctionName(bTop));

        }
        {
            LOG.error("Decoding  failed {}:", getFunctionName(bTop));

        }


    }

    public boolean compareMessage(ODLCapwapMessage o, ODLCapwapMessage n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        //Compare header
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (n.header.getVersion() != o.header.getVersion()) {
            LOG.error("Version not equal o->{}   n ->{} , {}:", o.getMessageType(), n.getMessageType(), getFunctionName(bTop));
            return false;
        }

        if (n.header.getType() != n.header.getType()) {
            LOG.error("Version not equal o->{}   n ->{} , {}:", o.getMessageType(), n.getMessageType(), getFunctionName(bTop));
            return true;
        }

        if (n.header.getHlen() != o.header.getHlen()) {
            LOG.error("HLEN not equal o->{}   n ->{} , {}:", o.header.getHlen(), n.header.getHlen(), getFunctionName(bTop));
            return false;
        }
        if (n.header.getRid() != o.header.getRid()) {
            LOG.error("Rid not equal o->{}   n ->{} , {}:", o.header.getRid(), n.header.getRid(), getFunctionName(bTop));
            return false;
        }

        if (n.header.getWbid() != o.header.getWbid()) {
            LOG.error("Wbid not equal o->{}   n ->{} , {}:", o.header.getWbid(), n.header.getWbid(), getFunctionName(bTop));
            return false;
        }

        if (n.header.isSetTbit() != o.header.isSetTbit()) {
            LOG.error("TBit not equal o->{}   n ->{} , {}:", o.header.isSetTbit(), n.header.isSetTbit(), getFunctionName(bTop));
            return false;
        }
        if (n.header.isSetWbit() != o.header.isSetWbit()) {
            LOG.error("WBit not equal o->{}   n ->{} , {}:", o.header.isSetWbit(), n.header.isSetWbit(), getFunctionName(bTop));
            return false;
        }

        if (n.header.isSetMbit() != o.header.isSetMbit()) {
            LOG.error("TMit not equal o->{}   n ->{} , {}:", o.header.isSetMbit(), n.header.isSetMbit(), getFunctionName(bTop));
            return false;
        }

        if (n.header.isSetFbit() != o.header.isSetFbit()) {
            LOG.error("TFit not equal o->{}   n ->{} , {}:", o.header.isSetFbit(), n.header.isSetFbit(), getFunctionName(bTop));
            return false;
        }

        if (n.header.isSetKbit() != o.header.isSetKbit()) {
            LOG.error("KBit not equal o->{}   n ->{} , {}:", o.header.isSetKbit(), n.header.isSetKbit(), getFunctionName(bTop));
            return false;
        }
        if (n.header.isSetLbit() != o.header.isSetLbit()) {
            LOG.error("LBit not equal o->{}   n ->{} , {}:", o.header.isSetLbit(), n.header.isSetLbit(), getFunctionName(bTop));
            return false;
        }

        if (n.header.getFragId() != o.header.getFragId()) {
            LOG.error("FragID not equal o->{}   n ->{} , {}:", o.header.getFragId(), n.header.getFragId(), getFunctionName(bTop));
            return false;
        }

        if (n.header.getFragOffset() != o.header.getFragOffset()) {
            LOG.error("FragOff Set not equal o->{}   n ->{} , {}:", o.header.getFragOffset(), n.header.getFragOffset(), getFunctionName(bTop));
            return false;
        }

        if (o.header.isSetMbit()) {
            boolean result = compareMacAddress(o.header.getRadioMacAddress(), n.header.getRadioMacAddress());
            if (result == false) {
                LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
                return false;
            }
        }
        if (o.header.isSetWbit()) {
            boolean result = compareWsiMac(o.header.getWsiInfo(), n.header.getWsiInfo());
            if (result == false) {
                LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
                return false;
            }
        }

        if (o.ctrlMsg != null) {
            boolean result = compareCtrlMsg(o.ctrlMsg, n.ctrlMsg);
            if (result == false) {
                LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
                return false;
            }
        }
        //compare Message
        return true;
    }

    // final StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];
    public String getFunctionName(StackTraceElement e) {
        final StackTraceElement aTop = e;
        return (aTop.getMethodName());

    }


    boolean compareCtrlMsg(ODLCapwapControlMessage o, ODLCapwapControlMessage n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }
        //Compare Message Type
        if (o.getMsgType() != n.getMsgType()) {
            LOG.error("Message Type not equal o->{}   n ->{} , {}:", o.getMsgType(), n.getMsgType(), getFunctionName(bTop));
            return false;
        }

        //Compare Message Length
        if (o.getMsgLen() != n.getMsgLen()) {
            LOG.error("Message Len not equal o->{}   n ->{} , {}:", o.getMsgLen(), n.getMsgLen(), getFunctionName(bTop));

            return false;
        }

        //Compare Message SeqNum
        if (o.getSeqNo() != n.getSeqNo()) {
            LOG.error("SeqNo not equal o->{}   n ->{} , {}:", o.getSeqNo(), n.getSeqNo(), getFunctionName(bTop));
            return false;
        }

        boolean result = compareMsgElements(o, n);
        if (!result) {
            LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
            return false;

        }

        //Compare Message Elements
        return true;
    }


    public boolean allDescriptorTester(int dType) {
        ByteBuf buf = Unpooled.buffer();
        boolean result = false;
        switch (dType) {
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_NAME:
                result = AcNameDescriptorTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_CONTROL_IPV4_ADDR:
                result = IPV4ControlDescriptorTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_RESULT_CODE:
                result = resultCodeTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV4_ADDR:
                result =localIPV4DescriptorTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_ECN_SUPPORT:
                result = ECNTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_SESSION_ID:
                result = sessionIdTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_NAME:
                result = wtpNameTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_STATISTICS_TIMER:
                result = statisticsTimerTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_MAX_MESSAGE_LENGTH:
                result = maxLenTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_REBOOT_STATS:
                result = WtpReootStatisticsTeaster(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_IPV4_LIST:
                result = IPV4ListDescriptorTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_TRANSPORT_PROTO:
                result = CapwapProtocolTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_IMAGE_IDENTIFIER:
                result = imageIdentifierTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_VENDOR_SPECIFIC_PAYLOAD:
                result = VendorSpecificPayloadTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR:
                result = WtpDesciptorTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DISCOVERY_TYPE:
                result = DiscoveryDescriptorTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;

            case ODLCapwapConsts.IEEE_80211_WTP_RADIO_INFORMATION:
                result = wtpRadioInfoTester(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_DESCRIPTOR:
                result = ACDescriptorTest(buf);
                if (!result) {

                    return false;
                }
                sender(buf);
                break;
            default:
        }

        return true;
    }

    boolean compareMsgElements(ODLCapwapControlMessage o, ODLCapwapControlMessage n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        Iterator<ODLCapwapMessageElement> itr = n.getElements().iterator();
        for (ODLCapwapMessageElement e_o : o.getElements()) {
            ODLCapwapMessageElement e_n = itr.next();
            boolean result = compareEachMessageElement(e_o, e_n);
            if (!result) {
                LOG.error("Comparison of message elements failed {}", getFunctionName(bTop));

            }
        }
        //Compare Message Elements
        return true;
    }


    boolean compareEachMessageElement(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        boolean result = false;
        switch (o.getType()) {
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DISCOVERY_TYPE:
                result = compareDiscoveryType(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_NAME:
                result = compareAcNameType(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;


            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_BOARD_DATA:
                result = compareWtpBoard(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR:
                result = compareWtpDescriptor(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_FRAME_TUNNEL_MODE:
                result = compareFrameTunnelDescriptor(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }

                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_MAC_TYPE:
                result = compareWtpMacTypeDescriptor(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }

                break;
            case ODLCapwapConsts.IEEE_80211_WTP_RADIO_INFORMATION:
                result = compareWtpRadioInfoTypeDescriptor(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }

                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_DESCRIPTOR:
                result = compareAcDescriptor(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }


                break;


            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_CONTROL_IPV4_ADDR:
                result = compareIPV4DescType(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_RESULT_CODE:
                result = compareResultCode(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_SESSION_ID:
                result = compareSessionID(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_NAME:
                result = compareWtpName(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_STATISTICS_TIMER:
                result = compareStatisticsTimer(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_MAX_MESSAGE_LENGTH:
                result = compareMaxMessageLength(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_REBOOT_STATS:
                result = compareWtpRebootStatistics(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_IPV4_LIST:
                result = compareIPV4List(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_TRANSPORT_PROTO:
                result = compareTransport(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_IMAGE_IDENTIFIER:
                result = compareImageId(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_VENDOR_SPECIFIC_PAYLOAD:
                result = compareVSA(o, n);
                if (!result) {
                    LOG.error(" Error in comparing Message Elements  {} {}", getFunctionName(bTop), result);
                    return false;

                }
                break;




            default:

        }
        return true;
    }

    boolean compareAcDescriptor(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        ACDescriptor oo = (ACDescriptor) o;
        ACDescriptor nn = (ACDescriptor) n;

        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        if (oo.getStations() != nn.getStations()) {

            LOG.error(" getStations    {} {} {}  ", oo.getStations(), nn.getStations(), getFunctionName(bTop));
            return false;
        }
        if (oo.getLimit() != nn.getLimit()) {

            LOG.error(" getLimit     {} {} {}  ", oo.getLimit(), nn.getLimit(), getFunctionName(bTop));
            return false;
        }
        if (oo.getActiveWtps() != nn.getActiveWtps()) {

            LOG.error(" getActiveWtps    {} {} {}  ", oo.getActiveWtps(), nn.getActiveWtps(), getFunctionName(bTop));
            return false;
        }
        if (oo.getMaxWtps() != nn.getMaxWtps()) {

            LOG.error(" getMaxWtps     {} {} {}  ", oo.getMaxWtps(), nn.getMaxWtps(), getFunctionName(bTop));
            return false;
        }
        if (oo.getSecurity() != nn.getSecurity()) {

            LOG.error(" getSecurity     {} {} {}  ", oo.getSecurity(), nn.getSecurity(), getFunctionName(bTop));
            return false;
        }

        if (oo.getRmac() != nn.getRmac()) {

            LOG.error(" getRmac     {} {} {}  ", oo.getRmac(), nn.getRmac(), getFunctionName(bTop));
            return false;
        }

        if (oo.getDtlsPolicy() != nn.getDtlsPolicy()) {

            LOG.error(" getDtlsPolicy   {} {} {}  ", oo.getDtlsPolicy(), nn.getDtlsPolicy(), getFunctionName(bTop));
            return false;
        }

        boolean result = compareAcInformationSubElements(oo.getAcInfolist(), nn.getAcInfolist());
        if (!result) {
            LOG.error(" Comparing Descriptor Subelements failed  {} {}", getFunctionName(bTop), result);
            return false;
        }





        return true;
    }

    boolean compareAcInformationSubElements(ArrayList<ACInformationSubElement> o, ArrayList<ACInformationSubElement> n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));
            return false;
        }
        Iterator<ACInformationSubElement> itr = n.iterator();
        for (ACInformationSubElement e_o : o) {
            ACInformationSubElement e_n = itr.next();
            boolean result = compareEachAcInformationSubElement(e_o, e_n);
            if (!result) {
                LOG.error("Comparison of Descriptor sub elements failed {}", getFunctionName(bTop));
                return false;
            }
        }


        return true;
    }


    private boolean compareEachAcInformationSubElement(ACInformationSubElement o, ACInformationSubElement n) {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }
        if (o.getAcInfoType() != n.getAcInfoType()) {
            LOG.error(" Vendor ID    o->  {} n->{} {}  ", o.getAcInfoType(), n.getAcInfoType(), getFunctionName(bTop));

        }
        if (o.getAcInfoLength() != n.getAcInfoLength()) {
            LOG.error(" Descriptor Type    o->  {} n->{} {}  ", o.getAcInfoLength(), n.getAcInfoLength(), getFunctionName(bTop));
        }
        if (o.getAcInfoVendorId() != n.getAcInfoVendorId()) {
            LOG.error(" Descriptor Type    o->  {} n->{} {}  ", o.getAcInfoLength(), n.getAcInfoLength(), getFunctionName(bTop));
        }

        if (o.getAcInfoLength() > 0) {
            boolean result = compareByteArray(o.getAcInfoData(), n.getAcInfoData());
            if (result == false) {
                LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
                return false;
            }
        }
        return true;
    }

    boolean compareWtpRadioInfoTypeDescriptor(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        WTP_Radio_Information  oo = (WTP_Radio_Information) o;
        WTP_Radio_Information nn = (WTP_Radio_Information) n;

        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }
        if (oo.getRadioId() != nn.getRadioId()) {

            LOG.error(" getRadioId     {} {} {}  ", oo.getRadioId(), nn.getRadioId(), getFunctionName(bTop));
            return false;
        }

        if (oo.is802_11aSet() != nn.is802_11aSet()) {

            LOG.error(" getRadioId     {} {} {}  ", oo.is802_11aSet(), nn.is802_11aSet(), getFunctionName(bTop));
            return false;
        }

        if (oo.is802_11bSet() != nn.is802_11bSet()) {

            LOG.error(" getRadioId     {} {} {}  ", oo.is802_11bSet(), nn.is802_11bSet(), getFunctionName(bTop));
            return false;
        }
        if (oo.is802_11gSet() != nn.is802_11gSet()) {

            LOG.error(" getRadioId     {} {} {}  ", oo.is802_11gSet(), nn.is802_11gSet(), getFunctionName(bTop));
            return false;
        }

        if (oo.is802_11nSet() != nn.is802_11nSet()) {

            LOG.error(" getRadioId     {} {} {}  ", oo.is802_11nSet(), nn.is802_11nSet(), getFunctionName(bTop));
            return false;
        }


        return true;
    }

    boolean compareWtpMacTypeDescriptor(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        WtpMacTypeMsgElem oo = (WtpMacTypeMsgElem) o;
        WtpMacTypeMsgElem nn = (WtpMacTypeMsgElem) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }
        if (oo.getmacType() != nn.getmacType()) {

            LOG.error(" eBitMisMatch     {} {} {}  ", oo.getmacType(), nn.getmacType(), getFunctionName(bTop));
            return false;
        }
        return true;
    }

    boolean compareSessionID(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        SessionID oo = (SessionID) o;
        SessionID nn = (SessionID) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        boolean result = compareByteArray(oo.getSessionid(), nn.getSessionid());
        if (result == false) {
            LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
           return false;
        }

        return true;
    }

    boolean compareWtpName(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        WTPName oo = (WTPName) o;
        WTPName nn = (WTPName) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }



        boolean result = compareByteArray(oo.getName(), nn.getName());
        if (result == false) {
            LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
            return false;
        }

        return true;
    }

    boolean compareStatisticsTimer(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        StatisticsTimer oo = (StatisticsTimer) o;
        StatisticsTimer nn = (StatisticsTimer) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        if (oo.getTimer() != nn.getTimer()) {

            LOG.error(" lBitMisMatch     {} {} {}  ", oo.getTimer(), nn.getTimer(), getFunctionName(bTop));
            return false;
        }


        return true;
    }


    boolean compareMaxMessageLength(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        MaxMsgLength oo = (MaxMsgLength) o;
        MaxMsgLength nn = (MaxMsgLength) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        if (oo.getMaxLength() != nn.getMaxLength()) {

            LOG.error(" getLength     {} {} {}  ", oo.getMaxLength(), nn.getMaxLength(), getFunctionName(bTop));
            return false;
        }

        return true;
    }

    boolean compareTransport(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        CapwapTransportProtocol oo = (CapwapTransportProtocol) o;
        CapwapTransportProtocol nn = (CapwapTransportProtocol) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        if (oo.getProtocol() != nn.getProtocol()) {

            LOG.error(" getProtocol     {} {} {}  ", oo.getProtocol(), nn.getProtocol(), getFunctionName(bTop));
            return false;
        }

        return true;
    }

    boolean compareWtpRebootStatistics(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        WTPRebootStatistics oo = (WTPRebootStatistics) o;
        WTPRebootStatistics nn = (WTPRebootStatistics) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        if (oo.getAcInitiated() != nn.getRebootCount()) {

            LOG.error(" getAcInitiated     {} {} {}  ", oo.getRebootCount(), nn.getRebootCount(), getFunctionName(bTop));
            return false;
        }

        if (oo.getAcInitiated() != nn.getAcInitiated()) {

            LOG.error(" getAcInitiated     {} {} {}  ", oo.getAcInitiated(), nn.getAcInitiated(), getFunctionName(bTop));
            return false;
        }

        if (oo.getLinkFailure() != nn.getLinkFailure()) {

            LOG.error(" getLinkFailure     {} {} {}  ", oo.getLinkFailure(), nn.getLinkFailure(), getFunctionName(bTop));
            return false;
        }

        if (oo.getSoftwareFailure() != nn.getSoftwareFailure()) {

            LOG.error(" getSoftwareFailure     {} {} {}  ", oo.getSoftwareFailure(), nn.getSoftwareFailure(), getFunctionName(bTop));
            return false;
        }
        if (oo.getHwFailure() != nn.getHwFailure()) {

            LOG.error(" getHwFailure     {} {} {}  ", oo.getHwFailure(), nn.getHwFailure(), getFunctionName(bTop));
            return false;
        }
        if (oo.getOtherFailure() != nn.getOtherFailure()) {

            LOG.error(" getOtherFailure    {} {} {}  ", oo.getOtherFailure(), nn.getOtherFailure(), getFunctionName(bTop));
            return false;
        }
        if (oo.getUnKnownFailure() != nn.getUnKnownFailure()) {

            LOG.error(" getUnKnownFailure     {} {} {}  ", oo.getUnKnownFailure(), nn.getUnKnownFailure(), getFunctionName(bTop));
            return false;
        }
        if (oo.getLastFailureType() != nn.getLastFailureType()) {

            LOG.error(" getLastFailureType     {} {} {}  ", oo.getLastFailureType(), nn.getLastFailureType(), getFunctionName(bTop));
            return false;
        }

        return true;
    }

    boolean compareImageId(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        ImageIdentifier oo = (ImageIdentifier) o;
        ImageIdentifier nn = (ImageIdentifier) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        if (oo.getVendorID() != nn.getVendorID()) {

            LOG.error(" getVendorID     {} {} {}  ", oo.getVendorID(), nn.getVendorID(), getFunctionName(bTop));
            return false;
        }


        boolean result = compareByteArray(oo.getData(), nn.getData());
        if (!result) {
            LOG.error(" compareImageId data failed  {} {}", getFunctionName(bTop), result);
            return false;

        }
        return true;

    }

    boolean compareVSA(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        VendorSpecificPayload oo = (VendorSpecificPayload) o;
        VendorSpecificPayload nn = (VendorSpecificPayload) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        if (oo.getElementID() != nn.getElementID()) {

            LOG.error(" getElementID     {} {} {}  ", oo.getElementID(), nn.getElementID(), getFunctionName(bTop));
            return false;
        }

        if (oo.getVendorId() != nn.getVendorId()) {

            LOG.error(" getVendorIdD     {} {} {}  ", oo.getVendorId(), nn.getVendorId(), getFunctionName(bTop));
            return false;
        }



        boolean result = compareByteArray(oo.getData(), nn.getData());
        if (!result) {
            LOG.error(" compareImageId data failed  {} {}", getFunctionName(bTop), result);
            return false;

        }
        return true;

    }



    boolean compareIPV4List(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        IPV4AddrList oo = (IPV4AddrList) o;
        IPV4AddrList nn = (IPV4AddrList) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }

        boolean result = compareIPV4ListElements(oo.getList(), nn.getList());
        if (!result) {
            LOG.error(" Comparing Board Subelements failed  {} {}", getFunctionName(bTop), result);
            return false;

        }
        return true;

    }

    private boolean compareIPV4ListElements(ArrayList<IPV4Address> o, ArrayList<IPV4Address> n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));
            return false;
        }
        Iterator<IPV4Address> itr = n.iterator();
        for (IPV4Address e_o : o) {
            IPV4Address e_n = itr.next();
            boolean result = compareByteArray(e_o.getAddress(), e_n.getAddress());
            if (!result) {
                LOG.error("compareIPV4ListElementssub elements failed {}", getFunctionName(bTop));
                return false;
            }
        }
        return true;
    }


    boolean compareFrameTunnelDescriptor(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        WtpFrameTunnelModeMsgElem oo = (WtpFrameTunnelModeMsgElem) o;
        WtpFrameTunnelModeMsgElem nn = (WtpFrameTunnelModeMsgElem) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }
        if (oo.iseBitSet() != nn.iseBitSet()) {

            LOG.error(" eBitMisMatch     {} {} {}  ", oo.iseBitSet(), nn.iseBitSet(), getFunctionName(bTop));
            return false;
        }
        if (oo.islBitSet() != nn.islBitSet()) {

            LOG.error(" lBitMisMatch     {} {} {}  ", oo.islBitSet(), nn.islBitSet(), getFunctionName(bTop));
            return false;
        }

        if (oo.isnBitSet() != nn.isnBitSet()) {

            LOG.error(" nBitMisMatch     {} {} {}  ", oo.isnBitSet(), nn.isnBitSet(), getFunctionName(bTop));
            return false;
        }

        if (oo.isrBitSet() != nn.isrBitSet()) {

            LOG.error(" rBitMisMatch     {} {} {}  ", oo.isrBitSet(), nn.isrBitSet(), getFunctionName(bTop));
            return false;
        }




        return true;
    }

    boolean compareWtpDescriptor(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        WtpDescriptor oo = (WtpDescriptor) o;
        WtpDescriptor nn = (WtpDescriptor) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {

            LOG.error(" Wrong Type     {} {} {}  ", oo.getType(), nn.getType(), getFunctionName(bTop));
            return false;
        }
        if (oo.getMaxRadios() != nn.getMaxRadios()) {

            LOG.error(" Wrong Max Radios     {} {} {}  ", oo.getMaxRadios(), nn.getMaxRadios(), getFunctionName(bTop));
            return false;
        }
        if (oo.getRadioInUse() != nn.getRadioInUse()) {

            LOG.error(" Wrong    o-> {} n->{} : {}  ", oo.getRadioInUse(), nn.getRadioInUse(), getFunctionName(bTop));
            return false;
        }
        if (oo.getNumEncrypt() != nn.getNumEncrypt()) {

            LOG.error(" Wrong    o-> {} n->{} : {}  ", oo.getNumEncrypt(), nn.getNumEncrypt(), getFunctionName(bTop));
            return false;
        }
        boolean result = compareEncryptionSubElements(oo.getEncysubElemList(), nn.getEncysubElemList());
        if (!result) {
            LOG.error(" Comparing Encryption Subelements failed  {} {}", getFunctionName(bTop), result);
            return false;
        }
        result = false;
        result = compareDescriptorSubElements(oo.getDescSubElemList(), nn.getDescSubElemList());
        if (!result) {
            LOG.error(" Comparing Descriptor Subelements failed  {} {}", getFunctionName(bTop), result);
            return false;
        }

        return true;
    }

    private boolean compareDescriptorSubElements(ArrayList<DescriptorSubElement> o, ArrayList<DescriptorSubElement> n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));
            return false;
        }
        Iterator<DescriptorSubElement> itr = n.iterator();
        for (DescriptorSubElement e_o : o) {
            DescriptorSubElement e_n = itr.next();
            boolean result = compareEachDescriptorSubElement(e_o, e_n);
            if (!result) {
                LOG.error("Comparison of Descriptor sub elements failed {}", getFunctionName(bTop));
                return false;
            }
        }


        return true;
    }


    private boolean compareEachDescriptorSubElement(DescriptorSubElement o, DescriptorSubElement n) {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }
        if (o.getVendorId() != n.getVendorId()) {
            LOG.error(" Vendor ID    o->  {} n->{} {}  ", o.getVendorId(), n.getVendorId(), getFunctionName(bTop));

        }
        if (o.getDescType() != n.getDescType()) {
            LOG.error(" Descriptor Type    o->  {} n->{} {}  ", o.getDescType(), n.getDescType(), getFunctionName(bTop));
        }
        if (o.getDescLength() != n.getDescLength()) {
            LOG.error(" Descriptor Type    o->  {} n->{} {}  ", o.getDescLength(), n.getDescLength(), getFunctionName(bTop));
        }

        if (o.getDescLength() > 0) {
            boolean result = compareByteArray(o.getDescData(), n.getDescData());
            if (result == false) {
                LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
                return false;
            }
        }
        return true;
    }


    private boolean compareEncryptionSubElements(ArrayList<EncryptionSubElement> o, ArrayList<EncryptionSubElement> n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));
            return false;
        }
        Iterator<EncryptionSubElement> itr = n.iterator();
        for (EncryptionSubElement e_o : o) {
            EncryptionSubElement e_n = itr.next();
            boolean result = compareEachEncryptionElement(e_o, e_n);
            if (!result) {
                LOG.error("Comparison of individual Board Sub elements failed {}", getFunctionName(bTop));
                return false;
            }
        }


        return true;
    }

    private boolean compareEachEncryptionElement(EncryptionSubElement o, EncryptionSubElement n) {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }
        if (o.getWbid() != n.getWbid()) {
            LOG.error(" WBID    o->  {} n->{} {}  ", o.getWbid(), n.getWbid(), getFunctionName(bTop));

        }
        if (o.getEncryptioCapability() != n.getEncryptioCapability()) {
            LOG.error(" Encryption Capability    o->  {} n->{} {}  ", o.getEncryptioCapability(), n.getEncryptioCapability(), getFunctionName(bTop));
        }
        return true;
    }


    boolean compareWtpBoard(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];

        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        WtpBoardDataMsgElem oo = (WtpBoardDataMsgElem) o;
        WtpBoardDataMsgElem nn = (WtpBoardDataMsgElem) n;
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        if (oo.getType() != nn.getType()) {
            LOG.error(" Wrong Message Type   {} ", getFunctionName(bTop));
            return false;
        }

        if (oo.getVendorId() != nn.getVendorId()) {
            LOG.error(" Wrong VendorID     {} {} {}  ", oo.getVendorId(), nn.getVendorId(), getFunctionName(bTop));
            return false;
        }

        boolean result = compareBoardSubElements(oo.getBoardDataList(), nn.getBoardDataList());
        if (!result) {
            LOG.error(" Comparing Board Subelements failed  {} {}", getFunctionName(bTop), result);
            return false;

        }
        return true;
    }

    boolean compareBoardSubElements(ArrayList<BoardDataSubElem> o, ArrayList<BoardDataSubElem> n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }

        Iterator<BoardDataSubElem> itr = n.iterator();
        for (BoardDataSubElem e_o : o) {
            BoardDataSubElem e_n = itr.next();
            boolean result = compareEachBaordSubElement(e_o, e_n);
            if (!result) {
                LOG.error("Comparison of individual Board Sub elements failed {}", getFunctionName(bTop));

            }
        }
        //Compare Message Elements
        return true;
    }


    private boolean compareEachBaordSubElement(BoardDataSubElem o, BoardDataSubElem n) {
        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
        if (o == null) {
            LOG.error("o is null {}", getFunctionName(bTop));

            return false;
        }
        if (n == null) {
            LOG.error("n is null {}", getFunctionName(bTop));
            return false;
        }
        if(o.getBoardDataType() != n.getBoardDataType()){
            LOG.error(" Wrong board type     {} {} {}  ", o.getBoardDataType(), n.getBoardDataType(), getFunctionName(bTop));

        }
        return true;
    }


    boolean compareDiscoveryType(ODLCapwapMessageElement o, ODLCapwapMessageElement n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        DiscoveryType oo= (DiscoveryType) o;
        DiscoveryType nn= (DiscoveryType) n;
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        if(oo.getMsgElemeType() != nn.getMsgElemeType()){
            LOG.error(" Wrong Message Type   {} ",getFunctionName(bTop));
            return false;
        }

        if(oo.getType() != nn.getType()){
            LOG.error(" Wrong Discovery  Type   {} {} {}  ",oo.getType(),nn.getType(),getFunctionName(bTop));
            return false;
        }
        return true;
    }

    boolean compareIPV4DescType(ODLCapwapMessageElement o, ODLCapwapMessageElement n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        CapwapControlIPV4Addr oo= (CapwapControlIPV4Addr) o;
        CapwapControlIPV4Addr nn= (CapwapControlIPV4Addr) n;
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        if(oo.getType() != nn.getType()){
            LOG.error(" Wrong Mes Elem Type   {} ",getFunctionName(bTop));
            return false;
        }

        if(oo.getIpv4() !=null){
            boolean result =  compareByteArray(oo.getIpv4().getAddress(),nn.getIpv4().getAddress());
            if (result ==false) {
                LOG.error(" Error in comparing IPV4 address {} {} ", getFunctionName(bTop), result);
                return false;
            }
        }


        return true;
    }

    boolean compareLocalIPV4DescType(ODLCapwapMessageElement o, ODLCapwapMessageElement n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        CapwapLocalIPV4Address oo= (CapwapLocalIPV4Address) o;
        CapwapLocalIPV4Address nn= (CapwapLocalIPV4Address) n;
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        if(oo.getType() != nn.getType()){
            LOG.error(" Wrong Mes Elem Type   {} ",getFunctionName(bTop));
            return false;
        }

        if(oo.getAddress() !=null){
            boolean result =  compareByteArray(oo.getAddress(),nn.getAddress());
            if (result ==false) {
                LOG.error(" Error in comparing IPV4 address {} {} ", getFunctionName(bTop), result);
                return false;
            }
        }


        return true;
    }


    boolean compareResultCode(ODLCapwapMessageElement o, ODLCapwapMessageElement n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        ResultCode oo= (ResultCode) o;
        ResultCode nn= (ResultCode) n;
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        if(oo.getType() != nn.getType()){
            LOG.error(" Wrong Mes Elem Type   {} ",getFunctionName(bTop));
            return false;
        }

        if(oo.getResultCode() != nn.getResultCode()){
            LOG.error(" Result Code mismatch    {} {} {}  ",oo.getType(),nn.getType(),getFunctionName(bTop));
            return false;
        }

        return true;
    }

    boolean compareEcn(ODLCapwapMessageElement o, ODLCapwapMessageElement n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        ECNSupport oo= (ECNSupport) o;
        ECNSupport nn= (ECNSupport) n;
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        if(oo.getType() != nn.getType()){
            LOG.error(" Wrong Mes Elem Type   {} ",getFunctionName(bTop));
            return false;
        }

        if(oo.getEcn() != nn.getEcn()){
            LOG.error(" ECN mismatch    {} {} {}  ",oo.getType(),nn.getType(),getFunctionName(bTop));
            return false;
        }

        return true;
    }



    boolean compareAcNameType(ODLCapwapMessageElement o, ODLCapwapMessageElement n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        ACName  oo= (ACName) o;
        ACName nn= (ACName) n;
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        if(oo.getType() != nn.getType()){
            LOG.error(" Wrong Message Type   {} ",getFunctionName(bTop));
            return false;
        }

        if(oo.getLength() != nn.getLength()){
            LOG.error(" Wrong Length  Type   {} {} {}  ",oo.getType(),nn.getType(),getFunctionName(bTop));
            return false;
        }

        if(oo.getLength()>0){
            boolean result =  compareByteArray(oo.getName(),nn.getName());
            if (result ==false) {
                LOG.error(" Error in  {} {} ", getFunctionName(bTop), result);
                return false;
            }
        }
        return true;
    }





    boolean compareMacAddress(MacAddress o, MacAddress n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        if(o.getLength() != n.getLength()){
            LOG.error("Length not equal o->{}   n ->{} , {}:",o.getLength(),n.getLength(),getFunctionName(bTop));
            return false;
        }

        if(o.getLength()>0){
            boolean result =  compareByteArray(o.getAddress(),n.getAddress());
            if (result ==false) {
                LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
                return false;
            }
        }

        return true;
    }

    boolean compareWsiMac(WsiInfo o, WsiInfo n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }
        if(o.getLength() != n.getLength()){
            LOG.error("Length not equal o->{}   n ->{} , {}:",o.getLength(),n.getLength(),getFunctionName(bTop));
            return false;
        }

        if(o.getLength()>0){
            boolean result = compareByteArray(o.getData(),n.getData());
            if (result ==false) {
                LOG.error(" Error in  {} {}", getFunctionName(bTop), result);
                return false;
            }


        }

        return true;
    }



    boolean compareByteArray(byte [] o, byte[] n){
        StackTraceElement bTop = Thread.currentThread ().getStackTrace ()[1];

        if(o==null){
            LOG.error("o is null {}",getFunctionName(bTop));

            return false;
        }
        if(n==null){
            LOG.error("n is null {}",getFunctionName(bTop));
            return false;
        }

        for (int i=0;i<o.length;i++){
            if(o[i] !=n[i]){
                LOG.error("Byte Array Content @ index {} o->{}   n ->{} , {}:",i,o[i],n[i],getFunctionName(bTop));
                return false;
            }
        }
        return true;
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

    boolean discoveryTypeEncodeDecodeTest(){
        //Encode
        //Decode
        //ODLCapwapMessageFactory.decodeFromByteArray(buf);
        //Compare
        return true;
    }


    public static void main(String args[]) {


        DescriptorTester tester = new DescriptorTester();
        ByteBuf buf = null;

        //buf = Unpooled.buffer();
        //buf = tester.ACDescriptorTest();
        //tester.sender(buf);

        //buf =  tester.WtpDesciptorTester();
        //tester.sender(buf);

        // buf =  tester.WtpBoardDataTester();
        //tester.sender(buf);

       //  buf =  tester.DeleteMacAclEntryTester();
        //tester.sender(buf);

        //buf =  tester.DeleteStationTester();
        //tester.sender(buf);

        //buf =  tester.DecryptionErrorReportTester();
        //tester.sender(buf);

        //buf = tester.ControlIPV4Tester();
        //tester.sender(buf);

        //buf = tester.LocalIPV4Tester();
        //tester.sender(buf);

        //buf = tester.LocalIPV6Tester();
        //tester.sender(buf);

        //buf = tester.CapwapTimerTester();
        //tester.sender(buf);

        //buf = tester.CapwapProtocolTester();
        //tester.sender(buf);

        //buf = tester.DataTransferDataTester();
        //tester.sender(buf);

        //buf = tester.DataTransferModeTester();
        //tester.sender(buf);

        //buf = tester.DecryptionErrorReportPeriodTester();
        //tester.sender(buf);


        //buf = tester.DuplicateIPV4Tester();
        //tester.sender(buf);

        //buf = tester.IdleTimeOutTester();
        //tester.sender(buf);

       // buf = tester.EcnTester();
        //tester.sender(buf);

        //buf = tester.imageDataTester();
        //tester.sender(buf);

        //buf = tester.imageIdentifierTester();
        // tester.sender(buf);
        //buf = tester.imageInfoTester();
        //tester.sender(buf);

        //buf = tester.initiateDownloadTester();
        //tester.sender(buf);

       // buf = tester.locationDataTester();
        //tester.sender(buf);

       // buf = tester.maxLenTester();
        //tester.sender(buf);

        //buf = tester.radioAdminStateTester();
        //tester.sender(buf);

        //buf = tester.radioOperationalStateTester();
        //tester.sender(buf);

       // buf = tester.resultCodeTester();
        //tester.sender(buf);

        //buf = tester.returnedMsgElementTester();
        //tester.sender(buf);

        //buf = tester.sessionIdTester();
        //tester.sender(buf);

       // buf = tester.statisticsTimerTester();
        //tester.sender(buf);

        //buf = tester.VendorSpecificPayloadTester();
        //tester.sender(buf);

        //buf = tester.fallBackModeTester();
        //tester.messageHederTester(buf);
        //tester.sender(buf);


        //tester.WtpDesciptorTester(buf);
        //tester.sender(buf);
        //tester.DiscoveryDescriptorTest(buf);
       // buf=tester.fallBackModeTester();
        //tester.sender(buf);
        //buf = Unpooled.buffer();
        //tester.wtpRadioInfoTester(buf);
        //tester.sender(buf);

        //tester.ACDescriptorTest(buf);
        //tester.sender(buf);

        int input = 0;
        Scanner sc=new Scanner(System.in);
        while(input != -1){
            System.out.printf("\nEnter the descriptor number , enter \"-1\" to exit\n");
            input = sc.nextInt();
            if (input == -1) break;
            tester.allDescriptorTester(input);


        }

    }
}
