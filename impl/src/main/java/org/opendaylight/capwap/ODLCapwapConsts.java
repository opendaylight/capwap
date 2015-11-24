/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

public class ODLCapwapConsts {
	public static final int ODL_CAPWAP_MIN_HDRLEN = 8;
	public static final int ODL_CAPWAP_MAX_HDRLEN = 128; /* Max size of the header is 128 byte 2^5*4*/
	public static final int ODL_CAPWAP_HLEN_MASK = 0x1F; /* 5 bits */
	public static final int ODL_CAPWAP_WBID_80211 = 1;
}