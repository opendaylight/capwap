/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 17/04/16.
 */
public class ReturnedMessageElement implements ODLCapwapMessageElement {
    int msgElm = 0;
    short reason = 0;
    short length = 0;



    byte[] msgElement= null;

    public ReturnedMessageElement(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_RETURNED_MESSAGE_ELEMENT;
    }


    public int getMsgElm() {
        return msgElm;
    }

    public void setMsgElm(byte[] msgElm) {
        this.msgElement = msgElm;
    }

    public void setMsgElm(int msgElm) {
        this.msgElm = msgElm;
    }

    public short getReason() {
        return reason;
    }

    public void setReason(short reason) {
        this.reason = reason;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public byte[] getMsgElement() {
        return msgElement;
    }

    public void setMsgElement(byte[] msgElement) {
        this.msgElement = msgElement;
        this.length = (short) msgElement.length;
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();

        buf.writeByte(ByteManager.shortToUnsingedByte(this.reason));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.length));
        buf.writeBytes(this.msgElement);

        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElm;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof ReturnedMessageElement))
            return false;
        if (!((msgElm == ((ReturnedMessageElement) o).getType()) &&
           (length == ((ReturnedMessageElement) o).getLength())&&
           (reason == ((ReturnedMessageElement) o).getReason())))
            return false;
        byte [] tmp = ((ReturnedMessageElement) o).getMsgElement();
        for (int i=0; i < length; i++)
        {
            if (msgElement[i] != tmp[i])
                return false;
        }
        return true;
    }
}
