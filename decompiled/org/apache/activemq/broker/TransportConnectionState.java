/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.broker;

import java.util.concurrent.atomic.AtomicInteger;
import org.apache.activemq.broker.ConnectionContext;
import org.apache.activemq.broker.TransportConnection;
import org.apache.activemq.command.ConnectionInfo;
import org.apache.activemq.state.ConnectionState;

public class TransportConnectionState
extends ConnectionState {
    private ConnectionContext context;
    private TransportConnection connection;
    private AtomicInteger referenceCounter = new AtomicInteger();
    private final Object connectionMutex = new Object();

    public TransportConnectionState(ConnectionInfo info, TransportConnection transportConnection) {
        super(info);
        this.connection = transportConnection;
    }

    public ConnectionContext getContext() {
        return this.context;
    }

    public TransportConnection getConnection() {
        return this.connection;
    }

    public void setContext(ConnectionContext context) {
        this.context = context;
    }

    public void setConnection(TransportConnection connection) {
        this.connection = connection;
    }

    public int incrementReference() {
        return this.referenceCounter.incrementAndGet();
    }

    public int decrementReference() {
        return this.referenceCounter.decrementAndGet();
    }

    public AtomicInteger getReferenceCounter() {
        return this.referenceCounter;
    }

    public void setReferenceCounter(AtomicInteger referenceCounter) {
        this.referenceCounter = referenceCounter;
    }

    public Object getConnectionMutex() {
        return this.connectionMutex;
    }
}

