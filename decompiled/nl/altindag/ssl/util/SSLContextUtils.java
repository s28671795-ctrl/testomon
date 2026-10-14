/*
 * Decompiled with CFR 0.152.
 */
package nl.altindag.ssl.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.Provider;
import java.security.SecureRandom;
import java.util.List;
import java.util.Objects;
import javax.net.ssl.SSLContext;
import javax.net.ssl.X509ExtendedKeyManager;
import javax.net.ssl.X509ExtendedTrustManager;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import nl.altindag.ssl.exception.GenericSSLContextException;
import nl.altindag.ssl.util.KeyManagerUtils;
import nl.altindag.ssl.util.TrustManagerUtils;

public final class SSLContextUtils {
    private static final String DEFAULT_SSL_CONTEXT_ALGORITHM = "TLS";

    private SSLContextUtils() {
    }

    public static SSLContext createSslContext(List<? extends X509KeyManager> keyManagers, List<? extends X509TrustManager> trustManagers) {
        return SSLContextUtils.createSslContext(keyManagers, trustManagers, null);
    }

    public static SSLContext createSslContext(List<? extends X509KeyManager> keyManagers, List<? extends X509TrustManager> trustManagers, SecureRandom secureRandom) {
        return SSLContextUtils.createSslContext(keyManagers, trustManagers, secureRandom, DEFAULT_SSL_CONTEXT_ALGORITHM, (Provider)null);
    }

    public static SSLContext createSslContext(List<? extends X509KeyManager> keyManagers, List<? extends X509TrustManager> trustManagers, SecureRandom secureRandom, String sslContextAlgorithm, Provider securityProvider) {
        return SSLContextUtils.createSslContext(!keyManagers.isEmpty() ? KeyManagerUtils.combine(keyManagers) : null, !trustManagers.isEmpty() ? TrustManagerUtils.combine(trustManagers) : null, secureRandom, sslContextAlgorithm, null, securityProvider);
    }

    public static SSLContext createSslContext(List<? extends X509KeyManager> keyManagers, List<? extends X509TrustManager> trustManagers, SecureRandom secureRandom, String sslContextAlgorithm, String securityProviderName) {
        return SSLContextUtils.createSslContext(!keyManagers.isEmpty() ? KeyManagerUtils.combine(keyManagers) : null, !trustManagers.isEmpty() ? TrustManagerUtils.combine(trustManagers) : null, secureRandom, sslContextAlgorithm, securityProviderName, null);
    }

    public static SSLContext createSslContext(X509KeyManager keyManager, X509TrustManager trustManager, SecureRandom secureRandom, String sslContextAlgorithm, String securityProviderName, Provider securityProvider) {
        return SSLContextUtils.createSslContext(keyManager != null ? KeyManagerUtils.toArray(keyManager) : null, trustManager != null ? TrustManagerUtils.toArray(trustManager) : null, secureRandom, sslContextAlgorithm, securityProviderName, securityProvider);
    }

    private static SSLContext createSslContext(X509ExtendedKeyManager[] keyManagers, X509ExtendedTrustManager[] trustManagers, SecureRandom secureRandom, String sslContextAlgorithm, String securityProviderName, Provider securityProvider) {
        try {
            SSLContext sslContext = Objects.nonNull(securityProvider) ? SSLContext.getInstance(sslContextAlgorithm, securityProvider) : (Objects.nonNull(securityProviderName) ? SSLContext.getInstance(sslContextAlgorithm, securityProviderName) : SSLContext.getInstance(sslContextAlgorithm));
            sslContext.init(keyManagers, trustManagers, secureRandom);
            return sslContext;
        }
        catch (KeyManagementException | NoSuchAlgorithmException | NoSuchProviderException e) {
            throw new GenericSSLContextException(e);
        }
    }
}

