/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.security.x509.impl;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.x509.PKIXTrustEngine;
import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationInformationResolver;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.impl.BasicX509CredentialNameEvaluator;
import org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator;
import org.opensaml.security.x509.impl.X509CredentialNameEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PKIXX509CredentialTrustEngine
implements PKIXTrustEngine<X509Credential> {
    private final Logger log = LoggerFactory.getLogger(PKIXX509CredentialTrustEngine.class);
    private final PKIXValidationInformationResolver pkixResolver;
    private final PKIXTrustEvaluator pkixTrustEvaluator;
    private final X509CredentialNameEvaluator credNameEvaluator;

    public PKIXX509CredentialTrustEngine(@Nonnull PKIXValidationInformationResolver resolver2) {
        this.pkixResolver = Constraint.isNotNull(resolver2, "PKIX trust information resolver cannot be null");
        this.pkixTrustEvaluator = new CertPathPKIXTrustEvaluator();
        this.credNameEvaluator = new BasicX509CredentialNameEvaluator();
    }

    public PKIXX509CredentialTrustEngine(@Nonnull PKIXValidationInformationResolver resolver2, @Nonnull PKIXTrustEvaluator pkixEvaluator, @Nullable X509CredentialNameEvaluator nameEvaluator) {
        this.pkixResolver = Constraint.isNotNull(resolver2, "PKIX trust information resolver cannot be null");
        this.pkixTrustEvaluator = Constraint.isNotNull(pkixEvaluator, "PKIX trust evaluator may not be null");
        this.credNameEvaluator = nameEvaluator;
    }

    @Override
    @Nonnull
    public PKIXValidationInformationResolver getPKIXResolver() {
        return this.pkixResolver;
    }

    @Nonnull
    public PKIXTrustEvaluator getPKIXTrustEvaluator() {
        return this.pkixTrustEvaluator;
    }

    @Nullable
    public X509CredentialNameEvaluator getX509CredentialNameEvaluator() {
        return this.credNameEvaluator;
    }

    @Override
    public boolean validate(@Nonnull X509Credential untrustedCredential, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        this.log.debug("Attempting PKIX validation of untrusted credential");
        if (untrustedCredential == null) {
            this.log.error("X.509 credential was null, unable to perform validation");
            return false;
        }
        if (untrustedCredential.getEntityCertificate() == null) {
            this.log.error("Untrusted X.509 credential's entity certificate was null, unable to perform validation");
            return false;
        }
        Set<String> trustedNames = null;
        if (this.getPKIXResolver().supportsTrustedNameResolution()) {
            try {
                trustedNames = this.pkixResolver.resolveTrustedNames(trustBasisCriteria);
            }
            catch (UnsupportedOperationException e) {
                throw new SecurityException("Error resolving trusted names", e);
            }
            catch (ResolverException e) {
                throw new SecurityException("Error resolving trusted names", e);
            }
        } else {
            this.log.debug("PKIX resolver does not support resolution of trusted names, skipping name checking");
        }
        try {
            return this.validate(untrustedCredential, trustedNames, this.pkixResolver.resolve(trustBasisCriteria));
        }
        catch (ResolverException e) {
            throw new SecurityException("Error resolving trusted credentials", e);
        }
    }

    protected boolean validate(@Nonnull X509Credential untrustedX509Credential, @Nullable Set<String> trustedNames, @Nonnull Iterable<PKIXValidationInformation> validationInfoSet) throws SecurityException {
        this.log.debug("Beginning PKIX validation using trusted validation information");
        if (!this.checkNames(trustedNames, untrustedX509Credential)) {
            this.log.debug("Evaluation of credential against trusted names failed. Aborting PKIX validation");
            return false;
        }
        for (PKIXValidationInformation validationInfo : validationInfoSet) {
            try {
                if (!this.getPKIXTrustEvaluator().validate(validationInfo, untrustedX509Credential)) continue;
                this.log.debug("Credential trust established via PKIX validation");
                return true;
            }
            catch (SecurityException e) {
                this.log.debug("Error performing PKIX validation on untrusted credential", e);
            }
        }
        this.log.debug("Trust of untrusted credential could not be established via PKIX validation");
        return false;
    }

    protected boolean checkNames(@Nullable Set<String> trustedNames, @Nonnull X509Credential untrustedCredential) throws SecurityException {
        if (this.getX509CredentialNameEvaluator() == null) {
            this.log.debug("No credential name evaluator was available, skipping trusted name evaluation");
            return true;
        }
        if (trustedNames == null) {
            this.log.debug("Trusted names was null, signalling PKIX resolver does not support trusted names resolution, skipping trusted name evaluation");
            return true;
        }
        return this.credNameEvaluator.evaluate(untrustedCredential, trustedNames);
    }
}

