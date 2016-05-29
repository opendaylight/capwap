/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapControlMessage;

import java.util.ArrayList;
import java.util.Iterator;

public class ODLCapwapMessage {
    public ODLCapwapHeader header =null;
    public ODLCapwapControlMessage ctrlMsg = null;

    public ODLCapwapMessage(){
        this.header = new ODLCapwapHeader();
        this.ctrlMsg = new ODLCapwapControlMessage();

    }

    public ODLCapwapMessage(ODLCapwapHeader header, ArrayList<ODLCapwapMessageElement> l){
        this.header = header;
        this.ctrlMsg = new ODLCapwapControlMessage(l);

    }

    public boolean encode(ByteBuf bbuf) {
        return true;
    }
    
    public long getMessageType() {
        return ctrlMsg.getMsgType();
    }

    public ODLCapwapMessageElement findMessageElement(int elementType) {
        Iterator<ODLCapwapMessageElement> iter = ctrlMsg.elements.iterator();
        while (iter.hasNext()) {
            ODLCapwapMessageElement element = iter.next();
            if (element.getType() == elementType) {
                return element;
            }
        }
        return null;
    }


    @Override
    public boolean equals(Object obj)
    {

        if (obj == this)
            return true;
        if (!(obj instanceof ODLCapwapMessage))
            return true;
        if((header.equals(((ODLCapwapMessage) obj).header)) &&  (ctrlMsg.equals(((ODLCapwapMessage) obj).ctrlMsg)))
        {
            return true;
        }

        return false;
    }


    
    public  boolean validate(){
        return false;
    };
}
