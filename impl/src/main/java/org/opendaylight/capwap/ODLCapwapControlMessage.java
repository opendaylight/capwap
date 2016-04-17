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

import java.util.ArrayList;
import java.util.Iterator;

public class ODLCapwapControlMessage {
    long msgType;
    short seqNo;
    int msgLen;
    short flags;
    protected ArrayList<ODLCapwapMessageElement> elements;
    
    ODLCapwapControlMessage() {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
    }
    
    ODLCapwapControlMessage(long msgType,
                            short seqNo,
                            int msgLen,
                            short flags,
                            ArrayList<ODLCapwapMessageElement> elements) {
        this.msgType = msgType;
        this.seqNo = seqNo;
        this.msgLen = msgLen;
        this.flags = flags;
        this.elements = elements;
    }
    
    public static ODLCapwapControlMessage decode(ByteBuf buf) {
        //ByteBuf bbuf = Unpooled.wrappedBuffer(buf);
        //ByteBuf tmpbuf = bbuf.readerIndex(pos);
        
        long msgType = buf.readUnsignedInt();
        short seqNo = buf.readUnsignedByte();
        int msgLen = buf.readUnsignedShort();
        short flags = buf.readUnsignedByte();
        ArrayList<ODLCapwapMessageElement> elements = ODLCapwapMessageElementFactory.decodeFromByteBuf(buf);

        return new ODLCapwapControlMessage(msgType, seqNo, msgLen, flags, elements);
    }
    
    boolean encode(ByteBuf buf) {
        buf.writeInt((int)msgType); 
        buf.writeByte((byte)seqNo);
        buf.writeShort((short)msgLen);
        buf.writeByte((byte)flags);
        return true;
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

    public ODLCapwapMessageElement findMessageElement(int elementType) {
        Iterator<ODLCapwapMessageElement> iter = elements.iterator();
        while (iter.hasNext()) {
            ODLCapwapMessageElement element = iter.next();
            if (element.getType() == elementType) {
                return element;
            }
        }
        return null;
    }
}
