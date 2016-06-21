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
 * Created by flat on 15/04/16.
 */
/*
    Multi-Domain Capability message element is used by
    the AC to inform the WTP of regulatory limits. The AC will transmit
    one message element per frequency band to indicate the regulatory
    constraints in that domain. The message element contains the
    following fields.
    0 1 2 3
     0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Radio ID | Reserved | First Channel # |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Number of Channels | Max Tx Power Level |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

    Type: 1032 for IEEE 802.11 Multi-Domain Capability
    Length: 8

    Radio ID: An 8-bit value representing the radio to configure, whose
    value is between one (1) and 31.

    Reserved: All implementations complying with this protocol MUST set
    to zero any bits that are reserved in the version of the protocol
    supported by that implementation. Receivers MUST ignore all bits
    not defined for the version of the protocol they support.

    First Channel #: This attribute indicates the value of the lowest
    channel number in the sub-band for the associated domain country
    string. The value of this field comes from the IEEE 802.11
    dot11FirstChannelNumber MIB element (see [IEEE.802-11.2007]).

    Number of Channels: This attribute indicates the value of the total
    number of channels allowed in the sub-band for the associated
    domain country string (see Section 6.23). The value of this field
    comes from the IEEE 802.11 dot11NumberofChannels MIB element (see
    [IEEE.802-11.2007]).

    Max Tx Power Level: This attribute indicates the maximum transmit
    power, in dBm, allowed in the sub-band for the associated domain
    country string (see Section 6.23). The value of this field comes
    from the IEEE 802.11 dot11MaximumTransmitPowerLevel MIB element
    (see [IEEE.802-11.2007]).
 */
public class MultiDomainCapability implements ODLCapwapMessageElement{
    int msgElem = 0;
    byte radioId = 0;
    byte reserved = 0;
    int firstChannelNumber = 0;
    int  numberOfChannels = 0;
    int maxTxPowerLevel = 0;

    public  MultiDomainCapability ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_MULTI_DOMAIN_CAPABILITY;
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

    public void setFirstChannelNumber (int firstChannel)
    {
        this.firstChannelNumber = firstChannel;
    }

    public int getFirstChannelNumber ()
    {
        return (this.firstChannelNumber);
    }

    public void setNumberOfChannels (int numberOfChannels)
    {
         this.numberOfChannels =  numberOfChannels;
    }

    public int getNumberOfChannels ()
    {
          return (this.numberOfChannels);
    }

    public void setMaxTxPowerLevel (int maxTxPowerLevel)
    {
        this.maxTxPowerLevel = maxTxPowerLevel;
    }

    public int getMaxTxPowerLevel ()
    {
        return (this.maxTxPowerLevel);
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.reserved);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.firstChannelNumber));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.numberOfChannels));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.maxTxPowerLevel));

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
        if (!(o instanceof MultiDomainCapability))
            return false;

        if (!((this.msgElem == ((MultiDomainCapability) o).getMsgElem()) &&
                (this.radioId == ((MultiDomainCapability) o).getRadioId()) &&
                (this.reserved == ((MultiDomainCapability) o).getReserved()) &&
                (this.firstChannelNumber == ((MultiDomainCapability) o).getFirstChannelNumber())&&
                (this.numberOfChannels == ((MultiDomainCapability) o).getNumberOfChannels()) &&
                (this.maxTxPowerLevel == ((MultiDomainCapability) o).getMaxTxPowerLevel())
                ))
        {
            return (false);
        }
        return true;
    }

}
