/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 22/04/16.
 *
 0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |         Reboot Count          |      AC Initiated Count       |
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |      Link Failure Count       |       SW Failure Count        |
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |       HW Failure Count        |      Other Failure Count      |
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |     Unknown Failure Count     |Last Failure Type|
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 */
public class WTPRebootStatistics implements ODLCapwapMessageElement {
    int msgElem = 0;


    int rebootCount = 0;
    int acInitiated = 0;
    int linkFailure = 0;
    int softwareFailure = 0;
    int hwFailure = 0;
    int otherFailure = 0;
    int unKnownFailure =0;
    short lastFailureType = 0;

    public int getRebootCount() {
        return rebootCount;
    }

    public WTPRebootStatistics setRebootCount(int rebootCount) {
        this.rebootCount = rebootCount;
        return this;
    }

    public int getAcInitiated() {
        return acInitiated;
    }

    public WTPRebootStatistics setAcInitiated(int acInitiated) {
        this.acInitiated = acInitiated;
        return this;
    }

    public int getLinkFailure() {
        return linkFailure;
    }

    public WTPRebootStatistics setLinkFailure(int linkFailure) {
        this.linkFailure = linkFailure;
        return this;
    }

    public int getSoftwareFailure() {
        return softwareFailure;
    }

    public WTPRebootStatistics setSoftwareFailure(int softwareFailure) {
        this.softwareFailure = softwareFailure;
        return this;
    }

    public int getHwFailure() {
        return hwFailure;
    }

    public WTPRebootStatistics setHwFailure(int hwFailure) {
        this.hwFailure = hwFailure;
        return this;
    }

    public int getOtherFailure() {
        return otherFailure;
    }

    public WTPRebootStatistics setOtherFailure(int otherFailure) {
        this.otherFailure = otherFailure;
        return this;
    }

    public int getUnKnownFailure() {
        return unKnownFailure;
    }

    public WTPRebootStatistics setUnKnownFailure(int unKnownFailure) {
        this.unKnownFailure = unKnownFailure;
        return this;
    }

    public short getLastFailureType() {
        return lastFailureType;
    }

    public WTPRebootStatistics setLastFailureType(short lastFailureType) {
        this.lastFailureType = lastFailureType;
        return this;
    }





    @Override
    public int encode(ByteBuf buf) {

        int start = buf.writerIndex();
        buf.writeBytes(ByteManager.unsignedShortToArray(this.rebootCount));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.acInitiated));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.linkFailure));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.softwareFailure));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.hwFailure));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.otherFailure));
        buf.writeBytes(ByteManager.unsignedShortToArray(this.unKnownFailure));
        buf.writeByte(ByteManager.shortToUnsingedByte(this.lastFailureType));
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
