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
 * Created by flat on 17/04/16.
 */
public class ImageInformation implements ODLCapwapMessageElement {


    long fileSize = 0;
    int msgElem = 0;
    byte [] hash = null;




    public  ImageInformation (){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_IMAGE_INFORMATION;
    }
    public int getMsgElem() {
        return msgElem;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }

    public void setMsgElem(int msgElem) {
        this.msgElem = msgElem;
    }

    public byte[] getHash() {
        return hash;
    }

    public void setHash(byte []hash) {
        this.hash = hash;
    }



    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeBytes(ByteManager.unsignedIntToArray(this.fileSize));
        buf.writeBytes(this.hash);
        return  buf.writerIndex()-start;
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
