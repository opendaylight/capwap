/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.capwap.binding_802_11;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.utils.ByteManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.opendaylight.capwap.ODLCapwapMessageElementFactory.decodeMacAddress;

/**
 * Created by flat on 21/04/16.
 */
public class MsgElem802_11Factory {
    private static final Logger LOG = LoggerFactory.getLogger(MsgElem802_11Factory.class);

    static public WTP_Radio_Information decodeWtpRadioInfoElm(ByteBuf buf, int length) {
        if (buf == null) {
            LOG.error("ByteBuf null WtpRadioInfoElm  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable WtpRadioInfoElm");
            return null;
        }

        WTP_Radio_Information radioInfo = new WTP_Radio_Information();
        radioInfo.setRadioId(buf.readByte());
        buf.skipBytes(3);
        radioInfo.setRadioType(buf.readByte());
        return radioInfo;
    }


    static public   AddWlan decodeAddWlan(ByteBuf buf, int length)
    {

        if (buf == null) {
            LOG.error("ByteBuf null AddWlan  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable AddWlan");
            return null;
        }
        int startIndex = buf.readerIndex();
        AddWlan addWlan = new AddWlan();
        addWlan.setRadioId(buf.readByte());
        addWlan.setWlanId (buf.readByte());
        byte[] capability = new byte[]  {0,0};
        buf.readBytes(capability);
        addWlan.setCapability(ByteManager.byteArrayToUnsingedShort(capability));
        addWlan.setKeyIndex(buf.readByte() );
        addWlan.setKeyStatus(buf.readByte());
        byte [] keyLength = new byte [] {0,0};
        buf.readBytes(keyLength);
        addWlan.setKeyLength(ByteManager.byteArrayToUnsingedShort(keyLength));
        byte [] key = new byte[addWlan.getKeyLength()];
        buf.readBytes(key);
        addWlan.setKey(key);
        byte []  groupTsc = new byte[6];
        buf.readBytes(groupTsc);
        addWlan.setGroupTsc(groupTsc);
        addWlan.setQos(buf.readByte());
        addWlan.setAuthType(buf.readByte());
        addWlan.setMacMode(buf.readByte());
        addWlan.setTunnelMode(buf.readByte());
        addWlan.setSuppressSSID(buf.readByte());
        int currentReadBytes =   buf.readerIndex()- startIndex;
        System.out.println(" decode AddWlan - current bytes = " + currentReadBytes);
        byte [] ssId = new byte[length-currentReadBytes];
        buf.readBytes(ssId);
        addWlan.setSsId(ssId);
        return (addWlan);
    }

    static public   UpdateWlan decodeUpdateWlan(ByteBuf buf, int length)
    {

        if (buf == null) {
            LOG.error("ByteBuf null UpdateWlan  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable updateWlan");
            return null;
        }
//        int startIndex = buf.readerIndex();
        UpdateWlan updateWlan = new UpdateWlan();
        updateWlan.setRadioId(buf.readByte());
        updateWlan.setWlanId (buf.readByte());
        byte[] capability = new byte[]  {0,0};
        buf.readBytes(capability);
        updateWlan.setCapability(ByteManager.byteArrayToUnsingedShort(capability));
        updateWlan.setKeyIndex(buf.readByte() );
        updateWlan.setKeyStatus(buf.readByte());
        byte [] keyLength = new byte [] {0,0};
        buf.readBytes(keyLength);
        updateWlan.setKeyLength(ByteManager.byteArrayToUnsingedShort(keyLength));
        byte [] key = new byte[updateWlan.getKeyLength()];
        buf.readBytes(key);
        updateWlan.setKey(key);

        return (updateWlan);

    }
    static public  DeleteWlan decodeDeleteWlan(ByteBuf buf, int length)
    {

        if (buf == null) {
            LOG.error("ByteBuf null UpdateWlan  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable updateWlan");
            return null;
        }
  //      int startIndex = buf.readerIndex();
        DeleteWlan deleteWlan = new DeleteWlan();
        deleteWlan.setRadioId(buf.readByte());
        deleteWlan.setWlanId (buf.readByte());

        return (deleteWlan);

    }

    static public   Antenna decodeAntenna(ByteBuf buf, int length)
    {

        if (buf == null) {
            LOG.error("ByteBuf null Antenna  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable Antenna");
            return null;
        }

        Antenna antenna = new Antenna();
        int startIndex = buf.readerIndex();
        antenna.setRadioId(buf.readByte());
        antenna.setDiversity(buf.readByte());
        antenna.setCombiner(buf.readByte());
        antenna.setAntennaCount(buf.readByte());
        int currentReadBytes =   buf.readerIndex()- startIndex;
        System.out.println(" decode Antenna - current bytes = " + currentReadBytes);
        byte [] antennaSelection = new byte[length-currentReadBytes];
        buf.readBytes(antennaSelection);
        antenna.setAntennaSelection(antennaSelection);

        return antenna;
    }

