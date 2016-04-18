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
public class LocationData implements ODLCapwapMessageElement {
    int msgElm = 0;
    byte[] locationData = null;
    int length = 0;

    public LocationData(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_LOCATION_DATA;
    }
    public byte[] getLocationData() {
        return locationData;
    }

    public void setLocationData(byte[] locationData) {
        this.locationData = locationData;
        this.length = locationData.length;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(this.locationData);
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
