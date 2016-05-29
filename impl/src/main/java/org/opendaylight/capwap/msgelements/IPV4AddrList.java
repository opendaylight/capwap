/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.bytes.ByteArrayDecoder;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.msgelements.subelem.IPV4Address;
import org.opendaylight.capwap.utils.ByteManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by flat on 22/04/16.
 */
public class IPV4AddrList implements ODLCapwapMessageElement{
    int msgElem = 0;
    ArrayList <IPV4Address> list = new ArrayList<IPV4Address>();

    public IPV4AddrList(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_IPV4_LIST;
    }

    public ArrayList<IPV4Address> getList() {
        return list;
    }

    public void setList(ArrayList<IPV4Address> list) {
        this.list = list;
    }
    public void addAddress(byte [] addr) {

        IPV4Address ipv4 = new IPV4Address();
        ipv4.setAddress(addr);
        this.list.add(ipv4);
    }
    @Override
    public boolean equals (Object obj)
    {
      	if(obj == this)
        	return  true;
      	if(!(obj instanceof IPV4AddrList))
        	return false;
      	if (list.size() != ((IPV4AddrList) obj).list.size() )
        	return false;

   	 	Iterator<IPV4Address> itr =   ((IPV4AddrList) obj).list.iterator();

    	for (IPV4Address e_o : list) {
        	IPV4Address e_n = itr.next();
	 		return ( e_o.equals(e_n));
    	}	
		return false;
    }
       


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        for (IPV4Address addr: list){
            addr.encode(buf);
        }
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElem;
    }
}
