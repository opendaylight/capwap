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
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 17/04/16.
 */
public class RadioAdministrativeState implements ODLCapwapMessageElement {
    int msgElm = 0;
    short radioId = 0;
    short adminState = 0;


    public int getMsgElm() {
        return msgElm;
    }

    public void setMsgElm(int msgElm) {
        this.msgElm = msgElm;
    }

    public short getRadioId() {
        return radioId;
    }

    public void setRadioId(short radioId) {
        this.radioId = radioId;
    }

    public short getAdminState() {
        return adminState;
    }

    public void setAdminState(short adminState) {
        this.adminState = adminState;
    }
    public RadioAdministrativeState(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_RADIO_ADMIN_STATE;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(ByteManager.shortToUnsingedByte(this.radioId));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.adminState));
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
