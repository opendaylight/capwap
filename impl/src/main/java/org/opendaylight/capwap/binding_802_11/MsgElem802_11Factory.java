/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.capwap.binding_802_11;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by flat on 21/04/16.
 */
public class MsgElem802_11Factory {
    private static final Logger LOG = LoggerFactory.getLogger(MsgElem802_11Factory.class);

    static public WTP_Radio_Information decodeWtpRadioInfoElm(ByteBuf buf, int length) {
        if (buf == null) {
            LOG.error("ByteBuf null WtpRadioInfoElm  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable WtpRadioInfoElm");
            return null;
        }

        WTP_Radio_Information radioInfo = new WTP_Radio_Information();
        radioInfo.setRadioId(buf.readByte());
        buf.skipBytes(3);
        radioInfo.setRadioType(buf.readByte());
        return radioInfo;
    }


}
