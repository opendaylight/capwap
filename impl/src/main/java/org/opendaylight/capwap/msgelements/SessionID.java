/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessageElement;

/**
 * Created by flat on 17/04/16.
 */
public class SessionID implements ODLCapwapMessageElement {

    int msgElm = 0;
    byte [] sessionid = null;


    public  SessionID(){
       this.msgElm = ODLCapwapConsts.CAPWAP_ELMT_TYPE_SESSION_ID;
   }

    public int getMsgElm() {
        return msgElm;
    }

    public void setMsgElm(int msgElm) {
        this.msgElm = msgElm;
    }

    public byte[] getSessionid() {
        return sessionid;
    }

    public void setSessionid(byte[] sessionid) {
        this.sessionid = sessionid;
    }



    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(this.sessionid);
        return buf.writerIndex()-start;
    }

    @Override
    public boolean equals (Object o)
    {
        if (o == this)
            return true;
        if(!(o instanceof SessionID))
        {
            return false;
        }
        byte [] temp = ((SessionID) o).getSessionid();
        if (sessionid.length != temp.length)
            return false;
        for (int i=0;i<sessionid.length;i++){
            if(sessionid[i] !=temp[i]){
                //LOG.error("Byte Array Content @ index {} o->{}   n ->{} , {}:",i,o[i],n[i],getFunctionName(bTop));
                return false;
            }
        }
        return true;
    }
    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElm;
    }
}
