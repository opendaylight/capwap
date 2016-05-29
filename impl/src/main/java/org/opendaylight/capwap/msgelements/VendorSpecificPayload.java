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
 * Created by flat on 15/04/16.
 */
public class VendorSpecificPayload implements ODLCapwapMessageElement {

    int msgElm = 0;
    long vendorId = 0;
    int elementID = 0;
    int length = 0;
    byte[] data = null;

    public int getLength() {
        return length;
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMsgElm() {
        return msgElm;
    }

    public void setMsgElm(int msgElm) {
        this.msgElm = msgElm;
    }

    public int getElementID() {
        return elementID;
    }

    public void setElementID(int elementID) {
        this.elementID = elementID;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.length = data.length;
    }


    public VendorSpecificPayload() {
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_VENDOR_SPECIFIC_PAYLOAD;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(ByteManager.unsignedIntToArray(this.vendorId));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.elementID));
        buf.writeBytes(this.data);
        return buf.writerIndex() - start;
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
        if(o == this)
            return true;
        if(!(o instanceof VendorSpecificPayload))
            return false;
        if(!((msgElm == ((VendorSpecificPayload) o).getType()) &&
          (vendorId == ((VendorSpecificPayload) o).getVendorId())&&
          (elementID == ((VendorSpecificPayload) o).getElementID()) &&
           (length == ((VendorSpecificPayload) o).getLength())))
             return false;
        byte [] tmp = ((VendorSpecificPayload) o).getData();
        for (int i = 0; i < length; i++)
            if(data[i] != tmp[i])
                return false;
        return true;
    }
}

