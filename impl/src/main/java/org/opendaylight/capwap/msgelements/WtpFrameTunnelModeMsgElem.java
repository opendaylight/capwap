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
 0 1 2 3 4 5 6 7
 +-+-+-+-+-+-+-+-+
 |Reservd|N|E|L|U|
 +-+-+-+-+-+-+-+-+


 */


public class WtpFrameTunnelModeMsgElem implements ODLCapwapMessageElement {

    int msgElmType = 0;
    byte modes = 0;

    protected final byte nBitMask = 0b00001000;
    protected final byte eBitMask = 0b00000100;
    protected final byte lBitMask = 0b00000010;
    protected final byte rBitMask = 0b00000001;

    public boolean isnBitSet() {
        return (1== (this.modes & this.nBitMask));
    }
    public boolean iseBitSet() {
        return (1== (this.modes & this.eBitMask));
    }
    public boolean islBitSet() {
        return (1== (this.modes & this.lBitMask));
    }
    public boolean isrBitSet() {
        return (1== (this.modes & this.rBitMask));
    }

    public byte getnBitMask() {
        return this.nBitMask;
    }

    public byte geteBitMask() {
        return this.eBitMask;
    }

    public byte getlBitMask() {
        return this.lBitMask;
    }

    public byte getrBitMask() {
        return this.rBitMask;
    }

    public void setModes(byte modes) {
        this.modes = modes;
    }

    public void setnBit() {
        this.modes |= this.nBitMask;
    }
    public void seteBit() {
        this.modes |= this.eBitMask;
    }
    public void setlBit() {
        this.modes |= this.lBitMask;
    }
    public void setrBit() {
        this.modes |= this.rBitMask;
    }

    public WtpFrameTunnelModeMsgElem(){
       this.msgElmType = ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_FRAME_TUNNEL_MODE;
    }



    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(this.modes);
        return buf.writerIndex() - start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElmType;
    }
}
