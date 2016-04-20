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
import org.opendaylight.capwap.msgelements.subelem.BoardDataSubElem;
import org.opendaylight.capwap.utils.ByteManager;

import java.util.ArrayList;

/**
 * Created by flat on 17/04/16.
 */

/*

      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                       Vendor Identifier                       |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                   Board Data Sub-Element...
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 */
public class WtpBoardDataMsgElem implements ODLCapwapMessageElement {



    long vendorId = 0;

    ArrayList<BoardDataSubElem> boardDataList = null;
    int msgElem = 0;


    public ArrayList<BoardDataSubElem> getBoardDataList() {
        return boardDataList;
    }

    public void setBoardDataList(ArrayList<BoardDataSubElem> boardDataList) {
        this.boardDataList = boardDataList;
    }

    public WtpBoardDataMsgElem(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_BOARD_DATA;
        boardDataList = new ArrayList<BoardDataSubElem>();
    }

    public long getVendorId() {
        return vendorId;
    }

    public void setVendorId(long vendorId) {
        this.vendorId = vendorId;
    }

    public WtpBoardDataMsgElem addBoardData(BoardDataSubElem e){

        this.boardDataList.add(e);
        return this;

    }

    public BoardDataSubElem getBoardData(int type){

        for(BoardDataSubElem e: this.boardDataList ){
            if ((e.getBoardDataType() == type) )
                return e;
        }
        return null;
    }

    public  boolean deleteDescriptorSubElm(int type){

        for(BoardDataSubElem e: this.boardDataList ){
            if (e.getBoardDataType() == type )
                this.boardDataList.remove(e);
            return true;
        }
        return false;
    }

    @Override
    public int encode(ByteBuf buf) {

        int start = buf.writerIndex();
        buf.writeBytes(ByteManager.unsignedIntToArray(this.getVendorId()));
        //encode Encryption Sub-Element
        for (BoardDataSubElem element :this.boardDataList){
            element.encode(buf);
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
