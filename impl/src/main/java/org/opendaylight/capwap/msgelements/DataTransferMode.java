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
public class DataTransferMode  implements ODLCapwapMessageElement {
    int msgElm = 0;

    byte mode = 0;


    public byte getMode() {
        return mode;
    }

    public void setMode(byte mode) {
        this.mode = mode;
    }

    public DataTransferMode(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DATA_TRANSFER_MODE;
    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.mode);
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
        if (!(o instanceof DataTransferMode))
            return false;

        if ((mode == ((DataTransferMode) o).getMode()) &&
                (msgElm == ((DataTransferMode) o).getType()))
        {
            return true;
        }
        return false;
    }
}
