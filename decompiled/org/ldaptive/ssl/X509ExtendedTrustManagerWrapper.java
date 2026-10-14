/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import org.ldaptive.ssl.CertificateHostnameVerifier;
import org.ldaptive.ssl.HostnameResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class X509ExtendedTrustManagerWrapper
extends X509ExtendedTrustManager {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final X509TrustManager trustManager;
    private final CertificateHostnameVerifier hostnameVerifier;

    public X509ExtendedTrustManagerWrapper(X509TrustManager manager, CertificateHostnameVerifier verifier) {
        this.trustManager = manager;
        this.hostnameVerifier = verifier;
    }

    protected void verifyHostname(SSLSession session, X509Certificate cert) throws CertificateException {
        HostnameResolver resolver2 = new HostnameResolver(session);
        String hostname = resolver2.resolve();
        if (!this.hostnameVerifier.verify(hostname, cert)) {
            throw new CertificateException("Hostname verification failed for " + hostname + " using " + this.hostnameVerifier);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        if (this.trustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)this.trustManager).checkClientTrusted(chain, authType, socket);
        } else {
            this.trustManager.checkClientTrusted(chain, authType);
            if (socket != null && socket.isConnected() && socket instanceof SSLSocket) {
                this.verifyHostname(((SSLSocket)socket).getHandshakeSession(), chain[0]);
            } else {
                throw new CertificateException("Could not retrieve SSL session from socket");
            }
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        if (this.trustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)this.trustManager).checkServerTrusted(chain, authType, socket);
        } else {
            this.trustManager.checkServerTrusted(chain, authType);
            if (socket != null && socket.isConnected() && socket instanceof SSLSocket) {
                this.verifyHostname(((SSLSocket)socket).getHandshakeSession(), chain[0]);
            } else {
                throw new CertificateException("Could not retrieve SSL session from socket");
            }
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        if (this.trustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)this.trustManager).checkClientTrusted(chain, authType, engine);
        } else {
            this.trustManager.checkClientTrusted(chain, authType);
            this.verifyHostname(engine.getHandshakeSession(), chain[0]);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        if (this.trustManager instanceof X509ExtendedTrustManager) {
            ((X509ExtendedTrustManager)this.trustManager).checkServerTrusted(chain, authType, engine);
        } else {
            this.trustManager.checkServerTrusted(chain, authType);
            this.verifyHostname(engine.getHandshakeSession(), chain[0]);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.trustManager.checkClientTrusted(chain, authType);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.trustManager.checkServerTrusted(chain, authType);
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return this.trustManager.getAcceptedIssuers();
    }
}

