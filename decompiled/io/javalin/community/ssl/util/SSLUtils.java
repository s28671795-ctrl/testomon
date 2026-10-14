/*
 * Decompiled with CFR 0.152.
 */
package io.javalin.community.ssl.util;

import io.javalin.community.ssl.SSLConfig;
import io.javalin.community.ssl.SSLConfigException;
import io.javalin.community.ssl.TrustConfig;
import javax.net.ssl.X509ExtendedKeyManager;
import nl.altindag.ssl.SSLFactory;
import nl.altindag.ssl.jetty.util.JettySslUtils;
import nl.altindag.ssl.pem.util.PemUtils;
import org.eclipse.jetty.util.ssl.SslContextFactory;

public class SSLUtils {
    public static SslContextFactory.Server createSslContextFactory(SSLFactory sslFactory, SSLConfig config) {
        return JettySslUtils.forServer(sslFactory);
    }

    public static SSLFactory getSslFactory(SSLConfig config) {
        return SSLUtils.getSslFactory(config, false);
    }

    public static SSLFactory getSslFactory(SSLConfig config, boolean reloading) {
        SSLFactory.Builder builder = SSLFactory.builder();
        SSLUtils.parseIdentity(config, builder);
        if (config.getTrustConfig() != null) {
            SSLUtils.parseTrust(config.getTrustConfig(), builder);
            builder.withNeedClientAuthentication();
        }
        if (!reloading) {
            builder.withSwappableIdentityMaterial();
            builder.withSwappableTrustMaterial();
            if (config.securityProvider != null) {
                builder.withSecurityProvider(config.securityProvider);
            }
            builder.withCiphers(config.tlsConfig.getCipherSuites());
            builder.withProtocols(config.tlsConfig.getProtocols());
        }
        return builder.build();
    }

    public static void parseIdentity(SSLConfig config, SSLFactory.Builder builder) throws SSLConfigException {
        X509ExtendedKeyManager keyManager;
        SSLConfig.InnerConfig.IdentityLoadingType identityLoadingType = config.inner.getIdentityLoadingType();
        boolean passwordProtectedPem = config.inner.privateKeyPassword != null;
        switch (identityLoadingType) {
            case PEM_FILE_PATH: {
                keyManager = passwordProtectedPem ? PemUtils.loadIdentityMaterial(config.inner.pemCertificatesPath, config.inner.pemPrivateKeyPath, config.inner.privateKeyPassword.toCharArray()) : PemUtils.loadIdentityMaterial(config.inner.pemCertificatesPath, config.inner.pemPrivateKeyPath);
                break;
            }
            case PEM_CLASS_PATH: {
                keyManager = passwordProtectedPem ? PemUtils.loadIdentityMaterial(config.inner.pemCertificatesFile, config.inner.pemPrivateKeyFile, config.inner.privateKeyPassword.toCharArray()) : PemUtils.loadIdentityMaterial(config.inner.pemCertificatesFile, config.inner.pemPrivateKeyFile);
                break;
            }
            case PEM_STRING: {
                keyManager = passwordProtectedPem ? PemUtils.parseIdentityMaterial(config.inner.pemCertificatesString, config.inner.pemPrivateKeyString, config.inner.privateKeyPassword.toCharArray()) : PemUtils.parseIdentityMaterial(config.inner.pemCertificatesString, config.inner.pemPrivateKeyString, null);
                break;
            }
            case PEM_INPUT_STREAM: {
                keyManager = passwordProtectedPem ? PemUtils.loadIdentityMaterial(config.inner.pemCertificatesInputStream, config.inner.pemPrivateKeyInputStream, config.inner.privateKeyPassword.toCharArray()) : PemUtils.loadIdentityMaterial(config.inner.pemCertificatesInputStream, config.inner.pemPrivateKeyInputStream);
                break;
            }
            case KEY_STORE_CLASS_PATH: {
                builder.withIdentityMaterial(config.inner.keyStoreFile, config.inner.keyStorePassword.toCharArray());
                return;
            }
            case KEY_STORE_FILE_PATH: {
                builder.withIdentityMaterial(config.inner.keyStorePath, config.inner.keyStorePassword.toCharArray());
                return;
            }
            case KEY_STORE_INPUT_STREAM: {
                builder.withIdentityMaterial(config.inner.keyStoreInputStream, config.inner.keyStorePassword.toCharArray());
                return;
            }
            default: {
                throw new SSLConfigException(SSLConfigException.Types.MISSING_CERT_AND_KEY_FILE);
            }
        }
        builder.withIdentityMaterial(keyManager);
    }

    public static void parseTrust(TrustConfig config, SSLFactory.Builder builder) {
        if (!config.certificates.isEmpty()) {
            builder.withTrustMaterial(config.certificates);
        }
        if (!config.keyStores.isEmpty()) {
            config.keyStores.forEach(builder::withTrustMaterial);
        }
    }
}

