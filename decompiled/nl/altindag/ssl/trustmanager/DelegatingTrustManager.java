/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.util.internal.ValidationUtils;

abstract class DelegatingTrustManager<T extends X509TrustManager>
extends X509ExtendedTrustManager {
    T trustManager;

    DelegatingTrustManager(T trustManager) {
        this.trustManager = (X509TrustManager)ValidationUtils.requireNotNull(trustManager, (String)ValidationUtils.GENERIC_EXCEPTION_MESSAGE.apply("TrustManager"));
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
    public abstract void checkClientTrusted(X509Certificate[] var1, String var2, Socket var3) throws CertificateException;

    @Override
    public abstract void checkServerTrusted(X509Certificate[] var1, String var2, Socket var3) throws CertificateException;

    @Override
    public abstract void checkClientTrusted(X509Certificate[] var1, String var2, SSLEngine var3) throws CertificateException;

    @Override
    public abstract void checkServerTrusted(X509Certificate[] var1, String var2, SSLEngine var3) throws CertificateException;

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        X509Certificate[] acceptedIssuers = this.trustManager.getAcceptedIssuers();
        return Arrays.copyOf(acceptedIssuers, acceptedIssuers.length);
    }

    public T getInnerTrustManager() {
        return this.trustManager;
    }
}

