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
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 15/04/16.
 */
/*
0                   1                   2                   3
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Radio ID | Supported Rates...
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
*/

public class SupportedRates implements ODLCapwapMessageElement {
    int msgElem = 0;
    byte radioId = 0;
    byte [] supportedRates = null;

    public SupportedRates ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_SUPPORTED_RATES;
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

    public void setSupportedRates (byte [] supportedRates)
    {
        this.supportedRates = new byte[supportedRates.length];
        for(int i = 0; i < supportedRates.length; i++)
            this.supportedRates[i] =  supportedRates[i];
    }

    public byte [] getSupportedRates ()
    {
        return (this.supportedRates);
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(radioId);
        buf.writeBytes(supportedRates);
        return buf.writerIndex()-start;
    }

    @Override
    public SupportedRates decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return (this.msgElem);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof SupportedRates))
            return false;
        if(!((this.msgElem == ((SupportedRates) o).getType()) &&
                (this.radioId == ((SupportedRates) o).getRadioId())))
        {
            return false;
        }

        byte [] tmp = ((SupportedRates)o).getSupportedRates();
        if (this.supportedRates.length != (((SupportedRates) o).getSupportedRates()).length)
            return false;
        for (int i = 0; i < supportedRates.length; i++)
        {
            if (this.supportedRates[i] != tmp[i])
            {
                return false;
            }
        }
        return true;
    }

}
