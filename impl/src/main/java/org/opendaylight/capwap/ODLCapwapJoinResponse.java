/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import java.util.ArrayList;

public class ODLCapwapJoinResponse extends ODLCapwapMessage{
    public ODLCapwapJoinResponse(ODLCapwapHeader header,
                                ODLCapwapControlMessage ctrlMsg) {
        super(header, ctrlMsg);
    }
    
    public boolean validate() {
        return true;        
    }
}
