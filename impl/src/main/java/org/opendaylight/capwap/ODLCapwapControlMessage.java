/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import org.opendaylight.capwap.ODLCapwapConsts;

public class ODLCapwapControlMessage {
    long msgType;
    short seqNo;
    int msgLen;
    short flags;
    
    ODLCapwapControlMessage() {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
    }
    
    ODLCapwapControlMessage(ByteBuf bbuf) {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
    }
    
    int decode(ByteBuf buf) {
        //ByteBuf bbuf = Unpooled.wrappedBuffer(buf);
        //ByteBuf tmpbuf = bbuf.readerIndex(pos);
        
        this.msgType = buf.readUnsignedInt();
        this.seqNo = buf.readUnsignedByte();
        this.msgLen = buf.readUnsignedShort();
        this.flags = buf.readUnsignedByte();
    
        return 0;
    }
    
    void encode(ByteBuf buf) {
        buf.writeInt((int)msgType); 
        buf.writeByte((byte)seqNo);
        buf.writeShort((short)msgLen);
        buf.writeByte((byte)flags);
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
      
}
