/**
 * Created by mahesh.govind on 2/5/16.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UdpClient {
    public static void main(String args[]){

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
        buf= "hello".getBytes();
        ByteBuf nett_buf = Unpooled.buffer();


        nett_buf.setInt(0,13);
        nett_buf.setInt(4,16);
        nett_buf.setByte(8,10);
        buf = nett_buf.array();
        DatagramPacket packet = new DatagramPacket(buf, buf.length, address, 5246);
        try {
            socket.send(packet);
            System.out.println(buf.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
