/*
 * Copyright (c) 2015 Navin Agrawal and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import com.google.common.base.Function;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapAcRoot;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapAcRootBuilder;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutionException;

public class ODLCapwapACProvider implements BindingAwareProvider, AutoCloseable {

    private static final Logger LOG = LoggerFactory.getLogger(ODLCapwapACProvider.class);

    public static final InstanceIdentifier<CapwapAcRoot> CAPWAP_AC_ROOT_IID = InstanceIdentifier.builder(CapwapAcRoot.class).build();
    private static final String AC_NAME = "Opendaylight CAPWAP AC";

    private NotificationProviderService notificationProvider;
    private DataBroker dataProvider;

    public ODLCapwapACProvider(DataBroker dataProvider) {
        if (dataProvider == null) {
            LOG.error("CAPWAP Shard data provider is null!");
        }
        this.dataProvider = dataProvider;
    }

    @Override
    public void onSessionInitiated(ProviderContext session) {
        LOG.info("ODLCapwapACProvider Started");

	setACStatusUp( null );

        try {
		Thread t = new Thread(new ODLCapwapACServer(dataProvider));
            t.start();
        }catch(Exception e){System.out.println(e);}

    }

    @Override
    public void close() throws ExecutionException, InterruptedException {
        LOG.info("ODLCapwapACProvider Closed");
    }

    private CapwapAcRoot buildCapwapAcRoot( ) {

        return new CapwapAcRootBuilder().setAcName( AC_NAME )
                                   .build();
    }

    private void setACStatusUp( final Function<Boolean,Void> resultCallback ) {
        WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
        tx.put (LogicalDatastoreType.OPERATIONAL, CAPWAP_AC_ROOT_IID, buildCapwapAcRoot( ) );

        Futures.addCallback( tx.submit(), new FutureCallback<Void>() {
            @Override
            public void onSuccess( final Void result ) {
                notifyCallback( true );
            }

            @Override
            public void onFailure( final Throwable t ) {
                LOG.error( "Failed to Update CAPWAP AC Status in Datastore", t );

                notifyCallback( false );
            }

            void notifyCallback( final boolean result ) {
                if( resultCallback != null ) {
                    resultCallback.apply( result );
                }
            }
        } );
    }

}

