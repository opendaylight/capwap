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
import org.opendaylight.capwap.msgelements.subelem.IPV4Address;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 17/04/16.
 */
public class CapwapControlIPV4Addr implements ODLCapwapMessageElement {



    IPV4Address ipv4 = null;
    int wtpCount = 0;
    int msgElm = 0;

    public CapwapControlIPV4Addr(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_CONTROL_IPV4_ADDR;
    }

    public int getWtpCount() {
        return wtpCount;
    }

    public CapwapControlIPV4Addr setWtpCount(int wtpCount) {
        this.wtpCount = wtpCount;
        return this;
    }

    public IPV4Address getIpv4() {
        return ipv4;
    }

    public CapwapControlIPV4Addr setIpv4(IPV4Address ipv4) {
        this.ipv4 = ipv4;
        return this;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start =buf.writerIndex();
        this.ipv4.encode(buf);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.wtpCount));

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
}
