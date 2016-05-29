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
    @Override
    public boolean equals (Object o)
    {
        System.out.println("WTPName - Equals func");
        if (o==this)
            return true;
        if (!(o instanceof WTPName))
        {
            System.out.println("WTPName - Equals func--1");
            return false;
        }
        if (msgElem != ((WTPName) o).getType())
        {
            System.out.println("WTPName - Equals func--2");
            return false;
        }
        byte [] tmp1 = getName();
        for(int i = 0; i< tmp1.length; i++)
        System.out.println("WTPName - Equals func--2--" +(char)tmp1[i]);

        byte [] tmp2 = ((WTPName) o).getName();
        for(int i = 0; i< tmp2.length; i++)
        System.out.println("WTPName - Equals func--2--" +(char)tmp2[i]);
        if (tmp1.length != tmp2.length)
        {
            System.out.println("WTPName - Equals func--3");
            return false;
        }

        for (int i = 0; i < tmp1.length; i++)
            if(tmp1[i] != tmp2[i])
                return false;
        return true;
    }

}
