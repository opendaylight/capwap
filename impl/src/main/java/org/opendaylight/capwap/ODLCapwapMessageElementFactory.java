/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

public class ODLCapwapMessageElementFactory {
    
    /*
     * 
    public static ArrayList<ODLCapwapMessageElement> decodeFromByteBuf(ByteBuf bbuf) {
        ArrayList<ODLCapwapMessageElement> tmp = new ArrayList<ODLCapwapMessageElement>();

        while (bbuf.isReadable()) {
            ODLCapwapMessageElement element = ODLCapwapMessageElement.decodeFromByteBuf(bbuf);
            
            if (element == null) {
                System.out.println("Error in message element parsing");
                tmp = null; // TODO - Check
                return tmp;
            }
            tmp.add(element);
        }
        return tmp;
    }

    public static ODLCapwapMessageElement createMessageElement(int type, int length, byte [] value) {
        ODLCapwapMessageElement tmp = null;
        return tmp;
    }
    
    */
}
