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
0 1 2 3
        0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Radio ID | Reserved | RTS Threshold |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Short Retry | Long Retry | Fragmentation Threshold |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Tx MSDU Lifetime |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Rx MSDU Lifetime |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        Type: 1030 for IEEE 802.11 MAC Operation
        Length: 16
*/
public class MacOperation implements ODLCapwapMessageElement{
    int msgElem = 0;
    byte radioId = 0;
    byte reserved = 0;
    int rtsThreshold = 0;
    int shortRetry = 0;
    int longRetry = 0;
    int fragmentThreshold = 0;
    long txMsduLifeTime = 0;
    long rxMsduLifeTime = 0;




    public MacOperation ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_MAC_OPERATION;
        this.rtsThreshold = 2347;
        this.shortRetry = 7;
        this.longRetry = 4;
        this.txMsduLifeTime = 512;
        this.rxMsduLifeTime = 512;
    }

    public void setMsgElem (int msgElem)
    {
        this.msgElem = msgElem;
    }

    public int getMsgElem ()
    {
        return (this.msgElem);
    }

    public void setRadioId (byte radioId)
    {
        this.radioId = radioId;
    }

    public byte getRadioId ()
    {
        return (this.radioId);
    }

    public byte getReserved ()
    {
        return (this.reserved);
    }

    public void setRtsThreshold (int rtsThreshold)
    {
        this.rtsThreshold =  rtsThreshold;
    }

    public int getRtsThreshold ()
    {
        return (this.rtsThreshold);
    }

    public void setShortRetry (int shortRetry)
    {
        this.shortRetry = shortRetry;
    }

    public int getShortRetry ()
    {
        return (this.shortRetry);
    }

    public void setLongRetry (int longRetry)
    {
        this.longRetry = longRetry;
    }

    public int getLongRetry ()
    {
        return (this.longRetry);
    }

    public void setFragmentationThreshold (int fragmentThreshold)
    {
        this.fragmentThreshold =  fragmentThreshold;
    }

    public int getFragmentationThreshold ()
    {
        return (this.fragmentThreshold);
    }

    public void setTxMsduLifeTime (long txMsduLifeTime)
    {
        this.txMsduLifeTime =  txMsduLifeTime;
    }

    public long getTxMsduLifeTime ()
    {
        return (this.txMsduLifeTime);
    }


    public void setRxMsduLifeTime (long rxMsduLifeTime)
    {
        this.rxMsduLifeTime =  rxMsduLifeTime;
    }

    public long geRxMsduLifeTime ()
    {
        return (this.rxMsduLifeTime);
    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.reserved);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.rtsThreshold));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.shortRetry));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.longRetry));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.fragmentThreshold));
        buf.writeBytes(ByteManager.unsignedIntToArray(this.txMsduLifeTime));
        buf.writeBytes(ByteManager.unsignedIntToArray(this.rxMsduLifeTime));

        return buf.writerIndex()-start;
    }

    @Override
    public MacOperation decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return (this.msgElem);
    }
    @Override
    public boolean equals(Object o)
    {
        System.out.println("equals");
        if (o == this)
            return true;
        if (!(o instanceof MacOperation))
            return false;


        if (!((this.msgElem == ((MacOperation) o).getMsgElem()) &&
        (this.radioId == ((MacOperation) o).getRadioId()) &&
        (this.reserved == ((MacOperation) o).getReserved()) &&
        (this.rtsThreshold == ((MacOperation) o).getRtsThreshold())&&
        (this.shortRetry == ((MacOperation) o).getShortRetry()) &&
        (this.longRetry == ((MacOperation) o).getLongRetry()) &&
        (this.fragmentThreshold == ((MacOperation) o).getFragmentationThreshold()) &&
        (this.txMsduLifeTime == ((MacOperation) o).getTxMsduLifeTime()) &&
        (this.rxMsduLifeTime == ((MacOperation) o).geRxMsduLifeTime())))
        {
            return (false);
        }
        return true;
    }

}

