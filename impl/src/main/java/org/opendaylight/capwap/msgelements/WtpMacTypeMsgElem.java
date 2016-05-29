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
 * Created by flat on 15/04/16.
 */
public class WtpMacTypeMsgElem implements ODLCapwapMessageElement {
    protected final byte local = 0;
    protected final byte split = 1;
    protected final byte both= 2;



    protected  byte type= 0;

    int msgElemType = 0;

    public void setTypeLocal(){
        this.type = this.local;
    }
    public void setTypeSplit(){
        this.type = this.split;
    }
    public void setTypeBoth(){
        this.type = this.both;
    }

    public void setType(byte type) {
        this.type = type;
    }

    public WtpMacTypeMsgElem(){
        this.msgElemType = ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_MAC_TYPE;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == this)
            return true;
        if(!(o instanceof WtpMacTypeMsgElem))
            return false;
        if((msgElemType == ((WtpMacTypeMsgElem) o).getType())&&
          (type == ((WtpMacTypeMsgElem) o).getmacType()))
        {
            return true;
        }
        return false;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.type);
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElemType;
    }
    public int getmacType() {
        return this.type;
    }
}
