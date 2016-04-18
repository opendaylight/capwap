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
 *
 0                   1                   2
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |Resvd|  WBID   |  Encryption Capabilities      |
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 */
public class EncryptionSubElement implements ODLCapwapMessageElement {

    byte wbid = 0;

    int encryptioCapability = 0;

    public EncryptionSubElement(byte wbid,int encryptioCapability){
        this.wbid = wbid;
        this.encryptioCapability = encryptioCapability;
    }

    public byte getWbid() {
        return wbid;
    }

    public void setWbid(byte wbid) {
        this.wbid = wbid;
    }

    public int getEncryptioCapability() {
        return encryptioCapability;
    }

    public void setEncryptioCapability(int encryptioCapability) {
        this.encryptioCapability = encryptioCapability;
    }



    @Override
    public int encode(ByteBuf buf) {

        int start = buf.writerIndex();
        //encode Resvd
        //encode WBID
        wbid = (byte) (wbid & 0b00011111);
        buf.writeByte(wbid);
        //encodeEncryptionCapabilites
        buf.writeBytes(ByteManager.unsignedShortToArray(this.encryptioCapability));
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
