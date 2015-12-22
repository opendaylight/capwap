/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import java.util.ArrayList;

import org.opendaylight.capwap.ODLCapwapMessage;

public class ODLCapwapJoinRequest extends ODLCapwapMessage{

    public ODLCapwapJoinRequest(ODLCapwapHeader header, 
                                ODLCapwapControlMessage ctrlMsg, 
                                ArrayList<ODLCapwapMessageElement> elements) {
        this.header = header;
        this.ctrlMsg = ctrlMsg;
        this.elements = elements;
    }
    
    public boolean validate() {
        //Add check for all mandatory elements
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_LOCATION_DATA) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_LOCATION_DATA);
            return false;
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_BOARD_DATA) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_BOARD_DATA);
            return false;
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR);
            return false;
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_NAME) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_NAME);
            return false;
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_SESSION_ID) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_SESSION_ID);
            return false;
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_FRAME_TUNNEL_MODE) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_FRAME_TUNNEL_MODE);
            return false;
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_MAC_TYPE) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_MAC_TYPE);
            return false;
        }
        
        /* Add handling for RADIO message elements
        if (this.findMessageElement(ODLCapwapConsts) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR);
            return false;
        }
        */
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_ECN_SUPPORT) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_ECN_SUPPORT);
            return false;
        }
        
        /* one of the below message should be present */
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV4_ADDR) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV4_ADDR);
            return false;
        }
        
        /*
        // TODO - IPv6 handling to be done later, IPv4 is mandatory for now
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV6_ADDR) == null) {
            System.out.println("Unable to find mandatory message element " + ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV6_ADDR);
            return false;
        }
         */
        
        /* Validating optional elements */
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_TRANSPORT_PROTO) == null) {
            
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_MAX_MESSAGE_LENGTH) == null) {
            
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_REBOOT_STATS) == null) {
            
        }
        
        if (this.findMessageElement(ODLCapwapConsts.CAPWAP_ELMT_TYPE_VENDOR_SPECIFIC_PAYLOAD) == null) {
            
        }
        
        return true;        
    }
    
}
