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

/**
 * Created by flat on 17/04/16.
 */
public class DeleteMacAclEntry  implements ODLCapwapMessageElement {
    int msgElm = 0;
    short numEntries = 0;



    short macAddrLength = 0;

    ArrayList <MacAddress> macList = null;
    public DeleteMacAclEntry(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DELETE_MAC_ACL_ENTRY;
        this.macList = new ArrayList<MacAddress>();
    }

    public short getNumEntries() {
        return numEntries;
    }

    public DeleteMacAclEntry setNumEntries(short numEntries) {
        this.numEntries = numEntries;
        return this;
    }

    public short getMacAddrLength() {
        return macAddrLength;
    }

    public DeleteMacAclEntry setMacAddrLength(short macAddrLength) {
        this.macAddrLength = macAddrLength;
        return this;
    }

    ///
    public DeleteMacAclEntry addMacAddress(MacAddress e){

        this.macList.add(e);
        this.setNumEntries((short)(this.getNumEntries()+1));
        return this;

    }
    ////

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();

        buf.writeByte(ByteManager.shortToUnsingedByte(this.numEntries));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.macAddrLength));
        for (MacAddress e:this.macList){
            e.encode(buf);
        }
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElm;
    }
}
