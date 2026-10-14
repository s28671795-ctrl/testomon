/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.security.Principal;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSessionContext;
import org.conscrypt.AbstractSessionContext;
import org.conscrypt.ConscryptSession;
import org.conscrypt.EmptyArray;
import org.conscrypt.NativeSsl;
import org.conscrypt.Preconditions;
import org.conscrypt.SSLUtils;

final class ActiveSession
implements ConscryptSession {
    private final NativeSsl ssl;
    private AbstractSessionContext sessionContext;
    private byte[] id;
    private long creationTime;
    private String protocol;
    private String applicationProtocol;
    private String peerHost;
    private int peerPort = -1;
    private long lastAccessedTime = 0L;
    private volatile javax.security.cert.X509Certificate[] peerCertificateChain;
    private X509Certificate[] localCertificates;
    private X509Certificate[] peerCertificates;
    private byte[] peerCertificateOcspData;
    private byte[] peerTlsSctData;

    ActiveSession(NativeSsl ssl, AbstractSessionContext sessionContext) {
        this.ssl = Preconditions.checkNotNull(ssl, "ssl");
        this.sessionContext = Preconditions.checkNotNull(sessionContext, "sessionContext");
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public byte[] getId() {
        if (this.id == null) {
            NativeSsl nativeSsl = this.ssl;
            synchronized (nativeSsl) {
                this.id = this.ssl.getSessionId();
            }
        }
        return this.id != null ? (byte[])this.id.clone() : EmptyArray.BYTE;
    }

    @Override
    public SSLSessionContext getSessionContext() {
        return this.isValid() ? this.sessionContext : null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public long getCreationTime() {
        if (this.creationTime == 0L) {
            NativeSsl nativeSsl = this.ssl;
            synchronized (nativeSsl) {
                this.creationTime = this.ssl.getTime();
            }
        }
        return this.creationTime;
    }

    @Override
    public long getLastAccessedTime() {
        return this.lastAccessedTime == 0L ? this.getCreationTime() : this.lastAccessedTime;
    }

    void setLastAccessedTime(long accessTimeMillis) {
        this.lastAccessedTime = accessTimeMillis;
    }

    @Override
    public List<byte[]> getStatusResponses() {
        if (this.peerCertificateOcspData == null) {
            return Collections.emptyList();
        }
        return Collections.singletonList(this.peerCertificateOcspData.clone());
    }

    @Override
    public byte[] getPeerSignedCertificateTimestamp() {
        if (this.peerTlsSctData == null) {
            return null;
        }
        return (byte[])this.peerTlsSctData.clone();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getRequestedServerName() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            return this.ssl.getRequestedServerName();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void invalidate() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.ssl.setTimeout(0L);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean isValid() {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            long creationTimeMillis = this.ssl.getTime();
            long timeoutMillis = this.ssl.getTimeout();
            return System.currentTimeMillis() - timeoutMillis < creationTimeMillis;
        }
    }

    @Override
    public void putValue(String name, Object value) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public Object getValue(String name) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public void removeValue(String name) {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public String[] getValueNames() {
        throw new UnsupportedOperationException("All calls to this method should be intercepted by ExternalSession.");
    }

    @Override
    public X509Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
        this.checkPeerCertificatesPresent();
        return (X509Certificate[])this.peerCertificates.clone();
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public Certificate[] getLocalCertificates() {
        if (this.localCertificates == null) {
            NativeSsl nativeSsl = this.ssl;
            synchronized (nativeSsl) {
                this.localCertificates = this.ssl.getLocalCertificates();
            }
        }
        return this.localCertificates == null ? null : (X509Certificate[])this.localCertificates.clone();
    }

    @Override
    public javax.security.cert.X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
        this.checkPeerCertificatesPresent();
        javax.security.cert.X509Certificate[] result = this.peerCertificateChain;
        if (result == null) {
            result = SSLUtils.toCertificateChain(this.peerCertificates);
            this.peerCertificateChain = result;
        }
        return result;
    }

    @Override
    public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
        this.checkPeerCertificatesPresent();
        return this.peerCertificates[0].getSubjectX500Principal();
    }

    @Override
    public Principal getLocalPrincipal() {
        X509Certificate[] certs = (X509Certificate[])this.getLocalCertificates();
        if (certs != null && certs.length > 0) {
            return certs[0].getSubjectX500Principal();
        }
        return null;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getCipherSuite() {
        String cipher;
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            cipher = this.ssl.getCipherSuite();
        }
        return cipher == null ? "SSL_NULL_WITH_NULL_NULL" : cipher;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getProtocol() {
        String protocol = this.protocol;
        if (protocol == null) {
            NativeSsl nativeSsl = this.ssl;
            synchronized (nativeSsl) {
                protocol = this.ssl.getVersion();
            }
            this.protocol = protocol;
        }
        return protocol;
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public String getApplicationProtocol() {
        String applicationProtocol = this.applicationProtocol;
        if (applicationProtocol == null) {
            NativeSsl nativeSsl = this.ssl;
            synchronized (nativeSsl) {
                applicationProtocol = SSLUtils.toProtocolString(this.ssl.getApplicationProtocol());
            }
            this.applicationProtocol = applicationProtocol;
        }
        return applicationProtocol;
    }

    void onPeerCertificatesReceived(String peerHost, int peerPort, X509Certificate[] peerCertificates2) {
        this.configurePeer(peerHost, peerPort, peerCertificates2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void configurePeer(String peerHost, int peerPort, X509Certificate[] peerCertificates2) {
        this.peerHost = peerHost;
        this.peerPort = peerPort;
        this.peerCertificates = peerCertificates2;
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.peerCertificateOcspData = this.ssl.getPeerCertificateOcspData();
            this.peerTlsSctData = this.ssl.getPeerTlsSctData();
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void onPeerCertificateAvailable(String peerHost, int peerPort) throws CertificateException {
        NativeSsl nativeSsl = this.ssl;
        synchronized (nativeSsl) {
            this.id = null;
            if (this.localCertificates == null) {
                this.localCertificates = this.ssl.getLocalCertificates();
            }
            if (this.peerCertificates == null) {
                this.configurePeer(peerHost, peerPort, this.ssl.getPeerCertificates());
            }
        }
    }

    private void checkPeerCertificatesPresent() throws SSLPeerUnverifiedException {
        if (this.peerCertificates == null || this.peerCertificates.length == 0) {
            throw new SSLPeerUnverifiedException("No peer certificates");
        }
    }
}

