/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements.subelem;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 17/04/16.
 *
 0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |        Board Data Type        |       Board Data Length       |
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |                      Board Data Value...
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */


public class BoardDataSubElem implements ODLCapwapMessageElement {



    int boardDataType = 0;
    int boardDataLength = 0;
    byte [] data = null;

    public int getBoardDataLength() {
        return boardDataLength;
    }

    public void setBoardDataLength(int boardDataLength) {
        this.boardDataLength = boardDataLength;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
        this.boardDataLength = data.length;
    }



    public BoardDataSubElem(int type, int length){
        this.boardDataType = type;
        this.boardDataLength = length;
        this.data = new byte[length];
    }
    public BoardDataSubElem(){
    }



    public int getBoardDataType() {
        return boardDataType;
    }

    public void setBoardDataType(int boardDataType) {
        this.boardDataType = boardDataType;
    }


    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();

        //encode type
        buf.writeBytes(ByteManager.unsignedShortToArray(this.boardDataType));
        //encode board data lenght . Received in constructor
        buf.writeBytes(ByteManager.unsignedShortToArray(this.boardDataLength));
        //encode data
        buf.writeBytes(this.data);

        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return 0;
    }
}
