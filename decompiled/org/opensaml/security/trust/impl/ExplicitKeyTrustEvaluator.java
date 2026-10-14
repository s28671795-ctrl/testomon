/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.trust.impl;

import java.security.Key;
import javax.annotation.Nonnull;
import org.opensaml.security.credential.Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitKeyTrustEvaluator {
    private final Logger log = LoggerFactory.getLogger(ExplicitKeyTrustEvaluator.class);

    public boolean validate(@Nonnull Key untrustedKey, @Nonnull Key trustedKey) {
        return untrustedKey.equals(trustedKey);
    }

    public boolean validate(@Nonnull Key untrustedKey, @Nonnull Iterable<Key> trustedKeys) {
        for (Key trustedKey : trustedKeys) {
            if (!untrustedKey.equals(trustedKey)) continue;
            return true;
        }
        return false;
    }

    public boolean validate(@Nonnull Credential untrustedCredential, @Nonnull Credential trustedCredential) {
        Key untrustedKey = null;
        Key trustedKey = null;
        if (untrustedCredential.getPublicKey() != null) {
            untrustedKey = untrustedCredential.getPublicKey();
            trustedKey = trustedCredential.getPublicKey();
        } else {
            untrustedKey = untrustedCredential.getSecretKey();
            trustedKey = trustedCredential.getSecretKey();
        }
        if (untrustedKey == null) {
            this.log.debug("Untrusted credential contained no key, unable to evaluate");
            return false;
        }
        if (trustedKey == null) {
            this.log.debug("Trusted credential contained no key of the appropriate type, unable to evaluate");
            return false;
        }
        if (this.validate(untrustedKey, trustedKey)) {
            this.log.debug("Successfully validated untrusted credential against trusted key");
            return true;
        }
        this.log.debug("Failed to validate untrusted credential against trusted key");
        return false;
    }

    public boolean validate(@Nonnull Credential untrustedCredential, @Nonnull Iterable<Credential> trustedCredentials) {
        for (Credential trustedCredential : trustedCredentials) {
            if (!this.validate(untrustedCredential, trustedCredential)) continue;
            return true;
        }
        return false;
    }
}

