/*
 * Copyright (c) 2015 Mahesh Govind and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap.fsm;

import akka.actor.FSM;
import akka.actor.AbstractFSM;
import akka.event.Logging;
import akka.event.LoggingAdapter;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.opendaylight.capwap.ODLCapwapConsts;
import org.opendaylight.capwap.ODLCapwapMessage;
import org.opendaylight.capwap.utils.CapwapMessageCreator;


public class CapwapNetworkEventHander {


    public static CapwapState handleDiscoveryRequest(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }
    public static CapwapState handleDiscoveryResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleJoinRequest(CapwapEvent m, CapwapContext d){
        ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());


        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.JOIN;
    }
    public static CapwapState handleJoinResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleChangeStateReqWithErrorReq(CapwapEvent m, CapwapContext d){
        ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());

        //stops WaitTLS timer
        //starts ChangeStatePendingTimer timer
        //checks the database

        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.RESET;
    }

    public static CapwapState handleDtlsTd(CapwapEvent m, CapwapContext d){
        return CapwapState.DTLS_TD;
    }





    public static CapwapState handleConfigurationStatusRequest(CapwapEvent m, CapwapContext d){
        ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());

        //stops WaitTLS timer
        //starts ChangeStatePendingTimer timer
        //checks the database

        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.CONFIGURE;
    }



    public static CapwapState handleConfigurationStatusResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleWtpEventRequest (CapwapEvent m, CapwapContext d){
        ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());

        //stops WaitTLS timer
        //starts ChangeStatePendingTimer timer
        //checks the database

        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.RUN;
    }

    public static CapwapState handleStationConfigReq (CapwapEvent m, CapwapContext d){
        ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());

        //stops WaitTLS timer
        //starts ChangeStatePendingTimer timer
        //checks the database

        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.RUN;
    }

    public static CapwapState handleWtpEventresponse (CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleChangeEventRequest(CapwapEvent m, CapwapContext d){

        //The AC responds with a Change State Event Response message
        //(see Section 8.7).  The AC MUST start the DataCheckTimer
        //timer and stops the ChangeStatePendingTimer timer (see
        //Section 4.7).

        return CapwapState.DATACHECK;
    }
    public static CapwapState handleChangeEventResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleEchoRequest(CapwapEvent m, CapwapContext d){
        ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());

        //Resets Eco Timer
        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.IMAGE_DATA;
    }

    public static CapwapState handleConfigUpdateRequest(CapwapEvent m, CapwapContext d){
        ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());

        //Resets Eco Timer
        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.RUN;
    }



    public static CapwapState handleEchoResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleImageDataRequest(CapwapEvent m, CapwapContext d){

        //The AC stops the WaitJoin timer.
        //The AC MUST transmit an Image Data Response message (see
        //        Section 9.1.2) to the WTP, which includes a portion of the
         ODLCapwapMessage res = CapwapMessageCreator.createResponse(m.getdMessage());
        //encode the message and store in capwapContext

        //set other headers from database

        CapwapFsmUtils.sendMessage(m,d,res);
        return CapwapState.IMAGE_DATA;
    }
    public static CapwapState handleImageDataResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleResetRequest(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }
    public static CapwapState handleResetResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }


    public static CapwapState handlePrimaryDiscoveryRequest(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }
    public static CapwapState handlePrimaryDiscoveryResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }


    public static CapwapState handleDataTransferRequest (CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleDataTransferResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }


    public static CapwapState handleClearConfigurationRequest(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }

    public static CapwapState handleClearConfigurationResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }


    public static CapwapState handleStationConfigurationRequest(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }
    public static CapwapState handleStationConfigurationResponse(CapwapEvent m, CapwapContext d){
        return CapwapState.IDLE;
    }
}
