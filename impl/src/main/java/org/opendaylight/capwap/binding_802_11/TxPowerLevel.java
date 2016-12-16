/*
 * Copyright (c) 2016 Brunda R Rajagopala  and others.  All rights reserved.
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
| Radio ID     | Num Levels    | Power Level [n] |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
*/


public class TxPowerLevel implements ODLCapwapMessageElement{
    int msgElem = 0;
    byte radioId = 0;
    byte numLevels = 0;
    int [] powerLevel = null;

    public TxPowerLevel ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_TX_POWER_LEVEL;
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

    public void setNumLevels (byte numLevels)
    {
        this.numLevels = numLevels;
    }

    public byte getNumLevels ()
    {
        return (this.numLevels);
    }


    public void setPowerLevel (int [] powerLevel)
    {
        if(this.numLevels != (byte)powerLevel.length)
            this.numLevels = (byte)powerLevel.length;
        this.powerLevel = new int[this.numLevels];
        for(int i = 0; i < this.numLevels; i++)
        {
            this.powerLevel[i] = powerLevel[i];
        }
    }

    public int [] getPowerLevel ()
    {
        return (this.powerLevel);
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(radioId);
        buf.writeByte(numLevels);
        for(int i = 0; i < numLevels;i++)
        buf.writeBytes(ByteManager.unsignedShortToArray(this.powerLevel[i]));
        return buf.writerIndex()-start;
    }

    @Override
    public TxPowerLevel decode(ByteBuf buf) {
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
        if (!(o instanceof TxPowerLevel))
            return false;
        if(!((this.msgElem == ((TxPowerLevel) o).getType()) &&
                (this.radioId == ((TxPowerLevel) o).getRadioId()) &&
                (this.numLevels == ((TxPowerLevel) o).getNumLevels())))
        {
            return false;
        }
        int [] tmp = ((TxPowerLevel)o).getPowerLevel ();

        for (int i = 0; i < numLevels; i++)
        {
            if (this.powerLevel[i] != tmp[i])
            {
                return false;
            }
        }
        return true;
    }
}
