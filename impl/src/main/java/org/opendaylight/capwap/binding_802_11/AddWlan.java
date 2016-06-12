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
  Convention used
  1 byte - data type byte;
  2 byte - data type int
  4 byte - data type long
  more bytes - byte array or array list
*/
/*
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Radio ID | WLAN ID | Capability |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Key Index | Key Status | Key Length |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Key... |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Group TSC |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Group TSC | QoS | Auth Type |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| MAC Mode | Tunnel Mode | Suppress SSID | SSID ...
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */
public class AddWlan  implements ODLCapwapMessageElement {


    int msgElem = 0;
    byte radioId = 0;
    byte wlanId = 0;
    int capability = 0;   //|E|I|C|F|P|S|B|A|M|Q|T|D|V|O|K|L|
    byte keyIndex = 0;
    byte keyStatus = 0;
    int keyLength = 0;
    byte [] key;
    byte [] groupTsc;        //48 bytes
    byte qos = 0;
    byte authType =0;
    byte macMode = 0;
    byte tunnelMode = 0;
    byte suppressSSID = 0;
    byte [] ssId;


    int capabilityEMask = 0b1000000000000000;
    int capabilityIMask = 0b0100000000000000;
    int capabilityCMask = 0b0010000000000000;
    int capabilityFMask = 0b0001000000000000;
    int capabilityPMask = 0b0000100000000000;
    int capabilitySMask = 0b0000010000000000;
    int capabilityBMask = 0b0000001000000000;
    int capabilityAMask = 0b0000000100000000;
    int capabilityMMask = 0b0000000010000000;
    int capabilityQMask = 0b0000000001000000;
    int capabilityTMask = 0b0000000000100000;
    int capabilityDMask = 0b0000000000010000;
    int capabilityVMask = 0b0000000000001000;
    int capabilityOMask = 0b0000000000000100;
    int capabilityKMask = 0b0000000000000010;
    int capabilityLMask = 0b0000000000000001;

    public  AddWlan()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_ADD_WLAN;
        System.out.println("Add Wlan constructor msgElem = " + this.msgElem);
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

    public void setCapability (int capability)
    {
        this.capability = capability;
    }


    public int getCapability ()
    {
        return this.capability;
    }
    public void setCapabilityEbit()
    {
        this.capability |= this.capabilityEMask;
    }

    public void unsetCapabilityEbit()
    {
        this.capability &= (~capabilityEMask);
    }

    public boolean isCapabilityEbitSet ()
    {
        return (0 != (this.capability & capabilityEMask));
    }

    public void setCapabilityIbit()
    {
        this.capability |= this.capabilityIMask;
    }

    public void unsetCapabilityIbit()
    {
        this.capability &= (~capabilityIMask);
    }

    public boolean isCapabilityIbitSet ()
    {
        return (0!=(this.capability & this.capabilityIMask ));
    }


    public void setCapabilityCbit()
    {
        this.capability |= this.capabilityCMask;
    }

    public void unsetCapabilityCbit()
    {
        this.capability &= (~(this.capabilityCMask));
    }

    public boolean isCapabilityCbitSet ()
    {
        return (0 != (this.capability & this.capabilityCMask));
    }

    public void setCapabilityFbit()
    {
        this.capability |= this.capabilityFMask;
    }

    public void unsetCapabilityFbit()
    {
        this.capability &= (~(this.capabilityFMask));
    }

    public boolean isCapabilityFbitSet ()
    {
        return (0 != (this.capability & this.capabilityFMask));
    }


    public void setCapabilityPbit()
    {
        this.capability |= this.capabilityPMask;
    }

    public void unsetCapabilityPbit()
    {
        this.capability &= (~(this.capabilityPMask));
    }

    public boolean isCapabilityPbitSet ()
    {
        return (0 != (this.capability & this.capabilityPMask));
    }

    public void setCapabilitySbit()
    {
        this.capability |= this.capabilitySMask;
    }

