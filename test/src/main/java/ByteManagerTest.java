/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 16/04/16.
 */
public class ByteManagerTest {

    public static void main( String []args){
        ByteManagerTest test  = new ByteManagerTest();

        short in = 255;
        if (!test.byteTester(in)) System.out.println("\nFailure");


        int intIN = 0xFFFF;
        if (!test.shortTester(intIN)) System.out.println("\nFailure");

        //long longIn = 0xFFFFFFFFL;
        long longIn = 4294295L ;
        if (!test.intTester(longIn)) System.out.println("\nFailure");

        //largest 13 bit value 0x1FFF;
        int fragOffset = 8191;; ;
        if (!test.short13Tester(fragOffset)) System.out.println("\nFailure");
    }


    public boolean byteTester(short in){

        byte out  = 0;
        out = ByteManager.shortToUnsingedByte(in);
        short byteToShort = ByteManager.unsignedByteToshort(out);
        if (byteToShort == in) {
            System.out.printf("\nSuccess %d  %d ", in, byteToShort);
            return true;
        }
        return false;
    }



    public boolean shortTester(int in){

        byte out[] =  ByteManager.unsignedShortToArray(in);
        int byteArrToUnsignedShort  = ByteManager.byteArrayToUnsingedShort(out);
        if ( byteArrToUnsignedShort == in) {
            System.out.printf("\nSuccess %d  %d ", in, byteArrToUnsignedShort);
            return true;
        }
        return false;
    }

    public boolean short13Tester(int in){

        byte out[] =  ByteManager.unsigned13bitsToArray(in);
        int byteArrToUnsignedShort  = ByteManager.byteArrayToUnsinged13(out);
        if ( byteArrToUnsignedShort == in) {
            System.out.printf("\nSuccess %d  %d ", in, byteArrToUnsignedShort);
            return true;
        }
        return false;
    }

    public boolean intTester(long in){

        byte out[] =  ByteManager.unsignedIntToArray(in);
        long byteArrToUnsignedInt  = ByteManager.byteArrayToUnsingedInt(out);
        long inFloat = in;
        long outFloat = byteArrToUnsignedInt;
        if ( byteArrToUnsignedInt == in) {
            System.out.printf("\nSuccess %d  %d ", inFloat,outFloat );
            return true;
        }
        return false;
    }



}
