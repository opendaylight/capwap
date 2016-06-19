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
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 15/04/16.
 */
  /*
        0                     1                   2                   3
         0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Radio ID      |Short Preamble| Num of BSSIDs | DTIM Period    |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | BSSID                                                         |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | BSSID                        | Beacon Period                  |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
        | Country String                                                |
        +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

        Type: 1046 for IEEE 802.11 WTP WLAN Radio Configuration
        Length: 16
        Radio ID: An 8-bit value representing the radio to configure, whose
        value is between one (1) and 31.

        Short Preamble: An 8-bit value indicating whether short preamble is
        supported. The following enumerated values are currently
        supported:
        0 - Short preamble not supported.
        1 - Short preamble is supported.

        BSSID: The WLAN Radio’s base MAC Address.

        Number of BSSIDs: This attribute contains the maximum number of
        BSSIDs supported by the WTP. This value restricts the number of
        logical networks supported by the WTP, and is between 1 and 16.

        DTIM Period: This attribute specifies the number of Beacon
        intervals that elapse between transmission of Beacons frames
        containing a Traffic Indication Map (TIM) element whose Delivery
        Traffic Indication Message (DTIM) Count field is 0. This value is
        transmitted in the DTIM Period field of Beacon frames. The value
        of this field comes from the IEEE 802.11 dot11DTIMPeriod MIB
        element (see [IEEE.802-11.2007]).

        Beacon Period: This attribute specifies the number of Time Unit
        (TU) that a station uses for scheduling Beacon transmissions.
        This value is transmitted in Beacon and Probe Response frames.
        The value of this field comes from the IEEE 802.11
        dot11BeaconPeriod MIB element (see [IEEE.802-11.2007]).

        Country String: This attribute identifies the country in which the
        station is operating. The value of this field comes from the IEEE
        802.11 dot11CountryString MIB element (see [IEEE.802-11.2007]).
        Some regulatory domains do not allow WTPs to have user
        configurable country string, and require that it be a fixed value
        during the manufacturing process. Therefore, WTP vendors that
        wish to allow for the configuration of this field will need to
        validate this behavior during its radio certification process.
        Other WTP vendors may simply wish to treat this WTP configuration
        parameter as read-only. The country strings can be found in
        [ISO.3166-1].
        The WTP and AC MAY ignore the value of this field, depending upon
        regulatory requirements, for example to avoid classification as a
        Software-Defined Radio. When this field is used, the first two
        octets of this string is the two-character country string as
        described in [ISO.3166-1], and the third octet MUST either be a
        space, ’O’, ’I’, or X’ as defined below. When the value of the


        third octet is 255 (HEX 0xff), the country string field is not
        used, and MUST be ignored. The following are the possible values
        for the third octet:
        1. an ASCII space character, if the regulations under which the
        station is operating encompass all environments in the
        country,
        2. an ASCII ’O’ character, if the regulations under which the
        station is operating are for an outdoor environment only, or
        3. an ASCII ’I’ character, if the regulations under which the
        station is operating are for an indoor environment only,
        4. an ASCII ’X’ character, if the station is operating under a
        non-country entity. The first two octets of the non-country
        entity shall be two ASCII ’XX’ characters,
        5. a HEX 0xff character means that the country string field is
        not used and MUST be ignored.
        Note that the last byte of the Country String MUST be set to NULL.
*/
public class WtpRadioConfiguration implements ODLCapwapMessageElement{

    int msgElem = 0;
    byte radioId = 0;
    byte shortPreamble = 0;
    byte numOfBssIds = 0;
    byte dtimPeriod  = 0;
    byte [] bssId = {0,0,0,0,0,0};
    int beaconPeriod = 0;
    byte [] countryString = {0,0,0,0};

    public        WtpRadioConfiguration ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_WTP_RADIO_CONFIGURATION;
        this.countryString  = new byte[4];
    }

    public void setRadioId (byte radioId)
    {
         this.radioId = radioId;
    }

    public byte getRadioId ()
    {
        return (this.radioId);
    }

    public void setMsgElem(int msgElem)
    {
        this.msgElem = msgElem;
    }

    public int getMsgElem ()
    {
        return (this.msgElem);
    }

    public void setShortPreamble(byte shortPreamble)
    {
        this.shortPreamble = shortPreamble;
    }

    public byte getShortPreamble()
    {
        return (this.shortPreamble);
    }

    public void setNumOfBssIds(byte numOfBssIds)
    {
        this.numOfBssIds = numOfBssIds;
    }

    public byte getNumOfBssIds()
    {
        return (this.numOfBssIds);
    }

    public void setDtimPeriod (byte dtimPeriod)
    {
        this.dtimPeriod = dtimPeriod;
    }

    public byte getDtimPeriod ()
    {
        return (this.dtimPeriod);
    }

    public void setBssId (byte[] bssId)
    {

        // this.bssId = bssId;
        for (int i = 0; i < bssId.length; i++)
        {
            this.bssId[i] = bssId[i];
        }
    }

    public byte []getBssId()
    {
        return (this.bssId);
    }

    public void setBeaconPeriod (int beaconPeriod)
    {
        this.beaconPeriod = beaconPeriod;
    }

    public int getBeaconPeriod ()
    {
        return (this.beaconPeriod);
    }

    public void setCountryString (byte [] countryString)
    {
        //last byte is null. hence not copying
        for (int i = 0; i < this.countryString.length-1 ; i++)
        {
            this.countryString [i]= countryString [i];

        }
        this.countryString[3]='\0';

    }

    public byte[] getCountryString ()
    {
                 return(this.countryString);

    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.shortPreamble);
        buf.writeByte(this.numOfBssIds);
        buf.writeByte(this.dtimPeriod);
        buf.writeBytes(bssId);
        //bssId.encode(buf);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.beaconPeriod));
        buf.writeBytes(this.countryString);
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
    public boolean  equals (Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof WtpRadioConfiguration))
            return false;
        if(!((this.msgElem == ((WtpRadioConfiguration) o).getType()) &&
              (this.radioId == ((WtpRadioConfiguration)o).getRadioId())  &&
              (this.shortPreamble == ((WtpRadioConfiguration) o).getShortPreamble())    &&
               (this.numOfBssIds == ((WtpRadioConfiguration)o).getNumOfBssIds())&&
                (this.dtimPeriod == ((WtpRadioConfiguration)o).getDtimPeriod())))
        {
            return false;
        }
        byte []tmp1 = ((WtpRadioConfiguration) o).getBssId();
        if(tmp1.length != this.bssId.length)
        {
            return false;
        }
        for (int i=0; i< this.bssId.length;i++)
            if(tmp1[i] != this.bssId[i])
                return false;
       // if(!(this.bssId.equals(((WtpRadioConfiguration) o).getBssId())))
         //   return false;
        if(this.beaconPeriod != ((WtpRadioConfiguration) o).getBeaconPeriod())
            return false;
        byte [] tmp = ((WtpRadioConfiguration) o).getCountryString();
        if(this.countryString.length != tmp.length )
            return false;

        for (int i= 0; i < tmp.length;i++)
            if(tmp[i] != this.countryString[i])
                return false;

        return true;

    }

}

