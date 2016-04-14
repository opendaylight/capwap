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
    /* 
     * TODO - Define classes for RBID and WSI
     */

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

    /**This method decodes the network bytes into capwap header format.
     * @param bbuf : ByteBuf of packets received from network
     * @return : true for success, false for failure
     */
    public boolean decodeHeader(ByteBuf bbuf) {
        int pktlen = bbuf.readableBytes();
        byte preamble;
        
        if ( pktlen < ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN) {
            System.out.println("Invalid packet recieved. Length " + pktlen);
            return false;
        }

        preamble = bbuf.readByte();
        this.version = (byte)(preamble >>> 4);
        this.type = (byte)(preamble & 0x0F);

        byte b;
        /* Header length - Most significant 5bits*4 */
        b = bbuf.readByte(); /* Read second byte */
        hlen = (byte)(((b >>> 3) & 0x1F) << 2);
        if (hlen > pktlen) {
            System.out.println("Invalid packet hlen " + hlen + " more than packet length " + pktlen);
            return false;
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

        if (isSetTbit() && (wbid != ODLCapwapConsts.ODL_CAPWAP_WBID_80211)) {
            System.out.println("Invalid WBID " + wbid);
            return false;
        }

        this.resvFlags = 0;

        /* Retrieves bytes 5 & 6 */
        fragId = bbuf.readShort(); 

        /* Retrieves bytes 7 & 8 */
        int tmpOffset = bbuf.readShort();
        /*Higher order 13 bits */
        fragOffset = (short)(tmpOffset >>> 3);

        /* TODO - Handle RADIO MAC address and WSI later */
        if (isSetMbit()) {
            /* TODO - Handle Radio MAC address */
        }

        if (isSetWbit()) {
            /* TODO : Handle WSI */ 
        }

        return true;
    }

    /**Method converts Capwap header encoder into byte array.
     * @return true: success, false: failure
     */
    public boolean encodeHeader(ByteBuf bbuf) {
        if (bbuf.writableBytes() < ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN) {
            System.out.println("Byte Buffer do not have capacity to write " 
                          + ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN + " bytes");
            return false;
        }

        byte[] hdrbytes = new byte[ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN];
        hdrbytes[0] = (byte)((version<<4) | type);
        /* Converting the header len value */
        hdrbytes[1] = (byte)((hlen >> 2) & ODLCapwapConsts.ODL_CAPWAP_HLEN_MASK);
        /* Filling higher 3bits of RID */
        hdrbytes[1] |= (byte)(rid & 0x1C); 
        /* Filling lower 2bits of RID (higher 2 bits of byte2) */
        hdrbytes[2] = (byte)((rid & 0x03) << 6);
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
        
        return true;
    }


    /**Method checks and return Tbit (set if frame Type other than 802.3). 
     * status
     * @return : Boolean true if set else false
     */
    public boolean isSetTbit() {
        return (((flagBits & 0x20) >> 5) == 1);
    }

    /**Method to set the Tbit.
     * 
     */
    public void setTbit() {
        flagBits |= 0x20;
    }

    /**Method checks and return Fbit(Fragment bit) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetFbit() {
        return (((flagBits & 0x10) >> 4) == 1);
    }

    /**Method sets Fbit.
     */
    public void setFbit() {
        flagBits |= 0x10;
    }

    /**Method checks and return Lbit(Last fragment) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetLbit() {
        return (((flagBits & 0x08) >> 3) == 1);
    }

    /**Method sets Fbit.
     */
    public void setLbit() {
        flagBits |= 0x08;
    }

    /**Method checks and return Wbit(Wireless binding Identifier) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetWbit() {
        return (((flagBits & 0x04) >> 2) == 1);
    }

    /**Method sets Wbit.
     * 
     */
    public void setWbit() {
        flagBits |= 0x04;
    }

    /**Method checks and return Mbit(Radio Mac Identifier) status.
     * @return: Boolean true if set else false
     */
    public boolean isSetMbit() {
        return (((flagBits & 0x02) >> 1) == 1);
    }

    /**Method sets Mbit.
     */
    public void setMbit() {
        flagBits |= 0x02;
    }


    /**Method checks and return Kbit(Keep Alive) status.
     * @return: Boolean true if set else false.
     */
    public boolean isSetKbit() {
        return ((flagBits & 0x01) == 1);
    }

    /**Method sets Kbit.
     */
    public void setKbit() {
        flagBits |= 0x01;
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
    
    /**Sets the header length.
     * @param hlen : Capwap header length
     */
    public void setHlen(int hlen) {
        this.hlen = (byte)hlen;
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
