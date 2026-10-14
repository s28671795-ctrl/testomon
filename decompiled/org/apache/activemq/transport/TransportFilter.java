/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.net.URI;
import java.security.cert.X509Certificate;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.wireformat.WireFormat;

public class TransportFilter
implements TransportListener,
Transport {
    protected final Transport next;
    protected TransportListener transportListener;

    public TransportFilter(Transport next) {
        this.next = next;
    }

    @Override
    public TransportListener getTransportListener() {
        return this.transportListener;
    }

    @Override
    public void setTransportListener(TransportListener channelListener) {
        this.transportListener = channelListener;
        if (channelListener == null) {
            this.next.setTransportListener(null);
        } else {
            this.next.setTransportListener(this);
        }
    }

    @Override
    public void start() throws Exception {
        if (this.next == null) {
            throw new IOException("The next channel has not been set.");
        }
        if (this.transportListener == null) {
            throw new IOException("The command listener has not been set.");
        }
        this.next.start();
    }

    @Override
    public void stop() throws Exception {
        this.next.stop();
    }

    @Override
    public void onCommand(Object command) {
        this.transportListener.onCommand(command);
    }

    public Transport getNext() {
        return this.next;
    }

    public String toString() {
        return this.next.toString();
    }

    @Override
    public void oneway(Object command) throws IOException {
        this.next.oneway(command);
    }

    @Override
    public FutureResponse asyncRequest(Object command, ResponseCallback responseCallback) throws IOException {
        return this.next.asyncRequest(command, null);
    }

    @Override
    public Object request(Object command) throws IOException {
        return this.next.request(command);
    }

    @Override
    public Object request(Object command, int timeout2) throws IOException {
        return this.next.request(command, timeout2);
    }

    @Override
    public void onException(IOException error) {
        this.transportListener.onException(error);
    }

    @Override
    public void transportInterupted() {
        this.transportListener.transportInterupted();
    }

    @Override
    public void transportResumed() {
        this.transportListener.transportResumed();
    }

    @Override
    public <T> T narrow(Class<T> target) {
        if (target.isAssignableFrom(this.getClass())) {
            return target.cast(this);
        }
        return this.next.narrow(target);
    }

    @Override
    public String getRemoteAddress() {
        return this.next.getRemoteAddress();
    }

    @Override
    public boolean isFaultTolerant() {
        return this.next.isFaultTolerant();
    }

    @Override
    public boolean isDisposed() {
        return this.next.isDisposed();
    }

    @Override
    public boolean isConnected() {
        return this.next.isConnected();
    }

    @Override
    public void reconnect(URI uri) throws IOException {
        this.next.reconnect(uri);
    }

    @Override
    public int getReceiveCounter() {
        return this.next.getReceiveCounter();
    }

    @Override
    public boolean isReconnectSupported() {
        return this.next.isReconnectSupported();
    }

    @Override
    public boolean isUpdateURIsSupported() {
        return this.next.isUpdateURIsSupported();
    }

    @Override
    public void updateURIs(boolean rebalance, URI[] uris) throws IOException {
        this.next.updateURIs(rebalance, uris);
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        return this.next.getPeerCertificates();
    }

    @Override
    public void setPeerCertificates(X509Certificate[] certificates) {
        this.next.setPeerCertificates(certificates);
    }

    @Override
    public WireFormat getWireFormat() {
        return this.next.getWireFormat();
    }
}

