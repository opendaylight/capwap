/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import java.util.ArrayList;

import io.netty.buffer.ByteBuf;

public class ODLCapwapControlMessageFactory {
    public static ODLCapwapControlMessage decodeFromByteBuf(ByteBuf bbuf) {
        ODLCapwapControlMessage ctrlMsg = new ODLCapwapControlMessage(bbuf);
        return ctrlMsg;
    }
}