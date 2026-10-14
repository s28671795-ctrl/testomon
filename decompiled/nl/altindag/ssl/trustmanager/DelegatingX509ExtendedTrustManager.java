/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.DelegatingTrustManager;

class DelegatingX509ExtendedTrustManager
extends DelegatingTrustManager<X509ExtendedTrustManager> {
    DelegatingX509ExtendedTrustManager(X509ExtendedTrustManager trustManager) {
        super(trustManager);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        ((X509ExtendedTrustManager)this.trustManager).checkClientTrusted(chain, authType, socket);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        ((X509ExtendedTrustManager)this.trustManager).checkClientTrusted(chain, authType, sslEngine);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        ((X509ExtendedTrustManager)this.trustManager).checkServerTrusted(chain, authType, socket);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        ((X509ExtendedTrustManager)this.trustManager).checkServerTrusted(chain, authType, sslEngine);
    }
}

