/*
 * Copyright (c) 2015 Brunda R Rajagopala and others.  All rights reserved.
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

     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Radio ID      | WLAN ID     | BSSID
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | BSSID |                                                       |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    Type: 1026 for IEEE 802.11 Assigned WTP BSSID
    Length: 8

    Radio ID: An 8-bit value representing the radio, whose value is
    between one (1) and 31.

    WLAN ID: An 8-bit value specifying the WLAN Identifier. The value
    MUST be between one (1) and 16

    BSSID: The BSSID assigned by the WTP for the WLAN created as a
        result of receiving an IEEE 802.11 Add WLAN.
 */
public class AssignedWtpBssid implements ODLCapwapMessageElement{

    int msgElem = 0;
    byte radioId = 0;
    byte wlanId = 0;
    byte [] bssId;

    public AssignedWtpBssid ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_ASSIGNED_WTP_BSSID;
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

    public void setWlanId (byte wlanId)
    {
        this.wlanId = wlanId;
    }

    public byte getWlanId ()
    {
        return this.wlanId;
    }

    public void setBssId (byte [] bssId)
    {
        if (bssId.length == 255)
        {
            System.out.println("Maximum number of bssId exceeded");
            return;
        }
        System.out.println("setBssId");
        this.bssId = new byte[bssId.length];
        for (int i = 0; i < bssId.length;i++)
        {
            this.bssId[i] = bssId[i];
        }
    }
    
    public byte[] getBssId ()
    {
        return (this.bssId);
    }
    
                    
    
    
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.wlanId);
        buf.writeBytes(this.bssId);
        return buf.writerIndex()-start;
    }

    @Override
    public AssignedWtpBssid decode(ByteBuf buf) {
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
        if (!(o instanceof AssignedWtpBssid))
            return false;
        if(!((this.msgElem == ((AssignedWtpBssid) o).getType()) &&
                (this.radioId == ((AssignedWtpBssid) o).getRadioId()) &&
                (this.wlanId == ((AssignedWtpBssid) o).getWlanId()) ))
        {
            return (false);
        }
        byte[] tmp = ((AssignedWtpBssid)o).getBssId();

        if (tmp.length != this.bssId.length)
            return false;

        for (int i = 0; i <this.bssId.length; i++)
        {

            if(this.bssId[i] != tmp[i])
            {
                System.out.println("equals func AssignedWtpBssid--3 i = " +i);
                return false;
            }
        }
        return true;
    }
}
