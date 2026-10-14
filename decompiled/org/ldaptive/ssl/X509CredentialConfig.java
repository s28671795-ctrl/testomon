/*
 * Decompiled with CFR 0.152.
 */
package org.ldaptive.ssl;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import org.ldaptive.LdapUtils;
import org.ldaptive.ssl.CredentialConfig;
import org.ldaptive.ssl.PrivateKeyCredentialReader;
import org.ldaptive.ssl.SSLContextInitializer;
import org.ldaptive.ssl.X509CertificateCredentialReader;
import org.ldaptive.ssl.X509CertificatesCredentialReader;
import org.ldaptive.ssl.X509SSLContextInitializer;

public class X509CredentialConfig
implements CredentialConfig {
    private static final int HASH_CODE_SEED = 1009;
    private final X509CertificatesCredentialReader certsReader = new X509CertificatesCredentialReader();
    private final X509CertificateCredentialReader certReader = new X509CertificateCredentialReader();
    private final PrivateKeyCredentialReader keyReader = new PrivateKeyCredentialReader();
    private String trustCertificates;
    private String authenticationCertificate;
    private String authenticationKey;

    public String getTrustCertificates() {
        return this.trustCertificates;
    }

    public void setTrustCertificates(String name) {
        this.trustCertificates = name;
    }

    public String getAuthenticationCertificate() {
        return this.authenticationCertificate;
    }

    public void setAuthenticationCertificate(String name) {
        this.authenticationCertificate = name;
    }

    public String getAuthenticationKey() {
        return this.authenticationKey;
    }

    public void setAuthenticationKey(String name) {
        this.authenticationKey = name;
    }

    @Override
    public SSLContextInitializer createSSLContextInitializer() throws GeneralSecurityException {
        X509SSLContextInitializer sslInit = new X509SSLContextInitializer();
        try {
            if (this.trustCertificates != null) {
                sslInit.setTrustCertificates(this.certsReader.read(this.trustCertificates, new String[0]));
            }
            if (this.authenticationCertificate != null) {
                sslInit.setAuthenticationCertificate((X509Certificate)this.certReader.read(this.authenticationCertificate, new String[0]));
            }
            if (this.authenticationKey != null) {
                sslInit.setAuthenticationKey((PrivateKey)this.keyReader.read(this.authenticationKey, new String[0]));
            }
        }
        catch (IOException e) {
            throw new GeneralSecurityException(e);
        }
        return sslInit;
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (o instanceof X509CredentialConfig) {
            X509CredentialConfig v = (X509CredentialConfig)o;
            return LdapUtils.areEqual(this.trustCertificates, v.trustCertificates) && LdapUtils.areEqual(this.authenticationCertificate, v.authenticationCertificate) && LdapUtils.areEqual(this.authenticationKey, v.authenticationKey);
        }
        return false;
    }

    public int hashCode() {
        return LdapUtils.computeHashCode(1009, this.trustCertificates, this.authenticationCertificate, this.authenticationKey);
    }

    public String toString() {
        return "[" + this.getClass().getName() + "@" + this.hashCode() + "::" + "trustCertificates=" + this.trustCertificates + ", " + "authenticationCertificate=" + this.authenticationCertificate + ", " + "authenticationKey=" + this.authenticationKey + "]";
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {
        private final X509CredentialConfig object = new X509CredentialConfig();

        protected Builder() {
        }

        public Builder trustCertificates(String certificates) {
            this.object.setTrustCertificates(certificates);
            return this;
        }

        public Builder authenticationCertificate(String certificate) {
            this.object.setAuthenticationCertificate(certificate);
            return this;
        }

        public Builder authenticationKey(String key) {
            this.object.setAuthenticationKey(key);
            return this;
        }

        public X509CredentialConfig build() {
            return this.object;
        }
    }
}

