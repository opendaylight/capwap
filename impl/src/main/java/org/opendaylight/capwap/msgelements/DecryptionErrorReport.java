/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.utils.ByteManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by flat on 17/04/16.
 */
public class DecryptionErrorReport implements ODLCapwapMessageElement {
    int msgElem = 0;


    short radioId = 0;


    short numEntries = 0;
    ArrayList <MacAddress> macAddressList = null;

    public DecryptionErrorReport(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DECRYPTION_ERROR_REPORT;
        macAddressList = new ArrayList<MacAddress>();

    }

    public DecryptionErrorReport addMacAddress(MacAddress e){

        this.macAddressList.add(e);
        this.numEntries = (short) (this.numEntries+1);
        return this;

    }

    public short getNumEntries () {

        return numEntries;
    }


    public short getRadioId() {
        return radioId;
    }

    public void setRadioId(short radioId) {
        this.radioId = radioId;
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();

        buf.writeByte(ByteManager.shortToUnsingedByte(this.radioId));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.numEntries));
        for(MacAddress e: this.macAddressList){
            e.encode(buf);
        }
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    public ArrayList<MacAddress> getMacAddressList()
    {
          return macAddressList;
    }


    @Override
    public int getType() {
        return this.msgElem;
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
            return true;
        if (!(o instanceof DecryptionErrorReport))
            return false;
        if ((msgElem == ((DecryptionErrorReport) o).getType()) &&
           (radioId == ((DecryptionErrorReport) o).getRadioId()))
        {
            ArrayList <MacAddress> tmp = ((DecryptionErrorReport) o).getMacAddressList();

            if (numEntries != ((DecryptionErrorReport) o).getNumEntries())
            {
                 return false;
            }
            Iterator <MacAddress> itr = tmp.iterator();
            for(MacAddress e_o : macAddressList)
            {

                MacAddress e_n = itr.next();
                if (!(e_o.equals(e_n)))
                {
                    return false;
                }

            }
            return true;
        }
        return false;

    }
}
