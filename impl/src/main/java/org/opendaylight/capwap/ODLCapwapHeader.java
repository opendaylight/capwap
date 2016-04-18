/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.msgelements.subelem.WsiInfo;
import org.opendaylight.capwap.utils.ByteManager;

public class ODLCapwapHeader {
    private byte preamble=0;
    private byte type =0;
    private byte version = 0;
    private byte hlen=0; // when you set 5 bit value mas out top 3 bit for safety
    private byte rid=0; // when you set 5 bit value mas out top 3 bit for safety
    private byte wbid = 0;// when you set 5 bit value mas out top 3 bit for safety
    private boolean tFlag = false;
    private boolean fFlag = false;
    private boolean lFlag = false;
    private boolean wFlag = false;
    private boolean mFlag = false;
    private boolean kFlag = false;

    private int fragId = 0;
    int _fragOffset = 0;
    MacAddress radioMacAddress = null;
    WsiInfo wsiInfo = null;

    /*
     * Header flags stored in a byte: Top two bits are unused.
     * 0|0|T|F|L|W|M|K
     */

    private byte flagBits;
    private byte resvFlags;
    private short fragOffset;

    private byte tFlagMask = 0b00000001;
    private byte fFlagMask = (byte)0b10000000;
    private byte lFlagMask = 0b01000000;
    private byte wFlagMask = 0b00100000;
    private byte mFlagMask = 0b00010000;
    private byte kFlagMask = 0b00001000;
    /*
     * TODO - Define classes for RBID and WSI
     */

    /**Capwap default constructor.
     */
    ODLCapwapHeader() {
    }

    byte[] createFirstWord(){
        byte [] word = new byte[] {0,0,0,0};
        //set preample
        //set hlen
        word[1] |= hlen <<3;
        //rid
        //rid in a byte [0,0,0,1,1,1,1,1] sign bit is assumed to be zero . Always made zero using set function
        //get rid's  msb 3 bits and make it hlens 3 bit lsb . So shift rid to right by 2 [remember rid is 5 bit ] &  hlen 3 bits are vacant
        //rid in a byte [0,0,0,1,1,1,1,1]
        word[1] |= rid >>>2;
        //set rid's  2 lsbs to MSB of word[2]
        //rid in a byte [0,0,0,1,1,1,1,1]  <<0
        //rid in a byte [0,0,1,1,1,1,1,0]  <<1
        //rid in a byte [0,1,1,1,1,1,0,0]  <<2
        //rid in a byte [1,1,1,1,1,0,0,0]  <<3
        //rid in a byte [1,1,1,1,0,0,0,0]  <<4
        //rid in a byte [1,1,1,0,0,0,0,0]  <<5
        //rid in a byte [1,1,0,0,0,0,0,0]  <<6
        word[2] |= (rid <<6  & 0b1100000000);
        //wbid
        // we have remaining 6 bits and wbid is 5 bit .
        //wbid in a byte [0,0,0,1,1,1,1,1]
        //rid       byte [1,1,0,0,0,0,0,0]     rid lsb in word [2]
        //wbid      byte [0,0,1,1,1,1,1,0]  << 1  set wbid in word [2]
        word[2] |= (wbid <<1);
        //setflags T|F|L|W|M|K|
        //settFlag

        if (isSetTbit()){
            word[2] |= tFlagMask;
        }
        if (isSetFbit()){
            word[3] |= fFlagMask;
        }
        if (isSetLbit()){
            word[3] |= lFlagMask;
        }
        if (isSetWbit()){
            word[3] |= wFlagMask;
        }
        if (isSetMbit()){
            word[3] |= mFlagMask;
        }
        if (isSetKbit()){
            word[3] |= kFlagMask;
        }

        return word;
    }

