/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
import org.conscrypt.ConscryptSession;

final class SessionSnapshot
implements ConscryptSession {
    private final SSLSessionContext sessionContext;
    private final byte[] id;
    private final String requestedServerName;
    private final List<byte[]> statusResponses;
    private final byte[] peerTlsSctData;
    private final long creationTime;
    private final long lastAccessedTime;
    private final String cipherSuite;
    private final String protocol;
    private final String peerHost;
    private final String applicationProtocol;
    private final int peerPort;

    SessionSnapshot(ConscryptSession session) {
        this.sessionContext = session.getSessionContext();
        this.id = session.getId();
        this.requestedServerName = session.getRequestedServerName();
        this.statusResponses = session.getStatusResponses();
        this.peerTlsSctData = session.getPeerSignedCertificateTimestamp();
        this.creationTime = session.getCreationTime();
        this.lastAccessedTime = session.getLastAccessedTime();
        this.cipherSuite = session.getCipherSuite();
        this.protocol = session.getProtocol();
        this.peerHost = session.getPeerHost();
        this.peerPort = session.getPeerPort();
        this.applicationProtocol = session.getApplicationProtocol();
    }

    @Override
    public String getRequestedServerName() {
        return this.requestedServerName;
    }

    @Override
    public List<byte[]> getStatusResponses() {
        ArrayList<byte[]> ret = new ArrayList<byte[]>(this.statusResponses.size());
        for (byte[] resp : this.statusResponses) {
            ret.add((byte[])resp.clone());
        }
        return ret;
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        return this.peerTlsSctData != null ? (byte[])this.peerTlsSctData.clone() : null;
    }

    @Override
    public byte[] getId() {
        return this.id;
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return this.sessionContext;
    }

    @Override
    public long getCreationTime() {
        return this.creationTime;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime;
    }

    @Override
    public void invalidate() {
    }

    @Override
    public boolean isValid() {
        return false;
    }

    @Override
    public void putValue(String s2, Object o) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public Object getValue(String s2) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public void removeValue(String s2) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public String[] getValueNames() {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public X509Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        throw new SSLPeerUnverifiedException("No peer certificates");
    }

    @Override
    public Certificate[] getLocalCertificates() {
        return null;
    }

    @Override
    public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        throw new SSLPeerUnverifiedException("No peer certificates");
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        throw new SSLPeerUnverifiedException("No peer certificates");
    }

    @Override
    public Principal getLocalPrincipal() {
        return null;
    }

    @Override
    public String getCipherSuite() {
        return this.cipherSuite;
    }

    @Override
    public String getProtocol() {
        return this.protocol;
    }

    @Override
    public String getPeerHost() {
        return this.peerHost;
    }

    @Override
    public int getPeerPort() {
        return this.peerPort;
    }

    @Override
    public int getPacketBufferSize() {
        return 16709;
    }

    @Override
    public int getApplicationBufferSize() {
        return 16384;
    }

    @Override
    public String getApplicationProtocol() {
        return this.applicationProtocol;
    }
}

