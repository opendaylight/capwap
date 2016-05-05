/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
/**
 * Created by mahesh.govind on 2/5/16.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class UdpClient {
    public static void main(String args[]) {

        byte[] buf = new byte[256];
        DatagramSocket socket = null;
        try {
            socket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        InetAddress address = null;
        try {
            address = InetAddress.getLoopbackAddress();
            System.out.println(address);
        } catch (Exception e) {
            e.printStackTrace();
        }
        buf = "hello".getBytes();
        ByteBuf nett_buf = Unpooled.buffer();

        InetSocketAddress socketAddress = new InetSocketAddress(address,5246);
        Scanner sc = new Scanner(System.in);
        {
            System.out.println("\nEnter Capwap Message ID");
            //int longIn = 4294967294;
            int longIn = 0XFFFFFFFE;
            nett_buf.writeInt(longIn);
            buf = nett_buf.array();

            DatagramPacket packet = new DatagramPacket(buf, nett_buf.writerIndex(), address, 5246);
            try {
                socket.send(packet);
                System.out.printf("\nSending Capwap Message %x ", longIn);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