    byte[] createSecondWord(){
        byte [] word = new byte[] {0,0,0,0};
        //setFragId
        byte [] fragid = ByteManager.unsignedShortToArray(this.fragId);
        word[0] = fragid[0];
        word[1] = fragid[1];
        byte [] fragoffset  = ByteManager.unsigned13bitsToArray(this._fragOffset);

        //setFragOffset
        word[2] = fragoffset[0];
        word[3] = fragoffset[1];
        return word;
    }




    /**Method converts Capwap header encoder into byte array.
     * @return true: success, false: failure
     */
    public int encodeHeader2(ByteBuf bbuf) {
        int hlen = 0;
        int len =0;
        int start  = bbuf.writerIndex();
        //reset the writer index to skip the first word .
        //First word will be filled at the end

        bbuf.setIndex(bbuf.readerIndex(),bbuf.writerIndex()+4);


        //createsecondWord
        bbuf.writeBytes(createSecondWord());


        //encode radio mac
        if (this.isSetMbit()){
            len= this.radioMacAddress.encode(bbuf);
            //!!!add padding if needed
            len = padMe(bbuf,len,4);

        }


        if (this.isSetWbit()) {
            //encode WSI
            len = this.wsiInfo.encode(bbuf);
            //!!!!add padding if needed
            len =padMe(bbuf, len, 4);
        }

        //calculate hlen
        hlen= bbuf.writerIndex()-start;

        //now set the hlen to the header
        this.hlen =(byte)( hlen >>2);

        //Since hlen is set we can encode the firstWord
        //remember hlen is the part of first word and this is tricky .
        //setBytes wont increment the writerIndex
        bbuf.setBytes(start,createFirstWord());

        //return the size of the header
        return bbuf.writerIndex()-start;
    }

    int padMe(ByteBuf buf,int length_of_last_encoding , int word_size){
        int start = buf.writerIndex();
        int numBytesToBepadded = 0;
        //Find our the number of bytes to be padded . for example word size 4. Length of last encoding 7
        // then number of bytes to be padded is  7%4  ie 3 . ie 3 bytes are there in the unaligned  word . we need to
        // add one more byte to get a 4 byte alingement
        numBytesToBepadded = word_size-(length_of_last_encoding % word_size);
        for(int i = 0 ;i< numBytesToBepadded;i++)
            buf.writeByte(0);

        return buf.writerIndex()-start;
    }


    /**This method decodes the network bytes into capwap header format.
     * @param bbuf : ByteBuf of packets received from network
     * @return : true for success, false for failure
     */
    public boolean decodeHeader(ByteBuf bbuf) {
        int pktlen = bbuf.readableBytes();
        
        if ( pktlen < ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN) {
            System.out.println("Invalid packet recieved. Length " + pktlen);
            return false;
        }

        preamble = bbuf.readByte();

        byte b;
        /* Header length - Most significant 5bits*4 */
        b = bbuf.readByte(); /* Read second byte */
        hlen = (byte)(((b >> 3) & 0x1F) << 2); 
        if (hlen >= pktlen) {
            System.out.println("Invalid packet hlen " + hlen + " more than packet length " + pktlen);
            return false;
        }
        
        byte bnext;
        bnext = bbuf.readByte(); /* Read third byte */
        /* Lower order 3bits from byte2 and higher 2 bits from byte3 */
        rid = (byte)(((b & 0x07) << 2) | ((bnext >> 6) & 0x03)); 
        /* Lower order 5 bits from byte 3 */
        wbid = (byte)((bnext & 0x3E) >> 1);
        
        b = bnext;
        bnext = bbuf.readByte(); /* Read 4th byte */
        /* Lower 1 bit from byte3 and higher 5bits from byte 4*/
        flagBits = (byte)(((b & 0x01) << 5) | ((bnext & 0xF8) >> 3)); 

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
        fragOffset = (short)(tmpOffset >> 3); 

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
        hdrbytes[0] = preamble;
        /* Converting the header len value */
        hdrbytes[1] = (byte) (((hlen >> 2) & ODLCapwapConsts.ODL_CAPWAP_HLEN_MASK)<<3);
        /* Filling higher 3bits of RID */
        hdrbytes[1] |= (byte)(rid & 0x1C);

        /* Filling lower 2bits of RID (higher 2 bits of byte2) */
        hdrbytes[2] = (byte)((rid & 0x03) << 6);
        /* WBID - 5 bits - bits 3-7 */
        hdrbytes[2] |= (byte)(wbid & 0x3E); 
        /* Copying the T flag */
        hdrbytes[2] |= (byte)((flagBits & 0x20) >> 5);
        /* Copying other flag bits */
        hdrbytes[3] = (byte)((flagBits & 0x1F) << 3); 
        /* Filling first byte of fragment ID */
        hdrbytes[4] = (byte)((fragId & 0xFF00) >> 8);
        /* Filling the second byte of fragment ID */
        hdrbytes[5] = (byte)(fragId & 0x00FF); 
        /* Most significant 8 bits of 13 bits */
        hdrbytes[6] = (byte)(((fragOffset) & 0x1FE0) >> 5);
        /* Least significant 5 bits (higher 5 bits of byte 8) */
        hdrbytes[7] = (byte)((fragOffset & 0x1F) << 3); 

        bbuf.writeBytes(hdrbytes);
        
        return true;
    }

