/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingEvent;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSessionContext;
import org.conscrypt.ConscryptSession;

final class ExternalSession
implements ConscryptSession {
    private final HashMap<String, Object> values = new HashMap(2);
    private final Provider provider;

    public ExternalSession(Provider provider) {
        this.provider = provider;
    }

    @Override
    public String getRequestedServerName() {
        return this.provider.provideSession().getRequestedServerName();
    }

    @Override
    public List<byte[]> getStatusResponses() {
        return this.provider.provideSession().getStatusResponses();
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        return this.provider.provideSession().getPeerSignedCertificateTimestamp();
    }

    @Override
    public byte[] getId() {
        return this.provider.provideSession().getId();
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return this.provider.provideSession().getSessionContext();
    }

    @Override
    public long getCreationTime() {
        return this.provider.provideSession().getCreationTime();
    }

    @Override
    public long getLastAccessedTime() {
        return this.provider.provideSession().getLastAccessedTime();
    }

    @Override
    public void invalidate() {
        this.provider.provideSession().invalidate();
    }

    @Override
    public boolean isValid() {
        return this.provider.provideSession().isValid();
    }

    @Override
    public X509Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        return this.provider.provideSession().getPeerCertificates();
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return this.provider.provideSession().getLocalCertificates();
    }

    @Override
    public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        return this.provider.provideSession().getPeerCertificateChain();
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        return this.provider.provideSession().getPeerPrincipal();
    }

    @Override
    public Principal getLocalPrincipal() {
        return this.provider.provideSession().getLocalPrincipal();
    }

    @Override
    public String getCipherSuite() {
        return this.provider.provideSession().getCipherSuite();
    }

    @Override
    public String getProtocol() {
        return this.provider.provideSession().getProtocol();
    }

    @Override
    public String getPeerHost() {
        return this.provider.provideSession().getPeerHost();
    }

    @Override
    public int getPeerPort() {
        return this.provider.provideSession().getPeerPort();
    }

    @Override
    public int getPacketBufferSize() {
        return this.provider.provideSession().getPacketBufferSize();
    }

    @Override
    public int getApplicationBufferSize() {
        return this.provider.provideSession().getApplicationBufferSize();
    }

    @Override
    public String getApplicationProtocol() {
        return this.provider.provideSession().getApplicationProtocol();
    }

    @Override
    public Object getValue(String name) {
        if (name == null) {
            throw new IllegalArgumentException("name == null");
        }
        return this.values.get(name);
    }

    @Override
    public String[] getValueNames() {
        return this.values.keySet().toArray(new String[this.values.size()]);
    }

    @Override
    public void putValue(String name, Object value) {
        this.putValue(this, name, value);
    }

    void putValue(SSLSession session, String name, Object value) {
        if (name == null || value == null) {
            throw new IllegalArgumentException("name == null || value == null");
        }
        Object old = this.values.put(name, value);
        if (value instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener)value).valueBound(new SSLSessionBindingEvent(session, name));
        }
        if (old instanceof SSLSessionBindingListener) {
            ((SSLSessionBindingListener)old).valueUnbound(new SSLSessionBindingEvent(session, name));
        }
    }

    @Override
    public void removeValue(String name) {
        this.removeValue(this, name);
    }

    void removeValue(SSLSession session, String name) {
        if (name == null) {
            throw new IllegalArgumentException("name == null");
        }
        Object old = this.values.remove(name);
        if (old instanceof SSLSessionBindingListener) {
            SSLSessionBindingListener listener = (SSLSessionBindingListener)old;
            listener.valueUnbound(new SSLSessionBindingEvent(session, name));
        }
    }

    static interface Provider {
        public ConscryptSession provideSession();
    }
}

