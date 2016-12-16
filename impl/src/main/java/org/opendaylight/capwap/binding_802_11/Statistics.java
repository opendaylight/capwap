/*
 * Copyright (c) 2015 Brunda R Rajagopala and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.binding_802_11;

import io.netty.buffer.ByteBuf;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessageElement;
import org.opendaylight.capwap.utils.ByteManager;

/**
 * Created by flat on 15/04/16.
 */
/*
0                   1                   2                   3
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Radio ID     | Reserved                                       |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Tx Fragment Count                                             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Multicast Tx Count                                            |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Failed Count                                                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Retry Count                                                   |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Multiple Retry Count                                          |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Frame Duplicate Count                                         |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| RTS Success Count                                             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| RTS Failure Count                                             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| ACK Failure Count                                             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Rx Fragment Count                                             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Multicast RX Count                                            |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| FCS Error Count                                               |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Tx Frame Count                                                |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Decryption Errors                                             |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Discarded QoS Fragment Count                                  |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| Associated Station Count                                      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| QoS CF Polls Received Count                                   |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| QoS CF Polls Unused Count                                     |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
| QoS CF Polls Unusable Count                                   |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
*/
public class Statistics implements ODLCapwapMessageElement {
    int msgElem = 0;
    byte radioId = 0;
    byte [] reserved = {0,0,0} ;
    long txFragmentCount = 0;
    long multicastTxCount = 0;
    long failedCount = 0;
    long retryCount = 0;
    long multipleRetryCount = 0;
    long frameDuplicateCount = 0;
    long rtsSuccessCount = 0;
    long rtsFailureCount = 0;
    long ackFailureCount = 0;
    long rxFragmentCount = 0;
    long multicastRxCount = 0;
    long fcsErrorCount = 0;
    long txFrameCount = 0;
    long decryptionErrors = 0;
    long discardedQosFragmentCount = 0;
    long associatedStationCount = 0;
    long qosCfPollsReceivedCount = 0;
    long qosCfPollsUnusedCount = 0;
    long qosCfPollsUnusableCount = 0;

    public Statistics ()
    {
        this.msgElem = ODLCapwapConsts.IEEE_80211_STATISTICS;
    }

    public void setMsgElem (int msgElem)
    {
        this.msgElem = msgElem;
    }

    public int getMsgElem ()
    {
        return (this.msgElem);
    }

    public void setRadioId(byte radioId)
    {
        this.radioId = radioId;
    }


    public byte getRadioId ()
    {
        return (this.radioId);
    }

    public   byte [] getReserved ()
    {
         return (this.reserved);
    }

    public long getTxFragmentCount() {
        return this.txFragmentCount;
    }

    public void setTxFragmentCount (long txFragmentCount) {
        this.txFragmentCount = txFragmentCount;
    }

    public long getMulticastTxCount () {
        return this.multicastTxCount;
    }

    public void setMulticastTxCount (long multicastTxCount) {
        this.multicastTxCount = multicastTxCount;
    }

    public long getFailedCount () {
        return this.failedCount;
    }

    public void setFailedCount (long failedCount) {
        this.failedCount = failedCount;
    }

    public long getRetryCount() {
        return this.retryCount;
    }

    public void setRetryCount (long retryCount) {
        this.retryCount = retryCount;
    }

    public long getMultipleRetryCount () {
        return this.multipleRetryCount;
    }

    public void setMultipleRetryCount (long multipleRetryCount) {
        this.multipleRetryCount = multipleRetryCount;
    }

    public long getFrameDuplicateCount () {
        return this.frameDuplicateCount;
    }

    public void setFrameDuplicateCount (long frameDuplicateCount)
    {
        this.frameDuplicateCount = frameDuplicateCount;
    }

    public long getRtsSuccessCount() {
        return this.rtsSuccessCount;
    }

    public void setRtsSuccessCount (long rtsSuccessCount) {
        this.rtsSuccessCount = rtsSuccessCount;
    }

    public long getRtsFailureCount () {
        return this.rtsFailureCount;
    }

    public void setRtsFailureCount (long rtsFailureCount) {
        this.rtsFailureCount = rtsFailureCount;
    }

    public long getAckFailureCount() {
        return this.ackFailureCount;
    }

    public void setAckFailureCount (long ackFailureCount) {
        this.ackFailureCount = ackFailureCount;
    }

    public long getRxFragmentCount() {
        return this.rxFragmentCount;
    }

    public void setRxFragmentCount(long rxFragmentCount) {
        this.rxFragmentCount = rxFragmentCount;
    }

    public long getMulticastRxCount() {
        return this.multicastRxCount;
    }

    public void setMulticastRxCount(long multicastRxCount) {
        this.multicastRxCount = multicastRxCount;
    }

    public long getFcsErrorCount() {
        return this.fcsErrorCount;
    }

    public void setFcsErrorCount(long fcsErrorCount) {
        this.fcsErrorCount = fcsErrorCount;
    }

    public long getTxFrameCount() {
        return this.txFrameCount;
    }

