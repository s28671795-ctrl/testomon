/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.trustmanager.DelegatingTrustManager;

public final class X509TrustManagerWrapper
extends DelegatingTrustManager<X509TrustManager> {
    public X509TrustManagerWrapper(X509TrustManager trustManager) {
        super(trustManager);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.trustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.trustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.trustManager.checkServerTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.trustManager.checkServerTrusted(chain, authType);
    }
}

