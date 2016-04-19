/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.msgelements.DiscoveryType;

/**
 * Created by flat on 15/04/16.
 */


public class CapwapMessageCreator {
    public static ByteBuf createDiscovery(ODLCapwapMessage msg) {
        ByteBuf buf= Unpooled.buffer();

        DiscoveryType discoveryType = null;


        if (msg ==null) {
            msg = new ODLCapwapMessage();
        }
        /*msg.header.setLbit();
        msg.header.setFbit();

        */
        discoveryType = new DiscoveryType();
        discoveryType.setDhcp();
        msg.ctrlMsg.addMessageElement(discoveryType);

        msg.ctrlMsg.setMsgType(ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST);
        msg.ctrlMsg.setSeqNo((short)1);
        msg.header.encodeHeader(buf);
        msg.ctrlMsg.encode(buf);
        return buf;
    }

}


/*
    byte[] unsignedIntToArray(long)
    long byteArrayToUnsingedInt(byte[])

    byte[] unsignedShortToArray(int)
    long byteArrayToUnsingedShort(byte[])

    byte shortToUnsingedByte(short)
    short unsignedByteToshort(byte)





 */