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
public class StatisticsTimer implements ODLCapwapMessageElement {
    int msgElm = 0;
    int timer = 0;

    public  StatisticsTimer(){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_STATISTICS_TIMER;
    }
    public int getMsgElm() {
        return msgElm;
    }

    public void setMsgElm(int msgElm) {
        this.msgElm = msgElm;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }
    @Override
    public boolean equals (Object o)
    {
        if (o == this)
          return true;
        if (!(o instanceof StatisticsTimer))
            return false;
        if ((msgElm == ((StatisticsTimer) o).getMsgElm())&&
            (timer == ((StatisticsTimer) o).getTimer()))
        {
            return true;

        }
        return false;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(ByteManager.unsignedShortToArray(this.getTimer()));
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
