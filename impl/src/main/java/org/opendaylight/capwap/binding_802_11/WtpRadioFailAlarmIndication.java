/*
 * Copyright (c) 2016 Brunda R Rajagoapala and others.  All rights reserved.
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
    | Radio ID      | Type          | Status       | Padm           |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    Type: 1047 for IEEE 802.11 WTP Radio Fail Alarm Indication
    Length: 4

    Radio ID: The Radio Identifier, whose value is between one (1) and
    31, typically refers to some interface index on the WTP.

    Type: The type of radio failure detected. The following enumerated
    values are supported:
    1 - Receiver
    2 - Transmitter

    Status: An 8-bit boolean indicating whether the radio failure is
    being reported or cleared. A value of zero is used to clear the
    event, while a value of one is used to report the event.

    Pad: All implementations complying with version zero of this
    protocol MUST set these bits to zero. Receivers MUST ignore all
    bits not defined for the version of the protocol they support.
 */
public class WtpRadioFailAlarmIndication implements ODLCapwapMessageElement {
    int msgElem = 0;
    byte radioId = 0;
    byte failureType = 0;
    byte status = 0;
    byte pad = 0;

    public WtpRadioFailAlarmIndication ()
    {

        this.msgElem = ODLCapwapConsts.IEEE_80211_WTP_RADIO_FAIL_ALARM_INDICATION;
    }

    public void setRadioId (byte radioId)
    {
        this.radioId = radioId;
    }

    public byte getRadioId ()
    {
        return (this.radioId);
    }

    public void setFailureType (byte failureType)
    {
        this.failureType = failureType;
    }

    public byte getFailureType ()
    {
        return (this.failureType);
    }

    public void setStatus(byte status)
    {
        this.status = status;
    }

    public byte getStatus ()
    {
        System.out.println("getStatus =" +this.status);
        return (this.status);
    }

    //not providing set function for pad as it should be always 0


    public byte getPad()
    {
        System.out.println("getpad =" +this.pad);
        return (this.pad);
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.failureType);
        buf.writeByte(this.status);
        buf.writeByte(this.pad);
        return buf.writerIndex()-start;
    }

    @Override
    public WtpRadioFailAlarmIndication decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return (this.msgElem);
    }

    @Override
    public boolean equals(Object o)
    {
        System.out.println("Equals");
        if(o==null)
        {
            System.out.println("Equals- null object");

        }
        if (o == this)
        {
            System.out.println("Equals-1");
            return true;
        }
        if (!(o instanceof WtpRadioFailAlarmIndication))
        {
            System.out.println("Equals-2");
            return false;
        }

        System.out.println("EQ - msgElem - = " + ((WtpRadioFailAlarmIndication) o).getType() +"radioId = " +((WtpRadioFailAlarmIndication) o).getRadioId() +"Ftype == " +((WtpRadioFailAlarmIndication) o).getFailureType());
        System.out.println("Status=" +((WtpRadioFailAlarmIndication) o).getStatus() +"pad =" +((WtpRadioFailAlarmIndication) o).getPad());
        if(!((this.msgElem == ((WtpRadioFailAlarmIndication) o).getType()) &&
                (this.radioId == ((WtpRadioFailAlarmIndication) o).getRadioId()) &&
                (this.failureType == ((WtpRadioFailAlarmIndication) o).getFailureType()) &&
                (this.status == ((WtpRadioFailAlarmIndication) o).getStatus())&&
                (this.pad == ((WtpRadioFailAlarmIndication) o).getPad())
                ))
        {
            System.out.println("Equals-3");
            return (false);
        }
        return true;
    }
}
