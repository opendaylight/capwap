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

/**
 * Created by flat on 13/04/16.
 */
public class CapwapMsgProcActor extends AbstractActor{

    CapwapMsgProcActor(String name){
        log.info("Starting    : {}", String.format(name));

    }

    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    {
        log.info("Received event    : {}");

        receive(ReceiveBuilder.
                match (CapwapEvent.class, m->m.dType == CapwapEventType.NONE , m -> {
                    log.info("MsgProcessorActor  Actor Received None Event");
                }).
                match (CapwapEvent.class, m->m.dType == CapwapEventType.DECODE , m -> {
                    processDecodeEvt(m);
                }).
                match (CapwapEvent.class, m->m.dType == CapwapEventType.ENCODE , m -> {
                    processEncodeEvt(m);
                }).
                matchAny(o -> log.info("MsgProcessorActor Actor received unknown message")).build()
        );
    }

    void processDecodeEvt(CapwapEvent event){

        log.info("MsgProcessorActor  Actor processing event");
        sendToFSM(event);

    }
    void processEncodeEvt(CapwapEvent event){

        log.info("MsgProcessorActor  Actor processing event");

    }


    void processSendToNetworkEvt(CapwapEvent event){

        log.info("MsgProcessorActor  Actor processing event");

    }

    void sendToFSM(CapwapEvent m){

        ActorRef child = this.getContext().getChild(createPath(m));
        if ( child ==null) {
            child = this.getContext().actorOf(Props.create(CapwapFSMActor.class,createPath(m)),createPath(m));
            log.info("sendToFSM Child  null   : {}", String.format(m.dType.toString()));
        }
        else {
            log.info("sendToFSM Child **NOT** null   : {}", String.format(m.dType.toString()));
        }
        //Send to child FSM Actor
        child.tell(m,self());

    }


    String createPath(CapwapEvent m){
        return( m.wtpID);
    }
}
