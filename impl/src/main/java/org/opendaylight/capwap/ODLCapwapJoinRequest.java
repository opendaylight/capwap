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
        
        return true;        
    }
    
}
