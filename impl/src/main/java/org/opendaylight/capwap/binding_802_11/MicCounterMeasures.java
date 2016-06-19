/*
 * Copyright (c) 2016 Brunda R Rajagppala and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.binding_802_11;
import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 15/04/16.
 */
/*
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Radio ID      | WLAN ID     | MAC Address                     |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | MAC Address                                                   |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        Type: 1031 for IEEE 802.11 MIC Countermeasures
        Length: 8
        Radio ID: The Radio Identifier, whose value is between one (1) and
        31, typically refers to some interface index on the WTP.
        WLAN ID: This 8-bit unsigned integer includes the WLAN Identifier,
        on which the MIC failure occurred. The value MUST be between one
        (1) and 16
        MAC Address: The MAC Address of the station that caused the MIC
failure.
        .*/
public class MicCounterMeasures implements ODLCapwapMessageElement {
    int msgElem = 0;
    byte radioId = 0;
    byte wlanId = 0;
    byte [] macAddress = {0,0,0,0,0,0};

    public    MicCounterMeasures ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_MIC_COUNTERMEASURES;
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

    public byte getRadioId()
    {
        return (this.radioId);
    }

    public void setWlanId (byte wlanId)
    {
        this.wlanId = wlanId;
    }

    public byte getWlanId ()
    {
        return (this.wlanId);
    }

    public void setMacAddress (byte [] macAddress)
    {
        //this.macAddress = macAddress;
        for (int i = 0; i < macAddress.length; i++)
        {
            this.macAddress[i] = macAddress[i];
        }

    }

    public byte[] getMacAddress ()
    {
        return (this.macAddress);
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.wlanId);
        buf.writeBytes(this.macAddress)  ;

        return buf.writerIndex()-start;
    }

    @Override
    public AddWlan decode(ByteBuf buf) {
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
        if (!(o instanceof MicCounterMeasures))
            return false;
        if(!((this.msgElem == ((MicCounterMeasures) o).getType()) &&
                (this.radioId == ((MicCounterMeasures) o).getRadioId()) &&
                (this.wlanId == ((MicCounterMeasures) o).getWlanId())
                ))
        {
            return (false);
        }
        byte [] tmp =    ((MicCounterMeasures)o).getMacAddress();

        if(this.macAddress.length != tmp.length)
            return false;
        for (int i = 0; i < this.macAddress.length; i++)
            if (this.macAddress[i] != tmp[i])
                return false;
  /*      if (!(this.macAddress.equals(((MicCounterMeasures)o).getMacAddress())))
        {
            return (false);
        }  */

        return true;
    }
}
