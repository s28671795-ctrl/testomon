/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.net.Socket;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509ExtendedTrustManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DefaultTrustManager
extends X509ExtendedTrustManager {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final X509ExtendedTrustManager[] trustManagers;

    public DefaultTrustManager() {
        try {
            TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            tmf.init((KeyStore)null);
            this.trustManagers = (X509ExtendedTrustManager[])Stream.of(tmf.getTrustManagers()).map(X509ExtendedTrustManager.class::cast).toArray(X509ExtendedTrustManager[]::new);
        }
        catch (GeneralSecurityException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            this.logger.trace("invoking checkClientTrusted for {}", (Object)tm);
            tm.checkClientTrusted(chain, authType, socket);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            this.logger.trace("invoking checkClientTrusted for {}", (Object)tm);
            tm.checkServerTrusted(chain, authType, socket);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            this.logger.trace("invoking checkClientTrusted for {}", (Object)tm);
            tm.checkClientTrusted(chain, authType, engine);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine engine) throws CertificateException {
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            this.logger.trace("invoking checkClientTrusted for {}", (Object)tm);
            tm.checkServerTrusted(chain, authType, engine);
        }
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            this.logger.trace("invoking checkClientTrusted for {}", (Object)tm);
            tm.checkClientTrusted(chain, authType);
        }
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        for (X509ExtendedTrustManager tm : this.trustManagers) {
            this.logger.trace("invoking checkServerTrusted for {}", (Object)tm);
            tm.checkServerTrusted(chain, authType);
        }
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        ArrayList issuers = new ArrayList();
        if (this.trustManagers != null) {
            for (X509ExtendedTrustManager tm : this.trustManagers) {
                this.logger.trace("invoking getAcceptedIssuers for {}", (Object)tm);
                Collections.addAll(issuers, tm.getAcceptedIssuers());
            }
        }
        return issuers.toArray(new X509Certificate[0]);
    }
}

