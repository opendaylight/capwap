/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

import akka.actor.AbstractFSM;
import akka.event.Logging;
import akka.event.LoggingAdapter;

/**
 * Created by flat on 13/04/16.
 */
    public  class CapwapFSMActor extends AbstractFSM<CapwapState,CapwapContext>{

    CapwapFSMActor(String name){

        log.info("Starting FSMActor    : {}", String.format(name));

    }
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    {
        startWith(CapwapState.IDLE, new CapwapContext());
        log.info("Current {}",this.stateName());
        when(CapwapState.IDLE,
                matchEvent(CapwapEvent.class,(e, d)->e.dType == CapwapEventType.DISCOVERY_MSG, (a, b)->processDiscovery()).
                        event(CapwapEvent.class,(e, d)->e.dType == CapwapEventType.JOIN_MSG, (a, b)->  processJoin(a,b))
        );
        initialize();


    }

    public void preStart() {
        log.info("FSMActor Prestart >>>{}", "Started");
    }

    public void postStop() {
        log.info("FSMActor Poststop >>>{}", "Stopped");
    }

    private State<CapwapState,CapwapContext> processDiscovery() {

        log.info("Current State {}",this.stateName());
        return goTo(CapwapState.IDLE);
    }

    private State<CapwapState,CapwapContext> processJoin(CapwapEvent m, CapwapContext d) {

        log.info("Current State {}",this.stateName());
        return goTo(CapwapState.IDLE);
    }

}
