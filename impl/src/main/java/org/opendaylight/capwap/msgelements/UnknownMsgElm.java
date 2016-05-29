/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.capwap.msgelements;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapMessageElement;

/**
 * Created by flat on 18/04/16.
 */
public class UnknownMsgElm implements ODLCapwapMessageElement {
    int msgElm = 0;
    byte [] val =null;

    public UnknownMsgElm(){

    }


    public int getMsgElm() {
        return msgElm;
    }

    public void setMsgElm(int msgElm) {
        this.msgElm = msgElm;
    }

    public byte[] getVal() {
        return val;
    }

    public void setVal(byte[] val) {
        this.val = val;
    }


    @Override
    public int encode(ByteBuf buf) {
        return 0;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.getMsgElm();
    }


    @Override
    public boolean equals (Object o)
    {
        if (o == this)
            return true;
        if( !(o instanceof UnknownMsgElm))
            return false;
        if(msgElm != ((UnknownMsgElm) o).getType())
            return false;
        byte [] tmp = ((UnknownMsgElm) o).getVal();

        if(val.length != tmp.length)
            return false;
        for (int i = 0; i < val.length; i++)
            if(val[i] != tmp[i])
                return false;
        return true;



    }
}