    static public  AssignedWtpBssid decodeAssignedWtpBssid(ByteBuf buf, int length)
    {
        if (buf == null) {
            LOG.error("ByteBuf null AssignedWtpBssid  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable AssignedWtpBssid");
            return null;
        }

        AssignedWtpBssid assignedWtpBssid = new AssignedWtpBssid();

        int startIndex = buf.readerIndex();
        assignedWtpBssid.setRadioId(buf.readByte());
        assignedWtpBssid.setWlanId(buf.readByte());
        byte [] bssId = new byte []  {0,0,0,0,0,0};
        buf.readBytes(bssId);
        assignedWtpBssid.setBssId(bssId);
        return (assignedWtpBssid);
    }

    static public   DirectSeqCtrl decodeDirectSeqCtrl(ByteBuf buf, int length)
    {

        if (buf == null) {
            LOG.error("ByteBuf null DirectSeqCtrl  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable DirectSeqCtrl");
            return null;
        }
        DirectSeqCtrl directSeqCtrl = new  DirectSeqCtrl ();
        directSeqCtrl.setRadioId(buf.readByte());
        directSeqCtrl.setCurrentChannel(buf.readByte());
        directSeqCtrl.setCurrentCCA(buf.readByte());
        byte [] edt = new byte[] {0,0,0,0};
        buf.readBytes(edt);
        directSeqCtrl.setEnergyDetectThreshold(ByteManager.byteArrayToUnsingedInt(edt));

         return directSeqCtrl;
    }

    static public   MicCounterMeasures decodeMicCounterMeasures(ByteBuf buf, int length)
    {
        if (buf == null) {
            LOG.error("ByteBuf null MicCounterMeasures  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable MicCounterMeasures");
            return null;
        }

        MicCounterMeasures micCounterMeasures = new    MicCounterMeasures ();
        micCounterMeasures.setRadioId(buf.readByte());
        micCounterMeasures.setWlanId(buf.readByte());


        byte [] macAddress = new byte[] {0,0,0,0,0,0};
        buf.readBytes(macAddress);
        micCounterMeasures.setMacAddress(macAddress);

            return (micCounterMeasures);

    }

    static public   TxPower decodeTxPower(ByteBuf buf, int length)
    {
        if (buf == null) {
            LOG.error("ByteBuf null TxPower  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable TxPower");
            return null;
        }

        TxPower txPower = new TxPower();
        txPower.setRadioId(buf.readByte());
        buf.skipBytes(1);
        byte [] curTxPower = new byte[] {0,0};
        buf.readBytes(curTxPower);
        txPower.setCurrentTxPower(ByteManager.byteArrayToUnsingedShort(curTxPower));
        return txPower;
    }

    static public  WtpRadioConfiguration decodeWtpRadioConfiguration(ByteBuf buf, int length)
    {

        if (buf == null) {

            LOG.error("ByteBuf null WtpRadioConfiguration  ");
            return null;
        }
        if (!buf.isReadable()) {
            LOG.error("ByteBuf not readable WtpRadioConfiguration");
            return null;
        }
        WtpRadioConfiguration wtpRadioConfiguration = new     WtpRadioConfiguration ();
        wtpRadioConfiguration.setRadioId(buf.readByte());
        wtpRadioConfiguration.setShortPreamble(buf.readByte());
        wtpRadioConfiguration.setNumOfBssIds(buf.readByte());
        wtpRadioConfiguration.setDtimPeriod(buf.readByte());
        byte [] bssId = new byte [] {0,0,0,0,0,0};
        buf.readBytes(bssId);
        //wtpRadioConfiguration.setBssId(decodeMacAddress(buf,length));
        wtpRadioConfiguration.setBssId(bssId);
        byte[] beaconPeriod = new byte[]  {0,0};
        buf.readBytes(beaconPeriod);
        wtpRadioConfiguration.setBeaconPeriod(ByteManager.byteArrayToUnsingedShort(beaconPeriod));
        byte [] countryString = new byte[] {0,0,0,0};
        buf.readBytes(countryString);
        wtpRadioConfiguration.setCountryString(countryString);
        return (wtpRadioConfiguration);
    }

    static public   WtpRadioFailAlarmIndication decodeWtpRadioFailAlarmIndication(ByteBuf buf, int length)
    {
        System.out.println("decodeWtpRadioFailAlarmIndication");
        if (buf == null) {
            System.out.println("decodeWtpRadioFailAlarmIndication-1");
            LOG.error("ByteBuf null WtpRadioFailAlarmIndication  ");
            return null;
        }
        if (!buf.isReadable()) {
            System.out.println("decodeWtpRadioFailAlarmIndication-2");
            LOG.error("ByteBuf not readable WtpRadioFailAlarmIndication");
            return null;
        }
    //    int startIndex = buf.readerIndex();
        WtpRadioFailAlarmIndication wtpRadioFailAlarmIndication = new WtpRadioFailAlarmIndication();
        wtpRadioFailAlarmIndication.setRadioId(buf.readByte());
        wtpRadioFailAlarmIndication.setFailureType(buf.readByte());
        wtpRadioFailAlarmIndication.setStatus(buf.readByte());
        if(buf.readByte() != 0)
        {
            System.out.println("decodeWtpRadioFailAlarmIndication--3");
            LOG.error("WtpRadioFailAlarmIndication pad should be 0");
            return null;
        }
        //wtpRadioFailAlarmIndication.setPad (buf.readByte());
        System.out.println("decodeWtpRadioFailAlarmIndication-4");
        return (wtpRadioFailAlarmIndication);

    }

}
