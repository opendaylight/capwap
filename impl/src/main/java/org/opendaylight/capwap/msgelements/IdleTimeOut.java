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
public class IdleTimeOut  implements ODLCapwapMessageElement {
    int msgElm = 0;
    long timout = 0;

    public  IdleTimeOut(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_IDLE_TIMEOUT;
    }

    public long getTimout() {
        return timout;
    }

    public void setTimout(long timout) {
        this.timout = timout;
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(ByteManager.unsignedIntToArray(this.timout));
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
        if(!(o instanceof IdleTimeOut))
            return false;
        if ((msgElm == ((IdleTimeOut) o).getType()) &&
           (timout == ((IdleTimeOut) o).getTimout()))
            return true;
        return false;
    }

}
