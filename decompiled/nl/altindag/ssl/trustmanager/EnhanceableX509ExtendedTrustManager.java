/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.trustmanager;

import java.net.Socket;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.function.Predicate;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedTrustManager;
import nl.altindag.ssl.model.TrustManagerParameters;
import nl.altindag.ssl.trustmanager.DelegatingX509ExtendedTrustManager;
import nl.altindag.ssl.trustmanager.TrustManagerRunnable;

public final class EnhanceableX509ExtendedTrustManager
extends DelegatingX509ExtendedTrustManager {
    private final Predicate<TrustManagerParameters> trustManagerParametersValidator;

    public EnhanceableX509ExtendedTrustManager(X509ExtendedTrustManager trustManager, Predicate<TrustManagerParameters> trustManagerParametersValidator) {
        super(trustManager);
        this.trustManagerParametersValidator = Optional.ofNullable(trustManagerParametersValidator).orElse(trustManagerParameters -> false);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType), chain, authType, null, null);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType, socket), chain, authType, socket, null);
    }

    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(() -> super.checkClientTrusted(chain, authType, sslEngine), chain, authType, null, sslEngine);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType), chain, authType, null, null);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, Socket socket) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType, socket), chain, authType, socket, null);
    }

    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType, SSLEngine sslEngine) throws CertificateException {
        this.checkTrusted(() -> super.checkServerTrusted(chain, authType, sslEngine), chain, authType, null, sslEngine);
    }

    private void checkTrusted(TrustManagerRunnable trustManagerRunnable, X509Certificate[] chain, String authType, Socket socket, SSLEngine sslEngine) throws CertificateException {
        TrustManagerParameters trustManagerParameters = new TrustManagerParameters(chain, authType, socket, sslEngine);
        if (this.trustManagerParametersValidator.test(trustManagerParameters)) {
            return;
        }
        trustManagerRunnable.run();
    }
}

