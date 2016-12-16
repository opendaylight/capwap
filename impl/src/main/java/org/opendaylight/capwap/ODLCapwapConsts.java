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

    /*iee 802.11 RFC 5416 binding message elements */
    public static final int IEEE_80211_ADD_WLAN                              = 1024;
    public static final int IEEE_80211_ANTENNA                               = 1025;
    public static final int IEEE_80211_ASSIGNED_WTP_BSSID                    = 1026;
    public static final int IEEE_80211_DELETE_WLAN                           = 1027;
    public static final int IEEE_80211_DIRECT_SEQUENCE_CONTROL               = 1028;
    public static final int IEEE_80211_INFORMATION_ELEMENT                   = 1029;
    public static final int IEEE_80211_MAC_OPERATION                         = 1030;
    public static final int IEEE_80211_MIC_COUNTERMEASURES                   = 1031;
    public static final int IEEE_80211_MULTI_DOMAIN_CAPABILITY               = 1032;
    public static final int IEEE_80211_OFDM_CONTROL                          = 1033;
    public static final int IEEE_80211_RATE_SET                              = 1034;
    public static final int IEEE_80211_RSNA_ERROR_REPORT_FROM_STATION        = 1035;
    public static final int IEEE_80211_STATION                               = 1036;
    public static final int IEEE_80211_STATION_QOS_PROFILE                   = 1037;
    public static final int IEEE_80211_STATION_SESSION_KEY                   = 1038;
    public static final int IEEE_80211_STATISTICS                            = 1039;
    public static final int IEEE_80211_SUPPORTED_RATES                       = 1040;
    public static final int IEEE_80211_TX_POWER                              = 1041;
    public static final int IEEE_80211_TX_POWER_LEVEL                        = 1042;
    public static final int IEEE_80211_UPDATE_STATION_QOS                    = 1043;
    public static final int IEEE_80211_UPDATE_WLAN                           = 1044;
    public static final int IEEE_80211_WTP_QUALITY_OF_SERVICE                = 1045;
    public static final int IEEE_80211_WTP_RADIO_CONFIGURATION               = 1046;
    public static final int IEEE_80211_WTP_RADIO_FAIL_ALARM_INDICATION       = 1047;
    public static final int IEEE_80211_WTP_RADIO_INFORMATION                 = 1048;

    public static String msgTypetoString(int type){

         if (type == ODL_CAPWAP_DISCOVERY_REQUEST )
             return "ODL_CAPWAP_DISCOVERY_REQUEST";

         if (type == ODL_CAPWAP_DISCOVERY_RESPONSE)
             return "ODL_CAPWAP_DISCOVERY_RESPONSE";

         if (type == ODL_CAPWAP_JOIN_REQUEST)
            return "ODL_CAPWAP_JOIN_REQUEST";

         if ( type ==ODL_CAPWAP_JOIN_RESPONSE)
            return "ODL_CAPWAP_JOIN_RESPONSE";

        if( type == ODL_CAPWAP_CONFIG_STATUS_REQUEST)
            return "ODL_CAPWAP_CONFIG_STATUS_REQUEST";

        if( type == ODL_CAPWAP_CONFIG_STATUS_RESPONSE)
            return "ODL_CAPWAP_CONFIG_STATUS_RESPONSE";

        if( type == ODL_CAPWAP_CONFIG_UPDATE_REQUEST)
            return "ODL_CAPWAP_CONFIG_UPDATE_REQUEST";

        if( type == ODL_CAPWAP_CONFIG_UPDATE_RESPONSE)
            return "ODL_CAPWAP_CONFIG_UPDATE_RESPONSE";

        if(type == ODL_CAPWAP_WTP_EVENT_REQUEST)
            return "ODL_CAPWAP_WTP_EVENT_REQUEST";

        if(type == ODL_CAPWAP_WTP_EVENT_RESPONSE)
            return "ODL_CAPWAP_WTP_EVENT_RESPONSE";

        if(type == ODL_CAPWAP_CHANGE_STATE_EVENT_REQUEST)
            return "ODL_CAPWAP_CHANGE_STATE_EVENT_REQUEST";

        if( type == ODL_CAPWAP_CHANGE_STATE_EVENT_RESPONSE)
            return "ODL_CAPWAP_CHANGE_STATE_EVENT_RESPONSE";

        if(type == ODL_CAPWAP_ECHO_REQUEST)
            return "ODL_CAPWAP_ECHO_REQUEST";

        if(type == ODL_CAPWAP_ECHO_RESPONSE)
            return "ODL_CAPWAP_ECHO_RESPONSE";

        if(type == ODL_CAPWAP_IMAGE_DATA_REQUEST)
            return "ODL_CAPWAP_IMAGE_DATA_REQUEST";

        if(type == ODL_CAPWAP_IMAGE_DATA_RESPONSE)
            return "ODL_CAPWAP_IMAGE_DATA_RESPONSE";

        if(type == ODL_CAPWAP_RESET_REQUEST)
            return "ODL_CAPWAP_RESET_REQUEST";

        if(type == ODL_CAPWAP_RESET_RESPONSE)
            return "ODL_CAPWAP_PRIMARY_DISCOVERY_REQUEST";

        if(type == ODL_CAPWAP_PRIMARY_DISCOVERY_REQUEST)
            return "ODL_CAPWAP_PRIMARY_DISCOVERY_REQUEST";

        if(type == ODL_CAPWAP_PRIMARY_DISCOVERY_RESPONSE)
            return "ODL_CAPWAP_PRIMARY_DISCOVERY_RESPONSE";

        if(type == ODL_CAPWAP_DATA_TRANSFER_REQUEST)
            return "ODL_CAPWAP_DATA_TRANSFER_REQUEST";

        if(type == ODL_CAPWAP_DATA_TRANSFER_RESPONSE)
            return "ODL_CAPWAP_DATA_TRANSFER_RESPONSE";

        if(type == ODL_CAPWAP_CLEAR_CONFIG_REQUEST)
            return "ODL_CAPWAP_CLEAR_CONFIG_REQUEST";

        if(type == ODL_CAPWAP_CLEART_CONFG_RESPONSE)
            return "ODL_CAPWAP_CLEART_CONFG_RESPONSE";

        if(type == ODL_CAPWAP_STATION_CONFIG_REQUEST)
            return "ODL_CAPWAP_STATION_CONFIG_REQUEST";

        if(type == ODL_CAPWAP_STATION_CONFIG_RESPONSE)
            return "ODL_CAPWAP_STATION_CONFIG_RESPONSE";

        if(type == ODL_CAPWAP_INVALID_MESSAGE)
            return "ODL_CAPWAP_INVALID_MESSAGE";
        return "";
    }
}
