/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import org.opendaylight.capwap.ODLCapwapMessage;

/**
 * Created by Mahesh Govind on 25/04/16.
 */
public class CapwapFsmUtils {

    public static boolean sendMessage(CapwapEvent e, CapwapContext c, ODLCapwapMessage res){

        ByteBuf buf = Unpooled.buffer();
        DatagramPacket packet = null;

        res.header.encodeHeader(buf);
        res.ctrlMsg.encode(buf);
        c.setLastSendBuf(buf);
        packet = new DatagramPacket(buf,e.packet.sender());
        e.ctx.writeAndFlush(packet);

        return true;
    }
}
