/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.msgelements.subelem.IPV4Address;

/**
 * Created by flat on 16/04/16.
 */
public class CapwapLocalIPV4Address extends IPV4Address {
    int msgElemeType = 0;
    public  CapwapLocalIPV4Address(){
        this.msgElemeType = ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV4_ADDR;
    }

    public int getType() {
        return this.msgElemeType;
    }
}
