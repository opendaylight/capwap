/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;


import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.msgelements.UnknownMsgElm;
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.msgelements.subelem.WsiInfo;
import org.opendaylight.capwap.utils.ByteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ODLCapwapMessageFactory {

    static private byte typeMask    = 0b00001111;
    static private byte versionMask = (byte)0b11110000;
    static private byte hlenMask = (byte)0b11111000;
    static private byte ridMsbMask = 0b00000111;
    static private byte ridLsbMask = (byte)0b11000000;
    static private byte wbidMask = 0b00111110;


    static private byte tFlagMask = 0b00000001;
    static private byte fFlagMask = (byte)0b10000000;
    static private byte lFlagMask = 0b01000000;
    static private byte wFlagMask = 0b00100000;
    static private byte mFlagMask = 0b00010000;
    static private byte kFlagMask = 0b00001000;

    private static final Logger LOG = LoggerFactory.getLogger(ODLCapwapMessageFactory.class);

    public static ODLCapwapMessage decodeFromByteArray(ByteBuf buf) {

        ODLCapwapMessage msg = null;

        if((buf == null)){
            LOG.error("ByteBuf is null ");
            return null;
        }

        msg = new ODLCapwapMessage();
        msg.header = decodeHeader(buf);

        if (msg.header == null) {
            LOG.error("Error in decoding Header");
            return null;
        }

        msg.ctrlMsg = decodeCtrlMsg(buf);
        if(msg.ctrlMsg == null){
            LOG.error("Error in decoding Control Message ");
            return null;
        }
        return msg;

    }

    /*
         0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                       Message Type                            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |    Seq Num    |        Msg Element Length     |     Flags     |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | Msg Element [0..N] ...
     +-+-+-+-+-+-+-+-+-+-+-+-+
      */

     public static  ODLCapwapControlMessage decodeCtrlMsg(ByteBuf buf) {

         ODLCapwapControlMessage ctrlMsg = null;
         int msgElemLength = 0;

         if((buf == null)){
             LOG.error("ByteBuf is null ");
             return null;
         }
         if( !buf.isReadable()){
             LOG.error("ByteBuf is not readable ");
             return null;
         }

         ctrlMsg = new ODLCapwapControlMessage();
         //read message Type
         if (buf.isReadable()) {
             byte[] msgType = new byte []{0,0,0,0};
             buf.readBytes(msgType);
             ctrlMsg.setMsgType(ByteManager.byteArrayToUnsingedInt(msgType));
         }
         if (buf.isReadable()) {
             byte seqNum =0;
             seqNum = buf.readByte();
             ctrlMsg.setSeqNo(ByteManager.unsignedByteToshort(seqNum));
         }


         if (buf.isReadable()) {
             byte [] msgElmLength = new byte[] {0,0};
             buf.readBytes(msgElmLength);
             ctrlMsg.setMsgLen(ByteManager.byteArrayToUnsingedShort(msgElmLength));
         }

         byte skip = buf.readByte(); //skip flags

         int lengthOfMsgElemts = ctrlMsg.getMsgLen();
         //decode each message element
         int index = 0;
         while(index <lengthOfMsgElemts){
             int type = 0; byte [] type_arr = new byte []{0,0};
             int length = 0;byte [] length_arr = new byte []{0,0};
             ODLCapwapMessageElement e = null;

             buf.readBytes(type_arr);
             type=ByteManager.byteArrayToUnsingedShort(type_arr);
             buf.readBytes(length_arr);
             length=ByteManager.byteArrayToUnsingedShort(length_arr);
             e = decodeEachMsgElement(buf,type,length);

             //add msg element to the list of msg elements
             ctrlMsg.addMessageElement(e);
             index= index + length+4;


         }


         return ctrlMsg;
     }

     /*
     0                   1                   2                   3
             0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
             +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
             |              Type             |             Length            |
             +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
             |   Value ...   |
             +-+-+-+-+-+-+-+-+
*/

     public static  ODLCapwapMessageElement decodeEachMsgElement(ByteBuf buf , int dType , int length){

         ODLCapwapMessageElement elm= null;
         //Now we are at the begining of msg element value
         if((buf == null) || !buf.isReadable()){
             return null;
         }

         switch(dType){

             default:
                 return decodeUnknownMsgElm(buf,dType,length);


         }

     }

     static ODLCapwapMessageElement decodeUnknownMsgElm(ByteBuf buf,int dType, int length){
         UnknownMsgElm u= new UnknownMsgElm();
         byte [] val = new byte [length];
         u.setMsgElm(dType);
         buf.readBytes(val);
         u.setVal( val);
         return u;
     }



    public static  ODLCapwapHeader decodeHeader(ByteBuf buf) {
        ODLCapwapHeader capwapHeader =  null;
        MacAddress macAddress = null;
        WsiInfo wsiInfo = null;
        byte [] firstWord = new byte []{0,0,0,0};
        byte [] secondWord = new byte []{0,0,0,0};

        if((buf == null)){
            LOG.error("ByteBuf is null ");
            return null;
        }
        if( !buf.isReadable()){
            LOG.error("ByteBuf is not readable ");
            return null;
        }
        capwapHeader = new ODLCapwapHeader();

        if (buf.isReadable()){
            //get first word and decode it
            buf.readBytes(firstWord);
            decodeFirstWord(firstWord,capwapHeader);

        }

        if (buf.isReadable()){
            //get second word and decode it
            buf.readBytes(secondWord);
            decodeSecondWord(secondWord,capwapHeader);
        }

        //decode  WSI by this time we know whether  WSI bit is set in the Header object

        if(capwapHeader.isSetMbit()){
            //first get the length
            int numPadded = 0;
            int length = ByteManager.unsignedByteToshort(buf.readByte());

            numPadded = getPaddingint(length+1,4);

            //now reset the reader index = readerIndex-1

            buf.setIndex(buf.readerIndex()-1,buf.writerIndex());

            capwapHeader.setRadioMacAddress(ODLCapwapMessageElementFactory.decodeMacAddress(buf));
            //Skip the padding
            buf.setIndex(buf.readerIndex()+numPadded,buf.writerIndex());
        }

        if (capwapHeader.isSetWbit()){
            //first get the length
            int numPadded = 0;
            int length = ByteManager.unsignedByteToshort(buf.readByte());

            numPadded = getPaddingint(length+1,4);

            //now reset the reader index = readerIndex-1

            buf.setIndex(buf.readerIndex()-1,buf.writerIndex());

            capwapHeader.setWsiInfo(ODLCapwapMessageElementFactory.decodeWsiInfo(buf));
            //Skip the padding
            buf.setIndex(buf.readerIndex()+numPadded,buf.writerIndex());

        }

        //make sure that Reader index is at the header length

        return capwapHeader;
    }

    static int getPaddingint(int length_of_last_encoding , int word_size){
        int numBytesToBepadded = 0;
        //Find our the number of bytes to be padded . for example word size 4. Length of last encoding 7
        // then number of bytes to be padded is  7%4  ie 3 . ie 3 bytes are there in the unaligned  word . we need to
        // add one more byte to get a 4 byte alingement
        numBytesToBepadded = word_size-(length_of_last_encoding % word_size);

        return  numBytesToBepadded;
    }



    private static void decodeFirstWord(byte[] word, ODLCapwapHeader capwapHeader){
        //decode type
        capwapHeader.setType((byte)(word[0] & typeMask));
        //decode version
        capwapHeader.setVersion((byte) (word[0] & versionMask));
        //decode hlen . The actual header length . not the one divided by 4.
        capwapHeader.setHlen((byte) ((word[1] & hlenMask ) >>>1)); //actually >> 3 , but hlen is acutal length/4 hence 3-2 ie >>>1
        //decode rid
        //recreate rid from word[1] and word[2]  stuff
        capwapHeader.setRid((byte) (((word[1] & ridMsbMask) << 2 ) |  (word[2] & ridLsbMask)));
        //decode wbid
        capwapHeader.setWbid((byte) (word[2] & wbidMask));
        //get TFlag
        if(0!= (word[2] & tFlagMask)) capwapHeader.setTbit();
        //get FFlag
        if(0!= (word[2] & fFlagMask)) capwapHeader.setFbit();
        //get LFlag
        if(0!= (word[3] & lFlagMask)) capwapHeader.setLbit();
        //get WFlag
        if(0!= (word[3] & wFlagMask)) capwapHeader.setWbit();
        //get MFlag
        if(0!= (word[3] & mFlagMask)) capwapHeader.setMbit();
        //get KFlag
        if(0!= (word[3] & kFlagMask)) capwapHeader.setKbit();

    }
    private static void decodeSecondWord(byte[] word, ODLCapwapHeader capwapHeader){
        byte [] byte2 = new byte[] {0,0};
        byte2[0] = word[0];
        byte2[1] = word[1];
        capwapHeader.setFragId(ByteManager.byteArrayToUnsingedShort(byte2));
        byte2[0] = word[2];
        byte2[1] = word[3];
        capwapHeader.setFragOffset(ByteManager.byteArrayToUnsinged13(byte2));
    }



}