    public void setTxFrameCount(long txFrameCount) {
        this.txFrameCount = txFrameCount;
    }

    public long getDecryptionErrors () {
        return this.decryptionErrors;
    }

    public void setDecryptionErrors (long decryptionErrors) {
        this.decryptionErrors = decryptionErrors;
    }

    public long getDiscardedQosFragmentCount() {
        return this.discardedQosFragmentCount;
    }

    public void setDiscardedQosFragmentCount (long discardedQosFragmentCount) {
        this.discardedQosFragmentCount = discardedQosFragmentCount;
    }

    public long getAssociatedStationCount() {
        return this.associatedStationCount;
    }

    public void setAssociatedStationCount (long associatedStationCount) {
        this.associatedStationCount = associatedStationCount;
    }

    public long getQosCfPollsReceivedCount() {
        return this.qosCfPollsReceivedCount;
    }

    public void setQosCfPollsReceivedCount (long qosCfPollsReceivedCount) {
        this.qosCfPollsReceivedCount = qosCfPollsReceivedCount;
    }

    public long getQosCfPollsUnusedCount () {
        return this.qosCfPollsUnusedCount;
    }

    public void setQosCfPollsUnusedCount(long qosCfPollsUnusedCount) {
        this.qosCfPollsUnusedCount = qosCfPollsUnusedCount;
    }

    public long getQosCfPollsUnusableCount () {
        return this.qosCfPollsUnusableCount;
    }

    public void setQosCfPollsUnusableCount(long qosCfPollsUnusableCount) {
        this.qosCfPollsUnusableCount = qosCfPollsUnusableCount;
    }

    @Override
    public int encode(ByteBuf buf) {
        int start = buf.writerIndex();
        buf.writeByte(radioId);
        buf.writeBytes(reserved);
        buf.writeBytes(ByteManager.unsignedIntToArray(txFragmentCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(multicastTxCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(failedCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(retryCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(multipleRetryCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(frameDuplicateCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(rtsSuccessCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(rtsFailureCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(ackFailureCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(rxFragmentCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(multicastRxCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(fcsErrorCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(txFrameCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(decryptionErrors));
        buf.writeBytes(ByteManager.unsignedIntToArray(discardedQosFragmentCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(associatedStationCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(qosCfPollsReceivedCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(qosCfPollsUnusedCount));
        buf.writeBytes(ByteManager.unsignedIntToArray(qosCfPollsUnusableCount));

        return buf.writerIndex()-start;
    }

    @Override
    public Statistics decode(ByteBuf buf) {
        return null;
    }

    @Override
    public int getType() {
        return (this.msgElem);
    }

    @Override
    public boolean equals(Object o)
    {
        if (o == this)
        {
            return true;
        }

        if (!(o instanceof Statistics))
        {
            return false;
        }
        if(!((this.msgElem == ((Statistics) o).getType()) &&
                (this.radioId == ((Statistics) o).getRadioId())))
        {
            return false;
        }

        byte [] tmp = ((Statistics)o).getReserved();
        if (this.reserved.length != tmp.length)
        {
            return false;
        }
        for (int i = 0; i < reserved.length; i++)
        {
            if (this.reserved[i] != tmp[i])
            {
                return false;
            }
        }

        if(!(this.txFragmentCount == ((Statistics) o).getTxFragmentCount() &&
            this.multicastTxCount == ((Statistics) o).getMulticastTxCount() &&
            this.failedCount == ((Statistics) o).getFailedCount()&&
            this.retryCount == ((Statistics) o).getRetryCount() &&
            this.multipleRetryCount == ((Statistics) o).getMultipleRetryCount() &&
            this.frameDuplicateCount == ((Statistics) o).getFrameDuplicateCount() &&
            this.rtsSuccessCount == ((Statistics) o).getRtsSuccessCount() &&
            this.rtsFailureCount == ((Statistics) o).getRtsFailureCount()  &&
            this.ackFailureCount == ((Statistics) o).getAckFailureCount() &&
            this.rxFragmentCount == ((Statistics) o).getRxFragmentCount() &&
            this.multicastRxCount == ((Statistics) o).getMulticastRxCount() &&
            this.fcsErrorCount == ((Statistics) o).getFcsErrorCount() &&
            this.txFrameCount == ((Statistics) o).getTxFrameCount() &&
            this.decryptionErrors == ((Statistics) o).getDecryptionErrors() &&
            this.discardedQosFragmentCount == ((Statistics) o).getDiscardedQosFragmentCount() &&
            this.associatedStationCount == ((Statistics) o).getAssociatedStationCount() &&
            this.qosCfPollsReceivedCount == ((Statistics) o).getQosCfPollsReceivedCount() &&
            this.qosCfPollsUnusedCount == ((Statistics) o).getQosCfPollsUnusedCount() &&
            this.qosCfPollsUnusableCount == ((Statistics) o).getQosCfPollsUnusableCount()))
        {
            return false;
        }

        return true;
    }








}
