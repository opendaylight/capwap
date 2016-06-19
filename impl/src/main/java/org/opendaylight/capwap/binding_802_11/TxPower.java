/*
 * Copyright (c) 2016 Brunda R Rajagoapala and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.binding_802_11;
import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 15/04/16.
 */

/*

0 1 2 3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Radio ID      | Reserved    | Current Tx Power                |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    Type: 1041 for IEEE 802.11 Tx Power


    Length: 4

    Radio ID: An 8-bit value representing the radio to configure, whose
    value is between one (1) and 31.

    Reserved: All implementations complying with this protocol MUST set
    to zero any bits that are reserved in the version of the protocol
    supported by that implementation. Receivers MUST ignore all bits
    not defined for the version of the protocol they support.

    Current Tx Power: This attribute contains the current transmit
    output power in mW, as described in the dot11CurrentTxPowerLevel
    MIB variable, see [IEEE.802-11.2007].
 */
public class TxPower implements ODLCapwapMessageElement{

    int msgElem = 0;
    byte radioId = 0;
    byte reserved = 0;
    int currentTxPower = 0;


    public TxPower ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_TX_POWER;
    }

    public void setRadioId(byte radioId)
    {
        this.radioId = radioId;
    }

    public byte getRadioId ()
    {
        return (this.radioId);
    }

    public void setReserved(byte reserved)
    {
        this.reserved = 0;

    }

    public byte getReserved ()
    {
        return (this.reserved);
    }

    public void setCurrentTxPower (int curTxPower)
    {
        this.currentTxPower = curTxPower;
    }

    public int getCurrentTxPower ()
    {
        return (this.currentTxPower);
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(radioId);
        buf.writeByte(reserved);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.currentTxPower));
        return buf.writerIndex()-start;
    }

    @Override
    public TxPower decode(ByteBuf buf) {
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
        if (!(o instanceof TxPower))
            return false;
        if(!((this.msgElem == ((TxPower) o).getType()) &&
                (this.radioId == ((TxPower) o).getRadioId()) &&
                (this.reserved == ((TxPower) o).getReserved()) &&
                (this.currentTxPower == ((TxPower) o).getCurrentTxPower())))
        {
            return (false);
        }

        return true;
    }
}
