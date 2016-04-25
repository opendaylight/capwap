/**
 * Created by mahesh.govind on 2/5/16.
 */

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.utils.CapwapMessageCreator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.Scanner;

public class CapwapMsgSender {
    public static void main(String args[]) {

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
        //ByteBuf nett_buf = Unpooled.buffer();
        ByteBuf buf =null;

        Scanner sc = new Scanner(System.in);
        while (true)
        {
            System.out.println("\nEnter Capwap Message ID");
            int capwapMsg = sc.nextInt();
            System.out.printf("Capacity of ByteBuf  %d",buf.writableBytes());
            System.out.printf("Writer Index %d",buf.writerIndex());
            int packet_size = buf.writerIndex();
            byte [] array = buf.array();
            DatagramPacket packet = new DatagramPacket(array, packet_size, address, 5246);
            try {
                socket.send(packet);
                System.out.printf("\nSending Capwap Message %s ", ODLCapwapConsts.msgTypetoString(capwapMsg));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
