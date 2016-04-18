/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements.subelem;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 16/04/16.
 */


/*

     This is a sub element used in WtpDescriptor

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                  Descriptor Vendor Identifier                 |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |        Descriptor Type        |       Descriptor Length       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                       Descriptor Data...
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 */
public class DescriptorSubElement implements ODLCapwapMessageElement {


    long vendorId = 0;
    int descType = 0;
    int descLength = 0;
    byte [] descData = null;

    public DescriptorSubElement(int descDataSize){
        this.descData = new byte[descDataSize];
        this.descLength = descDataSize;
    }

    @Override
    public int encode(ByteBuf buf) {

        int start = buf.writerIndex();

        //encode VendorID
        buf.writeBytes(ByteManager.unsignedIntToArray(this.vendorId));
        //encode Desctype
        buf.writeBytes(ByteManager.unsignedShortToArray(this.descType));
        //encode DescLength
        buf.writeBytes(ByteManager.unsignedShortToArray(this.descLength));
        //encode DescData
        buf.writeBytes(this.descData);

        return buf.writerIndex()-start;
    }


    public long getVendorId() {
        return vendorId;
    }

    public DescriptorSubElement setVendorId(long vendorId) {
        this.vendorId = vendorId;
        return this;
    }

    public int getDescType() {
        return descType;
    }

    public DescriptorSubElement setDescType(int descType) {
        this.descType = descType;
        return this;
    }

    public int getDescLength() {
        return descLength;
    }

    public DescriptorSubElement setDescLength(int descLength) {
        this.descLength = descLength;
        return this;
    }

    public byte [] getDescData() {
        return this.descData;
    }

    public DescriptorSubElement setDescData(byte[] descData) {
        this.descData = descData;
        return this;
    }



    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }
}
