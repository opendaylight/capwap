/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import java.util.ArrayList;
import java.util.Iterator;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class ODLCapwapMessage {
    protected ODLCapwapHeader header= null;
    protected ODLCapwapControlMessage ctrlMsg = null;

    public ODLCapwapMessage(ODLCapwapHeader header, ODLCapwapControlMessage ctrlMsg) {
        this.header = header;
        this.ctrlMsg = ctrlMsg;
    }
    public int encode(ByteBuf bbuf) {
        int packet_size = 0;

        int hdr_size;
        if ((hdr_size = header.encodeHeader(bbuf)) < 0) {
            /* Log error */
            return -1;

        }
        if (ctrlMsg != null && ctrlMsg.encode(bbuf)) {
            /* Log error */
            return -1;
        }

        return packet_size;
    }
    
    public int getMessageType() {
        return ctrlMsg.getMessageType();
    }
    
    public ODLCapwapMessageElement findMessageElement(int elementType) {
        return ctrlMsg.findMessageElement(elementType);
    }
    
    public boolean validate() {
        return true;
    }
}
