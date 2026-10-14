/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import org.ldaptive.LdapUtils;
import org.ldaptive.ssl.CredentialConfig;
import org.ldaptive.ssl.KeyStoreSSLContextInitializer;
import org.ldaptive.ssl.SSLContextInitializer;
import org.ldaptive.ssl.X509CertificatesCredentialReader;
import org.ldaptive.ssl.X509SSLContextInitializer;

public final class CredentialConfigFactory {
    private CredentialConfigFactory() {
    }

    public static CredentialConfig createKeyStoreCredentialConfig(KeyStore trustStore) {
        return CredentialConfigFactory.createKeyStoreCredentialConfig(trustStore, null, null, null, null);
    }

    public static CredentialConfig createKeyStoreCredentialConfig(KeyStore trustStore, String[] trustStoreAliases) {
        return CredentialConfigFactory.createKeyStoreCredentialConfig(trustStore, trustStoreAliases, null, null, null);
    }

    public static CredentialConfig createKeyStoreCredentialConfig(KeyStore keyStore, String keyStorePassword) {
        return CredentialConfigFactory.createKeyStoreCredentialConfig(null, null, keyStore, keyStorePassword, null);
    }

    public static CredentialConfig createKeyStoreCredentialConfig(KeyStore keyStore, String keyStorePassword, String[] keyStoreAliases) {
        return CredentialConfigFactory.createKeyStoreCredentialConfig(null, null, keyStore, keyStorePassword, keyStoreAliases);
    }

    public static CredentialConfig createKeyStoreCredentialConfig(KeyStore trustStore, KeyStore keyStore, String keyStorePassword) {
        return CredentialConfigFactory.createKeyStoreCredentialConfig(trustStore, null, keyStore, keyStorePassword, null);
    }

    public static CredentialConfig createKeyStoreCredentialConfig(final KeyStore trustStore, final String[] trustStoreAliases, final KeyStore keyStore, final String keyStorePassword, final String[] keyStoreAliases) {
        return new CredentialConfig(){

            @Override
            public SSLContextInitializer createSSLContextInitializer() throws GeneralSecurityException {
                KeyStoreSSLContextInitializer sslInit = new KeyStoreSSLContextInitializer();
                if (trustStore != null) {
                    sslInit.setTrustKeystore(trustStore);
                    sslInit.setTrustAliases(trustStoreAliases);
                }
                if (keyStore != null) {
                    sslInit.setAuthenticationKeystore(keyStore);
                    sslInit.setAuthenticationPassword(keyStorePassword != null ? keyStorePassword.toCharArray() : null);
                    sslInit.setAuthenticationAliases(keyStoreAliases);
                }
                return sslInit;
            }

            public String toString() {
                return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustStore=" + trustStore + ", " + "trustStoreAliases=" + Arrays.toString(trustStoreAliases) + ", " + "keyStore=" + keyStore + ", " + "keyStorePassword=" + (keyStorePassword != null ? "suppressed" : null) + ", " + "keyStoreAliases=" + Arrays.toString(keyStoreAliases) + "]";
            }
        };
    }

    public static CredentialConfig createX509CredentialConfig(X509Certificate[] trustCertificates) {
        return CredentialConfigFactory.createX509CredentialConfig(trustCertificates, null, null);
    }

    public static CredentialConfig createX509CredentialConfig(X509Certificate authenticationCertificate, PrivateKey authenticationKey) {
        return CredentialConfigFactory.createX509CredentialConfig(null, authenticationCertificate, authenticationKey);
    }

    public static CredentialConfig createX509CredentialConfig(final X509Certificate[] trustCertificates, final X509Certificate authenticationCertificate, final PrivateKey authenticationKey) {
        return new CredentialConfig(){

            @Override
            public SSLContextInitializer createSSLContextInitializer() throws GeneralSecurityException {
                X509SSLContextInitializer sslInit = new X509SSLContextInitializer();
                if (trustCertificates != null) {
                    sslInit.setTrustCertificates(trustCertificates);
                }
                if (authenticationCertificate != null) {
                    sslInit.setAuthenticationCertificate(authenticationCertificate);
                }
                if (authenticationKey != null) {
                    sslInit.setAuthenticationKey(authenticationKey);
                }
                return sslInit;
            }

            public String toString() {
                return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustCertificates=" + (trustCertificates != null ? "suppressed" : null) + ", " + "authenticationCertificate=" + (authenticationCertificate != null ? "suppressed" : null) + ", " + "authenticationKey=" + (authenticationKey != null ? "suppressed" : null) + "]";
            }
        };
    }

    public static CredentialConfig createX509CredentialConfig(final String trustCertificates) {
        return new CredentialConfig(){

            @Override
            public SSLContextInitializer createSSLContextInitializer() throws GeneralSecurityException {
                X509SSLContextInitializer sslInit = new X509SSLContextInitializer();
                try {
                    if (trustCertificates != null) {
                        X509CertificatesCredentialReader certsReader = new X509CertificatesCredentialReader();
                        ByteArrayInputStream trustCertStream = new ByteArrayInputStream(LdapUtils.utf8Encode(trustCertificates));
                        sslInit.setTrustCertificates(certsReader.read(trustCertStream, new String[0]));
                        ((InputStream)trustCertStream).close();
                    }
                }
                catch (IOException e) {
                    throw new GeneralSecurityException(e);
                }
                return sslInit;
            }

            public String toString() {
                return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustCertificates=" + (trustCertificates != null ? "suppressed" : null) + "]";
            }
        };
    }
}

