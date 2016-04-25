/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.capwap.fsm;

/**
 * Timer in seconds
 * Akka statemachine identifies timer as strings
 */
public class CapwapTimerConstants {


    public static final int  ChangeStatePendingTimer = 25 ;
    public static final int  DataChannelKeepAlive = 30;
    public static final int DataChannelDeadInterval = 60;
    public static final int DataCheckTimer =30;
    public static final int DiscoveryInterval = 5;
    public static final int DTLSSessionDelete =5;
    public static final int EchoInterval = 30;
    public static final int IdleTimeout = 300;
    public static final int ImageDataStartTimer = 30;
    public static final int MaxDiscoveryInterval = 20;
    public static final int  ReportInterval =  120;
    public static final int RetransmitInterval = 3;
    public static final int SilentInterval = 30;
    public static final int StatisticsTimer = 120;
    public static final int WaitDTLS =  60;
    public static final int WaitJoin = 60;
    public static String timerTypetoString(int type){
        if (type == ChangeStatePendingTimer )
            return "ChangeStatePendingTimer";
        if (type == DataChannelKeepAlive )
            return "DataChannelKeepAlive";
        if (type == DataChannelDeadInterval )
            return "DataChannelDeadInterval";
        if (type == DataCheckTimer )
            return "DataCheckTimer";
        if (type == DiscoveryInterval )
            return "DiscoveryInterval";
        if (type == DTLSSessionDelete )
            return "DTLSSessionDelete";
        if (type == EchoInterval )
            return "EchoInterval";
        if (type == IdleTimeout )
            return "IdleTimeout";
        if (type == ImageDataStartTimer )
            return "ImageDataStartTimer";
        if (type == MaxDiscoveryInterval )
            return "MaxDiscoveryInterval";
        if (type == ReportInterval )
            return "ReportInterval";
        if (type == RetransmitInterval )
            return "RetransmitInterval";
        if (type == SilentInterval )
            return "SilentInterval";
        if (type == StatisticsTimer )
            return "StatisticsTimer";
        if (type == WaitDTLS )
            return "WaitDTLS";
        if (type == WaitJoin )
            return "WaitJoin";

        return "";

    }
}
