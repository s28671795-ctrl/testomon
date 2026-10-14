/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.security.transport;

import java.security.cert.Certificate;
import javax.net.ssl.SSLSession;

public class TLSSessionInfo {
    private final SSLSession sslSession;
    private final Certificate[] peerCertificates;
    private final String cipherSuite;

    public TLSSessionInfo(String suite) {
        this(suite, null, null);
    }

    public TLSSessionInfo(String suite, SSLSession session, Certificate[] certs) {
        assert (suite != null);
        this.cipherSuite = suite;
        this.sslSession = session;
        this.peerCertificates = certs;
    }

    @Deprecated
    public final String getChipherSuite() {
        return this.cipherSuite;
    }

    public final String getCipherSuite() {
        return this.cipherSuite;
    }

    public final Certificate[] getPeerCertificates() {
        return this.peerCertificates;
    }

    public final SSLSession getSSLSession() {
        return this.sslSession;
    }
}

