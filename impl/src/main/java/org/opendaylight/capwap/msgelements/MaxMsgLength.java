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
public class MaxMsgLength implements ODLCapwapMessageElement {
    int msgElem = 0;
    int maxLength = 0;

    public MaxMsgLength(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_MAX_MESSAGE_LENGTH;
    }

    public int getMsgElem() {
        return msgElem;
    }

    public void setMsgElem(int msgElem) {
        this.msgElem = msgElem;
    }

    public long getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(ByteManager.unsignedShortToArray(this.maxLength));
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
