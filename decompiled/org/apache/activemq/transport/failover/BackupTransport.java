/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.failover;

import java.io.IOException;
import java.net.URI;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.failover.FailoverTransport;

class BackupTransport
extends DefaultTransportListener {
    private final FailoverTransport failoverTransport;
    private Transport transport;
    private URI uri;
    private boolean disposed;
    private BrokerInfo brokerInfo;

    BackupTransport(FailoverTransport ft) {
        this.failoverTransport = ft;
    }

    @Override
    public void onException(IOException error) {
        this.disposed = true;
        if (this.failoverTransport != null) {
            this.failoverTransport.reconnect(false);
        }
    }

    @Override
    public void onCommand(Object command) {
        if (command instanceof BrokerInfo) {
            this.brokerInfo = (BrokerInfo)command;
        }
    }

    public BrokerInfo getBrokerInfo() {
        return this.brokerInfo;
    }

    public Transport getTransport() {
        return this.transport;
    }

    public void setTransport(Transport transport) {
        this.transport = transport;
        this.transport.setTransportListener(this);
    }

    public URI getUri() {
        return this.uri;
    }

    public void setUri(URI uri) {
        this.uri = uri;
    }

    public boolean isDisposed() {
        return this.disposed || this.transport != null && this.transport.isDisposed();
    }

    public void setDisposed(boolean disposed) {
        this.disposed = disposed;
    }

    public int hashCode() {
        return this.uri != null ? this.uri.hashCode() : -1;
    }

    public boolean equals(Object obj) {
        if (obj instanceof BackupTransport) {
            BackupTransport other = (BackupTransport)obj;
            return this.uri == null && other.uri == null || this.uri != null && other.uri != null && this.uri.equals(other.uri);
        }
        return false;
    }

    public String toString() {
        return "Backup transport: " + this.uri;
    }
}

