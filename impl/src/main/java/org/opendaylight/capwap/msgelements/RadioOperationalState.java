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
public class RadioOperationalState implements ODLCapwapMessageElement {
    int msgElem = 0;
    short radioId = 0;
    short state = 0;
    short cause = 0;


    public RadioOperationalState(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_RADIO_OPER_STATE;
    }
    public int getMsgElem() {
        return msgElem;
    }

    public void setMsgElem(int msgElem) {
        this.msgElem = msgElem;
    }

    public short getRadioId() {
        return radioId;
    }

    public void setRadioId(short radioId) {
        this.radioId = radioId;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public short getCause() {
        return cause;
    }

    public void setCause(short cause) {
        this.cause = cause;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(ByteManager.shortToUnsingedByte(this.radioId));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.state));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.cause));
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElem;
    }
}
