/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport.mock;

import java.io.IOException;
import java.net.URI;
import java.security.cert.X509Certificate;
import org.apache.activemq.transport.DefaultTransportListener;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportFilter;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.wireformat.WireFormat;

public class MockTransport
extends DefaultTransportListener
implements Transport {
    protected Transport next;
    protected TransportListener transportListener;

    public MockTransport(Transport next) {
        this.next = next;
    }

    @Override
    public synchronized void setTransportListener(TransportListener channelListener) {
        this.transportListener = channelListener;
        if (channelListener == null) {
            this.getNext().setTransportListener(null);
        } else {
            this.getNext().setTransportListener(this);
        }
    }

    @Override
    public void start() throws Exception {
        if (this.getNext() == null) {
            throw new IOException("The next channel has not been set.");
        }
        if (this.transportListener == null) {
            throw new IOException("The command listener has not been set.");
        }
        this.getNext().start();
    }

    @Override
    public void stop() throws Exception {
        this.getNext().stop();
    }

    @Override
    public void onCommand(Object command) {
        this.getTransportListener().onCommand(command);
    }

    public synchronized Transport getNext() {
        return this.next;
    }

    @Override
    public synchronized TransportListener getTransportListener() {
        return this.transportListener;
    }

    public String toString() {
        return this.getNext().toString();
    }

    @Override
    public void oneway(Object command) throws IOException {
        this.getNext().oneway(command);
    }

    @Override
    public FutureResponse asyncRequest(Object command, ResponseCallback responseCallback) throws IOException {
        return this.getNext().asyncRequest(command, null);
    }

    @Override
    public Object request(Object command) throws IOException {
        return this.getNext().request(command);
    }

    @Override
    public Object request(Object command, int timeout2) throws IOException {
        return this.getNext().request(command, timeout2);
    }

    @Override
    public void onException(IOException error) {
        this.getTransportListener().onException(error);
    }

    @Override
    public <T> T narrow(Class<T> target) {
        if (target.isAssignableFrom(this.getClass())) {
            return target.cast(this);
        }
        return this.getNext().narrow(target);
    }

    public synchronized void setNext(Transport next) {
        this.next = next;
    }

    public void install(TransportFilter filter2) {
        filter2.setTransportListener(this);
        this.getNext().setTransportListener(filter2);
        this.setNext(filter2);
    }

    @Override
    public String getRemoteAddress() {
        return this.getNext().getRemoteAddress();
    }

    @Override
    public boolean isFaultTolerant() {
        return this.getNext().isFaultTolerant();
    }

    @Override
    public boolean isDisposed() {
        return this.getNext().isDisposed();
    }

    @Override
    public boolean isConnected() {
        return this.getNext().isConnected();
    }

    @Override
    public void reconnect(URI uri) throws IOException {
        this.getNext().reconnect(uri);
    }

    @Override
    public int getReceiveCounter() {
        return this.getNext().getReceiveCounter();
    }

    @Override
    public boolean isReconnectSupported() {
        return this.getNext().isReconnectSupported();
    }

    @Override
    public boolean isUpdateURIsSupported() {
        return this.getNext().isUpdateURIsSupported();
    }

    @Override
    public void updateURIs(boolean reblance, URI[] uris) throws IOException {
        this.getNext().updateURIs(reblance, uris);
    }

    @Override
    public X509Certificate[] getPeerCertificates() {
        return this.getNext().getPeerCertificates();
    }

    @Override
    public void setPeerCertificates(X509Certificate[] certificates) {
        this.getNext().setPeerCertificates(certificates);
    }

    @Override
    public WireFormat getWireFormat() {
        return this.getNext().getWireFormat();
    }
}

