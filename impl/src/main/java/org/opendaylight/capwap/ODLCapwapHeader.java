/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;

import org.opendaylight.capwap.ODLCapwapConsts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/* Capwap header. It is quite complicated one and hence the first 4 bytes are filled
 * byte by byte.
 * 0                   1                   2                   3
 * 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |CAPWAP Preamble|  HLEN   |   RID   | WBID    |T|F|L|W|M|K|Flags|
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |          Fragment ID          |     Frag Offset         |Rsvd |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                 (optional) Radio MAC Address                  |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |            (optional) Wireless Specific Information           |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * |                        Payload ....                           |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
public class ODLCapwapHeader {
    private byte version;
    private byte type;
    private byte hlen;
    private byte rid;
    private byte wbid;
    /*
     * Header flags stored in a byte: Top two bits are unused.
     * 0|0|T|F|L|W|M|K
     */
    private byte flagBits;
    private byte resvFlags;
    private int fragId;
    private short fragOffset;
    private RadioMACAddress rma;
    private WSI wsi;
    private static final Logger log = LoggerFactory.getLogger(ODLCapwapHeader.class);

    /**Capwap default constructor.
     */
    ODLCapwapHeader() {
        version = 0;
        type = 0;
        hlen = 0;
        rid = 0;
        wbid = 0;
        flagBits = 0;
        resvFlags = 0;
        fragId = 0;
        fragOffset = 0;
    }

    ODLCapwapHeader(Builder b) {
        this.version = b.version;
        this.type = b.type;
        this.hlen = b.hlen;
        this.rid = b.rid;
        this.wbid = b.wbid;
        this.flagBits = b.flagBits;
        this.resvFlags = b.resvFlags;
        this.fragId = b.fragId;
        this.fragOffset = b.fragOffset;
        this.rma = b.rma;
        this.wsi = b.wsi;
    }

    public static class Builder {
        private byte version;
        private byte type;
        private byte hlen;
        private byte rid;
        private byte wbid;
        /*
         * Header flags stored in a byte: Top two bits are unused.
         * 0|0|T|F|L|W|M|K
         */
        private byte flagBits;
        private byte resvFlags;
        private int fragId;
        private short fragOffset;
        RadioMACAddress rma;
        WSI wsi;

        public Builder version(byte version) {
            this.version = version;
            return this;
        }

        public Builder type(byte type) {
            this.type = type;
            return this;
        }

        public Builder hlen(byte hlen) {
            this.hlen = hlen;
            return this;
        }

        public Builder rid(byte rid) {
            this.rid = rid;
            return this;
        }

        public Builder wbid(byte wbid) {
            this.wbid = wbid;
            return this;
        }

        public Builder flagBits(byte flagBits) {
            this.flagBits = flagBits;
            return this;
        }

        public Builder resvFlags(byte resvFlags) {
            this.resvFlags = resvFlags;
            return this;
        }

        public Builder fragId(int fragId) {
            this.fragId = fragId;
            return this;
        }

        public Builder fragOffset(short fragOffset) {
            this.fragOffset = fragOffset;
            return this;
        }

        public Builder rma(RadioMACAddress rma) {
            this.rma = rma;
            return this;
        }

        public Builder wsi(WSI wsi) {
            this.wsi = wsi;
            return this;
        }

        public ODLCapwapHeader build() {
            return new ODLCapwapHeader(this);
        }
    }

    public static int padBuffer(ByteBuf bbuf, int padLen, byte padByte) {
        int pos = bbuf.writerIndex();
        int bytesToPad;
        if ((bytesToPad = (padLen - (pos % padLen))) != 0) {
            for (int i = 0; i < bytesToPad; i++) {
                    /* Padding */
                bbuf.writeByte(padByte);
            }
        }

        System.out.println(bytesToPad + " bytes padded with " + padByte);

        return bytesToPad;
    }

    public static class RadioMACAddress {
        private byte length;
        private ByteBuf macAddress;

        public RadioMACAddress(ByteBuf macAddress) {
            this.length = (byte)macAddress.writerIndex();
            this.macAddress = macAddress;
        }

        public static RadioMACAddress decode(ByteBuf bbuf) {
            byte length;

            if (bbuf.readableBytes() < 1) {
                log.error("Invalid packet, only " + bbuf.readableBytes() + " bytes left in the buffer");
                return null;
            }

            length = bbuf.readByte();
            if (bbuf.readableBytes() < length) {
                log.error("RadioMACAddress: Invalid packet, only " + bbuf.readableBytes() + " bytes left in the buffer");
                return null;
            }
            ByteBuf macAddress = bbuf.readBytes(length);

            return new RadioMACAddress(macAddress);
        }

        public int encode(ByteBuf bbuf) {
            if (this.macAddress.writerIndex() > 255) {
                log.error("Invalid MAC address.");
                return -1;
            }

            int start = bbuf.writerIndex();
            bbuf.writeByte(0); /* Dummy value */
            bbuf.writeBytes(this.macAddress);
            ODLCapwapHeader.padBuffer(bbuf, 4, (byte)0x00);
            int end = bbuf.writerIndex();
            bbuf.setByte(start, end - start - 1); /*Excluding length field */

            return end - start;
        }

        int getLength() {
            return this.length;
        }

        ByteBuf geMacAddress() {
            return this.macAddress;
        }

        void setMacAddress(ByteBuf macAddress) {
            this.macAddress = macAddress;
        }
    }

    public static class WSI {
        byte length;
        ByteBuf data;

        public WSI(ByteBuf data) {
            this.length = (byte)data.writerIndex();
            this.data = data;
        }

        public static WSI decode(ByteBuf bbuf) {
            byte length;

            if (bbuf.readableBytes() < 1) {
                log.error("Invalid packet, only " + bbuf.readableBytes() + " bytes left in the buffer");
                return null;
            }

            length = bbuf.readByte();
            if (bbuf.readableBytes() < length) {
                log.error("length=" + length + "Invalid packet, only " + bbuf.readableBytes() + " bytes left in the buffer");
                return null;
            }
            ByteBuf data = bbuf.readBytes(length);

            return new WSI(data);
        }

        public int encode(ByteBuf bbuf) {
            if (this.data.writerIndex() > 255) {
                log.error("Invalid MAC address.");
                return -1;
            }

            int start = bbuf.writerIndex();
            bbuf.writeByte(0); /* Dummy value */
            bbuf.writeBytes(this.data);
            ODLCapwapHeader.padBuffer(bbuf, 4, (byte)0x00);
            int end = bbuf.writerIndex();
            bbuf.setByte(start, end - start - 1); /*Excluding length field */

            return end - start;
        }

        int getLength() {
            return this.length;
        }

        ByteBuf getData() {
            return this.data;
        }

        void setData(ByteBuf data) {
            this.data = data;
            this.length = (byte)data.writerIndex();
        }
    }

    /**This method decodes the network bytes into capwap header format.
     * @param bbuf : ByteBuf of packets received from network
     * @return : true for success, false for failure
     */
    public static ODLCapwapHeader decodeHeader(ByteBuf bbuf) {
        byte preamble;
        byte version;
        byte type;
        byte hlen;
        byte rid;
        byte wbid;
        byte flagBits;
        byte resvFlags;
        int fragId;
        short fragOffset;
        RadioMACAddress rma = null;
        WSI wsi = null;


        int pktlen = bbuf.readableBytes();
        int hsize;

        if ( pktlen < ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN) {
            log.error("Invalid packet recieved. Length " + pktlen);
            return null;
        }

        preamble = bbuf.readByte();
        version = (byte)(preamble >>> 4);
        type = (byte)(preamble & 0x0F);

        byte b;
        /* Header length - Most significant 5bits*4 */
        b = bbuf.readByte(); /* Read second byte */
        hlen = (byte)(((b >>> 3) & 0x1F) << 2);
        if (hlen > pktlen) {
            log.error("Invalid packet hlen " + hlen + " more than packet length " + pktlen);
            return null;
        }
        
        byte bnext;
        bnext = bbuf.readByte(); /* Read third byte */
        /* Lower order 3bits from byte2 and higher 2 bits from byte3 */
        rid = (byte)(((b & 0x07) << 2) | ((bnext >>> 6) & 0x03));
        /* Lower order 5 bits from byte 3 */
        wbid = (byte)((bnext & 0x3E) >>> 1);
        
        b = bnext;
        bnext = bbuf.readByte(); /* Read 4th byte */
        /* Lower 1 bit from byte3 and higher 5bits from byte 4*/
        flagBits = (byte)(((b & 0x01) << 5) | ((bnext & 0xF8) >>> 3));

        if (isSetTbit(flagBits) && (wbid != ODLCapwapConsts.ODL_CAPWAP_WBID_80211)) {
            log.error("Invalid WBID " + wbid);
            return null;
        }

        resvFlags = 0;

        /* Retrieves bytes 5 & 6 */
        fragId = bbuf.readShort(); 

        /* Retrieves bytes 7 & 8 */
        int tmpOffset = bbuf.readShort();
        /*Higher order 13 bits */
        fragOffset = (short)(tmpOffset >>> 3);

        hsize = 8;

        if (isSetMbit(flagBits)) {
            rma = RadioMACAddress.decode(bbuf);

            if (rma == null) {
                log.error("Invalid RadioMACAddress in the packet");
                return null;
            }
            hsize += rma.getLength();
        }

        if (isSetWbit(flagBits)) {
            wsi = WSI.decode(bbuf);

            if (wsi == null) {
                log.error("Invalid WSI in the packet");
                return null;
            }
            hsize += wsi.getLength();
        }

        /* Basic check to see if the hlen is set correctly */
        if (hsize != hlen) {
            log.error("Invalid capwap header hlen=" + hlen + ", header size=" + hsize);
            return null;
        }

        return new ODLCapwapHeader.Builder().
                version(version).
                type(type).
                hlen(hlen).
                rid(rid).
                wbid(wbid).
                flagBits(flagBits).
                resvFlags(resvFlags).
                fragId(fragId).
                fragOffset(fragOffset).
                rma(rma).
                wsi(wsi).
                build();
    }

    /**Method converts Capwap header encoder into byte array.
     * @return Number of bytes encoded: success, false: failure
     */
    public int encodeHeader(ByteBuf bbuf) {
        byte[] hdrbytes = new byte[ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN];
        int hpos= bbuf.writerIndex() + 1;

        hdrbytes[0] = (byte)((version<<4) | type);
        /* Converting the header len value */
        hdrbytes[1] = (byte)(((hlen >>> 2) & ODLCapwapConsts.ODL_CAPWAP_HLEN_MASK) << 3);
        /* Filling higher 3bits of RID */
        hdrbytes[1] |= (byte)(rid & 0x07);
        /* Filling lower 2bits of RID (higher 2 bits of byte2) */
        hdrbytes[2] = (byte)((rid & 0xC0) << 6);
        /* WBID - 5 bits - bits 3-7 */
        hdrbytes[2] |= (byte)(wbid & 0x3E); 
        /* Copying the T flag */
        hdrbytes[2] |= (byte)((flagBits & 0x20) >>> 5);
        /* Copying other flag bits */
        hdrbytes[3] = (byte)((flagBits & 0x1F) << 3); 
        /* Filling first byte of fragment ID */
        hdrbytes[4] = (byte)((fragId & 0xFF00) >>> 8);
        /* Filling the second byte of fragment ID */
        hdrbytes[5] = (byte)(fragId & 0x00FF); 
        /* Most significant 8 bits of 13 bits */
        hdrbytes[6] = (byte)(((fragOffset) & 0x1FE0) >>> 5);
        /* Least significant 5 bits (higher 5 bits of byte 8) */
        hdrbytes[7] = (byte)((fragOffset & 0x1F) << 3); 

        //bbuf.resetWriterIndex();
        bbuf.writeBytes(hdrbytes);
        this.hlen = ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN;

        /* Add RadioMacId */
        if (isSetMbit()) {
            int len;
            if ((len = rma.encode(bbuf)) < 0) {
                log.error("Error in encoding RadioMACIdentifier");
                return -1;
            }
            System.out.println("RMA Len=" + len);
            this.hlen += len;
        }

        /* Add WSI */
        if (isSetWbit()) {
            int len;
            if ((len = wsi.encode(bbuf)) < 0) {
                log.error("Error in encoding WSI");
                return -1;
            }
            System.out.println("WSI Len=" + len);
            this.hlen += len;
        }

        System.out.println("Header length=" + this.hlen);
        byte hdr;
        /* Converting the header len value */
        hdr = (byte)(((this.hlen >>> 2) & ODLCapwapConsts.ODL_CAPWAP_HLEN_MASK) << 3);
        /* Filling higher 3bits of RID */
        hdr |= (byte)(rid & 0x07);
        bbuf.setByte(hpos, hdr);

        return this.hlen;
    }


    /**Method checks and return Tbit (set if frame Type other than 802.3). 
     * status
     * @return : Boolean true if set else false
     */
    public boolean isSetTbit() {
        return (((this.flagBits & 0x20) >> 5) == 1);
    }

    /**Method checks and return Tbit (set if frame Type other than 802.3).
     * status
     * @return : Boolean true if set else false
     */
    public static boolean isSetTbit(byte flagBits) {
        return (((flagBits & 0x20) >> 5) == 1);
    }

    /**Method to set the Tbit.
     *
     */
    public void setTbit() {
        this.flagBits |= 0x20;
    }

    /**Method to set the Tbit.
     *
     */
    public static byte setTbit(byte flagBits) {
        flagBits |= 0x20;
        return flagBits;
    }

    /**Method checks and return Fbit(Fragment bit) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetFbit() {
        return (((this.flagBits & 0x10) >> 4) == 1);
    }

    /**Method sets Fbit.
     */
    public void setFbit() {
        this.flagBits |= 0x10;
    }

    /**Method sets Fbit.
     */
    public static byte setFbit(byte flagBits) {
        flagBits |= 0x10;
        return flagBits;
    }

    /**Method checks and return Lbit(Last fragment) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetLbit() {
        return (((this.flagBits & 0x08) >> 3) == 1);
    }

    /**Method sets Fbit.
     */
    public void setLbit() {
        this.flagBits |= 0x08;
    }

    /**Method sets Fbit.
     */
    public static byte setLbit(byte flagBits) {
        flagBits |= 0x08;
        return flagBits;
    }

    /**Method checks and return Wbit(Wireless binding Identifier) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetWbit() {
        return (((this.flagBits & 0x04) >> 2) == 1);
    }

    /**Method checks and return Wbit(Wireless binding Identifier) status.
     * @return : Boolean true if set else false
     */
    public static boolean isSetWbit(byte flagBits) {
        return (((flagBits & 0x04) >> 2) == 1);
    }

    /**Method sets Wbit.
     * 
     */
    public void setWbit() {
        this.flagBits |= 0x04;
    }

    /**Method sets Wbit.
     *
     */
    public static byte setWbit(byte flagBits) {
        flagBits |= 0x04;
        return flagBits;
    }

    /**Method checks and return Mbit(Radio Mac Identifier) status.
     * @return: Boolean true if set else false
     */
    public boolean isSetMbit() {
        return (((this.flagBits & 0x02) >> 1) == 1);
    }

    /**Method checks and return Mbit(Radio Mac Identifier) status.
     * @return: Boolean true if set else false
     */
    public static boolean isSetMbit(byte flagBits) {
        return (((flagBits & 0x02) >> 1) == 1);
    }

    /**Method sets Mbit.
     */
    public void setMbit() {
        this.flagBits |= 0x02;
    }

    /**Method sets Mbit.
     */
    public static byte setMbit(byte flagBits) {
        flagBits |= 0x02;
        return flagBits;
    }


    /**Method checks and return Kbit(Keep Alive) status.
     * @return: Boolean true if set else false.
     */
    public boolean isSetKbit() {
        return ((this.flagBits & 0x01) == 1);
    }

    /**Method sets Kbit.
     */
    public void setKbit() {
        this.flagBits |= 0x01;
    }

    /**Method sets Kbit.
     */
    public static byte setKbit(byte flagBits) {
        flagBits |= 0x01;
        return flagBits;
    }

    /**Method sets flagBits.
     */
    public void setFlagBits(byte flagBits) {
        this.flagBits = flagBits;
    }
    
    /**Method sets Reserved Flags.
     */
    public void setResvFlags(byte resvFlags) {
        this.resvFlags = resvFlags;
    }
    
    /**Method to get capwap version.
     * @return: capwap version.
     */
    public byte getVersion() {
        return version;
    }
    
    /**Sets the preamble.
     * @param version : Capwap version.
     */
    
    public void setVersion(byte version) {
        this.version = version;
    }

    /**Method to get capwap type.
     * @return: capwap type.
     */
    public byte getType() {
        return type;
    }

    /**Sets the capwap type.
     * @param type : Capwap type.
     */

    public void setType(byte type) {
        this.type = type;
    }

    /**Method to get hlen.
     * @return: capwap header length
     */
    public int getHlen() {
        return hlen;
    }
    
    /**Method to get Radio ID.
     * @return rid: Radio ID
     */
    public int getRid() {
        return this.rid;
    }
    
    /**Sets the Radio ID.
     * @param rid : Raido ID
     */
    public void setRid(int rid) {
        this.rid = (byte)rid;
    }
    
    /**Method to get Wireless binding identifier.
     * @return wbid: Wireless binding identifier
     */
    public int getWbid() {
        return this.wbid;
    }
    
    /**Sets the Wireless binding identifier.
     * @param wbid : Wireless binding identifier
     */
    public void setWbid(int wbid) {
        this.wbid = (byte)wbid;
    }
    
    /**Method to get Fragment ID.
     * @return fragId: Fragment ID
     */
    public int getFragId() {
        return this.fragId;
    }
    
    /**Sets the Fragment ID.
     * @param fragId : Fragment Identifier
     */
    public void setFragId(int fragId) {
        this.fragId = (byte)fragId;
    }
    
    /**Method to get Fragment Offset.
     * @return fragOffset: Fragment Offset
     */
    public int getFragOffset() {
        return this.fragOffset;
    }
    
    /**Sets the Fragment Offset.
     * @param fragOffset : Fragment Offset
     */
    public void setFragOffset(int fragOffset) {
        this.fragOffset = (byte)fragOffset;
    }

    public void printHeader() {
        System.out.println("Version="+version);
        System.out.println("Type="+type);
        System.out.println("hlen="+hlen);
        System.out.println("rid="+rid);
        System.out.println("wbid="+wbid);
        System.out.println("TFlag="+isSetTbit());
        System.out.println("FFlag="+isSetFbit());
        System.out.println("LFlag="+isSetLbit());
        System.out.println("WFlag="+isSetWbit());
        System.out.println("MFlag="+isSetMbit());
        System.out.println("KFlag="+isSetKbit());
        System.out.println("resvFlags="+resvFlags);
        System.out.println("FragId="+fragId);
        System.out.println("FragOffset="+fragOffset);
    }
}
