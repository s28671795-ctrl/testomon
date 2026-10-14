/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.trust.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.trust.TrustedCredentialTrustEngine;
import org.opensaml.security.trust.impl.ExplicitX509CertificateTrustEvaluator;
import org.opensaml.security.x509.X509Credential;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitX509CertificateTrustEngine
implements TrustedCredentialTrustEngine<X509Credential> {
    private final Logger log = LoggerFactory.getLogger(ExplicitX509CertificateTrustEngine.class);
    private final CredentialResolver credentialResolver;
    private final ExplicitX509CertificateTrustEvaluator trustEvaluator;

    public ExplicitX509CertificateTrustEngine(@Nonnull CredentialResolver resolver2) {
        this.credentialResolver = Constraint.isNotNull(resolver2, "Credential resolver cannot be null");
        this.trustEvaluator = new ExplicitX509CertificateTrustEvaluator();
    }

    @Override
    @Nonnull
    public CredentialResolver getCredentialResolver() {
        return this.credentialResolver;
    }

    @Override
    public boolean validate(@Nonnull X509Credential untrustedCredential, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        if (untrustedCredential == null) {
            this.log.error("X.509 credential was null, unable to perform validation");
            return false;
        }
        this.log.debug("Attempting to validate untrusted credential");
        try {
            Iterable<Credential> trustedCredentials = this.getCredentialResolver().resolve(trustBasisCriteria);
            return this.trustEvaluator.validate(untrustedCredential, trustedCredentials);
        }
        catch (ResolverException e) {
            throw new SecurityException("Error resolving trusted credentials", e);
        }
    }
}

