/*
 * Decompiled with CFR 0.152.
 */
package org.apache.activemq.transport;

import java.io.IOException;
import java.net.URI;
import java.security.cert.X509Certificate;
import org.apache.activemq.Service;
import org.apache.activemq.transport.FutureResponse;
import org.apache.activemq.transport.ResponseCallback;
import org.apache.activemq.transport.TransportListener;
import org.apache.activemq.wireformat.WireFormat;

public interface Transport
extends Service {
    public void oneway(Object var1) throws IOException;

    public FutureResponse asyncRequest(Object var1, ResponseCallback var2) throws IOException;

    public Object request(Object var1) throws IOException;

    public Object request(Object var1, int var2) throws IOException;

    public TransportListener getTransportListener();

    public void setTransportListener(TransportListener var1);

    public <T> T narrow(Class<T> var1);

    public String getRemoteAddress();

    public boolean isFaultTolerant();

    public boolean isDisposed();

    public boolean isConnected();

    public boolean isReconnectSupported();

    public boolean isUpdateURIsSupported();

    public void reconnect(URI var1) throws IOException;

    public void updateURIs(boolean var1, URI[] var2) throws IOException;

    public int getReceiveCounter();

    public X509Certificate[] getPeerCertificates();

    public void setPeerCertificates(X509Certificate[] var1);

    public WireFormat getWireFormat();
}

