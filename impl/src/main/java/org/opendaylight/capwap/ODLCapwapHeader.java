/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import java.nio.ByteBuffer;

import org.opendaylight.capwap.ODLCapwapConsts;

public class ODLCapwapHeader {
    private byte preamble;
    private byte hlen;
    private byte rid;
    private byte wbid;
    /*
     * Header flags stored in a byte: Top two bits are unused.
     * 0|0|T|F|L|W|M|K
     */
    private byte flagBits;
    private byte resvFlags;
    private int fragId;
    private short fragOffset;
    /* 
     * TODO - Define classes for RBID and WSI
     */

    /**Capwap default constructor.
     */
    ODLCapwapHeader() {
        preamble = 0;
        hlen = 0;
        rid = 0;
        wbid = 0;
        flagBits = 0;
        resvFlags = 0;
        fragId = 0;
        fragOffset = 0;
    }

    /**This method decodes the network bytes into capwap header format.
     * @param msg : Byte array of packets received from network
     * @return : 0 for success, !=0 for failure
     */
    public int decodeHeader(byte [] msg) {
        preamble = msg[0];

        /* Header length - Most significant 5bits*4 */
        hlen = (byte)(((msg[1] >> 3) & 0x1F) << 2); 
        if (hlen >= msg.length) {
            System.out.println("Invalid packet hlen " + hlen + " more than packet length " + msg.length);
            return 1;
        }
        
        /* Lower order 3bits from byte2 and higher 2 bits from byte3 */
        rid = (byte)(((msg[1] & 0x07) << 2) | ((msg[2] >> 6) & 0x03)); 
        /* Lower order 5 bits from byte 3 */
        wbid = (byte)((msg[2] & 0x3E) >> 1); 
        /* Lower 1 bit from byte3 and higher 5bits from byte 4*/
        flagBits = (byte)(((msg[2] & 0x01) << 5) | ((msg[3] & 0xF8) >> 3)); 

        if (isSetTbit() && (wbid != ODLCapwapConsts.ODL_CAPWAP_WBID_80211)) {
            System.out.println("Invalid WBID " + wbid);
            return 1;
        }

        this.resvFlags = 0;

        ByteBuffer msgbuf = ByteBuffer.wrap(msg);
        /* Retrieves bytes 5 & 6 */
        fragId = msgbuf.getShort(4); 

        /* Retrieves bytes 7 & 8 */
        int tmpOffset = msgbuf.getShort();
        /*Higher order 13 bits */
        fragOffset = (short)(tmpOffset >> 3); 

        /* TODO - Handle RADIO MAC address and WSI later */
        if (isSetMbit()) {
            /* TODO - Handle Radio MAC address */
        }

        if (isSetWbit()) {
            /* TODO : Handle WSI */ 
        }

        return 0;
    }

    /**Method converts Capwap header encoder into byte array.
     * @return byte[]: Byte array for sending over network
     */
    public byte[] encodeHeader() {
        byte[] hdrbytes = new byte[ODLCapwapConsts.ODL_CAPWAP_MIN_HDRLEN];
        hdrbytes[0] = preamble;
        /* Converting the header len value */
        hdrbytes[1] = (byte)((hlen >> 2) & ODLCapwapConsts.ODL_CAPWAP_HLEN_MASK);
        /* Filling higher 3bits of RID */
        hdrbytes[1] |= (byte)(rid & 0x1C); 
        /* Filling lower 2bits of RID (higher 2 bits of byte2) */
        hdrbytes[2] = (byte)((rid & 0x03) << 6);
        /* WBID - 5 bits - bits 3-7 */
        hdrbytes[2] |= (byte)(wbid & 0x3E); 
        /* Copying the T flag */
        hdrbytes[2] |= (byte)((flagBits & 0x20) >> 5);
        /* Copying other flag bits */
        hdrbytes[3] = (byte)((flagBits & 0x1F) << 3); 
        /* Filling first byte of fragment ID */
        hdrbytes[4] = (byte)((fragId & 0xFF00) >> 8);
        /* Filling the second byte of fragment ID */
        hdrbytes[5] = (byte)(fragId & 0x00FF); 
        /* Most significant 8 bits of 13 bits */
        hdrbytes[6] = (byte)(((fragOffset) & 0x1FE0) >> 5);
        /* Least significant 5 bits (higher 5 bits of byte 8) */
        hdrbytes[7] = (byte)((fragOffset & 0x1F) << 3); 

        return hdrbytes;
    }


    /**Method checks and return Tbit (set if frame Type other than 802.3). 
     * status
     * @return : Boolean true if set else false
     */
    public boolean isSetTbit() {
        return (((flagBits & 0x20) >> 5) == 1);
    }

    /**Method to set the Tbit.
     * 
     */
    public void setTbit() {
        flagBits |= 0x20;
    }

    /**Method checks and return Fbit(Fragment bit) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetFbit() {
        return (((flagBits & 0x10) >> 4) == 1);
    }

    /**Method sets Fbit.
     */
    public void setFbit() {
        flagBits |= 0x10;
    }

    /**Method checks and return Lbit(Last fragment) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetLbit() {
        return (((flagBits & 0x08) >> 3) == 1);
    }

    /**Method sets Fbit.
     */
    public void setLbit() {
        flagBits |= 0x08;
    }

    /**Method checks and return Wbit(Wireless binding Identifier) status.
     * @return : Boolean true if set else false
     */
    public boolean isSetWbit() {
        return (((flagBits & 0x04) >> 2) == 1);
    }

    /**Method sets Wbit.
     * 
     */
    public void setWbit() {
        flagBits |= 0x04;
    }

    /**Method checks and return Mbit(Radio Mac Identifier) status.
     * @return: Boolean true if set else false
     */
    public boolean isSetMbit() {
        return (((flagBits & 0x02) >> 1) == 1);
    }

    /**Method sets Mbit.
     */
    public void setMbit() {
        flagBits |= 0x02;
    }


    /**Method checks and return Kbit(Keep Alive) status.
     * @return: Boolean true if set else false
     */
    public boolean isSetKbit() {
        return ((flagBits & 0x01) == 1);
    }

    /**Method sets Kbit.
     */
    public void setKbit() {
        flagBits |= 0x01;
    }
}
