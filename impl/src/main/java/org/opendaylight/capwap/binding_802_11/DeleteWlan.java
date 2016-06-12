/*
 * Copyright (c) 2016 Brunda R Rajagoapla and others.  All rights reserved.
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
 * Created by flat on 11/06/16.
 */
/*
  Convention used
  1 byte - data type byte;
  2 byte - data type int
  4 byte - data type long
  more bytes - byte array or array list
*/
/*
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Radio ID | WLAN ID |
 */
public class DeleteWlan implements ODLCapwapMessageElement {


    int msgElem = 0;
    byte radioId = 0;
    byte wlanId = 0;

    public  DeleteWlan()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_DELETE_WLAN;
        System.out.println("Delete Wlan constructor msgElem = " + this.msgElem);
    }
    public void setRadioId(byte radioId)
    {
        this.radioId = radioId;
    }


    public byte getRadioId ()
    {
        return (this.radioId);
    }

    public void setWlanId (byte wlanId)
    {
        this.wlanId = wlanId;
    }

    public byte getWlanId ()
    {
        return this.wlanId;
    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.wlanId);
        return buf.writerIndex()-start;
    }

    @Override
    public DeleteWlan decode(ByteBuf buf) {
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
        if (!(o instanceof DeleteWlan))
            return false;
        if(!((this.msgElem == ((DeleteWlan) o).getType()) &&
                (this.radioId == ((DeleteWlan) o).getRadioId()) &&
                (this.wlanId == ((DeleteWlan) o).getWlanId())) )
        {
            return (false);
        }
        return true;
    }
}
