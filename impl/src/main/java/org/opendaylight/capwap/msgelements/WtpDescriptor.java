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
import org.opendaylight.capwap.msgelements.subelem.DescriptorSubElement;
import org.opendaylight.capwap.msgelements.subelem.EncryptionSubElement;
import org.opendaylight.capwap.utils.ByteManager;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by flat on 15/04/16.
 *  0                   1                   2                   3
 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |   Max Radios  | Radios in use |  Num Encrypt  |Encryp Sub-Elmt|
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 |     Encryption Sub-Element    |    Descriptor Sub-Element...
 +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+

 */

public class WtpDescriptor implements ODLCapwapMessageElement {

    int msgElemeType = 0;
    short maxRadios = 0;
    short radioInUse = 0;
    short numEncrypt = 0;

    ArrayList <EncryptionSubElement> encysubElemList = null;


    ArrayList <DescriptorSubElement> descSubElemList = null;

    public WtpDescriptor(){
        this.msgElemeType = ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR;
        this.encysubElemList = new ArrayList<EncryptionSubElement>();
        this.descSubElemList = new ArrayList<DescriptorSubElement>();
    }

    public ArrayList<EncryptionSubElement> getEncysubElemList() {
        return encysubElemList;
    }

    public void setEncysubElemList(ArrayList<EncryptionSubElement> encysubElemList) {
        this.encysubElemList = encysubElemList;
    }

    public ArrayList<DescriptorSubElement> getDescSubElemList() {
        return descSubElemList;
    }

    public void setDescSubElemList(ArrayList<DescriptorSubElement> descSubElemList) {
        this.descSubElemList = descSubElemList;
    }


    public int getMsgElemeType() {
        return msgElemeType;
    }

    public WtpDescriptor setMsgElemeType(int msgElemeType) {
        this.msgElemeType = msgElemeType;
        return this;
    }

    public short getMaxRadios() {
        return maxRadios;
    }

    public WtpDescriptor setMaxRadios(short maxRadios) {
        this.maxRadios = maxRadios;
        return this;
    }

    public short getRadioInUse() {
        return radioInUse;
    }

    public WtpDescriptor setRadioInUse(short radioInUse) {
        this.radioInUse = radioInUse;
        return this;
    }

    public short getNumEncrypt() {
        return numEncrypt;
    }

    public WtpDescriptor setNumEncrypt(short numEncrypt) {
        this.numEncrypt = numEncrypt;
        return this;
    }

    //Add encryption subelement
    //When you add a sub element increment the numEncrypt Counter
    public WtpDescriptor addEncryptSubElement(EncryptionSubElement e){

        this.encysubElemList.add(e);
        this.setNumEncrypt((short)(this.getNumEncrypt()+1));
        return this;

    }

    public EncryptionSubElement getEncryptSubElement(byte wbid){

        for(EncryptionSubElement e: this.encysubElemList ){
            if (e.getWbid()== wbid)
                return e;
        }
        return null;
    }

    public  boolean deleteEncryptionSubElement(byte wbid){

        for(EncryptionSubElement e: this.encysubElemList ){
            if (e.getWbid()== wbid)
                this.encysubElemList.remove(e);
            //Decrease Num Encrypt Field
            if(this.getNumEncrypt() >0)this.setNumEncrypt((short)(this.getNumEncrypt()-1));
            return true;
        }
        return false;
    }

    //Add Descriptor Sub element

    public WtpDescriptor addDescriptorSubElm(DescriptorSubElement e){

        this.descSubElemList.add(e);
        return this;

    }

    public DescriptorSubElement getDescriptorSubElm(int vendorId, int descType){

        for(DescriptorSubElement e: this.descSubElemList ){
            if ((e.getVendorId() == vendorId) && (e.getDescType() == descType))
                return e;
        }
        return null;
    }

    public  boolean deleteDescriptorSubElm(int vendorId, int descType){

        for(DescriptorSubElement e: this.descSubElemList ){
            if ((e.getVendorId() == vendorId) && (e.getDescType() == descType))
                this.descSubElemList.remove(e);
            return true;
        }
        return false;
    }


    @Override
    public int encode(ByteBuf buf)
    {
        int start = buf.writerIndex();
        //encode Max Radios
        buf.writeByte(ByteManager.shortToUnsingedByte(this.maxRadios));

        //encode radiosInUse
        buf.writeByte(ByteManager.shortToUnsingedByte(this.radioInUse));

        //encode numEncrypt
        buf.writeByte(ByteManager.shortToUnsingedByte(this.numEncrypt));


        //encode Encryption Sub-Element
        for (EncryptionSubElement element :this.encysubElemList){
            element.encode(buf);
        }

        //encode Descriptor Sub-Element
        for (DescriptorSubElement element :this.descSubElemList){
            element.encode(buf);
        }

        return buf.writerIndex()-start;
    }



    @Override
    public ODLCapwapMessageElement decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType()

    {
        return this.getMsgElemeType();
    }

    @Override
    public boolean equals (Object o)
    {
        if ( o == this)
          return true;
        if (!(o instanceof WtpDescriptor))
            return false;
        if ((msgElemeType == ((WtpDescriptor) o).getType()) &&
           (maxRadios == ((WtpDescriptor) o).getMaxRadios())&&
            (radioInUse  == ((WtpDescriptor) o).getRadioInUse())&&
            (numEncrypt == ((WtpDescriptor) o).getNumEncrypt()))
        {
            ArrayList <EncryptionSubElement> tmp1 = ((WtpDescriptor) o).getEncysubElemList();
            Iterator<EncryptionSubElement> itr =  tmp1.iterator();
            for (EncryptionSubElement e_o : encysubElemList)
            {

                EncryptionSubElement e_n = itr.next();
                if (!(e_o.equals(e_n)))
                {
                    return false;
                }

            }
            ArrayList <DescriptorSubElement> tmp2 = ((WtpDescriptor) o).getDescSubElemList();
            Iterator<DescriptorSubElement> itr1 =  tmp2.iterator();
            for (DescriptorSubElement e_o : descSubElemList)
            {

                DescriptorSubElement e_n = itr1.next();
                if (!(e_o.equals(e_n)))
                {
                    return false;
                }

            }

           return true;
        }
        return false;

    }

    }
