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

public abstract class ODLCapwapMessage {
    protected ODLCapwapHeader header;
    protected ODLCapwapControlMessage ctrlMsg;
    protected ArrayList<ODLCapwapMessageElement> elements;
  

    public boolean encode(ByteBuf bbuf) {
        return true;
    }
    
    public int getMessageType() {
        return ctrlMsg.getMessageType();
    }
    
    public ODLCapwapMessageElement findMessageElement(int elementType) {
        Iterator<ODLCapwapMessageElement> iter = elements.iterator();
        while (iter.hasNext()) {
            ODLCapwapMessageElement element = iter.next();
            if (element.getType() == elementType) {
                return element;
            }
        }
        return null;
    }
    
    public abstract boolean validate();
}
