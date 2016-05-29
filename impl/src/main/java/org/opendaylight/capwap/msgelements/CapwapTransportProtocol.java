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

/**
 * Created by flat on 17/04/16.
 */
public class CapwapTransportProtocol implements ODLCapwapMessageElement {
    int msgElm = 0;


    byte protocol = 0;
    public  CapwapTransportProtocol(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_TRANSPORT_PROTO;
    }


    public byte getProtocol() {
        return protocol;
    }

    public void setProtocol(byte protocol) {
        this.protocol = protocol;
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.protocol);
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
        if (!(o instanceof CapwapTransportProtocol))
            return false;

        if ((protocol == ((CapwapTransportProtocol) o).getProtocol()) &&
            (msgElm == ((CapwapTransportProtocol) o).getType()))
        {
            return true;
        }
        return false;
    }
}
