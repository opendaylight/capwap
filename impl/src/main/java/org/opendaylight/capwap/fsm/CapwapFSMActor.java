/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

import akka.actor.AbstractFSM;
import akka.actor.AbstractLoggingFSM;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import org.opendaylight.capwap.ODLCapwapConsts;

/**
 * Created by flat on 13/04/16.
 */
    public  class CapwapFSMActor extends AbstractLoggingFSM<CapwapState,CapwapContext> {

    CapwapFSMActor(String name){

        log.info("Starting FSMActor    : {}", String.format(name));

    }
    private final LoggingAdapter log = Logging.getLogger(context().system(), this);

    {
        startWith(CapwapState.IDLE, new CapwapContext());

        log.info("Current {}",this.stateName());
        when(CapwapState.IDLE,
                matchEvent(CapwapEvent.class,(e, d)->e.getdMessage().getMessageType() == ODLCapwapConsts.ODL_CAPWAP_INVALID_MESSAGE, (a, b)->processInvalidMessage()).
                        event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_JOIN_REQUEST), (a, b)->  processJoin(a,b))
        );
        when(CapwapState.JOIN,
                matchEvent(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_INVALID_MESSAGE), (a, b)->processInvalidMessage()).
                 event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_IMAGE_DATA_REQUEST),(a, b)->  processImageDataReq(a,b)).
                 event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_CONFIG_STATUS_REQUEST),(a, b)->  processConfigStatusReqReq(a,b))
        );
        when(CapwapState.CONFIGURE,
                matchEvent(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_INVALID_MESSAGE), (a, b)->processInvalidMessage()).
                event(CapwapEvent.class,(e, d)->isEventChStErr(e,ODLCapwapConsts.ODL_CAPWAP_CHANGE_STATE_EVENT_REQUEST),(a, b)->  processChangeStateWithErrorReq(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_CHANGE_STATE_EVENT_REQUEST),(a, b)->  processChangeStateReq(a,b))
        );
        when(CapwapState.IMAGE_DATA,
                matchEvent(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_INVALID_MESSAGE), (a, b)->processInvalidMessage()).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_IMAGE_DATA_REQUEST+1),(a, b)->  processImageDataResponse(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_ECHO_REQUEST),(a, b)->  processEchoRequest(a,b)).
                event(CapwapEvent.class,(e, d)->dtlsFailure(e),(a, b)->  processDtlsTD(a,b))
        );

        when(CapwapState.DTLS_TD,
                matchEvent(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_INVALID_MESSAGE), (a, b)->processInvalidMessage()).
                        event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_IMAGE_DATA_REQUEST+1),(a, b)->  processImageDataResponse(a,b)).
                        event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_ECHO_REQUEST),(a, b)->  processEchoRequest(a,b)).
                        event(CapwapEvent.class,(e, d)->dtlsFailure(e),(a, b)->  processEchoRequest(a,b))
        );

        when(CapwapState.DATACHECK,
                matchEvent(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_INVALID_MESSAGE), (a, b)->processInvalidMessage()).
                        event(CapwapEvent.class,(e, d)->isEventDataCheckTimerExpiry(e),(a, b)->  processDataCheckTimerExpiry(a,b))
        );
        when(CapwapState.RUN,
                matchEvent(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_INVALID_MESSAGE), (a, b)->processInvalidMessage()).
                event(CapwapEvent.class,(e, d)->isEventDataCheckTimerExpiry(e),(a, b)->  processDataCheckTimerExpiry(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_CHANGE_STATE_EVENT_REQUEST),(a, b)->  processChangeStateReq(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_ECHO_REQUEST),(a, b)->  processEchoRequest(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_CLEART_CONFG_RESPONSE),(a, b)->  processEchoRequest(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_WTP_EVENT_REQUEST),(a, b)->  processWtpEventRequest(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_STATION_CONFIG_REQUEST),(a, b)->  processStationConfigurationRequest(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_CONFIG_UPDATE_REQUEST),(a, b)->  processConfigUpdateReq(a,b)).
                event(CapwapEvent.class,(e, d)->isEvent(e,ODLCapwapConsts.ODL_CAPWAP_RESET_RESPONSE),(a, b)->  processConfigUpdateReq(a,b))
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
    private State<CapwapState,CapwapContext> processInvalidMessage() {

        log.info("Current State {}",this.stateName());
        return goTo(CapwapState.IDLE);
    }

    private State<CapwapState,CapwapContext> processJoin(CapwapEvent m, CapwapContext ctx) {


        return goTo( CapwapNetworkEventHander.handleJoinRequest(m,ctx));
    }

    //Process image data request
    private State<CapwapState,CapwapContext> processImageDataReq(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleImageDataRequest(m,ctx));
    }

    private State<CapwapState,CapwapContext> processConfigStatusReqReq(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleConfigurationStatusRequest(m,ctx));
    }

    private State<CapwapState,CapwapContext> processChangeStateWithErrorReq(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleChangeStateReqWithErrorReq(m,ctx));
    }

    private State<CapwapState,CapwapContext> processChangeStateReq(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleChangeEventRequest(m,ctx));
    }

    private State<CapwapState,CapwapContext> processConfigUpdateReq(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleConfigUpdateRequest(m,ctx));
    }

    private State<CapwapState,CapwapContext> processResetResponse(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapState.DTLS_TD);
    }


    private State<CapwapState,CapwapContext> processEchoRequest(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleChangeStateReqWithErrorReq(m,ctx));
    }

    private State<CapwapState,CapwapContext> processWtpEventRequest(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleWtpEventRequest(m,ctx));
    }

    private State<CapwapState,CapwapContext> processStationConfigurationRequest(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleStationConfigReq(m,ctx));
    }


    private State<CapwapState,CapwapContext> processClearConfigResponse(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapState.RUN);
    }

    private State<CapwapState,CapwapContext> processDtlsTD(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapNetworkEventHander.handleDtlsTd(m,ctx));
    }

    private State<CapwapState,CapwapContext> processImageDataOver(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapState.RESET);
    }



    private State<CapwapState,CapwapContext> processImageDataResponse(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());

        return goTo(CapwapState.IMAGE_DATA);
    }

    private State<CapwapState,CapwapContext> processDataCheckTimerExpiry(CapwapEvent m, CapwapContext ctx) {


        log.info("Current State {}",this.stateName());
        //DTLSSessionDelete timer

        return goTo(CapwapState.DTLS_TD);
    }



    boolean isEvent(CapwapEvent e,int c){
        return  (e.getdMessage().getMessageType() == c);

     }
    boolean isEventChStErr(CapwapEvent e,int c){
        return  (e.getdMessage().getMessageType() == c);

    }


    boolean isEventImageTfrCompleted(CapwapEvent e){
        return  (e.getdType() == CapwapEventType.IMAGE_TRANSFER_COMPLETE);

    }

    boolean isEventDataCheckTimerExpiry(CapwapEvent e){
        return  ((e.getdType() == CapwapEventType.TIMER_EXPIRY) && (e.getTimer() == CapwapTimerConstants.DataCheckTimer));

    }



    boolean dtlsFailure(CapwapEvent e){
        return  (
                (e.getdType() == CapwapEventType.DTLS_PEER_AUTHORIZE) ||
                        (e.getdType() == CapwapEventType.DTLS_ABORTED) ||
                        (e.getdType() == CapwapEventType.DTLS_REASSEMBLY_FAILUE));
    }

}
