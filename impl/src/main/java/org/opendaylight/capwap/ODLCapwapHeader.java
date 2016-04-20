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
    private byte hlen=0; // when you set 5 bit value mask out top 3 bit for safety
    private byte rid=0; // when you set 5 bit value mask out top 3 bit for safety
    private byte wbid = 0;// when you set 5 bit valuek mas out top 3 bit for safety



    private int fragId = 0;
    private int fragOffset = 0;
    MacAddress radioMacAddress = null;
    WsiInfo wsiInfo = null;

    private boolean tFlag = false;
    private boolean fFlag = false;
    private boolean lFlag = false;
    private boolean wFlag = false;
    private boolean mFlag = false;
    private boolean kFlag = false;



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
        word[1] |= (hlen>>2) <<3;
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
        byte [] fragoffset  = ByteManager.unsigned13bitsToArray(this.fragOffset);

        //setFragOffset
        word[2] = fragoffset[0];
        word[3] = fragoffset[1];
        return word;
    }




    /**Method converts Capwap header encoder into byte array.
     * @return true: success, false: failure
     */
    public int encodeHeader(ByteBuf bbuf) {
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
        //this.hlen =(byte)( hlen >>2);
        this.hlen =(byte)( hlen);

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
    public ODLCapwapHeader setTbit() {
        //flagBits |= 0x20;
        this.tFlag = true;
        return this;
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
    public ODLCapwapHeader setFbit()
    {
        //this.flagBits |= 0x10;
        this.fFlag = true;
        return this;
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
    public ODLCapwapHeader setLbit() {
        //flagBits |= 0x08;
        this.lFlag = true;
        return this;
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
    public ODLCapwapHeader setWbit() {
        //flagBits |= 0x04;
        this.wFlag = true;
        return this;
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
    public ODLCapwapHeader setMbit() {
        //flagBits |= 0x02;
        this.mFlag = true;
        return this;
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
    public ODLCapwapHeader setKbit() {
        //flagBits |= 0x01;
        this.kFlag = true;
        return this;
    }

    public byte getPreamble() {
        return preamble;
    }

    public ODLCapwapHeader setPreamble(byte preamble) {
        this.preamble = preamble;
        return this;
    }

    public byte getType() {
        return type;
    }

    public ODLCapwapHeader setType(byte type) {
        this.type = type;
        return this;
    }

    public byte getVersion() {
        return version;
    }

    public ODLCapwapHeader setVersion(byte version) {
        this.version = version;
        return this;
    }

    public byte getHlen() {
        return hlen;
    }

    public ODLCapwapHeader setHlen(byte hlen) {
        this.hlen = hlen;
        return this;
    }

    public byte getRid() {
        return rid;
    }

    public ODLCapwapHeader setRid(byte rid) {
        this.rid = rid;
        return this;
    }

    public byte getWbid() {
        return wbid;
    }

    public ODLCapwapHeader setWbid(byte wbid) {
        this.wbid = wbid;
        return this;
    }

    public int getFragId() {
        return fragId;
    }

    public ODLCapwapHeader setFragId(int fragId) {
        this.fragId = fragId;
        return this;
    }

    public int getFragOffset() {
        return fragOffset;
    }

    public ODLCapwapHeader setFragOffset(int fragOffset) {
        this.fragOffset = fragOffset;
        return this;
    }

    public MacAddress getRadioMacAddress() {
        return radioMacAddress;
    }

    public ODLCapwapHeader setRadioMacAddress(MacAddress radioMacAddress) {
        this.radioMacAddress = radioMacAddress;
        return this;
    }

    public WsiInfo getWsiInfo() {
        return wsiInfo;
    }

    public ODLCapwapHeader setWsiInfo(WsiInfo wsiInfo) {
        this.wsiInfo = wsiInfo;
        return this;
    }

}
