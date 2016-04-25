/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

import akka.actor.AbstractActor;
import akka.actor.AbstractLoggingFSM;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.utils.CapwapMessageCreator;


//This actor services discovery request . Sending Discovery response is a stateless action
// for better load handling one could have multiple instances of the same .

public class CapwapDiscoveryServiceActor extends AbstractActor {
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);


    public CapwapDiscoveryServiceActor(String name) {
        log.info("Created Discovery Service Actor {}", name);

    }

    public CapwapDiscoveryServiceActor() {
    }

    {
        receive(ReceiveBuilder.
                match(CapwapEvent.class, e -> {
                    log.info("Received String message: {}", e);
                    handleDiscovery(e);




                }).
                matchAny(o -> log.info("received unknown message")).build()
        );
    }


    void handleDiscovery(CapwapEvent e){

        ODLCapwapMessage m = CapwapMessageCreator.createResponse(e.getdMessage());

        //Need to access configuration data base to create  discovery response

        //Need to access operational database to update discovery count
        ByteBuf buf = Unpooled.buffer();
        int len =  m.header.encodeHeader(buf);
        m.ctrlMsg.encode(buf);
        //Create a Datagrampacket
        DatagramPacket packet = new DatagramPacket(buf,e.packet.sender());

        e.ctx.writeAndFlush(packet);



    }
}
