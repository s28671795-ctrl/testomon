/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.vm;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.transport.MutexTransport;
import org.apache.activemq.transport.ResponseCorrelator;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportAcceptListener;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.transport.vm.VMTransport;
import org.apache.activemq.transport.vm.VMTransportFactory;

public class VMTransportServer
implements TransportServer {
    private TransportAcceptListener acceptListener;
    private final URI location;
    private boolean disposed;
    private final AtomicInteger connectionCount = new AtomicInteger(0);
    private final boolean disposeOnDisconnect;
    private boolean allowLinkStealing;

    public VMTransportServer(URI location, boolean disposeOnDisconnect) {
        this.location = location;
        this.disposeOnDisconnect = disposeOnDisconnect;
    }

    public String toString() {
        return "VMTransportServer(" + this.location + ")";
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public VMTransport connect() throws IOException {
        TransportAcceptListener al;
        VMTransportServer vMTransportServer = this;
        synchronized (vMTransportServer) {
            if (this.disposed) {
                throw new IOException("Server has been disposed.");
            }
            al = this.acceptListener;
        }
        if (al == null) {
            throw new IOException("Server TransportAcceptListener is null.");
        }
        this.connectionCount.incrementAndGet();
        VMTransport client = new VMTransport(this.location){

            @Override
            public void stop() throws Exception {
                if (!this.disposed.get()) {
                    super.stop();
                    if (VMTransportServer.this.connectionCount.decrementAndGet() == 0 && VMTransportServer.this.disposeOnDisconnect) {
                        VMTransportServer.this.stop();
                    }
                }
            }
        };
        VMTransport server = new VMTransport(this.location);
        client.setPeer(server);
        server.setPeer(client);
        al.onAccept(VMTransportServer.configure(server));
        return client;
    }

    public static Transport configure(Transport transport) {
        transport = new MutexTransport(transport);
        transport = new ResponseCorrelator(transport);
        return transport;
    }

    @Override
    public synchronized void setAcceptListener(TransportAcceptListener acceptListener) {
        this.acceptListener = acceptListener;
    }

    @Override
    public void start() throws IOException {
    }

    @Override
    public void stop() throws IOException {
        VMTransportFactory.stopped(this);
    }

    @Override
    public URI getConnectURI() {
        return this.location;
    }

    public URI getBindURI() {
        return this.location;
    }

    @Override
    public void setBrokerInfo(BrokerInfo brokerInfo) {
    }

    @Override
    public InetSocketAddress getSocketAddress() {
        return null;
    }

    public int getConnectionCount() {
        return this.connectionCount.intValue();
    }

    @Override
    public boolean isSslServer() {
        return false;
    }

    @Override
    public boolean isAllowLinkStealing() {
        return this.allowLinkStealing;
    }

    public void setAllowLinkStealing(boolean allowLinkStealing) {
        this.allowLinkStealing = allowLinkStealing;
    }
}

