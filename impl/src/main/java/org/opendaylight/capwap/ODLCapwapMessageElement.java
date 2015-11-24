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

public class ODLCapwapMessageElement {
    private short type;
    private short length;
    private byte [] value;
    
    ODLCapwapMessageElement(short type, short length, byte [] value) {
        this.type = type;
        this.length = length;
        this.value = value; //TODO - Check
    }
    
    public static ODLCapwapMessageElement decodeFromByteBuf(ByteBuf bbuf) {
        ODLCapwapMessageElement element = null;
        
        short type, length;
        
        type = bbuf.readUnsignedByte();
        
        return element;
    }
}
