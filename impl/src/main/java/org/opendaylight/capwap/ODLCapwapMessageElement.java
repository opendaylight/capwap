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
    private ByteBuf value;
    
    ODLCapwapMessageElement(short type, short length, ByteBuf value) {
        this.type = type;
        this.length = length;
        this.value = value; //TODO - Check
    }
    
    public short getType() {
        return this.type;        
    }
    
    public short getLength() {
        return this.length;
    }
    
    public ByteBuf getValue() {
        return value;
    }
    
    public byte[] getValueBytes() {
        return value.array();
    }
    
    public static ODLCapwapMessageElement decodeFromByteBuf(ByteBuf bbuf) {
        short type, length;
        ByteBuf value;
        
        type = bbuf.readShort();
        length = bbuf.readShort();
        value = bbuf.readBytes(length); // TODO - Add validations
        
        return new ODLCapwapMessageElement(type, length, value);
    }
}
