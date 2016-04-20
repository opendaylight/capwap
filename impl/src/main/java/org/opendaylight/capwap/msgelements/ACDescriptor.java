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
import org.opendaylight.capwap.msgelements.subelem.ACInformationSubElement;
import org.opendaylight.capwap.utils.ByteManager;

import java.util.ArrayList;

/**
 * Created by flat on 16/04/16.
 */
public class ACDescriptor implements ODLCapwapMessageElement {
    int stations = 0;
    int limit = 0;
    int activeWtps = 0;
    int maxWtps = 0;
    byte security = 0;   //ojnly 3 bits ued  so we can use native byte
    byte rmac = 0;
    byte dtlsPolicy = 0;
    int msgElm = 0;
    byte reserved = 0;

    byte securityRMask = 0b00000001;
    byte securityXMask = 0b00000010;
    byte securitySMask = 0b00000100;

    byte dtlspolicyRMask = 0b00000001;
    byte dtlspolicyCMask = 0b00000010;
    byte dtlspolicyDMask = 0b00000100;

    ArrayList<ACInformationSubElement> acInfolist = null;


    public ACDescriptor(){
        this.msgElm= ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_DESCRIPTOR;
        this.acInfolist = new ArrayList<ACInformationSubElement>();
    }

    public int getStations() {
        return this.stations;
    }

    public ACDescriptor setStations(int stations) {
        this.stations = stations;
        return this;
    }

    public int getLimit() {
        return this.limit;
    }

    public ACDescriptor setLimit(int limit) {
        this.limit = limit;
        return this;
    }

    public int getActiveWtps() {
        return activeWtps;
    }

    public ACDescriptor setActiveWtps(int activeWtps) {
        this.activeWtps = activeWtps;
        return this;
    }

    public int getMaxWtps() {
        return this.maxWtps;
    }

    public ACDescriptor setMaxWtps(int maxWtps) {
        this.maxWtps = maxWtps;
        return this;
    }

    public boolean isSecurityRbitSet() {
        return 0!=(this.security & this.securityRMask );
    }

    public boolean isSecurityXbitSet() {
        return 0!=(this.security & this.securityXMask );
    }

    public boolean isSecuritySbitSet() {
        return 0!=(this.security & this.securitySMask );
    }

    public ACDescriptor setSecurityRbit() {
        this.security |= this.securityRMask ;
        return this;
    }
    public ACDescriptor setSecurityXbit() {
        this.security |= this.securityXMask ;
        return this;
    }
    public ACDescriptor setSecuritySbit() {
        this.security |= this.securitySMask ;
        return this;
    }


    public short getRmac() {
        return this.rmac;
    }

    public ACDescriptor setRmac(byte rmac) {
        this.rmac = rmac;
        return this;
    }


    public boolean isDtlsPolicyRbitSet() {
        return 0!=(this.security & this.dtlspolicyRMask );
    }

    public boolean isDtlsPolicyCbitSet() {
        return 0!=(this.security & this.dtlspolicyCMask );
    }

    public boolean isDtlsPolicyDbitSet() {
        return 0!=(this.security & this.dtlspolicyDMask );
    }


    public ACDescriptor setDtlsPolicyRbit() {
        this.dtlsPolicy |= this.dtlspolicyRMask ;
        return this;
    }
    public ACDescriptor setDtlsPolicyCbit() {
        this.dtlsPolicy |= this.dtlspolicyCMask ;
        return this;
    }
    public ACDescriptor setDtlsPolicyDbit() {
        this.dtlsPolicy |= this.dtlspolicyDMask ;
        return this;
    }


    public int getMsgElm() {
        return msgElm;
    }



    public ACDescriptor addAcInformationSubElem(ACInformationSubElement e){

        this.acInfolist.add(e);
        return this;

    }

    public ACInformationSubElement getAcInformationSubElem(long vendorInfo, int infoType){

        for(ACInformationSubElement e: this.acInfolist ){
            if ((e.getAcInfoVendorId() == vendorInfo) && (e.getAcInfoType() == infoType))
                return e;

        }
        return null;

    }

    public  boolean deleteAcInformationSubElem(long vendorInfo,int infoType){

        for(ACInformationSubElement e: this.acInfolist ){
            if ((e.getAcInfoVendorId() == vendorInfo) && (e.getAcInfoType() == infoType))
                this.acInfolist.remove(e);
            return true;
        }
        return false;
    }


    @Override
    public int encode(ByteBuf buf) {

        int start = buf.writerIndex();
        //encode stations
        buf.writeBytes(ByteManager.unsignedShortToArray(this.stations));
        //encode limit
        buf.writeBytes(ByteManager.unsignedShortToArray(this.limit));
        //encode activ WTP
        buf.writeBytes(ByteManager.unsignedShortToArray(this.activeWtps));
        //encode MAX wtp
        buf.writeBytes(ByteManager.unsignedShortToArray(this.maxWtps));
        //encode security
        buf.writeByte(this.security);
        //encode rmac
        buf.writeByte(this.rmac);
        //encode reserved
        buf.writeByte(this.reserved);
        //encode dtls
        buf.writeByte(this.dtlsPolicy);
        //encode acinformation sub element
        for (ACInformationSubElement e: this.acInfolist){
            e.encode(buf);
        }
        return buf.writerIndex()-start;
    }

    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return this.msgElm;
    }
}
