/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.net.InetSocketAddress;
import java.net.URI;
import org.apache.activemq.command.BrokerInfo;
import org.apache.activemq.transport.TransportAcceptListener;
import org.apache.activemq.transport.TransportServer;

public class TransportServerFilter
implements TransportServer {
    protected final TransportServer next;

    public TransportServerFilter(TransportServer next) {
        this.next = next;
    }

    @Override
    public URI getConnectURI() {
        return this.next.getConnectURI();
    }

    @Override
    public void setAcceptListener(TransportAcceptListener acceptListener) {
        this.next.setAcceptListener(acceptListener);
    }

    @Override
    public void setBrokerInfo(BrokerInfo brokerInfo) {
        this.next.setBrokerInfo(brokerInfo);
    }

    @Override
    public void start() throws Exception {
        this.next.start();
    }

    @Override
    public void stop() throws Exception {
        this.next.stop();
    }

    @Override
    public InetSocketAddress getSocketAddress() {
        return this.next.getSocketAddress();
    }

    @Override
    public boolean isSslServer() {
        return this.next.isSslServer();
    }

    @Override
    public boolean isAllowLinkStealing() {
        return this.next.isAllowLinkStealing();
    }
}

