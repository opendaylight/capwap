/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.dtls;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelPromise;
import org.bouncycastle.crypto.tls.DTLSClientProtocol;
import org.bouncycastle.crypto.tls.DTLSTransport;
//import org.opendaylight.capwap.dtls.DtlsClient;
//import org.opendaylight.capwap.dtls.DtlsHandler;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.SecureRandom;

/**
 * @author Abi
 *
 */
public class DtlsClientHandler extends DtlsHandler {

    private final DtlsClient mclient;
    private final SecureRandom secureRandom;

    public DtlsClientHandler(DtlsClient dtlsClient, SecureRandom secureRandom) {
        this.mclient = dtlsClient;
        this.secureRandom = secureRandom;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.opendaylight.usc.crypto.DtlsHandler#getDtlsTransport()
     */
    @Override
    protected DTLSTransport getDtlsTransport() throws IOException {
        DTLSClientProtocol clientProtocol = new DTLSClientProtocol(secureRandom);
        //return clientProtocol.connect(mclient, rawTransport);
        return null;
    }

    @Override
    public void connect(ChannelHandlerContext ctx, SocketAddress remoteAddress, SocketAddress localAddress,
            ChannelPromise future) throws Exception {
        //rawTransport.setRemoteAddress((InetSocketAddress) remoteAddress);

        super.connect(ctx, remoteAddress, localAddress, future);
    }

}
