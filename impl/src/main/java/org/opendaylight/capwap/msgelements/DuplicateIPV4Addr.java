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
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 17/04/16.
 */
public class DuplicateIPV4Addr implements ODLCapwapMessageElement {

    int msgElm = 0;

    IPV4Address ipv4 = null;
    short status = 0;

    public IPV4Address getIpv4() {
        return ipv4;
    }

    public DuplicateIPV4Addr setIpv4(IPV4Address ipv4) {
        this.ipv4 = ipv4;
        return this;
    }

    public short getStatus() {
        return status;
    }

    public DuplicateIPV4Addr setStatus(short status) {
        this.status = status;
        return this;
    }

    public MacAddress getMacAddress() {
        return macAddress;
    }

    public DuplicateIPV4Addr setMacAddress(MacAddress macAddress) {
        this.macAddress = macAddress;
        return this;
    }

    MacAddress macAddress = null;



    public DuplicateIPV4Addr(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DUPLICATE_IPV4_ADDRESS;

    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        this.ipv4.encode(buf);
        buf.writeByte(ByteManager.shortToUnsingedByte(this.getStatus()));
        this.macAddress.encode(buf);
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
        if (!(o instanceof DuplicateIPV4Addr))
            return false;
        if ((msgElm == ((DuplicateIPV4Addr) o).getType()) &&
           (ipv4.equals(((DuplicateIPV4Addr) o).getIpv4())) &&
           (macAddress.equals(((DuplicateIPV4Addr) o).getMacAddress())))
            return true;
        return false;
    }
}
