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
public class IPV4Address implements ODLCapwapMessageElement {



    byte [] address = null;


    public IPV4Address(){
        this.address = new byte[4];
    }
    @Override
    public int encode(ByteBuf buf) {
        int start  = buf.writerIndex();
        buf.writeBytes(this.address);
        return buf.writerIndex()-start;
    }

    public byte[] getAddress() {
        return address;
    }

    public void setAddress(byte[] address) {
        this.address = address;
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
        if (!(o instanceof IPV4Address))
            return false;

        byte [] tmpAddr = ((IPV4Address) o).getAddress();

        if (tmpAddr.length != address.length)
        {
            System.out.println(" IPv4ADDR length not matching");
            return false;
        }

        for (int i = 0; i < address.length; i++)
        {
            if (address[i] != tmpAddr[i])
                return false;
        }
        return true;
    }
}
