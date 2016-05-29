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
 * Created by flat on 15/04/16.
 */


/*
        0 1 2 3 4 5 6 7
        +-+-+-+-+-+-+-+-+
        | Discovery Type|
        +-+-+-+-+-+-+-+-+

        Type:   20 for Discovery Type
      0 -   Unknown

      1 -   Static Configuration

      2 -   DHCP

      3 -   DNS

      4 -   AC Referral (used when the AC was configured either through
            the AC IPv4 List or AC IPv6 List message element)

*/
public class DiscoveryType  implements ODLCapwapMessageElement {
    protected final byte unknown = 0;
    protected final byte staticCfg = 1;
    protected final byte dhcp = 2;
    protected final byte dns = 3;
    protected final byte acReferral = 4;
    int msgElemeType = 0;

    /* Initialize each elem  with the element type . This is a  predefined value */
    public  DiscoveryType(){
        this.msgElemeType = ODLCapwapConsts.CAPWAP_ELMT_TYPE_DISCOVERY_TYPE;
    }
    byte discoveryType = 0;
    byte getDiscoveryType(){
        return discoveryType;
    }

    public DiscoveryType setType(byte dType){
        this.discoveryType = dType;
        return this;
    }

    public DiscoveryType setUnknown(){
        this.setType((byte)0);
        return this;
    }

    public DiscoveryType setStatic(){
        this.setType((byte)1);
        return this;
    }

    public DiscoveryType setDhcp(){
        this.setType((byte)2);
        return this;
    }

    public DiscoveryType setDns(){
        this.setType((byte)3);
        return this;
    }
    public DiscoveryType setAcReferral(){
        this.setType((byte)4);
        return this;
    }

    public int getMsgElemeType(){
        return this.msgElemeType;
    }

    public int encode(ByteBuf buf){
        int start = buf.writerIndex();
        System.out.printf("\nWriter index @ function %d  %s",buf.writerIndex(),"encode");
        int end  = 0;

        buf.writeByte(this.discoveryType);

        end  = buf.writerIndex();

        return end - start;
    }
    @Override
    public boolean equals (Object obj)
    {

	     if (obj == this)
         {
                return true;

         }
         if (!(obj instanceof DiscoveryType))
         {
            return false;
         }
         if (((DiscoveryType)obj).getDiscoveryType() == discoveryType)
         {
             return true;
         }
         return false;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElemeType;
    }
}
