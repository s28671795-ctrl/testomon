/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.net.URI;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.Transport;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.util.ServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class TransportSupport
extends ServiceSupport
implements Transport {
    private static final Logger LOG = LoggerFactory.getLogger(TransportSupport.class);
    TransportListener transportListener;

    @Override
    public TransportListener getTransportListener() {
        return this.transportListener;
    }

    @Override
    public void setTransportListener(TransportListener commandListener) {
        this.transportListener = commandListener;
    }

    @Override
    public <T> T narrow(Class<T> target) {
        boolean assignableFrom = target.isAssignableFrom(this.getClass());
        if (assignableFrom) {
            return target.cast(this);
        }
        return null;
    }

    @Override
    public FutureResponse asyncRequest(Object command, ResponseCallback responseCallback) throws IOException {
        throw new AssertionError((Object)"Unsupported Method");
    }

    @Override
    public Object request(Object command) throws IOException {
        throw new AssertionError((Object)"Unsupported Method");
    }

    @Override
    public Object request(Object command, int timeout2) throws IOException {
        throw new AssertionError((Object)"Unsupported Method");
    }

    public void doConsume(Object command) {
        if (command != null) {
            if (this.transportListener != null) {
                this.transportListener.onCommand(command);
            } else {
                LOG.error("No transportListener available to process inbound command: " + command);
            }
        }
    }

    public void onException(IOException e) {
        if (this.transportListener != null) {
            try {
                this.transportListener.onException(e);
            }
            catch (RuntimeException e2) {
                LOG.debug("Unexpected runtime exception: " + e2, e2);
            }
        }
    }

    protected void checkStarted() throws IOException {
        if (!this.isStarted()) {
            throw new IOException("The transport is not running.");
        }
    }

    @Override
    public boolean isFaultTolerant() {
        return false;
    }

    @Override
    public void reconnect(URI uri) throws IOException {
        throw new IOException("Not supported");
    }

    @Override
    public boolean isReconnectSupported() {
        return false;
    }

    @Override
    public boolean isUpdateURIsSupported() {
        return false;
    }

    @Override
    public void updateURIs(boolean reblance, URI[] uris) throws IOException {
        throw new IOException("Not supported");
    }

    @Override
    public boolean isDisposed() {
        return this.isStopped();
    }

    @Override
    public boolean isConnected() {
        return this.isStarted();
    }
}

