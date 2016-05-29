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
public class CapwapTimers implements ODLCapwapMessageElement {

    int msgElm = 0;
    short discovery = 0;
    short echoReq = 0;

    public short getDiscovery() {
        return discovery;
    }

    public CapwapTimers setDiscovery(short discovery) {
        this.discovery = discovery;
        return this;
    }

    public short getEchoReq() {
        return echoReq;
    }

    public CapwapTimers setEchoReq(short echoReq) {
        this.echoReq = echoReq;
        return this;
    }


    public CapwapTimers(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_TIMERS;
    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();

        buf.writeByte(ByteManager.shortToUnsingedByte(this.discovery));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.echoReq));
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof CapwapTimers))
            return false;

        if ((echoReq == ((CapwapTimers) o).getEchoReq()) &&
            (discovery == ((CapwapTimers) o).getDiscovery()) &&
            (msgElm == ((CapwapTimers) o).getType()))
        {
            return true;
        }
        return false;
    }
    @Override
    public int getType() {
        return this.msgElm;
    }
}
