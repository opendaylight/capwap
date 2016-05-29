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
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 17/04/16.
 */
public class DeleteStation implements ODLCapwapMessageElement {
    int msgElm = 0;
    short radioId = 0;
    MacAddress macAddress = null;
    public DeleteStation(short radioId,MacAddress macAddr){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DELETE_STATION;
        this.radioId = radioId;
        this.macAddress = macAddr;
    }

    public short getRadioId() {
        return radioId;
    }

    public MacAddress getMacAddress ()
    {
        return macAddress;
    }

    public DeleteStation setRadioId(short radioId) {
        this.radioId = radioId;
        return this;
    }



    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();

        buf.writeByte(ByteManager.shortToUnsingedByte(this.radioId));
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
        if (!(o instanceof DeleteStation))
            return true;
        if ((msgElm == ((DeleteStation) o).getType())&&
           (radioId == ((DeleteStation) o).getRadioId()) &&
           (macAddress.equals(((DeleteStation) o).getMacAddress())))
                   return true;
        return false;
    }
}