    public void unsetCapabilitySbit()
    {
        this.capability &= (~(this.capabilitySMask));
    }

    public boolean isCapabilitySbitSet ()
    {
        return (0 != (this.capability & this.capabilitySMask));
    }

    public void setCapabilityBbit()
    {
        this.capability |= this.capabilityBMask;
    }

    public void unsetCapabilityBbit()
    {
        this.capability &= (~(this.capabilityBMask));
    }

    public boolean isCapabilityBbitSet ()
    {
        return (0 != (this.capability & this.capabilityBMask));
    }

    public void setCapabilityAbit()
    {
        this.capability |= this.capabilityAMask;
    }

    public void unsetCapabilityAbit()
    {
        this.capability &= (~(this.capabilityAMask));
    }

    public boolean isCapabilityAbitSet ()
    {
        return (0 != (this.capability & this.capabilityAMask));
    }

    public void setCapabilityMbit()
    {
        this.capability |= this.capabilityMMask;
    }

    public void unsetCapabilityMbit()
    {
        this.capability &= (~(this.capabilityMMask));
    }

    public boolean isCapabilityMbitSet ()
    {
        return (0 != (this.capability & this.capabilityMMask));
    }

    public void setCapabilityQbit()
    {
        this.capability |= this.capabilityQMask;
    }

    public void unsetCapabilityQbit()
    {
        this.capability &= (~(this.capabilityQMask));
    }

    public boolean isCapabilityQbitSet ()
    {
        return (0 != (this.capability & this.capabilityQMask));
    }

    public void setCapabilityTbit()
    {
        this.capability |= this.capabilityTMask;
    }

    public void unsetCapabilityTbit()
    {
        this.capability &= (~(this.capabilityTMask));
    }

    public boolean isCapabilityTbitSet ()
    {
        return (0 != (this.capability & this.capabilityTMask));
    }

    public void setCapabilityDbit()
    {
        this.capability |= this.capabilityDMask;
    }

    public void unsetCapabilityDbit()
    {
        this.capability &= (~(this.capabilityDMask));
    }

    public boolean isCapabilityDbitSet ()
    {
        return (0 != (this.capability & this.capabilityDMask));
    }

    public void setCapabilityVbit()
    {
     //   this.capability |= this.capabilityVMask;
        //This is a reserved bit. Should be 0 always. Hence unsetting it
        unsetCapabilityVbit();
    }

    public void unsetCapabilityVbit()
    {
        this.capability &= (~(this.capabilityVMask));
    }

    public boolean isCapabilityVbitSet ()
    {
        return (0 != (this.capability & this.capabilityVMask));
    }

    public void setCapabilityObit()
    {
        this.capability |= this.capabilityOMask;
    }

    public void unsetCapabilityObit()
    {
        this.capability &= (~(this.capabilityOMask));
    }

    public boolean isCapabilityObitSet ()
    {
        return (0 != (this.capability & this.capabilityOMask));
    }

    public void setCapabilityKbit()
    {
        this.capability |= this.capabilityKMask;
    }

    public void unsetCapabilityKbit()
    {
        this.capability &= (~(this.capabilityKMask));
    }

    public boolean isCapabilityKbitSet ()
    {
        return (0 != (this.capability & this.capabilityKMask));
    }

    public void setCapabilityLbit()
    {
        this.capability |= this.capabilityLMask;
    }

    public void unsetCapabilityLbit()
    {
        this.capability &= (~(this.capabilityLMask));
    }

    public boolean isCapabilityLbitSet ()
    {
        return (0 != (this.capability & this.capabilityLMask));
    }

    public void setKeyIndex( byte keyIndex)
    {
        this.keyIndex = keyIndex;
    }

    public byte getKeyIndex()
    {
        return (this.keyIndex);
    }

    public void setKeyStatus( byte keyStatus)
    {
        this.keyStatus = keyStatus;
    }

