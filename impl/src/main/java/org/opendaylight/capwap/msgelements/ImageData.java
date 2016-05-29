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
public class ImageData implements ODLCapwapMessageElement {

    int msgElm = 0;
    byte dataType = 0;
    int length = 0;
    byte [] data = null;

    public int getDataType() {
        return dataType;
    }

    public void setDataType(byte dataType) {
        this.dataType = dataType;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.length = data.length;
        this.data = data;
    }

    public int getLength() {
        return length;
    }

    public ImageData(){
        this.msgElm= ODLCapwapConsts.CAPWAP_ELMT_TYPE_IMAGE_DATA;
    }
    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.dataType);
        buf.writeBytes(this.data);
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElm;
    }

    @Override
    public boolean equals (Object o)
    {
        if( o == this)
            return true;
        if( !(o instanceof ImageData))
            return false;
        if (( msgElm == ((ImageData) o).getType())&&
           (dataType == ((ImageData) o).getDataType()) &&
           (length == ((ImageData) o).getLength()))
        {
            byte [] tmp = ((ImageData) o).getData() ;
            for (int i = 0; i < length; i++)
            {
                  if (data[i] != tmp [i])
                      return false;
            }
            return true;
        }
        return false;
    }
}
