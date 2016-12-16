/*
 * Copyright (c) 2015 Brunda R Rajagopala and others.  All rights reserved.
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
| Client MAC Address                                            |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Client MAC Address           | BSSID                          |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| BSSID                                                         |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Radio ID     | WLAN ID       | Reserved                       |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| TKIP ICV Errors                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| TKIP Local MIC Failures                                       |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| TKIP Remote MIC Failures                                      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| CCMP Replays                                                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| CCMP Decrypt Errors                                           |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| TKIP Replays                                                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
*/
public class RsnaErrReportFrmStn implements ODLCapwapMessageElement {
    int msgElem = 0;
    byte [] clientMacAddress = {0,0,0,0,0,0};
    byte [] bssId = {0,0,0,0,0,0};
    byte radioId = 0;
    byte wlanId = 0;
    int reserved =0;
    long txIpICVErrors = 0;
    long txIpLocalMICFailures = 0;
    long txIpRemoteMICFailures = 0;
    long ccmpReplays = 0;
    long ccmpDecryptErrors = 0;
    long txIpReplays = 0;

    public RsnaErrReportFrmStn ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_RSNA_ERROR_REPORT_FROM_STATION;
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

    public void setClientMacAddress (byte [] macAddress)
    {
        //this.macAddress = macAddress;
        for (int i = 0; i < macAddress.length; i++)
        {
            this.clientMacAddress[i] = macAddress[i];
        }

    }

    public byte[] getClientMacAddress ()
    {
        return (this.clientMacAddress);
    }

    public byte [] getBssId ()
    {
        return (this.bssId);
    }

    public void setBssId (byte [] bssId)
    {
        for (int i = 0; i < bssId.length; i++)
        {
            this.bssId[i] = bssId[i];
        }
    }

    public byte getWlanId ()
    {
        return (this.wlanId);

    }

    public void setWlanId (byte wlanId)
    {
        this.wlanId = wlanId;
    }

    public int getReserved ()
    {
        return (this.reserved);
    }

    public long getTxIpICVErrors()
    {
        return (this.txIpICVErrors);
    }

    public void setTxIpICVErrors (long txIpICVErrors)
    {
        this.txIpICVErrors = txIpICVErrors;
    }

    public long getTxIpLocalMICFailures ()
    {
        return (this.txIpLocalMICFailures);
    }

    public void setTxIpLocalMICFailures (long txIpLocalMICFailures)
    {
        this.txIpLocalMICFailures = txIpLocalMICFailures;
    }

    public long getTxIpRemoteMICFailures ()
    {
        return (this.txIpRemoteMICFailures);
    }

    @Override
    public int getType() {
        return (this.msgElem);
    }

    public void setTxIpRemoteMICFailures (long txIpRemoteMICFailures)
    {
        this.txIpRemoteMICFailures = txIpRemoteMICFailures;
    }

    public  long getCcmpReplays ()
    {
        return (this.ccmpReplays);
    }

    public void setCcmpReplays (long ccmpReplays)
    {
        this.ccmpReplays = ccmpReplays;
    }

    public long getCcmpDecryptErrors ()
    {
        return (this.ccmpDecryptErrors);
    }

    public void setCcmpDecryptErrors (long ccmpDecryptErrors)
    {
        this.ccmpDecryptErrors =   ccmpDecryptErrors;
    }

    public long getTxIpReplays ()
    {
        return (this.txIpReplays);
    }

    public void setTxIpReplays (long txIpReplays)
    {
        this.txIpReplays = txIpReplays;
    }

    @Override
    public boolean equals (Object o)
    {
        System.out.println("RSNA - Equals func");
        if (o == this)
        {        
            return true;
        }
        if (!(o instanceof RsnaErrReportFrmStn))
        {
            return false;
        }


        byte [] temp = ((RsnaErrReportFrmStn) o).getClientMacAddress();

        if (this.clientMacAddress.length != temp.length)
        {
            return false;
        }


        for (int i = 0; i < this.clientMacAddress.length; i++)
        {
            if     (this.clientMacAddress[i] != temp [i])
            {
                return false;
            }
        }

        byte [] temp1 = ((RsnaErrReportFrmStn) o).getBssId();

        if (this.bssId.length != temp1.length)
        {
            return false;
        }

        for (int i = 0; i<this.bssId.length; i++)
            if (this.bssId[i] != temp1[i] )
            {
                return false;
            }

         if (this.radioId != ((RsnaErrReportFrmStn) o).getRadioId())
         {
             return false;
         }
        if(this.wlanId != ((RsnaErrReportFrmStn) o).getWlanId())

        {
            return false;
        }
        if (this.reserved != ((RsnaErrReportFrmStn) o).getReserved())
        {
            return false;
        }
        if (this.txIpICVErrors != ((RsnaErrReportFrmStn) o).getTxIpICVErrors())
        {
            return false;
        }
        if (this.txIpLocalMICFailures != ((RsnaErrReportFrmStn) o).getTxIpLocalMICFailures())
        {
            return false;
        }
        if (this.txIpRemoteMICFailures != ((RsnaErrReportFrmStn) o).getTxIpRemoteMICFailures())
        {
            return false;
        }
        if (this.ccmpReplays != ((RsnaErrReportFrmStn) o).getCcmpReplays())
        {
            return false;
        }
        if (this.ccmpDecryptErrors != ((RsnaErrReportFrmStn) o).getCcmpDecryptErrors())
        {
            return false;
        }
        if (this.txIpReplays != ((RsnaErrReportFrmStn) o).getTxIpReplays())
        {
            return false;
        }

        return true;
    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        //Vendor info
        buf.writeBytes (this.clientMacAddress);
        buf.writeBytes (this.bssId);
        buf.writeByte(this.radioId);
        buf.writeByte (this.wlanId);
        buf.writeBytes(ByteManager.unsignedShortToArray(this.reserved));
        buf.writeBytes (ByteManager.unsignedIntToArray(this.txIpICVErrors));
        buf.writeBytes (ByteManager.unsignedIntToArray(this.txIpLocalMICFailures));
        buf.writeBytes(ByteManager.unsignedIntToArray(this.txIpRemoteMICFailures));
        buf.writeBytes(ByteManager.unsignedIntToArray(this.ccmpReplays));
        buf.writeBytes(ByteManager.unsignedIntToArray(this.ccmpDecryptErrors));
        buf.writeBytes(ByteManager.unsignedIntToArray(this.txIpReplays));

        return buf.writerIndex()-start;
    }

    @Override
    public RsnaErrReportFrmStn decode(ByteBuf buf) {
        return null;
    }


}

