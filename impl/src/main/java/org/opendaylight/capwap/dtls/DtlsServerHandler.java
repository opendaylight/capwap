/*
 * Copyright (c) 2015 Abi Varghese, Inc and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

//package org.opendaylight.capwap.dtls;
package org.opendaylight.capwap.dtls;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.bouncycastle.crypto.tls.DTLSServerProtocol;
import org.bouncycastle.crypto.tls.DTLSTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.security.SecureRandom;

/**
 * @author Abi
 *
 */
public class DtlsServerHandler extends DtlsHandler {

    private final DtlsServer mserver;
    private final SecureRandom secureRandom;
    private static final Logger LOG = LoggerFactory.getLogger(DtlsServerHandler.class);
    public DtlsServerHandler(DtlsServer dtlsServer, SecureRandom secureRandom) {
        this.mserver = dtlsServer;
        this.secureRandom = secureRandom;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.usc.crypto.DtlsHandler#getDtlsTransport()
     */
    @Override
    protected DTLSTransport getDtlsTransport() throws IOException {
        //DTLSServerProtocol serverProtocol = new DTLSServerProtocol(secureRandom);
        //LOG.error("DTLSServerProtocol Before Accept-------------->");
        //DTLSTransport transport = serverProtocol.accept(mserver, rawTransport);
        //LOG.error("DTLSServerProtocol After Accept   -------------->");
        //return transport;
        return null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
      //  if (obj instanceof DatagramPacket) {
      //      LOG.error("Channel Read -------------->");
      //      DatagramPacket msg = (DatagramPacket) obj;
      //      rawTransport.setRemoteAddress(msg.sender());
      //  }

        super.channelRead(ctx, obj);
    }

}
