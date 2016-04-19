/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.utils;

/**
 * Created by flat on 16/04/16.
 * Credit of some of the function goes to google guava .
 * Assumption the byte[]  is in network byte order
 */


public class ByteManager {

    static final long unsigned32Mask = 0x00000000FFFFFFFF;
    static final int unsigned16Mask = 0x0000FFFF;
    static final int unsigned13Mask = 0x00001FFF;
    static final int unsigned8Mask = 0x00FF;

    public static byte[] unsignedIntToArray(long in){
        //Long is 64 bits in java
        //mask out higher order 8 bytes .
        long maskedIn = in & unsigned32Mask ;

        return new byte[] {
                (byte)(maskedIn >>> 24),
                (byte)(maskedIn>>> 16),
                (byte)(maskedIn >>> 8),
                (byte)maskedIn};

    }

    public static long byteArrayToUnsingedInt(byte[] bytes){
        return fromBytes(bytes[0], bytes[1], bytes[2], bytes[3]);
    }

    public static long fromBytes(byte b1, byte b2, byte b3, byte b4) {

        long out = 0;
        out =  ( b1 << 24 | (b2 & 0xFF) << 16 | (b3 & 0xFF) << 8 | (b4 & 0xFF))&0xFFFFFFFFL ; //Let us mask the sign bit
        return out;
    }




    public static byte[] unsignedShortToArray(int in){

        int maskedIn = in & unsigned16Mask ; //mask out higher order 8 bytes .

        return new byte[] {
                (byte)(maskedIn >>> 8),
                (byte)maskedIn};

    }

    public static byte[] unsigned13bitsToArray(int in){

        int maskedIn = in & unsigned13Mask ; //mask out higher order 8 bytes .

        return new byte[] {
                (byte)(maskedIn >>> 5),
                (byte)((maskedIn & 0b00011111  )<< 3)};

    }
    public static int byteArrayToUnsingedShort(byte[] bytes){
        return fromBytesto16(bytes[0], bytes[1]);

    }

    public static int byteArrayToUnsinged13(byte[] bytes){
        return fromBytesto13(bytes[0], bytes[1]);

    }

    public static int fromBytesto13(byte b1, byte b2) {
        int out = 0;
        // b1 11111111   b2 11111000
        //shift b2 by 3

        int byte1 =0, byte2 =0;

        byte1 = ByteManager.unsignedByteToshort((byte)b1);
        byte2 = ByteManager.unsignedByteToshort((byte)b2);

        out = (byte1 <<5 )| (byte2 >>>3);
        return out;
    }


    public static int fromBytesto16(byte b1, byte b2) {
        int out = 0;
        out =  (b1 << 8 |  (b2 & 0xFF))&0x0000FFFF ;
        return out;
    }



        /**
         * Following are utility function to operate on unsigned byte
         * the value for byte is stored in short , so that unsigned value is taken care .
         * @param in short in
         * @return  the unsigned byte
         */
    public static byte shortToUnsingedByte(short in){
        return (byte)( in & unsigned8Mask);
    }

    /**
     * Following are utility function to operate on byte
     * Octect from network is passed to this function . The actual value is returned as short
     * the value for byte is stored in short , so that unsigned value is taken care .
     * @param in byte in
     * @return  the hort
     */
    public static short unsignedByteToshort(byte in){
        short out = 0;
        out = (short) (out | in);
        out = (short) (out &0b0000000011111111);  //clear sign bit
        return (out);
    }
}
