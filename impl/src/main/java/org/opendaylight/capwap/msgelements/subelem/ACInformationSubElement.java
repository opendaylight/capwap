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
public class ACInformationSubElement implements ODLCapwapMessageElement {
    long acInfoVendorId = 0L;
    int acInfoType = 0;
    int acInfoLength = 0;
    byte [] acInfoData = null;


    public long getAcInfoVendorId() {
        return acInfoVendorId;
    }

    public void setAcInfoVendorId(long acInfoVendorId) {
        this.acInfoVendorId = acInfoVendorId;
    }

    public int getAcInfoType() {
        return acInfoType;
    }

    public void setAcInfoType(int acInfoType) {
        this.acInfoType = acInfoType;
    }

    public int getAcInfoLength() {
        return acInfoLength;
    }

    public void setAcInfoLength(int acInfoLength) {
        this.acInfoLength = acInfoLength;
    }

    public byte[] getAcInfoData() {
        return acInfoData;
    }

    public void setAcInfoData(byte[] acInfoData) {
        this.acInfoData = acInfoData;
    }

    public ACInformationSubElement(int length){
        this.acInfoLength = length;
        this.acInfoData = new byte[length];

    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        //Vendor info
        buf.writeBytes(ByteManager.unsignedIntToArray(this.acInfoVendorId));
        //InfoType
        buf.writeBytes(ByteManager.unsignedShortToArray(this.acInfoType));
        //AcLength
        buf.writeBytes(ByteManager.unsignedShortToArray(this.acInfoLength));
        //Ac info data
        buf.writeBytes(this.acInfoData);
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }
}
