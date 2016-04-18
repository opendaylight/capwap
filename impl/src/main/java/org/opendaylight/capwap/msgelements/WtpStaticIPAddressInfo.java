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
 * Created by flat on 16/04/16.
 */
public class WtpStaticIPAddressInfo implements ODLCapwapMessageElement {

    IPV4Address address = null;
    IPV4Address netmask = null;
    IPV4Address gateway = null;
    short _static = 0;
    int msgElemType = 0;

    WtpStaticIPAddressInfo(){
        this.address = new IPV4Address();
        this.netmask = new IPV4Address();
        this.gateway = new IPV4Address();
        this.msgElemType = ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_STATIC_IP_ADDR_INFO;
    }




    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        this.address.encode(buf);
        this.netmask.encode(buf);
        this.gateway.encode(buf);
        buf.writeByte(ByteManager.shortToUnsingedByte(this._static));
        return 0;
    }


    public IPV4Address getAddress() {
        return address;
    }

    public WtpStaticIPAddressInfo setAddress(IPV4Address address) {
        this.address = address;
        return this;
    }

    public IPV4Address getNetmask() {
        return netmask;
    }

    public WtpStaticIPAddressInfo setNetmask(IPV4Address netmask) {
        this.netmask = netmask;
        return this;
    }

    public IPV4Address getGateway() {
        return gateway;
    }

    public WtpStaticIPAddressInfo setGateway(IPV4Address gateway) {
        this.gateway = gateway;
        return this;
    }

    public short get_static() {
        return _static;
    }

    public WtpStaticIPAddressInfo set_static(short _static) {
        this._static = _static;
        return this;
    }

    public WtpStaticIPAddressInfo setMsgElemType(int msgElemType) {
        this.msgElemType = msgElemType;
        return this;
    }


    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElemType ;
    }
}
