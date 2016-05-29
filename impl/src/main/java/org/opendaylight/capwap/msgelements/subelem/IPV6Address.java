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

/**
 * Created by flat on 16/04/16.
 */
public class IPV6Address implements ODLCapwapMessageElement {

    byte [] address = null;

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
    }

    public IPV6Address(){
        this.address = new byte[16];
    }

    @Override
    public int encode(ByteBuf buf) {
        int start  = buf.writerIndex();

        buf.writeBytes(this.address);
        return (buf.writerIndex()-start);
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }

    @Override
    public boolean equals (Object o)
    {

        if (o == this)
        {
            return true;
        }
        if (!(o instanceof IPV6Address))
            return false;

        byte [] tmpAddr = ((IPV6Address) o).getAddress();

        if (tmpAddr.length != address.length)
            return false;

        for (int i = 0; i < address.length; i++)
        {
            if (address[i] != tmpAddr[i])
                return false;
        }
        return true;
    }
}
