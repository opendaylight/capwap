/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.dtls;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bouncycastle.crypto.tls.DTLSServerProtocol;
import org.bouncycastle.crypto.tls.DTLSTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.security.SecureRandom;

public class DtlsEngine {

    //private static final Logger log = LoggerFactory.getLogger(org.opendaylight.capwap.dtls.DtlsEngine.class);
    private static final Logger log = LoggerFactory.getLogger(DtlsEngine.class);

    private DTLSTransport encTransport = null;
    private final DtlsHandlerTransport rawTransport;

    //private final LinkedBlockingQueue<DtlsHandler.ChannelContext> writeCtxQueue = new LinkedBlockingQueue<>();
    private final LinkedBlockingQueue<DatagramPacket> writeQueue = new LinkedBlockingQueue<>();
    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    DtlsHandler.ChannelContext context = null;
    String name;
    SecureRandom secureRandom;
    private static DtlsServer dtlsServer = null;


    DtlsEngine(String name, Channel ch) {
        rawTransport = new DtlsHandlerTransport();
        rawTransport.setChannel(ch);
        secureRandom = new SecureRandom();
    }

    private DTLSTransport accept() throws IOException {
        DTLSServerProtocol serverProtocol = new DTLSServerProtocol(secureRandom);
        log.trace("DTLSServerProtocol Before Accept-------------->");
        if (dtlsServer == null) {
            log.trace("DTLS Server null");
        }

        DTLSTransport transport = serverProtocol.accept(dtlsServer, rawTransport);
        log.trace("DTLSServerProtocol After Accept   -------------->");
        return transport;

    }

    public static void setDtlsServer(DtlsServer server) {
        dtlsServer = server;
    }

    void setRemoteAddress(InetSocketAddress addr) {
        rawTransport.setRemoteAddress(addr);
    }

    void setContext(DtlsHandler.ChannelContext ctx) {
        context = ctx;
    }

    DtlsHandler.ChannelContext getContext() {
        return context;
    }

    void start() {
        executor.submit(new Runnable() {
            @Override
            public void run() {
                //while (true) {
                try {
                    log.trace(name + " init start ");

                    final DTLSTransport encTransport = accept();
                    initialize(encTransport);
                    log.trace(name + " init end ");
                } catch (IOException | InterruptedException | ExecutionException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                //}
            }
        });
    }

    public ArrayList<DatagramPacket> read(DatagramPacket msg) throws InterruptedException, ExecutionException,
            IOException {

        log.trace("DtlsEngine read " + msg);
        // add to queue irrespective of whether initialized or not;
        // this way the protocol handshake can retrieve them
        rawTransport.enqueue(msg);

        ArrayList<DatagramPacket> packets = new ArrayList<>();
        if (encTransport != null) {
            byte buf[] = new byte[encTransport.getReceiveLimit()];
            while (rawTransport.hasPackets()) {
                log.trace("before enctransport receive");
                int bytesRead = encTransport.receive(buf, 0, buf.length, 100);
                log.trace("after enctransport receive");
                if (bytesRead > 0) {
                    packets.add(new DatagramPacket(Unpooled.copiedBuffer(buf, 0, bytesRead), null, rawTransport
                            .getRemoteAddress()));
                }
            }
        }
        return packets;
    }

    private static void write(DTLSTransport encTransport, DatagramPacket packet) throws IOException {
        ByteBuf byteBuf = packet.content();
        int readableBytes = byteBuf.readableBytes();
        log.trace("DtlsEngine write " + packet);
        byte buf[] = new byte[encTransport.getSendLimit()];
        byteBuf.readBytes(buf, 0, readableBytes);
        byteBuf.release();
        encTransport.send(buf, 0, readableBytes);
    }

    public void write(DatagramPacket packet) throws IOException, InterruptedException, ExecutionException {
        log.trace("DtlsEngine write queue" + packet);
        if (encTransport != null) {
            log.trace("DtlsEngine write encTransport");
            write(encTransport, packet);
        } else {
            log.trace("DtlsEngine write queue");
            writeQueue.add(packet);
        }
    }

    public void initialize(DTLSTransport encTransport) throws InterruptedException, ExecutionException, IOException {
        // first send all queued up messages
        log.trace("Initialize");
        ArrayList<DatagramPacket> packets = new ArrayList<>();
        writeQueue.drainTo(packets);
        for (DatagramPacket packet : packets) {
            write(encTransport, packet);
        }

        // expose this to the outside world last to avoid race conditions
        this.encTransport = encTransport;
    }

    public boolean isInitialized() {
        return encTransport != null;
    }

}
