/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.capwap.binding_802_11;

import io.netty.buffer.ByteBuf;

/**
 * Created by flat on 14/04/16.
 */

/*
 0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |   Radio ID    |                  Radio Type                   |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |  Radio Type   |
     +-+-+-+-+-+-+-+-+
     Radio Type:   The type of radio present.  Note this is a bit field
      that is used to specify support for more than a single type of
      PHY/MAC.  The field has the following format:

         0 1 2 3 4 5 6 7
        +-+-+-+-+-+-+-+-+
        |Reservd|N|G|A|B|
        +-+-+-+-+-+-+-+-+

         Reservd:  A set of reserved bits for future use.  All
         implementations complying with this protocol MUST set to zero
         any bits that are reserved in the version of the protocol
         supported by that implementation.  Receivers MUST ignore all
         bits not defined for the version of the protocol they support.

      N:   An IEEE 802.11n radio.

      G:   An IEEE 802.11g radio.

      A:   An IEEE 802.11a radio.

      B:   An IEEE 802.11b radio.


 */
public class WTP_Radio_Information {
    protected byte radioId = 0;
    protected byte radioType = 0;
    protected final byte nBitMask = 0b00001000;
    protected final byte gBitMask = 0b00000100;
    protected final byte aBitMask = 0b00000010;
    protected final byte bBitMask = 0b00000001;


    //Make the wtp information for this  WTP
    public WTP_Radio_Information(byte radioId){

        this.radioId=radioId;
    }

    public WTP_Radio_Information(){

    }

    public byte getRadioId() {
        return radioId;
    }

    public void setRadioId(byte radioId) {
        this.radioId = radioId;
    }

    public byte getRadioType() {
        return radioType;
    }

    public void setRadioType(byte radioType) {
        this.radioType = radioType;
    }
    //N fifth bit
    public void set802_11n(){
        this.radioType |=nBitMask;
    }

    //n Fifth bit
    public boolean is802_11nSet(){

        return ((this.radioType & nBitMask) !=0);
    }

    public void set802_11g(){
        this.radioType |=gBitMask;
    }

    public boolean is802_11gSet(){

        return ((this.radioType & gBitMask) !=0);
    }

    public void set802_11a(){
        this.radioType |=aBitMask;
    }

    public boolean is802_11aSet(){

        return ((this.radioType & aBitMask) !=0);
    }

    public void set802_11b(){
        this.radioType |=bBitMask;
    }

    public boolean is802_11bSet(){

        return ((this.radioType & bBitMask) !=0);
    }


    boolean encode(ByteBuf buf){
        buf.setByte(0,this.radioId);
        buf.setByte(1,0);
        buf.setByte(2,0);
        buf.setByte(3,0);
        buf.setByte(4,this.radioType);
        return true;
    }





}
