/*
 * Copyright (c) 2015 Abi Varghese and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.msgelements.*;
import org.opendaylight.capwap.binding_802_11.*;
import org.opendaylight.capwap.msgelements.subelem.ACInformationSubElement;
import org.opendaylight.capwap.msgelements.subelem.EncryptionSubElement;

import java.util.ArrayList;
import java.util.Iterator;

public class ODLCapwapControlMessage {
    long msgType = 0;
    short seqNo = 0;
    int msgLen = 0;
    short flags= 0;


    protected ArrayList<ODLCapwapMessageElement> elements =null;


    ODLCapwapControlMessage() {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
        this.elements = new ArrayList<ODLCapwapMessageElement>();
    }

    ODLCapwapControlMessage(ArrayList<ODLCapwapMessageElement> l) {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
        this.elements = l;
    }
   /*
    ODLCapwapControlMessage(ByteBuf bbuf) {
        this.msgType = 0;
        this.seqNo = 0;
        this.msgLen = 0;
        this.flags = 0;
    }
    */
    




    /*
     0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |                       Message Type                            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |    Seq Num    |        Msg Element Length     |     Flags     |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     | Msg Element [0..N] ...
     +-+-+-+-+-+-+-+-+-+-+-+-+

     */
    public  int encode(ByteBuf buf) {

        int start = buf.writerIndex();
        int end = 0;
        int msgLengthIndex = 0;
        int msgElemLength = 0;

        buf.writeInt((int)this.msgType);
        buf.writeByte((byte)this.seqNo);
        msgLengthIndex = buf.writerIndex();
        System.out.printf("\nWriter index @ function %d  %s",msgLengthIndex,"encodeCtrl");
        buf.setIndex(buf.readerIndex(),buf.writerIndex()+2);
        System.out.printf("\nWriter index @ function after incrementing %d  %s",buf.writerIndex(),"encodeCtrl");

        buf.writeByte((byte)this.flags);



        msgElemLength = encodeMsgElements(buf);
        this.msgLen = msgElemLength; //this is set to check automated decoding test cases. This will be available only after encoding

        //Set the Message lement length
        buf.setShort(msgLengthIndex,msgElemLength);

        end = buf.writerIndex();
        System.out.printf("\nWriter index @ function %d  %s",msgLengthIndex,"encodeCtrl");
        return end-start;

    }

    /*

      0                   1                   2                   3
      0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |              Type             |             Length            |
     +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
     |   Value ...   |
     +-+-+-+-+-+-+-+-+

     */

    private int encodeMsgElements(ByteBuf buf){

        int start = buf.writerIndex();
        System.out.printf("\nWriter index @ function %d  %s",buf.writerIndex(),"encodeMsgElements");
        int end = 0;

        for (ODLCapwapMessageElement element:this.elements ) {
            int lengthIndex = 0;
            int length = 0;

            buf.writeShort(element.getType());
            lengthIndex = buf.writerIndex();
            buf.setIndex(buf.readerIndex(),buf.writerIndex()+2);

            int len = element.encode(buf);

            System.out.printf("\nLength of Msg element   %d",len);
            buf.setShort(lengthIndex,len);
        }
        end = buf.writerIndex();
        return end-start;

    }


    public boolean addMessageElement(ODLCapwapMessageElement e) {
        this.elements.add(e);

        return true;
    }



    public long getMsgType() {
        return msgType;
    }

    public void setMsgType(long msgType) {
        this.msgType = msgType;
    }

    public short getSeqNo() {
        return seqNo;
    }

    public void setSeqNo(short seqNo) {
        this.seqNo = seqNo;
    }

    public int getMsgLen() {
        return msgLen;
    }

    public void setMsgLen(int msgLen) {
        this.msgLen = msgLen;
    }

    public short getFlags() {
        return flags;
    }

    public void setFlags(short flags) {
        this.flags = flags;
    }
    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof ODLCapwapControlMessage)) {
            return false;
        }
        if ((msgType == ((ODLCapwapControlMessage) o).getMsgType()) &&
        (seqNo == ((ODLCapwapControlMessage) o).getSeqNo()) &&
                (msgLen == ((ODLCapwapControlMessage) o).getMsgLen()) &&
                (flags == ((ODLCapwapControlMessage) o).getFlags()))
        {
        
       		Iterator <ODLCapwapMessageElement> itr = ((ODLCapwapControlMessage) o).getElements().iterator();
        	for (ODLCapwapMessageElement e_c : elements) {
            	ODLCapwapMessageElement e_o = itr.next();
            	boolean result = compareEachMessageElement(e_c, e_o);
           		 if (!result) {
           			System.out.println ("Comparison of message elements failed {}");
            		return result;
            	}

        	}
			return true;
		}
        return false;

    }

    public ArrayList<ODLCapwapMessageElement> getElements() {
        return elements;
    }

    public void setElements(ArrayList<ODLCapwapMessageElement> elements) {
        this.elements = elements;
    }

    boolean compareEachMessageElement(ODLCapwapMessageElement o, ODLCapwapMessageElement n) {

        StackTraceElement bTop = Thread.currentThread().getStackTrace()[1];
      System.out.println("In func compareEachMessageElement" + o.getType());

        boolean result = false;
        switch (o.getType()) {
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DISCOVERY_TYPE:
                System.out.println("In func compareEachMessageElement- DiscoveryType");
                DiscoveryType oo= (DiscoveryType) o;
                DiscoveryType nn= (DiscoveryType) n;

                result = oo.equals(nn);
                System.out.println("In func compareEachMessageElement- DiscoveryType result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_NAME:
                System.out.println("In func compareEachMessageElement- ACNAME");
                ACName  ac1= (ACName) o;
                ACName ac2= (ACName) n;
                result = ac1.equals(ac2);

                System.out.println("In func compareEachMessageElement- ACNAME result = " + result);

                break;



            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_DESCRIPTOR:
                //
                System.out.println("In func compareEachMessageElement- ACDescriptor");
                ACDescriptor acd1 =       (ACDescriptor) o;

                ACDescriptor acd2= (ACDescriptor) n;
                result = acd1.equals(acd2);


                System.out.println("In func compareEachMessageElement- ACDescriptor result = " + result);

                break;


            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_CONTROL_IPV4_ADDR:
                System.out.println("In func compareEachMessageElement- CapwapControlIPV4Addr");
                CapwapControlIPV4Addr ctrlIpAddr = (CapwapControlIPV4Addr)o;
                CapwapControlIPV4Addr ctrlIpAddr2 = (CapwapControlIPV4Addr)n;
                result = ctrlIpAddr.equals(ctrlIpAddr2);

                System.out.println("In func compareEachMessageElement- CapwapControlIPV4Addr result = " + result);

                break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV4_ADDR:
            System.out.println("In func compareEachMessageElement- CapwapLocalIPV4Addr");
            CapwapLocalIPV4Address localIpAddr = (CapwapLocalIPV4Address)o;
            CapwapLocalIPV4Address localIpAddr2 = (CapwapLocalIPV4Address)n;
            result = localIpAddr.equals(localIpAddr2);

            System.out.println("In func compareEachMessageElement- CapwapLocalIPV4Address result = " + result);

            break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_LOCAL_IPV6_ADDR:
                System.out.println("In func compareEachMessageElement- CapwapLocalIPV6Addr");
                CapwapLocalIPV6Address localIpV6Addr = (CapwapLocalIPV6Address)o;
                CapwapLocalIPV6Address localIpV6Addr2 = (CapwapLocalIPV6Address)n;
                result = localIpV6Addr.equals(localIpV6Addr2);

                System.out.println("In func compareEachMessageElement- CapwapLocalIPV6Address result = " + result);

            break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_TIMERS:
                System.out.println("In func compareEachMessageElement- CapwapTimers");
                CapwapTimers timer1 = (CapwapTimers)o;
                CapwapTimers timer2 = (CapwapTimers)n;
                result = timer1.equals(timer2);

                System.out.println("In func compareEachMessageElement- CapwapTimers result = " + result);

            break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_CAPWAP_TRANSPORT_PROTO:
                System.out.println("In func compareEachMessageElement- CapwapTransportProtocol");

                CapwapTransportProtocol   trpro1 =  (CapwapTransportProtocol)o;
                CapwapTransportProtocol   trpro2 =  (CapwapTransportProtocol)n;
                result = trpro1.equals(trpro2);

                System.out.println("In func compareEachMessageElement- CapwapTransportProtocol result = " + result);

            break;

            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DATA_TRANSFER_DATA:
                System.out.println("In func compareEachMessageElement- DataTransferData");
                DataTransferData dt1 = (DataTransferData)o;
                DataTransferData dt2 = (DataTransferData)n;
                result = dt1.equals(dt2);

                System.out.println("In func compareEachMessageElement- DataTransferData result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DATA_TRANSFER_MODE:
                System.out.println("In func compareEachMessageElement- DataTransferMode");
                DataTransferMode dtm1 = (DataTransferMode)o;
                DataTransferMode dtm2 = (DataTransferMode)n;
                result = dtm1.equals(dtm2);

                System.out.println("In func compareEachMessageElement- DataTransferMode result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DECRYPTION_ERROR_REPORT_PERIOD:
                System.out.println("In func compareEachMessageElement- DecryptionErrorPeriod");
                DecryptionErrorPeriod ep1 = (DecryptionErrorPeriod)o;
                DecryptionErrorPeriod ep2 = (DecryptionErrorPeriod)n;
                result = ep1.equals(ep2);

                System.out.println("In func compareEachMessageElement- DecryptionErrorPeriod result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DECRYPTION_ERROR_REPORT:
                System.out.println("In func compareEachMessageElement- DecryptionErrorReport");
                DecryptionErrorReport errorReport1 = (DecryptionErrorReport)o;
                DecryptionErrorReport errorReport2 = (DecryptionErrorReport)n;
                result = errorReport1.equals(errorReport2);

                System.out.println("In func compareEachMessageElement- DecryptionErrorReport result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DELETE_MAC_ACL_ENTRY:
                System.out.println("In func compareEachMessageElement- DeleteMacAclEntry");
                DeleteMacAclEntry macAcl1 = (DeleteMacAclEntry)o;
                DeleteMacAclEntry macAcl2 = (DeleteMacAclEntry)n;
                result = macAcl1.equals(macAcl2);

                System.out.println("In func compareEachMessageElement- DeleteMacAclEntry result = " + result);


                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DELETE_STATION:
                System.out.println("In func compareEachMessageElement- DeleteStation");
                DeleteStation deleteStation1 = (DeleteStation)o;
                DeleteStation deleteStation2 = (DeleteStation)n;
                result = deleteStation1.equals(deleteStation2);

                System.out.println("In func compareEachMessageElement- DeleteStation result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_DUPLICATE_IPV4_ADDRESS:
                System.out.println("In func compareEachMessageElement- DuplicateIPV4Addr");
                DuplicateIPV4Addr dupIp1 = (DuplicateIPV4Addr)o;
                DuplicateIPV4Addr dupIp2 = (DuplicateIPV4Addr)n;

                result = dupIp1.equals(dupIp2) ;

                System.out.println("In func compareEachMessageElement- DuplicateIPV4Addr result = " + result);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_ECN_SUPPORT:
                System.out.println("In func compareEachMessageElement- ECNSupport");
                ECNSupport ecn1 = (ECNSupport)o;
                ECNSupport ecn2 = (ECNSupport)n;
                result = ecn1.equals(ecn2);
                System.out.println("In func compareEachMessageElement- ECNSupport result = " + result);
                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_IDLE_TIMEOUT:
                System.out.println("In func compareEachMessageElement- IdleTimeOut");
                IdleTimeOut timeOut1 = (IdleTimeOut)o;
                IdleTimeOut timeOut2 = (IdleTimeOut)n;

                result = timeOut1.equals(timeOut2);
                System.out.println("In func compareEachMessageElement- IdleTimeOut result = " +result);

                 break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_IMAGE_DATA:
                System.out.println("In func compareEachMessageElement- ImageData");
                ImageData data1 = (ImageData)o;
                ImageData data2 = (ImageData)n;
                result = data1.equals(data2);
                System.out.println("In func compareEachMessageElement- ImageData result " + result);
                break;
             case  ODLCapwapConsts.CAPWAP_ELMT_TYPE_IMAGE_IDENTIFIER:
                 System.out.println("In func compareEachMessageElement- ImageIdentifier");
                 ImageIdentifier id1 = (ImageIdentifier)o;
                 ImageIdentifier id2 = (ImageIdentifier)n;
                 result = id1.equals(id2);
                 System.out.println("In func compareEachMessageElement- ImageIdentifier result = " + result);

                 break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_IMAGE_INFORMATION:
                System.out.println("In func compareEachMessageElement- ImageInformation");
                ImageInformation imageInformation1 = (ImageInformation)o;
                ImageInformation imageInformation2 = (ImageInformation)n;

                result = imageInformation1.equals(imageInformation2);
                System.out.println("In func compareEachMessageElement- ImageInformation result = " +result);


                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_INITIATE_DOWNLOAD:
                System.out.println("In func compareEachMessageElement- InitiateDownload");
                InitiateDownload d1 = (InitiateDownload)o;
                InitiateDownload d2 = (InitiateDownload)n;

                result = d1.equals(d2);
                System.out.println("In func compareEachMessageElement- InitiateDownload result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_AC_IPV4_LIST:
                System.out.println("In func compareEachMessageElement- IPV4AddrList");
                IPV4AddrList ipList1 = (IPV4AddrList)o;
                IPV4AddrList ipList2 = (IPV4AddrList)n;

                result = ipList1.equals(ipList2);

                System.out.println("In func compareEachMessageElement- IPV4AddrList result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_LOCATION_DATA:
                System.out.println("In func compareEachMessageElement- LocationData");
                LocationData locationData1 = (LocationData)o;
                LocationData locationData2 = (LocationData)n;

                result = locationData1.equals(locationData2);

                System.out.println("In func compareEachMessageElement- LocationData result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_MAX_MESSAGE_LENGTH:
                System.out.println("In func compareEachMessageElement- MaxMsgLength");
                MaxMsgLength maxMsgLength1 = (MaxMsgLength)o;
                MaxMsgLength maxMsgLength2 = (MaxMsgLength)n;

                result = maxMsgLength1.equals(maxMsgLength2);

                System.out.println("In func compareEachMessageElement- MaxMsgLength result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_MTU_DISCOVERY_PADDING:
                System.out.println("In func compareEachMessageElement- MtuDisPa dding");
                MtuDisPadding   mtuDisPadding1 = (MtuDisPadding)o;
                MtuDisPadding   mtuDisPadding2 = (MtuDisPadding)n;

                result = mtuDisPadding1.equals(mtuDisPadding2);


                System.out.println("In func compareEachMessageElement- MtuDisPa result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_RADIO_ADMIN_STATE:
                System.out.println("In func compareEachMessageElement- RadioAdministrativeState");

                RadioAdministrativeState radioAdminState1 = (RadioAdministrativeState)o;
                RadioAdministrativeState radioAdminState2 = (RadioAdministrativeState)n;


                result = radioAdminState1.equals(radioAdminState2);

                System.out.println("In func compareEachMessageElement- RadioAdministrativeState result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_RADIO_OPER_STATE:
                System.out.println("In func compareEachMessageElement- RadioOperationalState");
                RadioOperationalState radioOpStatus1 = (RadioOperationalState)o;
                RadioOperationalState radioOpStatus2 = (RadioOperationalState)n;

                result = radioOpStatus1.equals(radioOpStatus2);

                System.out.println("In func compareEachMessageElement- RadioOperationalState result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_RESULT_CODE:
                System.out.println("In func compareEachMessageElement- ResultCode");
                ResultCode rc1 = (ResultCode)o;
                ResultCode rc2 = (ResultCode)n;

                result = rc1.equals(rc2);

                System.out.println("In func compareEachMessageElement- ResultCode result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_RETURNED_MESSAGE_ELEMENT:
                System.out.println("In func compareEachMessageElement- ReturnedMessageElement");
                ReturnedMessageElement rtrMsgElem1 = (ReturnedMessageElement)o;
                ReturnedMessageElement rtrMsgElem2 = (ReturnedMessageElement)n;

                result = rtrMsgElem1.equals(rtrMsgElem2);

                System.out.println("In func compareEachMessageElement- ReturnedMessageElement result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_SESSION_ID:
                System.out.println("In func compareEachMessageElement- SessionID");
                SessionID sessionID1 = (SessionID)o;
                SessionID sessionID2 = (SessionID)n;

                 result = sessionID1.equals(sessionID2);

                System.out.println("In func compareEachMessageElement- SessionID result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_STATISTICS_TIMER:
                System.out.println("In func compareEachMessageElement- StatisticsTimer");
                StatisticsTimer st1 = (StatisticsTimer)o;
                StatisticsTimer st2 = (StatisticsTimer)n;
                result = st1.equals(st2);

                System.out.println("In func compareEachMessageElement- StatisticsTimer result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_VENDOR_SPECIFIC_PAYLOAD:
                System.out.println("In func compareEachMessageElement- VendorSpecificPayload");
                VendorSpecificPayload vsp1 = (VendorSpecificPayload)o;
                VendorSpecificPayload vsp2 = (VendorSpecificPayload)n;
                result = vsp1.equals(vsp2);

                System.out.println("In func compareEachMessageElement- VendorSpecificPayload result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_BOARD_DATA:
                System.out.println("In func compareEachMessageElement- CAPWAP_ELMT_TYPE_WTP_BOARD_DATA");
                WtpBoardDataMsgElem wtpBoard =    (WtpBoardDataMsgElem)o;
                WtpBoardDataMsgElem wtpBoard1 =    (WtpBoardDataMsgElem)n;

                result = wtpBoard.equals(wtpBoard1);


                System.out.println("In func compareEachMessageElement- CAPWAP_ELMT_TYPE_WTP_BOARD_DATA result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR:
                System.out.println("In func compareEachMessageElement- CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR");
                WtpDescriptor wtpDescriptor =    (WtpDescriptor)o;
                WtpDescriptor wtpDescriptor1 =    (WtpDescriptor)n;

                result = wtpDescriptor.equals(wtpDescriptor1);


                System.out.println("In func compareEachMessageElement- CAPWAP_ELMT_TYPE_WTP_DESCRIPTOR result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_FALLBACK:
                System.out.println("In func compareEachMessageElement- WtpFallBack");
                WtpFallBack wfallback1 = (WtpFallBack)o;
                WtpFallBack wfallback2 = (WtpFallBack)n;
                result = wfallback1.equals(wfallback2);

                System.out.println("In func compareEachMessageElement- WtpFallBack result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_FRAME_TUNNEL_MODE:
                System.out.println("In func compareEachMessageElement- WtpFrameTunnelMode");
                WtpFrameTunnelModeMsgElem wftm1 = (WtpFrameTunnelModeMsgElem)o;
                WtpFrameTunnelModeMsgElem wftm2 = (WtpFrameTunnelModeMsgElem)n;
                result = wftm1.equals(wftm2);

                System.out.println("In func compareEachMessageElement- WtpFrameTunnelMode result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_MAC_TYPE:
                System.out.println("In func compareEachMessageElement- WtpMacTypeMsgElem");
                WtpMacTypeMsgElem wtpMacMsgElm1 = (WtpMacTypeMsgElem)o;
                WtpMacTypeMsgElem wtpMacMsgElm2 = (WtpMacTypeMsgElem)n;
                result = wtpMacMsgElm1.equals(wtpMacMsgElm2);

                System.out.println("In func compareEachMessageElement- WtpMacTypeMsgElem result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_NAME:
                System.out.println("In func compareEachMessageElement- WTPName");
                WTPName wtpname1 = (WTPName)o;
                WTPName wtpname2 = (WTPName)n;
                result = wtpname1.equals(wtpname2);

                System.out.println("In func compareEachMessageElement- WTPName result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_REBOOT_STATS:
                System.out.println("In func compareEachMessageElement- WTPRebootStatistics");
                WTPRebootStatistics wtpRebootStats1 = (WTPRebootStatistics)o;
                WTPRebootStatistics wtpRebootStats2 = (WTPRebootStatistics)n;
                result = wtpRebootStats1.equals(wtpRebootStats2);

                System.out.println("In func compareEachMessageElement- WTPRebootStatistics result = " + result);

                break;
            case ODLCapwapConsts.CAPWAP_ELMT_TYPE_WTP_STATIC_IP_ADDR_INFO:
                System.out.println("In func compareEachMessageElement- WtpStaticIPAddressInfo");
                WtpStaticIPAddressInfo wtpstIpAddrInfo1 = (WtpStaticIPAddressInfo)o;
                WtpStaticIPAddressInfo wtpstIpAddrInfo2 = (WtpStaticIPAddressInfo)n;
                result = wtpstIpAddrInfo1.equals(wtpstIpAddrInfo2);
                System.out.println("In func compareEachMessageElement- WtpStaticIPAddressInfo result = " + result);

                break;

            case ODLCapwapConsts.IEEE_80211_ADD_WLAN:
                System.out.println("In func compareEachMessageElement- AddWlan");
                AddWlan addWlan1 = (AddWlan)o;
                AddWlan addWlan2 = (AddWlan)n;
                result = addWlan1.equals(addWlan2);
                System.out.println("In func compareEachMessageElement- AddWlan result = " + result);

                break;

            case ODLCapwapConsts.IEEE_80211_UPDATE_WLAN:
                System.out.println("In func compareEachMessageElement- UpdateWlan");
                UpdateWlan updateWlan1 = (UpdateWlan)o;
                UpdateWlan updateWlan2 = (UpdateWlan)n;
                result = updateWlan1.equals(updateWlan2);
                System.out.println("In func compareEachMessageElement- UpdateWlan result = " + result);

                break;
            case ODLCapwapConsts.IEEE_80211_DELETE_WLAN:
                System.out.println("In func compareEachMessageElement- DeleteWlan");
                DeleteWlan deleteWlan1 = (DeleteWlan)o;
                DeleteWlan deleteWlan2 = (DeleteWlan)n;
                result = deleteWlan1.equals(deleteWlan2);
                System.out.println("In func compareEachMessageElement- DeleteWlan result = " + result);

                break;
            default:
                break;
        }
        return result;
    }
}
