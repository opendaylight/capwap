/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.msgelements;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessageElement;

/**
 * Created by flat on 17/04/16.
 */
public class InitiateDownload implements ODLCapwapMessageElement {
    int msgElem = 0;

    public  InitiateDownload(){
        this.msgElem = ODLCapwapConsts.CAPWAP_ELMT_TYPE_INITIATE_DOWNLOAD;
    }
    @Override
    public int encode(ByteBuf buf) {
        return 0;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElem;
    }
}
