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
public class ECNSupport implements ODLCapwapMessageElement {

    int msgElm = 0;
    byte ecn = 0;


    public ECNSupport(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_ECN_SUPPORT;
    }

    public byte getEcn() {
        return ecn;
    }

    public void setEcn(byte ecn) {
        this.ecn = ecn;
    }



    public int getMsgElm() {
        return msgElm;
    }

    public void setMsgElm(int msgElm) {
        this.msgElm = msgElm;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.ecn);
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
        if (!(o instanceof ECNSupport))
            return false;
        if ((msgElm == ((ECNSupport) o).getType()) &&
            (ecn == ((ECNSupport) o).getEcn()))
            return true;
        return false;
    }


}
