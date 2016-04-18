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
public class DataTransferData implements ODLCapwapMessageElement {

    int msgElm = 0;
    short dataType = 0;
    short dataMode = 0;
    int dataLength = 0;


    byte [] data =null;


    public DataTransferData (){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DATA_TRANSFER_DATA;

    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.dataLength=this.data.length;
    }


    public short getDataType() {
        return dataType;
    }

    public DataTransferData setDataType(short dataType) {
        this.dataType = dataType;
        return this;
    }

    public short getDataMode() {
        return dataMode;
    }

    public DataTransferData setDataMode(short dataMode) {
        this.dataMode = dataMode;
        return this;
    }

    public int getDataLength() {
        return dataLength;
    }

    public DataTransferData setDataLength(int dataLength) {
        this.dataLength = dataLength;
        return this;
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(ByteManager.shortToUnsingedByte(this.dataType));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.dataMode));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.dataLength));
        buf.writeBytes(this.data);
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
