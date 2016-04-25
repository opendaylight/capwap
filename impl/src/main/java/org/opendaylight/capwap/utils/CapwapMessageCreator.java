/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.utils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.msgelements.DiscoveryType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by flat on 15/04/16.
 */


public class CapwapMessageCreator {
    private static final Logger LOG = LoggerFactory.getLogger(CapwapMessageCreator.class);



    public static ODLCapwapMessage createRequest(int dType){
        ODLCapwapMessage m = null;
        m = new ODLCapwapMessage();
        m.ctrlMsg.setMsgType(dType);
        LOG.info("Creating Capwap  Request Message {}:", ODLCapwapConsts.msgTypetoString(dType));
        return m;
    }

    public static ODLCapwapMessage createResponse(ODLCapwapMessage inRequest){
        ODLCapwapMessage m = null;
        m = new ODLCapwapMessage();
        m.ctrlMsg.setMsgType(inRequest.ctrlMsg.getMsgType()+1 );//Response is always 1+ request
        LOG.info("Creating Capwap  Response Message {}:", ODLCapwapConsts.msgTypetoString((int) m.ctrlMsg.getMsgType()));
        return m;
    }

}


