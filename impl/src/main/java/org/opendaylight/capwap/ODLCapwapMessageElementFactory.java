/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.msgelements.subelem.MacAddress;
import org.opendaylight.capwap.msgelements.subelem.WsiInfo;
import org.opendaylight.capwap.utils.ByteManager;

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

    static public MacAddress decodeMacAddress(ByteBuf buf){
        MacAddress mac = null;
        byte []address = null;

        mac= new MacAddress();
        //Decode Length
        mac.setLength(ByteManager.unsignedByteToshort(buf.readByte()));
        address = new byte[mac.getLength()];
        buf.readBytes(address);
        mac.setAddress(address);
        return mac;
    }

    static public WsiInfo decodeWsiInfo(ByteBuf buf){
        WsiInfo wsi= null;
        byte [] data = null;

        wsi = new WsiInfo();
        //Decode Length
        wsi.setLength(ByteManager.unsignedByteToshort(buf.readByte()));
        data = new byte[wsi.getLength()];
        buf.readBytes(data);
        wsi.setData(data);
        return wsi;
    }



}
