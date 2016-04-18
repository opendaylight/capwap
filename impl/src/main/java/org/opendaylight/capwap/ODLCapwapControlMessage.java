/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;

import java.util.ArrayList;

public class ODLCapwapControlMessage {
    long msgType;
    short seqNo;
    int msgLen;
    short flags;
    protected ArrayList<ODLCapwapMessageElement> elements =null;


    ODLCapwapControlMessage() {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
        this.elements = new ArrayList<ODLCapwapMessageElement>();
    }
   /*
    ODLCapwapControlMessage(ByteBuf bbuf) {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
    }
    */
    
    int decode(ByteBuf buf) {
        //ByteBuf bbuf = Unpooled.wrappedBuffer(buf);
        //ByteBuf tmpbuf = bbuf.readerIndex(pos);
        
        this.msgType = buf.readUnsignedInt();
        this.seqNo = buf.readUnsignedByte();
        this.msgLen = buf.readUnsignedShort();
        this.flags = buf.readUnsignedByte();
    
        return 0;
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
    public  int encode(ByteBuf buf) {

        int start = buf.writerIndex();
        int end = 0;
        int msgLengthIndex = 0;
        int msgElemLength = 0;

        buf.writeInt((int)this.msgType);
        buf.writeByte((byte)this.seqNo);
        msgLengthIndex = buf.writerIndex();
        System.out.printf("\nWriter index @ function %d  %s",msgLengthIndex,"encodeCtrl");
        buf.setIndex(buf.readerIndex(),buf.writerIndex()+2);
        System.out.printf("\nWriter index @ function after incrementing %d  %s",buf.writerIndex(),"encodeCtrl");

        buf.writeByte((byte)this.flags);

        /* Start decoding message Element List */

        //store the location to write the messageLength

        msgElemLength = encodeMsgElements(buf);

        //Set the Message lement length
        buf.setShort(msgLengthIndex,msgElemLength);

        end = buf.writerIndex();
        System.out.printf("\nWriter index @ function %d  %s",msgLengthIndex,"encodeCtrl");
        return end-start;

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

    private int encodeMsgElements(ByteBuf buf){

        int start = buf.writerIndex();
        System.out.printf("\nWriter index @ function %d  %s",buf.writerIndex(),"encodeMsgElements");
        int end = 0;

        for (ODLCapwapMessageElement element:this.elements ) {
            int lengthIndex = 0;
            int length = 0;

            buf.writeShort(element.getType());
            lengthIndex = buf.writerIndex();
            buf.setIndex(buf.readerIndex(),buf.writerIndex()+2);

            int len = element.encode(buf);

            System.out.printf("\nLength of Msg element   %d",len);
            buf.setShort(lengthIndex,len);
        }
        end = buf.writerIndex();
        return end-start;

    }

    public int getMessageType() {
        return (int)msgType;
    }

    public void setMsgType(int msgType) {
        this.msgType = msgType;
    }

    public short getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(short seqNo) {
        this.seqNo = seqNo;
    }

    public short getMsgLen() {
        return (short)msgLen;
    }

    public void setMsgLen(short msgLen) {
        this.msgLen = msgLen;
    }

    public byte getFlags() {
        return (byte)flags;
    }

    public void setFlags(byte flags) {
        this.flags = flags;
    }

    public boolean addMessageElement(ODLCapwapMessageElement e) {
        this.elements.add(e);

        return true;
    }

}
