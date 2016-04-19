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
 * Created by flat on 18/04/16.
 */
public class WsiInfo  implements ODLCapwapMessageElement{

    int msgElemeType = 0;
    public byte []  data=null;
    public short length = 0;

    public WsiInfo(){
        this.msgElemeType = 0;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.length = (short) this.data.length;
    }

    public short getLength() {
        return length;
    }

    public void setLength(short length) {
        this.length = length;
    }

    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(ByteManager.shortToUnsingedByte(this.length));
        buf.writeBytes(this.data);

        return buf.writerIndex()-start;
    }

     public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    public int getType() {
        return this.msgElemeType;
    }


}
