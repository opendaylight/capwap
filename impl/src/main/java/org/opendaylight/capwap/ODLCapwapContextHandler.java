/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import java.util.HashMap;

import org.opendaylight.capwap.ODLCapwapContext;

public class ODLCapwapContextHandler {
    //A storage for contexts of all the WTPs controlled by this controller
    //ODLCapwapContexStore store;
    HashMap<String, ODLCapwapContext> map;

    ODLCapwapContextHandler(){
        //One could initialize the Context store here
        // Key, The object to be inserted

        map = new HashMap<String, ODLCapwapContext>();
    }


    public ODLCapwapContext getContext(String key) {
        return map.get(key);
    }
	
    public ODLCapwapContext createContext(ODLCapwapContext ctx) {
        map.put(ctx.getCtxId(), ctx);
        return ctx;
    }

    public void deleteContext(String key) {
        map.remove(key);
        return;
    }
}
