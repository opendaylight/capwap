/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

/**
 * Created by flat on 13/04/16.
 */
public enum CapwapEventType {
    NONE,
    DECODE,
    ENCODE,
    SENDTONETWORK,
    DISCOVERY_MSG,
    JOIN_MSG,
    DTLS_PEER_AUTHORIZE, //2.3.2.2 of RFC
    DTLS_ESTABLISHED ,
    DTLS_ESTABLISH_FAIL,
    DTLS_AUTHENTICATE_FAIL,
    DTLS_ABORTED,
    DTLS_REASSEMBLY_FAILUE,
    DTLS_DECAP_FAILURE,
    DTLS_PEER_DISCONNECT,
    DTLS_MTU_UPDATE,
    DTLS_SHUTDOWN,
    DTLS_START,
    DTLS_LISTEN,
    DTLS_ACCEPT,
    DTLS_ABORT_SESSION,
    IMAGE_TRANSFER_COMPLETE,
    TIMER_EXPIRY



}
