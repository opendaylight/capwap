/*
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */
package org.opendaylight.capwap;

import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.ProviderContext;
import org.opendaylight.controller.sal.binding.api.BindingAwareBroker.RpcRegistration;
import org.opendaylight.controller.sal.binding.api.BindingAwareProvider;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

import org.opendaylight.controller.md.sal.binding.api.DataBroker;
import org.opendaylight.controller.md.sal.binding.api.DataChangeListener;
import org.opendaylight.controller.md.sal.binding.api.ReadWriteTransaction;
import org.opendaylight.controller.md.sal.binding.api.WriteTransaction;
import org.opendaylight.controller.md.sal.common.api.data.AsyncDataChangeEvent;
import org.opendaylight.controller.md.sal.common.api.data.LogicalDatastoreType;
import org.opendaylight.controller.md.sal.common.api.data.OptimisticLockFailedException;
import org.opendaylight.controller.md.sal.common.api.data.TransactionCommitFailedException;
import org.opendaylight.controller.sal.binding.api.NotificationProviderService;

import org.opendaylight.yangtools.yang.binding.DataObject;
import org.opendaylight.yangtools.yang.binding.InstanceIdentifier;
import org.opendaylight.yangtools.yang.common.RpcError;
import org.opendaylight.yangtools.yang.common.RpcResultBuilder;
import org.opendaylight.yangtools.yang.common.RpcError.ErrorType;
import org.opendaylight.yangtools.yang.common.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapAcRoot;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapAcRootBuilder;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import org.opendaylight.capwap.ODLCapwapACServer;

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
        }catch(java.lang.Exception e){System.out.println(e);}

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

