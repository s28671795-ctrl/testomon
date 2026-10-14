/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.trustmanager.CombinableX509TrustManager;

public final class CompositeX509ExtendedTrustManager
extends X509ExtendedTrustManager
implements CombinableX509TrustManager {
    private final List<X509ExtendedTrustManager> trustManagers;

    public CompositeX509ExtendedTrustManager(List<? extends X509ExtendedTrustManager> trustManagers) {
        this.trustManagers = Collections.unmodifiableList(trustManagers);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(trustManager -> trustManager.checkClientTrusted(chain, authType));
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(trustManager -> trustManager.checkClientTrusted(chain, authType, socket));
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(trustManager -> trustManager.checkClientTrusted(chain, authType, sslEngine));
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(trustManager -> trustManager.checkServerTrusted(chain, authType));
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(trustManager -> trustManager.checkServerTrusted(chain, authType, socket));
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(trustManager -> trustManager.checkServerTrusted(chain, authType, sslEngine));
    }

    @Override
    public X509Certificate[] getAcceptedIssuers() {
        return (X509Certificate[])this.trustManagers.stream().map(X509TrustManager::getAcceptedIssuers).flatMap(Arrays::stream).toArray(X509Certificate[]::new);
    }

    @Override
    public List<X509ExtendedTrustManager> getInnerTrustManagers() {
        return this.trustManagers;
    }
}

