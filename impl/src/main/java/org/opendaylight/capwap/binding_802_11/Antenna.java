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
    | Radio ID      | Diversity     | Combiner       |  Antenna Cnt |
    +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
    | Antenna Selection...
    +-+-+-+-+-+-+-+-+
    Type: 1025 for IEEE 802.11 Antenna
    Length: >= 5

    Radio ID: An 8-bit value representing the radio to configure, whose
    value is between one (1) and 31.

    Diversity: An 8-bit value specifying whether the antenna is to
    provide receiver diversity. The value of this field is the same
    as the IEEE 802.11 dot11DiversitySelectionRx MIB element, see
    [IEEE.802-11.2007]. The following enumerated values are
    supported:
    0 - Disabled
    1 - Enabled (may only be true if the antenna can be used as a
        receiving antenna)

    Combiner: An 8-bit value specifying the combiner selection. The
    following enumerated values are supported:
    1 - Sectorized (Left)
    2 - Sectorized (Right)
    3 - Omni
    4 - Multiple Input/Multiple Output (MIMO)

    Antenna Count: An 8-bit value specifying the number of Antenna
    Selection fields. This value SHOULD be the same as the one found
    in the IEEE 802.11 dot11CurrentTxAntenna MIB element (see
    [IEEE.802-11.2007]).

    Antenna Selection: One 8-bit antenna configuration value per
    antenna in the WTP, containing up to 255 antennas. The following
    enumerated values are supported:
    1 - Internal Antenna
    2 - External Antenna
 */
public class Antenna implements ODLCapwapMessageElement {

    int msgElem = 0;
    byte radioId = 0;
    byte diversity = 0;
    byte combiner = 0;
    byte antennaCount = 0;
    byte [] antennaSelection;

    public Antenna () {

        this.msgElem = ODLCapwapConsts.IEEE_80211_ANTENNA;
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

    public void setDiversity (byte diversity)
    {
        this.diversity = diversity;
    }

    public byte getDiversity()
    {
        return (this.diversity);
    }


    public void setCombiner (byte combiner)
    {
        this.combiner = combiner;
    }

    public byte getCombiner()
    {
        return (this.combiner);
    }


    public void setAntennaCount (byte antennaCount)
    {
        this.antennaCount = antennaCount;
    }

    public byte getAntennaCount ()
    {
        return (this.antennaCount);
    }

    public void setAntennaSelection (byte [] antennaSelection)
    {
        System.out.println("setAntennaSelection");
        //this.antennaSelection = antennaSelection;
          this.antennaSelection = new byte[antennaSelection.length];
        for (int i = 0; i < antennaSelection.length; i++ ){
            System.out.println("setAntennaSelection i = " +i);
            this.antennaSelection[i] = antennaSelection[i];
        }
    }

    public byte [] getAntennaSelection ()
    {
        return (this.antennaSelection);
    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.diversity);
        buf.writeByte(this.combiner);
        buf.writeByte(this.antennaCount);
        buf.writeBytes(this.antennaSelection);
        return buf.writerIndex()-start;
    }

    @Override
    public Antenna decode(ByteBuf buf) {
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
        if (!(o instanceof Antenna))
            return false;
        if(!((this.msgElem == ((Antenna) o).getType()) &&
                (this.radioId == ((Antenna) o).getRadioId()) &&
                (this.diversity == ((Antenna) o).getDiversity()) &&
                (this.combiner == ((Antenna) o).getCombiner())&&
                (this.antennaCount == ((Antenna) o).getAntennaCount())))
        {
            return (false);
        }
        byte[] tmp = ((Antenna)o).getAntennaSelection();

        if(tmp.length != this.antennaSelection.length)
            return false;

        for (int i = 0; i <this.antennaSelection.length; i++)
        {

            if(this.antennaSelection[i] != tmp[i])
            {
                System.out.println("equals func Antenna--3 i = " +i);
                return false;
            }
        }        return true;
    }
}
