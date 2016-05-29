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
public class DecryptionErrorPeriod implements ODLCapwapMessageElement {
    int msgElm = 0;
    short radioId = 0;
    int reportInterval = 0;


    public DecryptionErrorPeriod (){
        this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DECRYPTION_ERROR_REPORT_PERIOD;
    }

    public short getRadioId() {
        return radioId;
    }

    public DecryptionErrorPeriod setRadioId(short radioId) {
        this.radioId = radioId;
        return this;
    }

    public int getReportInterval() {
        return reportInterval;
    }

    public DecryptionErrorPeriod setReportInterval(int reportInterval) {
        this.reportInterval = reportInterval;
        return this;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == this)
        {
            return true;
        }
        if (!(o instanceof DecryptionErrorPeriod))
        {
            return false;
        }
        if ((msgElm == ((DecryptionErrorPeriod) o).getType())&&
           (radioId == ((DecryptionErrorPeriod) o).getRadioId())&&
            (reportInterval == ((DecryptionErrorPeriod) o).getReportInterval()))
        {
            return true;
        }
      return false;
    }



    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(ByteManager.shortToUnsingedByte(this.radioId));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.getReportInterval()));
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
