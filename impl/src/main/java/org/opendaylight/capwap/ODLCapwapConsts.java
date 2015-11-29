/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

public class ODLCapwapConsts {
    public static final int ODL_CAPWAP_MIN_HDRLEN = 8;
    /* Max size of the header is 128 byte 2^5*4*/
    public static final int ODL_CAPWAP_MAX_HDRLEN = 128;
    public static final int ODL_CAPWAP_HLEN_MASK = 0x1F; /* 5 bits */
    public static final int ODL_CAPWAP_WBID_80211 = 1;
	
    /* Capwap Message type definition - start */
    public static final int ODL_CAPWAP_DISCOVERY_REQUEST = 1;
    public static final int ODL_CAPWAP_DISCOVERY_RESPONSE = 2;
    public static final int ODL_CAPWAP_JOIN_REQUEST = 3;
    public static final int ODL_CAPWAP_JOIN_RESPONSE = 4;
    public static final int ODL_CAPWAP_CONFIG_STATUS_REQUEST = 5;
    public static final int ODL_CAPWAP_CONFIG_STATUS_RESPONSE = 6;
    public static final int ODL_CAPWAP_CONFIG_UPDATE_REQUEST = 7;
    public static final int ODL_CAPWAP_CONFIG_UPDATE_RESPONSE = 8;
    public static final int ODL_CAPWAP_WTP_EVENT_REQUEST = 9;
    public static final int ODL_CAPWAP_WTP_EVENT_RESPONSE = 10;
    public static final int ODL_CAPWAP_CHANGE_STATE_EVENT_REQUEST = 11;
    public static final int ODL_CAPWAP_CHANGE_STATE_EVENT_RESPONSE = 12;
    public static final int ODL_CAPWAP_ECHO_REQUEST = 13;
    public static final int ODL_CAPWAP_ECHO_RESPONSE = 14;
    public static final int ODL_CAPWAP_IMAGE_DATA_REQUEST = 15;
    public static final int ODL_CAPWAP_IMAGE_DATA_RESPONSE = 16;    
    public static final int ODL_CAPWAP_RESET_REQUEST = 17;
    public static final int ODL_CAPWAP_RESET_RESPONSE = 18;
    public static final int ODL_CAPWAP_PRIMARY_DISCOVERY_REQUEST = 19;
    public static final int ODL_CAPWAP_PRIMARY_DISCOVERY_RESPONSE = 20;
    public static final int ODL_CAPWAP_DATA_TRANSFER_REQUEST = 21;
    public static final int ODL_CAPWAP_DATA_TRANSFER_RESPONSE = 22;
    public static final int ODL_CAPWAP_CLEAR_CONFIG_REQUEST = 23;
    public static final int ODL_CAPWAP_CLEART_CONFG_RESPONSE = 24;
    public static final int ODL_CAPWAP_STATION_CONFIG_REQUEST = 25;
    public static final int ODL_CAPWAP_STATION_CONFIG_RESPONSE = 26;
    public static final int ODL_CAPWAP_INVALID_MESSAGE = 27;
    /* Capwap Message type definition - End */
}
