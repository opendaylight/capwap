/*
 * Copyright (c) 2016 Brunda R Rajagopala and others.  All rights reserved.
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
    | Radio ID | Reserved | Current Chan | Current CCA |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Energy Detect Threshold |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    Type: 1028 for IEEE 802.11 Direct Sequence Control
    Length: 8

    Radio ID: An 8-bit value representing the radio to configure, whose
    value is between one (1) and 31.

    Reserved: All implementations complying with this protocol MUST set
    to zero any bits that are reserved in the version of the protocol
    supported by that implementation. Receivers MUST ignore all bits
    not defined for the version of the protocol they support.

    Current Channel: This attribute contains the current operating
    frequency channel of the Direct Sequence Spread Spectrum (DSSS)
    PHY. This value comes from the IEEE 802.11 dot11CurrentChannel
    MIB element (see [IEEE.802-11.2007]).

    Current CCA: The current Clear Channel Assessment (CCA) method in
    operation, whose value can be found in the IEEE 802.11
    dot11CCAModeSupported MIB element (see [IEEE.802-11.2007]). Valid
    values are:
    1 - energy detect only (edonly)
    2 - carrier sense only (csonly)
    4 - carrier sense and energy detect (edandcs)
    8 - carrier sense with timer (cswithtimer)
    16   - high rate carrier sense and energy detect (hrcsanded)

    Energy Detect Threshold: The current Energy Detect Threshold being
    used by the DSSS PHY. The value can be found in the IEEE 802.11
    dot11EDThreshold MIB element
 */
public class DirectSeqCtrl implements ODLCapwapMessageElement {

    int msgElem = 0;
    byte radioId = 0;
    byte currentChannel = 0;
    byte currentCCA = 0;
    long energyDetectThreshold = 0;

    public DirectSeqCtrl ()
    {

        this.msgElem = ODLCapwapConsts.IEEE_80211_DIRECT_SEQUENCE_CONTROL;
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


    public void setCurrentChannel(byte currentChannel)
    {
        this.currentChannel =   currentChannel;
    }

    public byte getCurrentChannel ()
    {
        return (this.currentChannel);
    }


    public void setCurrentCCA(byte currentCCA)
    {
        this.currentCCA = currentCCA;
    }

    public byte getCurrentCCA ()
    {
        return (this.currentCCA);
    }

    public void setEnergyDetectThreshold (long energyDetectThreshold)
    {
        this.energyDetectThreshold = energyDetectThreshold;   
    }
        
    public long getEnergyDetectThreshold()
    {
        return (this.energyDetectThreshold);   
    }
    
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.currentChannel);
        buf.writeByte(this.currentCCA);
        buf.writeBytes(ByteManager.unsignedIntToArray(this.energyDetectThreshold));
        return buf.writerIndex()-start;
    }

    @Override
    public DirectSeqCtrl decode(ByteBuf buf) {
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
        if (!(o instanceof DirectSeqCtrl))
            return false;
        if(!((this.msgElem == ((DirectSeqCtrl) o).getType()) &&
                (this.radioId == ((DirectSeqCtrl) o).getRadioId()) &&
                (this.currentChannel == ((DirectSeqCtrl) o).getCurrentChannel()) &&
                 (this.currentCCA == ((DirectSeqCtrl) o).getCurrentCCA())&&
                 (this.energyDetectThreshold == ((DirectSeqCtrl) o).getEnergyDetectThreshold())))
        {
            return (false);
        }
        return true;
    }



}