    /**Method checks and return Tbit (set if frame Type other than 802.3).
     * status
     * @return : Boolean true if set else false
     */
    public boolean isSetTbit() {
        //return (((flagBits & 0x20) >> 5) == 1);
        return this.tFlag;
    }

    /**Method to set the Tbit.
     * 
     */
    public void setTbit() {
        //flagBits |= 0x20;
        this.tFlag = true;
    }

    /**Method checks and return Fbit(Fragment bit) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetFbit() {
       // return (((flagBits & 0x10) >> 4) == 1);
        return fFlag;
    }

    /**Method sets Fbit.
     */
    public void setFbit()
    {
        //this.flagBits |= 0x10;
        this.fFlag = true;
    }

    /**Method checks and return Lbit(Last fragment) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetLbit() {
        //return (((flagBits & 0x08) >> 3) == 1);
        return lFlag;
    }

    /**Method sets Fbit.
     */
    public void setLbit() {
        //flagBits |= 0x08;
        this.lFlag = true;
    }

    /**Method checks and return Wbit(Wireless binding Identifier) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetWbit() {
        //return (((flagBits & 0x04) >> 2) == 1);
        return wFlag;
    }

    /**Method sets Wbit.
     * 
     */
    public void setWbit() {
        //flagBits |= 0x04;
        this.wFlag = true;
    }

    /**Method checks and return Mbit(Radio Mac Identifier) status.
     * @return: Boolean true if set else false
     */
    public boolean isSetMbit() {
        //return (((flagBits & 0x02) >> 1) == 1);
        return mFlag;
    }

    /**Method sets Mbit.
     */
    public void setMbit() {
        //flagBits |= 0x02;
        this.mFlag = true;
    }


    /**Method checks and return Kbit(Keep Alive) status.
     * @return: Boolean true if set else false.
     */
    public boolean isSetKbit() {
        //return ((flagBits & 0x01) == 1);
        return kFlag;
    }

    /**Method sets Kbit.
     */
    public void setKbit() {
        //flagBits |= 0x01;
        this.kFlag = true;
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
    
    /**Method to get capwap preamble.
     * @return: capwap preamble.
     */
    public byte getPreamble() {
        return preamble;
    }
    
    /**Sets the preamble.
     * @param preamble : Capwap preamble.
     */
    
    public void setPreamble(byte preamble) {
        this.preamble = preamble;
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

    public MacAddress getRadioMacAddress() {
        return radioMacAddress;
    }

    public void setRadioMacAddress(MacAddress radioMacAddress) {
        this.radioMacAddress = radioMacAddress;
    }

    public WsiInfo getWsiInfo() {
        return wsiInfo;
    }

    public void setWsiInfo(WsiInfo wsiInfo) {
        this.wsiInfo = wsiInfo;
    }
}
