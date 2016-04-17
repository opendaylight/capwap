/**
 * Created by mahesh.govind on 2/5/16.
 */

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapControlMessageFactory;
import org.opendaylight.capwap.ODLCapwapHeader;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.utils.CapwapMessageCreator;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
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

        Scanner sc = new Scanner(System.in);
        while (true)
        {
            System.out.println("\nEnter Capwap Message ID");

            int capwapMsg = sc.nextInt();
            int version = 0;
            int type = 0;
            int hlen = 8;
            int rid = 20;
            int wbid = 1;
            byte flagBits = 0;
            flagBits = ODLCapwapHeader.setFbit(flagBits);
            flagBits = ODLCapwapHeader.setLbit(flagBits);
            flagBits = ODLCapwapHeader.setMbit(flagBits);
            byte fragId = 100;
            short fragOffset = 200;
            byte brma [] = {0x01, 0x02, 0x03, 0x04};
            ByteBuf bbrma = Unpooled.wrappedBuffer(brma);
            ODLCapwapHeader.RadioMACAddress rma = new ODLCapwapHeader.RadioMACAddress(bbrma);


            ODLCapwapHeader hdr = new ODLCapwapHeader.Builder().
                version((byte)version).
                type((byte)type).
                hlen((byte)hlen).
                rid((byte)rid).
                wbid((byte)wbid).
                flagBits(flagBits).
                resvFlags((byte)0).
                fragId(fragId).
                fragOffset(fragOffset).rma(rma).
                build();
            ODLCapwapMessage msg = new ODLCapwapMessage(hdr, null);
            ByteBuf nett_buf = Unpooled.buffer();
            msg.encode(nett_buf);

            DatagramPacket packet = new DatagramPacket(nett_buf.array(), hlen, address, 5246);
            try {
                socket.send(packet);
                System.out.printf("\nSending Capwap Message from wtp");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
