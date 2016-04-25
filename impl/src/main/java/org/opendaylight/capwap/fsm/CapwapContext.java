/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;
import akka.event.Logging;

import io.netty.buffer.ByteBuf;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CapwapContext {
    ByteBuf lastSendBuf  = null;

    public ByteBuf getLastSendBuf() {
        return lastSendBuf;
    }

    public void setLastSendBuf(ByteBuf lastSendBuf) {
        this.lastSendBuf = lastSendBuf;
    }

    private static final Logger LOG = LoggerFactory.getLogger(CapwapContext.class);

}
