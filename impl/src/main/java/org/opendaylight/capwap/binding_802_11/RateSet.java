/*
 * Copyright (c) 2016 Brunda R Rajagopala and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.binding_802_11;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessageElement;

/**
 * Created by flat on 15/04/16.
 */

/* 
0                   1                   2                   3
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Radio ID     | Rate Set...
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
*/
public class RateSet implements ODLCapwapMessageElement{
    int msgElem = 0;
    byte radioId = 0;
    byte [] rateSet = null; // int array ?

    public RateSet ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_RATE_SET;
    }

    public void setMsgElem (int msgElem)
    {
        this.msgElem = msgElem;
    }

    public int getMsgElem ()
    {
        return (this.msgElem);
    }

    public void setRadioId(byte radioId)
    {
        this.radioId = radioId;
    }


    public byte getRadioId ()
    {
        return (this.radioId);
    }

    public void setRateSet (byte [] rateSet)
    {
        this.rateSet = new byte[rateSet.length];
        for(int i = 0; i < rateSet.length; i++)
            this.rateSet[i] =  rateSet[i];
    }

    public byte [] getRateSet ()
    {
        return (this.rateSet);
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(radioId);
        buf.writeBytes(rateSet);
        return buf.writerIndex()-start;
    }

    @Override
    public RateSet decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return (this.msgElem);
    }

    @Override
    public boolean equals(Object o)
    {
        System.out.println ("equals in RateSet");
        if (o == this)
            return true;
        if (!(o instanceof RateSet))
            return false;
        if(!((this.msgElem == ((RateSet) o).getType()) &&
                (this.radioId == ((RateSet) o).getRadioId())))
        {
            return false;
        }

        byte [] tmp = ((RateSet)o).getRateSet();
        if (this.rateSet.length != (((RateSet) o).getRateSet()).length)
            return false;
        for (int i = 0; i < rateSet.length; i++)
        {
            if (this.rateSet[i] != tmp[i])
            {
                return false;
            }
        }
        return true;
    }
}
