/*
 * Decompiled with CFR 0.152.
 */
package com.typesafe.sslconfig.ssl;

import java.security.KeyStore;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;
import scala.Array$;
import scala.Tuple2;

public final class FakeSSLTools$ {
    public static final FakeSSLTools$ MODULE$ = new FakeSSLTools$();

    public Tuple2<SSLContext, X509TrustManager> buildContextAndTrust(KeyStore keyStore) {
        KeyManagerFactory kmf = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        kmf.init(keyStore, Array$.MODULE$.emptyCharArray());
        KeyManager[] kms = kmf.getKeyManagers();
        TrustManagerFactory tmf = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        tmf.init(keyStore);
        TrustManager[] tms = tmf.getTrustManagers();
        X509TrustManager x509TrustManager = (X509TrustManager)tms[0];
        SSLContext sslContext = SSLContext.getInstance("TLS");
        sslContext.init(kms, tms, null);
        return new Tuple2<SSLContext, X509TrustManager>(sslContext, x509TrustManager);
    }

    private FakeSSLTools$() {
    }
}

