/*
 * Copyright (c) 2015 Navin Agrawal and others.  All rights reserved.
 *
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0 which accompanies this distribution,
 * and is available at http://www.eclipse.org/legal/epl-v10.html
 */

package org.opendaylight.capwap;

import java.net.InetAddress;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

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

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.util.concurrent.AsyncFunction;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.SettableFuture;

import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.capwap.ac.DiscoveredWtps;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.capwap.ac.DiscoveredWtpsKey;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.capwap.ac.DiscoveredWtpsBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.discovered.wtp.Descriptor;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.model.rev150217.discovered.wtp.DescriptorBuilder;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapAcRoot;
import org.opendaylight.yang.gen.v1.urn.opendaylight.capwap.impl.rev150217.CapwapAcRootBuilder;


public class ODLCapwapACServer implements ODLCapwapACBaseServer,Runnable {

    private static final Logger LOG = LoggerFactory.getLogger(ODLCapwapACServer.class);

    private final DatagramSocket socket;
    private final int port;
    private static boolean run_server;

    private DataBroker dataProvider;

    private DiscoveredWtps localDiscoveredWtps;
    private Descriptor localDescriptor;

    private InstanceIdentifier<DiscoveredWtps> WTPS_IID;

    public ODLCapwapACServer(DataBroker dataProvider) throws SocketException {
        this.port = 5246;
        this.socket = new DatagramSocket(port);
        this.run_server = true;
	this.dataProvider = dataProvider;
    }

    public void run() {
	try {
		start();
	} catch (Exception e) {System.out.println (e);}
    }

    @Override
    public void start() throws Exception {

        byte buffer[] = new byte[2048];
	byte rcvPktBuf[];
        int rcvPktLength;
        InetAddress srcAddr;

        while (run_server) {
            DatagramPacket data = new DatagramPacket(buffer, buffer.length);
            socket.receive(data);
	    rcvPktBuf = data.getData();
            rcvPktLength = data.getLength();
	    srcAddr = data.getAddress();
	    rcvPktProcessing (rcvPktBuf, rcvPktLength, srcAddr);
        }
        socket.close();
    }

    @Override
    public void close() throws Exception {
        run_server = false;
    }

    private Descriptor generateWtpDescriptor ( int Index ) {
	DescriptorBuilder descriptorBuilder = new DescriptorBuilder ();
        return descriptorBuilder.setMaxRadios((short)Index)
                                 .build();
    }


    private void addWTPEntryInDataStore ( String ipv4Addr, int Index ) {

        localDescriptor = generateWtpDescriptor (Index);

        DiscoveredWtpsBuilder wtpsBuilder = new DiscoveredWtpsBuilder();
        localDiscoveredWtps = wtpsBuilder.setIpv4Addr(ipv4Addr)
                                         .setKey(new DiscoveredWtpsKey(ipv4Addr))
                                         .setDescriptor(localDescriptor)
                                         .build();

        WTPS_IID = InstanceIdentifier.builder(CapwapAcRoot.class)
                                     .child(DiscoveredWtps.class, new DiscoveredWtpsKey(ipv4Addr))
                                     .build();

        WriteTransaction tx = dataProvider.newWriteOnlyTransaction();
        tx.put (LogicalDatastoreType.OPERATIONAL, WTPS_IID, localDiscoveredWtps);

        Futures.addCallback( tx.submit(), new FutureCallback<Void>() {
            @Override
            public void onSuccess( final Void result ) {
            }

            @Override
            public void onFailure( final Throwable t ) {
                LOG.error( "WTP Add Failed", t );
            }
        } );
    }

    private void rcvPktProcessing ( byte pktBuf[], int pktLength, InetAddress srcAddr ) {
	byte buf[] = pktBuf;
	int index;
	int hlen;

	//System.out.print("rcvPktProcessing, pktLength: " + pktLength + "\n");

	/* Basic packet validation */

	/* Smaller than capwap header */
	if (pktLength < 8) return;

	/* Packet length smaller than header length specified in the capwap header */
	hlen = (buf[1] >> 3) * 4;
	//System.out.print("rcvPktProcessing, hlen: " + hlen + "\n");
	if (pktLength < hlen) return;

	addWTPEntryInDataStore (srcAddr.getHostAddress(), 0);

    }
}

