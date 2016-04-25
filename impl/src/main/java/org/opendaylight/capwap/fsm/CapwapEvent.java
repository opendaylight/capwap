/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import org.opendaylight.capwap.ODLCapwapMessage;

/**
 * Created by flat on 13/04/16.
 */
public class CapwapEvent {
    CapwapEventType dType ;
    int timer = 0;



    ODLCapwapMessage dMessage = null;
    ByteBuf outGoingMsg = null;



    ByteBuf incomingBuf = null;
    CapwapDestination destination =null;
    DatagramPacket packet =null;
    ChannelHandlerContext ctx = null;
    String wtpID =null;


    //Primitive constructor

    public CapwapEvent(CapwapEventType dType){
        this.dType = dType ;
    }

    //Could be used to send to FSM Actor

    CapwapEvent(CapwapEventType dType, ODLCapwapMessage dMsg, ChannelHandlerContext ctx){
        this.dType = dType ;
        this.dMessage = dMsg;
        this.ctx = ctx;
    }


    //Constructor used  for Decode event

    CapwapEvent(DatagramPacket packet, CapwapEventType dType, ChannelHandlerContext ctx){
        this.packet = packet;
        this.dType = dType;
        this.ctx = ctx;


    }
    CapwapEvent(ByteBuf buf, CapwapEventType dType, ChannelHandlerContext ctx){
        this.incomingBuf = buf;
        this.dType = dType;
        this.ctx = ctx;


    }

    //Could be used for send to network event
    CapwapEvent(CapwapEventType dType, ByteBuf outGoingMsg , CapwapDestination destination, ChannelHandlerContext ctx){
        this.dType = dType ;
        this.outGoingMsg = outGoingMsg;
        this.destination = destination;
        this.ctx = ctx;
    }

    public ByteBuf getIncomingBuf() {
        return incomingBuf;
    }

    public void setIncomingBuf(ByteBuf incomingBuf) {
        this.incomingBuf = incomingBuf;
    }

    public CapwapEventType getdType() {
        return dType;
    }

    public void setdType(CapwapEventType dType) {
        this.dType = dType;
    }

    public ODLCapwapMessage getdMessage() {
        return dMessage;
    }

    public void setdMessage(ODLCapwapMessage dMessage) {
        this.dMessage = dMessage;
    }

    public ByteBuf getOutGoingMsg() {
        return outGoingMsg;
    }

    public void setOutGoingMsg(ByteBuf outGoingMsg) {
        this.outGoingMsg = outGoingMsg;
    }

    public CapwapDestination getDestination() {
        return destination;
    }

    public void setDestination(CapwapDestination destination) {
        this.destination = destination;
    }

    public DatagramPacket getPacket() {
        return packet;
    }

    public void setPacket(DatagramPacket packet) {
        this.packet = packet;
    }

    public ChannelHandlerContext getCtx() {
        return ctx;
    }

    public void setCtx(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    public String getWtpID() {
        return wtpID;
    }

    public void setWtpID(String wtpID) {
        this.wtpID = wtpID;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

}
