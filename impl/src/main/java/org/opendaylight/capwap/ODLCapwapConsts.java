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
    
    /* Capwap Message Element Type Definition - Start */
    public static final int CAPWAP_ELMT_TYPE_AC_DESCRIPTOR = 1;
    public static final int CAPWAP_ELMT_TYPE_AC_IPV4_LIST = 2;
    public static final int CAPWAP_ELMT_TYPE_AC_IPV6_LIST = 3;
    public static final int CAPWAP_ELMT_TYPE_AC_NAME = 4;
    public static final int CAPWAP_ELMT_TYPE_AC_NAME_WITH_PRIO = 5;
    public static final int CAPWAP_ELMT_TYPE_AC_TIMESTAMP = 6;
    public static final int CAPWAP_ELMT_TYPE_ADD_MAC_ACL_ENTRY = 7;
    public static final int CAPWAP_ELMT_TYPE_ADD_STATION = 8;
    public static final int CAPWAP_ELMT_TYPE_CAPWAP_CONTROL_IPV4_ADDR = 10;
    public static final int CAPWAP_ELMT_TYPE_CAPWAP_CONTROL_IPV6_ADDR = 11;
    public static final int CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV4_ADDR = 30;
    public static final int CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV6_ADDR = 50;
    public static final int CAPWAP_ELMT_TYPE_CAPWAP_TIMERS = 12;
    public static final int CAPWAP_ELMT_TYPE_CAPWAP_TRANSPORT_PROTO = 51;
    public static final int CAPWAP_ELMT_TYPE_DATA_TRANSFER_DATA = 13;
    public static final int CAPWAP_ELMT_TYPE_DATA_TRANSFER_MODE = 14;
    public static final int CAPWAP_ELMT_TYPE_DECRYPTION_ERROR_REPORT = 15;
    public static final int CAPWAP_ELMT_TYPE_DECRYPTION_ERROR_REPORT_PERIOD = 16;
    public static final int CAPWAP_ELMT_TYPE_DELETE_MAC_ACL_ENTRY = 17;
    public static final int CAPWAP_ELMT_TYPE_DELETE_STATION = 18;
    public static final int CAPWAP_ELMT_TYPE_DISCOVERY_TYPE = 20;
    public static final int CAPWAP_ELMT_TYPE_DUPLICATE_IPV4_ADDRESS = 21;
    public static final int CAPWAP_ELMT_TYPE_DUPLICATE_IPV6_ADDRESS = 22;
    public static final int CAPWAP_ELMT_TYPE_ECN_SUPPORT = 53;
    public static final int CAPWAP_ELMT_TYPE_IDLE_TIMEOUT = 23;
    public static final int CAPWAP_ELMT_TYPE_IMAGE_DATA = 24;
    public static final int CAPWAP_ELMT_TYPE_IMAGE_IDENTIFIER = 25;
    public static final int CAPWAP_ELMT_TYPE_IMAGE_INFORMATION = 26;
    public static final int CAPWAP_ELMT_TYPE_INITIATE_DOWNLOAD = 27;
    public static final int CAPWAP_ELMT_TYPE_LOCATION_DATA = 28;
    public static final int CAPWAP_ELMT_TYPE_MAX_MESSAGE_LENGTH = 29;
    public static final int CAPWAP_ELMT_TYPE_MTU_DISCOVERY_PADDING = 52;
    public static final int CAPWAP_ELMT_TYPE_RADIO_ADMIN_STATE = 31;
    public static final int CAPWAP_ELMT_TYPE_RADIO_OPER_STATE = 32;
    public static final int CAPWAP_ELMT_TYPE_RESULT_CODE = 33;
    public static final int CAPWAP_ELMT_TYPE_RETURNED_MESSAGE_ELEMENT = 34;
    public static final int CAPWAP_ELMT_TYPE_SESSION_ID = 35;
    public static final int CAPWAP_ELMT_TYPE_STATISTICS_TIMER = 36;
    public static final int CAPWAP_ELMT_TYPE_VENDOR_SPECIFIC_PAYLOAD = 37;
    public static final int CAPWAP_ELMT_TYPE_WTP_BOARD_DATA = 38;
    public static final int CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR = 39;
    public static final int CAPWAP_ELMT_TYPE_WTP_FALLBACK = 40;
    public static final int CAPWAP_ELMT_TYPE_WTP_FRAME_TUNNEL_MODE = 41;
    public static final int CAPWAP_ELMT_TYPE_WTP_MAC_TYPE = 44;
    public static final int CAPWAP_ELMT_TYPE_WTP_NAME = 45;
    public static final int CAPWAP_ELMT_TYPE_WTP_RADIO_STATS = 47;
    public static final int CAPWAP_ELMT_TYPE_WTP_REBOOT_STATS = 48;
    public static final int CAPWAP_ELMT_TYPE_WTP_STATIC_IP_ADDR_INFO = 49;
    /* Capwap Message Element Type Definition - End */
}
