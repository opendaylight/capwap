/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.msgelements.*;
import org.opendaylight.capwap.msgelements.subelem.*;
import org.opendaylight.capwap.utils.ByteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ODLCapwapMessageElementFactory {

    private static final Logger LOG = LoggerFactory.getLogger(ODLCapwapMessageElementFactory.class);

    static public MacAddress decodeMacAddress(ByteBuf buf , int length){
        MacAddress mac = null;
        byte []address = null;

        mac= new MacAddress();
        //Decode Length
        mac.setLength(ByteManager.unsignedByteToshort(buf.readByte()));
        address = new byte[mac.getLength()];
        buf.readBytes(address);
        mac.setAddress(address);
        return mac;
    }

    static public WsiInfo decodeWsiInfo(ByteBuf buf , int length){
        WsiInfo wsi= null;
        byte [] data = null;

        wsi = new WsiInfo();
        //Decode Length
        wsi.setLength(ByteManager.unsignedByteToshort(buf.readByte()));
        data = new byte[wsi.getLength()];
        buf.readBytes(data);
        wsi.setData(data);
        return wsi;
    }

    static public DiscoveryType decodeDiscoveryType(ByteBuf buf , int length){
        if(buf==null){
            LOG.error("ByteBuf null decodeDiscoveryType ");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable");
            return null;
        }

        DiscoveryType discoveryType= new DiscoveryType();
        discoveryType.setType(buf.readByte());
        return discoveryType;
    }

    static public CapwapControlIPV4Addr decodeControlIPV4Address(ByteBuf buf , int length){
        if(buf==null){
            LOG.error("ByteBuf null decodeControlIPV4Address");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable");
            return null;
        }
        CapwapControlIPV4Addr ipv4Addr = null;
        ipv4Addr = new CapwapControlIPV4Addr();
        ipv4Addr.setIpv4(decodeIPV4Addr(buf,4));
        byte [] count = new byte [] {0,0};
        ipv4Addr.setWtpCount(ByteManager.byteArrayToUnsingedShort(count));
        return ipv4Addr;

    }

    static public ResultCode decodeResultCode (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeControlIPV4Address");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        ResultCode resultCode = new ResultCode();
        byte [] code = new byte [] {0,0,0,0};
        buf.readBytes(code);
        resultCode.setResultCode(ByteManager.byteArrayToUnsingedInt(code));

        return resultCode;
    }
    static public CapwapLocalIPV4Address decodeLocalIPV4 (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeControlIPV4Address");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        CapwapLocalIPV4Address addr = new CapwapLocalIPV4Address();
        byte [] ipV4 = new byte [] {0,0,0,0};
        buf.readBytes(ipV4);
        addr.setAddress(ipV4);
        return addr;
    }

    static public ECNSupport decodeEcnSupport (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeControlIPV4Address");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        ECNSupport eCNSupport = new ECNSupport();
        eCNSupport.setEcn(buf.readByte());

        return eCNSupport;
    }

    static public SessionID decodeSessionID (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeSessionID");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        SessionID sessionID = new SessionID();
        byte[] session = new byte [16];
        buf.readBytes(session);
        sessionID.setSessionid(session);

        return sessionID;
    }

    static public WTPName decodeWtpName (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeAcDescMsgElm  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable decodeAcDescMsgElm");
            return null;
        }

        WTPName wtpName = new WTPName();
        byte [] name = new byte[length];
        buf.readBytes(name);
        wtpName.setName(name);
        return wtpName;

    }



    static public IPV4Address decodeIPV4Addr (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeIPV4Addr");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        IPV4Address ipv4Address = new IPV4Address();
        byte [] address = new byte []{0,0,0,0};
        buf.readBytes(address);
        ipv4Address.setAddress(address);

        return ipv4Address;
    }

    static public StatisticsTimer decodeStaticsTimer (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeStaticsTimer");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        StatisticsTimer statisticsTimer = new StatisticsTimer();
        byte [] timer = new byte []{0,0};
        buf.readBytes(timer);
        statisticsTimer.setTimer(ByteManager.byteArrayToUnsingedShort(timer));

        return statisticsTimer;
    }


    static public IPV4AddrList decodeIPV4AddrList (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeStaticsTimer");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }

        IPV4AddrList addrList = new IPV4AddrList();

        int index = 0;
        while (index <length){
            byte [] addr = new byte []{0,0,0,0};
            buf.readBytes(addr);
            addrList.addAddress(addr);
            index = index+4;
        }
        return addrList;
    }

    static public ImageIdentifier decodeImageId (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeStaticsTimer");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }

        ImageIdentifier id = new ImageIdentifier();

        byte [] vid = new byte []{0,0,0,0};

        buf.readBytes(vid);
        id.setVendorID(ByteManager.byteArrayToUnsingedInt(vid));
        byte []data = new byte [length-4]; //substract the size of vendorid
        id.setData(data);

        return id;
    }

    static public VendorSpecificPayload decodeVendorSpecificPayload (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeStaticsTimer");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }

        VendorSpecificPayload id = new VendorSpecificPayload();

        byte [] vid = new byte []{0,0,0,0};
        buf.readBytes(vid);
        id.setVendorId(ByteManager.byteArrayToUnsingedInt(vid));
        byte [] e = new byte []{0,0};
        buf.readBytes(e);
        id.setElementID(ByteManager.byteArrayToUnsingedShort(e));

        byte []data = new byte [length-4-2]; //actual data is length - [vid +elementID sizes ]
        id.setData(data);

        return id;
    }


    static public CapwapTransportProtocol decodeTransportProtocol (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeStaticsTimer");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }

        CapwapTransportProtocol transport = new CapwapTransportProtocol();

            transport.setProtocol(buf.readByte());
        return transport;
    }



    static public WTPRebootStatistics decodeRebootStatistics (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeRebootStatistics");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        WTPRebootStatistics wtpRebootStatistics = new WTPRebootStatistics();

        byte [] count = new byte []{0,0};
        buf.readBytes(count);
        wtpRebootStatistics.setRebootCount(ByteManager.byteArrayToUnsingedShort(count));

        count [0]= 0; count[1] = 0;
        buf.readBytes(count);
        wtpRebootStatistics.setAcInitiated(ByteManager.byteArrayToUnsingedShort(count));

        count [0]= 0; count[1] = 0;
        buf.readBytes(count);
        wtpRebootStatistics.setLinkFailure(ByteManager.byteArrayToUnsingedShort(count));

        count [0]= 0; count[1] = 0;
        buf.readBytes(count);
        wtpRebootStatistics.setSoftwareFailure(ByteManager.byteArrayToUnsingedShort(count));

        count [0]= 0; count[1] = 0;
        buf.readBytes(count);
        wtpRebootStatistics.setHwFailure(ByteManager.byteArrayToUnsingedShort(count));

        count [0]= 0; count[1] = 0;
        buf.readBytes(count);
        wtpRebootStatistics.setOtherFailure(ByteManager.byteArrayToUnsingedShort(count));

        count [0]= 0; count[1] = 0;
        buf.readBytes(count);
        wtpRebootStatistics.setUnKnownFailure(ByteManager.byteArrayToUnsingedShort(count));

        byte last = 0;
        last = buf.readByte();
        wtpRebootStatistics.setLastFailureType(ByteManager.unsignedByteToshort(last));


        return wtpRebootStatistics;
    }

    static public MaxMsgLength decodeMaxMessageLength (ByteBuf buf , int length) {
        if (buf == null) {
            LOG.error("ByteBuf null decodeMaxMessageLength");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable");
            return null;
        }
        MaxMsgLength maximumMessageLength = new MaxMsgLength();
        byte [] timer = new byte []{0,0};
        buf.readBytes(timer);
        maximumMessageLength.setMaxLength(ByteManager.byteArrayToUnsingedShort(timer));

        return maximumMessageLength;
    }



    static public WtpFrameTunnelModeMsgElem decodeFrameTunnelModeDescriptor(ByteBuf buf , int length){
        WtpFrameTunnelModeMsgElem wtpTunnel = null;
        if(buf==null){
            LOG.error("ByteBuf null decodeFrameTunnelModeDescriptor ");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeFrameTunnelModeDescriptor");
            return null;
        }
        wtpTunnel = new WtpFrameTunnelModeMsgElem();
        byte mode =buf.readByte();
        wtpTunnel.setModes(mode);
        return wtpTunnel;
    }

    static public WtpDescriptor decodeWtpDescriptor(ByteBuf buf, int length){
        WtpDescriptor wtpDescriptor = null;
        if(buf==null){
            LOG.error("ByteBuf null decodeDiscoveryType  decodeWtpDescriptor");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeWtpDescriptor");
            return null;
        }

        wtpDescriptor = new WtpDescriptor();
        /*

        0                   1                   2                   3
        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
                +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
                |   Max Radios  | Radios in use |  Num Encrypt  |Encryp Sub-Elmt|
                +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
                |     Encryption Sub-Element    |    Descriptor Sub-Element...
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        */

        wtpDescriptor.setMaxRadios(ByteManager.unsignedByteToshort(buf.readByte()));
        wtpDescriptor.setRadioInUse(ByteManager.unsignedByteToshort(buf.readByte()));

        int numEncrypt = ByteManager.unsignedByteToshort(buf.readByte());

        int encryption_length = buf.readerIndex();
        while(numEncrypt>0){
            EncryptionSubElement en = decodeEncryptSubElement(buf,length);
            wtpDescriptor.addEncryptSubElement(en);
            //Num encrypt will be incremented when it is added to the list
            numEncrypt--;
        }

        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeWtpDescriptor afer encryption capabilites");
            return null;
        }
        encryption_length = buf.readerIndex()-encryption_length; //length of encryptionfield

        //Descriptor sub element
        int index = 0;
        int start = buf.readerIndex();
        while (index<length-(encryption_length+3)){ //adjusting for first 3 bytes

            DescriptorSubElement ds = decodeDescriptorSubElement(buf,length);
            wtpDescriptor.addDescriptorSubElm(ds);
            index=buf.readerIndex()-start;
        }

        return wtpDescriptor;
    }

    private static DescriptorSubElement decodeDescriptorSubElement(ByteBuf buf, int length) {

        if(buf==null){
            LOG.error("ByteBuf null descriptorSubElement  ");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable descriptorSubElement");
            return null;
        }

        //get  descriptor vendor id
        byte [] four = new byte []{0,0,0,0};
        buf.readBytes(four);
        long vendor = ByteManager.byteArrayToUnsingedInt(four);

        //Descriptor Type
        byte [] two_type= new byte []{0,0};
        buf.readBytes(two_type);
        int type = ByteManager.byteArrayToUnsingedShort(two_type);

        //Descriptor Length
        byte [] two_len= new byte []{0,0};
        buf.readBytes(two_len);  int len = ByteManager.byteArrayToUnsingedShort(two_len);


        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable while reading data");
            return null;
        }
        //Descriptor daata
        byte [] data = new byte[len];
        buf.readBytes(data);

        DescriptorSubElement e = new DescriptorSubElement();
        e.setVendorId(vendor);
        e.setDescType(type);
        e.setDescLength(len);
        e.setDescData(data);
        return e;
    }



    private static EncryptionSubElement decodeEncryptSubElement(ByteBuf buf, int length) {
        EncryptionSubElement e = null;
        if(buf==null){
            LOG.error("ByteBuf null EncryptionSubElement");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable EncryptionSubElement");
            return null;
        }
        byte wbid = buf.readByte(); // wbid is lsb nibble so we can take that as such
        byte [] twobyte = new byte []{0,0};
        buf.readBytes(twobyte);
        e = new EncryptionSubElement(wbid, ByteManager.byteArrayToUnsingedShort(twobyte));

        return e;
    }

    static public WtpBoardDataMsgElem decodeWtpBoardData(ByteBuf buf, int length){
        WtpBoardDataMsgElem wtpBoardData = null;
        int index = 0;

        if(buf==null){
            LOG.error("ByteBuf null decodeDiscoveryType  decodeWtpBoardData");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeWtpBoardData");
            return null;
        }
        //decode vendor identifier
        long vendorId = 0;
        byte [] vi = new byte []{0,0,0,0};
        wtpBoardData = new WtpBoardDataMsgElem();
        buf.readBytes(vi);
        wtpBoardData.setVendorId(ByteManager.byteArrayToUnsingedInt(vi));

        length = length-4; // adjust the length for vendor id
        //decode board data elements
        int start = buf.readerIndex();
        while (index<length){

            BoardDataSubElem e = ODLCapwapMessageElementFactory.decodeBoardDataSubElm(buf,length);
            if (e==null){
                LOG.error("BoardDataSubElem null after decoding");
                return null;
            }
            wtpBoardData.addBoardData(e);
            index=buf.readerIndex()-start;
        }
        return wtpBoardData;
    }

    static public BoardDataSubElem decodeBoardDataSubElm(ByteBuf buf , int length){
        BoardDataSubElem boardDataSubElem = null;
        if(buf==null){
            LOG.error("ByteBuf null decodeDiscoveryType  decodeBoardDataSubElm");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeBoardDataSubElm");
            return null;
        }


        boardDataSubElem = new BoardDataSubElem();
        byte [] data_length = new byte[]{0,0};
        byte [] type = new byte[]{0,0};

        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeBoardDataSubElm {}" ,"type");
            return null;
        }
        buf.readBytes(type);
        boardDataSubElem.setBoardDataType(ByteManager.byteArrayToUnsingedShort(type));

        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeBoardDataSubElm {}","length");
            return null;
        }
        buf.readBytes(data_length);
        boardDataSubElem.setBoardDataLength(ByteManager.byteArrayToUnsingedShort(data_length));

        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeBoardDataSubElm {} type ->{}   data_length->{}","val" , type , data_length);
            return null;
        }

        byte [] val = new byte [boardDataSubElem.getBoardDataLength()];
        buf.readBytes(val);

        return  boardDataSubElem;
    }


    static public WtpMacTypeMsgElem decodeMacTypeMsgElm(ByteBuf buf,int length){
        if(buf==null){
            LOG.error("ByteBuf null decodeMacTypeMsgElm  ");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeMacTypeMsgElm");
            return null;
        }

        WtpMacTypeMsgElem wtpMacType = new WtpMacTypeMsgElem();
        wtpMacType.setType(buf.readByte());

        return wtpMacType;
    }


    static public ACName decodeAcNameMsgElm(ByteBuf buf,int length) {

        if (buf == null) {
            LOG.error("ByteBuf null decodeAcDescMsgElm  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable decodeAcDescMsgElm");
            return null;
        }

        ACName acName = new ACName();
        byte [] name = new byte[length];
        buf.readBytes(name);
        acName.setName(name);
        return acName;


    }
    static public ACDescriptor decodeAcDescMsgElm(ByteBuf buf,int length){

        if(buf==null){
            LOG.error("ByteBuf null decodeAcDescMsgElm  ");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeAcDescMsgElm");
            return null;
        }
        ACDescriptor acDescriptor = new ACDescriptor();
        byte[] stations = new byte [] {0,0};
        byte[] limit = new byte [] {0,0};
        byte[] activeWtps = new byte [] {0,0};
        byte[]  maxWtp = new byte [] {0,0};

        int start_decode = buf.readerIndex();

        buf.readBytes(stations);
        acDescriptor.setStations(ByteManager.byteArrayToUnsingedShort(stations));

        buf.readBytes(limit);
        acDescriptor.setLimit(ByteManager.byteArrayToUnsingedShort(limit));

        buf.readBytes(activeWtps);
        acDescriptor.setActiveWtps(ByteManager.byteArrayToUnsingedShort(activeWtps));

        buf.readBytes(maxWtp);
        acDescriptor.setMaxWtps(ByteManager.byteArrayToUnsingedShort(maxWtp));

        acDescriptor.setSecurity(buf.readByte());
        acDescriptor.setRmac(buf.readByte());
        buf.skipBytes(1);
        acDescriptor.setDtlsPolicy(buf.readByte());
        int end_decode = buf.readerIndex();
        ACInformationSubElement acInformationSubElement = null;
        int index = 0;
        int start = buf.readerIndex();
        while (index< (length-start)){ //adjusting for first 3 bytes

            acInformationSubElement = decodeAcInformationSubElement(buf,length-(end_decode-start));
            acDescriptor.addAcInformationSubElem(acInformationSubElement);
            index=buf.readerIndex()-start;
        }
        return acDescriptor;
    }

    public static ACInformationSubElement decodeAcInformationSubElement(ByteBuf buf,int length){
        if(buf==null){
            LOG.error("ByteBuf null decodeAcInformationSubElement  ");
            return null;
        }
        if(!buf.isReadable()){
            LOG.error("ByteBuf not readable decodeAcInformationSubElement");
            return null;
        }
        ACInformationSubElement acInformationSubElement =null;
        acInformationSubElement = new ACInformationSubElement();
        byte [] vendor = new byte [] {0,0,0,0};
        byte [] type = new byte [] {0,0};
        byte []  ac_len = new byte [] {0,0};

        try {
            buf.readBytes(vendor);
            acInformationSubElement.setAcInfoVendorId(ByteManager.byteArrayToUnsingedInt(vendor));

            buf.readBytes(type);
            acInformationSubElement.setAcInfoType(ByteManager.byteArrayToUnsingedShort(type));

            buf.readBytes(ac_len);
            acInformationSubElement.setAcInfoLength(ByteManager.byteArrayToUnsingedShort(ac_len));

            byte[] data = new byte[acInformationSubElement.getAcInfoLength()];
            buf.readBytes(data);
            acInformationSubElement.setAcInfoData(data);
        }catch(Exception e){
           return null;
        }
        return acInformationSubElement;

    }

    static public MacAddress decodeMacAddress(ByteBuf buf){
        MacAddress mac = null;
        byte []address = null;

        mac= new MacAddress();
        //Decode Length
        mac.setLength(ByteManager.unsignedByteToshort(buf.readByte()));
        address = new byte[mac.getLength()];
        buf.readBytes(address);
        mac.setAddress(address);
        return mac;
    }

    static public WsiInfo decodeWsiInfo(ByteBuf buf){
        WsiInfo wsi= null;
        byte [] data = null;

        wsi = new WsiInfo();
        //Decode Length
        wsi.setLength(ByteManager.unsignedByteToshort(buf.readByte()));
        data = new byte[wsi.getLength()];
        buf.readBytes(data);
        wsi.setData(data);
        return wsi;
    }



}
