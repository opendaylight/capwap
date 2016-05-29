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
public class MacAddress  implements ODLCapwapMessageElement {

    int msgElemeType = 0;



    public byte [] address =null;

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public short length = 0;

    public MacAddress(short length){
        this.msgElemeType = 0;
        this.length=length;
        this.address = new byte[length];

    }
    public MacAddress(){
        this.msgElemeType = 0;
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
        this.length = (short)address.length;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(ByteManager.shortToUnsingedByte(this.length));
        buf.writeBytes(this.address);

        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElemeType;
    }
    @Override
    public boolean equals (Object o)
    {

        if (o == this)
        {
            return true;
        }
        if (!(o instanceof MacAddress))
            return false;

        if (length != ((MacAddress) o).getLength())
            return false;

        byte [] tmpAddr = ((MacAddress) o).getAddress();

        if (msgElemeType != ((MacAddress) o).msgElemeType)
            return false;

        //if (tmpAddr.length != address.length)
        //    return false;

        for (int i = 0; i < length; i++)
        {
            if (address[i] != tmpAddr[i])
                return false;
        }
        return true;
    }
}
