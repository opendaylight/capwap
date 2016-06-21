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
        | Radio ID | Reserved | Current Chan | Band Support |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | TI Threshold |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

        Type: 1033 for IEEE 802.11 OFDM Control
        Length: 8

        Radio ID: An 8-bit value representing the radio to configure, whose
        value is between one (1) and 31.

        Reserved: All implementations complying with this protocol MUST set
        to zero any bits that are reserved in the version of the protocol
        supported by that implementation. Receivers MUST ignore all bits
        not defined for the version of the protocol they support.

        Current Channel: This attribute contains the current operating
        frequency channel of the OFDM PHY. The value of this field comes
        from the IEEE 802.11 dot11CurrentFrequency MIB element (see
        [IEEE.802-11.2007]).

        Band Supported: The capability of the OFDM PHY implementation to
        operate in the three Unlicensed National Information
        Infrastructure (U-NII) bands. The value of this field comes from
        the IEEE 802.11 dot11FrequencyBandsSupported MIB element (see
        [IEEE.802-11.2007]), coded as a bit field, whose values are:
        Bit 0 - capable of operating in the 5.15-5.25 GHz band
        Bit 1 - capable of operating in the 5.25-5.35 GHz band
        Bit 2 - capable of operating in the 5.725-5.825 GHz band
        Bit 3 - capable of operating in the 5.47-5.725 GHz band
        Bit 4 - capable of operating in the lower Japanese 5.25 GHz band
        Bit 5 - capable of operating in the 5.03-5.091 GHz band
        Bit 6 - capable of operating in the 4.94-4.99 GHz band
        For example, for an implementation capable of operating in the
        5.15-5.35 GHz bands, this attribute would take the value 3.

        TI Threshold: The threshold being used to detect a busy medium
        (frequency). CCA MUST report a busy medium upon detecting the
        RSSI above this threshold. The value of this field comes from the
        IEEE 802.11 dot11TIThreshold MIB element (see [IEEE.802-11.2007]).
*/

public class OFDMControl implements ODLCapwapMessageElement {

    int msgElem = 0;
    byte radioId = 0;
    byte reserved = 0;
    byte currentChannel = 0;
    byte bandSupport = 0;
    long tiThreshold = 0;

    public  OFDMControl ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_OFDM_CONTROL;
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

    public void setCurrentChannel (byte curChannel)
    {
        this.currentChannel = curChannel;
    }

    public byte getCurrentChannel ()
    {
        return (this.currentChannel);
    }

    public void setBandSupport (byte bandSupport)
    {
        this.bandSupport =  bandSupport;
    }

    public byte getBandSupport ()
    {
        return (this.bandSupport);
    }

    public void setTiThreshold (long tiThreshold)
    {
        this.tiThreshold = tiThreshold;
    }

    public long getTiThreshold ()
    {
        return (this.tiThreshold);
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.reserved);
        buf.writeByte(this.currentChannel);
        buf.writeByte(this.bandSupport);
        buf.writeBytes(ByteManager.unsignedIntToArray(this.tiThreshold));

        return buf.writerIndex()-start;
    }

    @Override
    public MultiDomainCapability decode(ByteBuf buf) {
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
        if (!(o instanceof OFDMControl))
            return false;

        if (!((this.msgElem == ((OFDMControl) o).getMsgElem()) &&
                (this.radioId == ((OFDMControl) o).getRadioId()) &&
                (this.reserved == ((OFDMControl) o).getReserved()) &&
                (this.currentChannel == ((OFDMControl) o).getCurrentChannel())&&
                (this.bandSupport == ((OFDMControl) o).getBandSupport()) &&
                (this.tiThreshold == ((OFDMControl) o).getTiThreshold())))
        {
            return (false);
        }
        return true;
    }

}
