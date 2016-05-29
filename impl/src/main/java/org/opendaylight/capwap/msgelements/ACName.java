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
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 22/04/16.
 */
public class ACName implements ODLCapwapMessageElement {
    int msgElem = 0;
    int length = 0;
    byte [] name = null;


    public ACName(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_NAME;

    }
    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public byte[] getName() {
        return name;
    }

    public void setName(byte[] name) {
        this.name = name;
        this.setLength(name.length);
    }


    @Override
     public boolean equals(Object o)
    {
        System.out.println("equals func ACNAME");
        if (o == this)
            return true;
        if (!(o instanceof ACName))
            return false;
        if(msgElem != ((ACName) o).getType())
        {
            System.out.println("equals func ACNAME-1");
            return false;
        }
        if (name.length != ((ACName)o).getLength())
        {
            System.out.println("equals func ACNAME--2 length = " + length + "length2=" + ((ACName) o).getLength() +"namelength="+name.length);
            return false;
        }

        byte[] tmp = ((ACName)o).getName();

        for (int i = 0; i < name.length; i++)
        {

            if(name[i] != tmp[i])
            {
                System.out.println("equals func ACNAME --3 i = " +i);
                return false;
            }
        }
        return true;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(this.name);
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
