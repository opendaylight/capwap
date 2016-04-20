/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.msgelements.DiscoveryType;
import org.opendaylight.capwap.msgelements.WtpBoardDataMsgElem;
import org.opendaylight.capwap.msgelements.WtpDescriptor;
import org.opendaylight.capwap.msgelements.WtpFrameTunnelModeMsgElem;
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
