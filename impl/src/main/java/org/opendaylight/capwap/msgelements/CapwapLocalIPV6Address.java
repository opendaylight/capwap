/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.msgelements.subelem.IPV6Address;

/**
 * Created by flat on 16/04/16.
 */
public class CapwapLocalIPV6Address extends IPV6Address {
    int msgElemeType = 0;
    public CapwapLocalIPV6Address(){
        this.msgElemeType = ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV6_ADDR;
    }
    public int getType() {
        return this.msgElemeType;
    }
}
