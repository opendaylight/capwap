/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import org.opendaylight.capwap.ODLCapwapConsts;

/**
 * Created by flat on 22/04/16.
 */
public class WTPName extends  ACName {
    public WTPName(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_NAME;
    }
}
