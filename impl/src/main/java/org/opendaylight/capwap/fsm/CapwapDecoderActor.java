/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.Props;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import akka.japi.pf.ReceiveBuilder;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.ODLCapwapMessageFactory;

import java.net.InetAddress;
import java.net.InetSocketAddress;


//This actor performs decoding of the incomming message.
// After decoding this actor checks the type of message and sends the decoded event  to the right actor[either right fsmActor or discoveryActor]

public class CapwapDecoderActor extends AbstractActor {
    ActorRef discoveryActor = null;

    private final LoggingAdapter log = Logging.getLogger(context().system(), this);


    public CapwapDecoderActor(String name, ActorRef discoveryActor) {
        this.discoveryActor = discoveryActor;
    }




    {
        receive(ReceiveBuilder.
                match(CapwapEvent.class, m -> {
                    handleIncommingMessage(m);
                }).
                matchAny(o -> log.info("received unknown message")).build()
        );
    }




    public void handleIncommingMessage(CapwapEvent e){

        ByteBuf buf = e.getPacket().content();

        ODLCapwapMessage msg = ODLCapwapMessageFactory.decodeFromByteArray(e.incomingBuf);
        e.setdMessage(msg);
        long msgType = msg.getMessageType();

        if ((msgType == ODLCapwapConsts.ODL_CAPWAP_DISCOVERY_REQUEST )|| (msgType == ODLCapwapConsts.ODL_CAPWAP_PRIMARY_DISCOVERY_REQUEST)){
            log.info("Going to process -> {}",ODLCapwapConsts.msgTypetoString((int) msgType));
            e.setdMessage(msg);
            this.discoveryActor.tell(e,self());

            // tell discovery actor to process discovery

        }else{
            log.info("Going to process -> {}",ODLCapwapConsts.msgTypetoString((int) msgType));

            sendToFSM(e);

            //post to FSM to handle state
        }

    }
    void sendToFSM(CapwapEvent m){
        String path = createPath(m);
        ActorRef child = this.getContext().getChild(path);
        if ( child ==null) {
            child = this.getContext().actorOf(Props.create(CapwapFSMActor.class,path),path);
            log.info("sendToFSM Child  null   : {}", String.format(m.dType.toString()));
        }
        else {
            log.info("sendToFSM Child **NOT** null   : {}", String.format(m.dType.toString()));
        }
        //Send to child FSM Actor
        child.tell(m,self());

    }
    String createPath(CapwapEvent m){
        //return( m.packet.sender().getHostString()+":"+m.packet.sender().getPort());
        return( "WTP-"+m.packet.sender().getHostString());
    }
}
