/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.trust.impl;

import java.security.cert.X509Certificate;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitX509CertificateTrustEvaluator {
    private final Logger log = LoggerFactory.getLogger(ExplicitX509CertificateTrustEvaluator.class);

    public boolean validate(@Nonnull X509Certificate untrustedCertificate, @Nonnull X509Certificate trustedCertificate) {
        return untrustedCertificate.equals(trustedCertificate);
    }

    public boolean validate(@Nonnull X509Certificate untrustedCertificate, @Nonnull Iterable<X509Certificate> trustedCertificates) {
        for (X509Certificate trustedCertificate : trustedCertificates) {
            if (!untrustedCertificate.equals(trustedCertificate)) continue;
            return true;
        }
        return false;
    }

    public boolean validate(@Nonnull X509Credential untrustedCredential, @Nonnull X509Credential trustedCredential) {
        X509Certificate untrustedCertificate = untrustedCredential.getEntityCertificate();
        X509Certificate trustedCertificate = trustedCredential.getEntityCertificate();
        if (untrustedCertificate == null) {
            this.log.debug("Untrusted credential contained no entity certificate, unable to evaluate");
            return false;
        }
        if (trustedCertificate == null) {
            this.log.debug("Trusted credential contained no entity certificate, unable to evaluate");
            return false;
        }
        if (this.validate(untrustedCertificate, trustedCertificate)) {
            this.log.debug("Successfully validated untrusted credential against trusted certificate");
            return true;
        }
        this.log.debug("Failed to validate untrusted credential against trusted certificate");
        return false;
    }

    public boolean validate(@Nonnull X509Credential untrustedCredential, @Nonnull Iterable<Credential> trustedCredentials) {
        for (Credential trustedCredential : trustedCredentials) {
            if (!(trustedCredential instanceof X509Credential)) {
                this.log.debug("Skipping evaluation against trusted, non-X509Credential");
                continue;
            }
            X509Credential trustedX509Credential = (X509Credential)trustedCredential;
            if (!this.validate(untrustedCredential, trustedX509Credential)) continue;
            return true;
        }
        return false;
    }
}

