/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.net.URI;
import java.util.Map;
import org.apache.activemq.transport.TransportAcceptListener;
import org.apache.activemq.transport.TransportServer;
import org.apache.activemq.util.ServiceSupport;

public abstract class TransportServerSupport
extends ServiceSupport
implements TransportServer {
    private URI connectURI;
    private URI bindLocation;
    private TransportAcceptListener acceptListener;
    protected Map<String, Object> transportOptions;
    protected boolean allowLinkStealing;

    public TransportServerSupport() {
    }

    public TransportServerSupport(URI location) {
        this.connectURI = location;
        this.bindLocation = location;
    }

    public TransportAcceptListener getAcceptListener() {
        return this.acceptListener;
    }

    @Override
    public void setAcceptListener(TransportAcceptListener acceptListener) {
        this.acceptListener = acceptListener;
    }

    @Override
    public URI getConnectURI() {
        return this.connectURI;
    }

    public void setConnectURI(URI location) {
        this.connectURI = location;
    }

    protected void onAcceptError(Exception e) {
        if (this.acceptListener != null) {
            this.acceptListener.onAcceptError(e);
        }
    }

    public URI getBindLocation() {
        return this.bindLocation;
    }

    public void setBindLocation(URI bindLocation) {
        this.bindLocation = bindLocation;
    }

    public void setTransportOption(Map<String, Object> transportOptions) {
        this.transportOptions = transportOptions;
    }

    @Override
    public boolean isAllowLinkStealing() {
        return this.allowLinkStealing;
    }

    public void setAllowLinkStealing(boolean allowLinkStealing) {
        this.allowLinkStealing = allowLinkStealing;
    }
}

