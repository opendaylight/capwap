/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

/**
 * Created by flat on 13/04/16.
 */
public enum CapwapEventType {
    NONE,
    DECODE,
    ENCODE,
    SENDTONETWORK,
    DISCOVERY_MSG,
    JOIN_MSG

}