    public byte getKeyStatus()
    {
        return (this.keyStatus);
    }

    public void setKeyLength (int keyLength)
    {
        this.keyLength = keyLength;
    }

    public int getKeyLength()
    {
        return (this.keyLength);
    }

    public  void setKey (byte[] key)
    {
        this.key = key;
        this.keyLength = key.length;
    }

    public byte[] getKey ()
    {
        return this.key;
    }

    public void setGroupTsc(byte[] groupTsc)
    {
        this.groupTsc = groupTsc;
    }

    public byte[] getGroupTsc ()
    {
        return (this.groupTsc);
    }

    public void setQos (byte qos)
    {
        this.qos = qos;
    }

    public byte getQos ()
    {
        return (this.qos);
    }

    public void setAuthType (byte authType)
    {
        this.authType = authType;
    }

    public byte getAuthType ()
    {
        return (this.authType);
    }

    public void setMacMode (byte macMode)
    {
        this.macMode = macMode;
    }

    public byte getMacMode ()
    {
        return (this.macMode);
    }

    public void setTunnelMode (byte tunnelMode)
    {
        this.tunnelMode = tunnelMode;
    }

    public byte getTunnelMode ()
    {
        return (this.tunnelMode);
    }

    public void setSuppressSSID (byte suppressSSID)
    {
        this.suppressSSID = suppressSSID;
    }

    public byte getSuppressSSID ()
    {
        return (this.suppressSSID);
    }

    public void setSsId (byte [] ssId)
    {
        this.ssId = ssId;
    }

    public byte[] getSsId ()
    {
        return (this.ssId);
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.radioId);
        buf.writeByte(this.wlanId);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.capability));
        buf.writeByte(this.keyIndex);
        buf.writeByte(this.keyStatus);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.keyLength));
        buf.writeBytes(this.key);
        buf.writeBytes(this.groupTsc);
        buf.writeByte(this.qos);
        buf.writeByte(this.authType);
        buf.writeByte(this.macMode);
        buf.writeByte(this.tunnelMode);
        buf.writeByte(this.suppressSSID);
        buf.writeBytes(this.ssId);

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
        if (!(o instanceof AddWlan))
            return false;
        if(!((this.msgElem == ((AddWlan) o).getType()) &&
          (this.radioId == ((AddWlan) o).getRadioId()) &&
          (this.wlanId == ((AddWlan) o).getWlanId()) &&
          (this.keyIndex == ((AddWlan) o).getKeyIndex())&&
          (this.keyStatus == ((AddWlan) o).getKeyStatus()) &&
          (this.keyLength == ((AddWlan) o).getKeyLength())))
        {
            return (false);
        }
        byte[] tmp = ((AddWlan)o).getKey();

        for (int i = 0; i <this.key.length; i++)
        {

            if(this.key[i] != tmp[i])
            {
                System.out.println("equals func AddWlan--3 i = " +i);
                return false;
            }
        }
        byte[] tmp1 = ((AddWlan)o).getGroupTsc();

        for (int i = 0; i < this.groupTsc.length; i++)
        {

            if(this.groupTsc[i] != tmp1[i])
            {
                System.out.println("equals func AddWlan --4 i = " +i);
                return false;
            }
        }

        if(!((this.qos == ((AddWlan) o).getQos()) &&
            (this.authType == ((AddWlan) o).getAuthType()) &&
            (this.macMode ==     ((AddWlan) o).getMacMode())  &&
            (this.tunnelMode == ((AddWlan) o).getTunnelMode()) &&
            (this.getSuppressSSID()) == ((AddWlan) o).getSuppressSSID()))
        {
            return false;
        }

        byte[] tmp2 = ((AddWlan)o).getSsId();

        for (int i = 0; i < this.ssId.length; i++)
        {

            if(this.ssId[i] != tmp2[i])
            {
                System.out.println("equals func AddWlan --5 i = " +i);
                return false;
            }
        }
        return true;
    }
}
