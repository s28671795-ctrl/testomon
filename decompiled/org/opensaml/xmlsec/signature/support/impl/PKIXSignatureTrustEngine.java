/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support.impl;

import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.security.x509.PKIXTrustEngine;
import org.opensaml.security.x509.PKIXTrustEvaluator;
import org.opensaml.security.x509.PKIXValidationInformation;
import org.opensaml.security.x509.PKIXValidationInformationResolver;
import org.opensaml.security.x509.X509Credential;
import org.opensaml.security.x509.impl.BasicX509CredentialNameEvaluator;
import org.opensaml.security.x509.impl.CertPathPKIXTrustEvaluator;
import org.opensaml.security.x509.impl.X509CredentialNameEvaluator;
import org.opensaml.xmlsec.crypto.XMLSigningUtil;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.impl.BaseSignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PKIXSignatureTrustEngine
extends BaseSignatureTrustEngine<Pair<Set<String>, Iterable<PKIXValidationInformation>>>
implements PKIXTrustEngine<Signature> {
    private final Logger log = LoggerFactory.getLogger(PKIXSignatureTrustEngine.class);
    private final PKIXValidationInformationResolver pkixResolver;
    private final PKIXTrustEvaluator pkixTrustEvaluator;
    private final X509CredentialNameEvaluator credNameEvaluator;

    public PKIXSignatureTrustEngine(@Nonnull @ParameterName(name="resolver") PKIXValidationInformationResolver resolver2, @Nonnull @ParameterName(name="keyInfoResolver") KeyInfoCredentialResolver keyInfoResolver) {
        super(keyInfoResolver);
        this.pkixResolver = Constraint.isNotNull(resolver2, "PKIX trust information resolver cannot be null");
        this.pkixTrustEvaluator = new CertPathPKIXTrustEvaluator();
        this.credNameEvaluator = new BasicX509CredentialNameEvaluator();
    }

    public PKIXSignatureTrustEngine(@Nonnull @ParameterName(name="resolver") PKIXValidationInformationResolver resolver2, @Nonnull @ParameterName(name="keyInfoResolver") KeyInfoCredentialResolver keyInfoResolver, @Nonnull @ParameterName(name="pkixEvaluator") PKIXTrustEvaluator pkixEvaluator, @Nullable @ParameterName(name="nameEvaluator") X509CredentialNameEvaluator nameEvaluator) {
        super(keyInfoResolver);
        this.pkixResolver = Constraint.isNotNull(resolver2, "PKIX trust information resolver cannot be null");
        this.pkixTrustEvaluator = Constraint.isNotNull(pkixEvaluator, "PKIX trust evaluator cannot be null");
        this.credNameEvaluator = nameEvaluator;
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
    @Nonnull
    public PKIXValidationInformationResolver getPKIXResolver() {
        return this.pkixResolver;
    }

    @Override
    protected boolean doValidate(@Nonnull Signature signature, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        Pair<Set<String>, Iterable<PKIXValidationInformation>> validationPair = this.resolveValidationInfo(trustBasisCriteria);
        if (this.validate(signature, validationPair)) {
            return true;
        }
        this.log.debug("PKIX validation of signature failed, unable to resolve valid and trusted signing key");
        return false;
    }

    @Override
    protected boolean doValidate(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nullable CriteriaSet trustBasisCriteria, @Nullable Credential candidateCredential) throws SecurityException {
        if (candidateCredential == null || CredentialSupport.extractVerificationKey(candidateCredential) == null) {
            this.log.debug("Candidate credential was either not supplied or did not contain verification key");
            this.log.debug("PKIX trust engine requires supplied key, skipping PKIX trust evaluation");
            return false;
        }
        Pair<Set<String>, Iterable<PKIXValidationInformation>> validationPair = this.resolveValidationInfo(trustBasisCriteria);
        try {
            if (XMLSigningUtil.verifyWithURI(candidateCredential, algorithmURI, signature, content)) {
                this.log.debug("Successfully verified raw signature using supplied candidate credential");
                this.log.debug("Attempting to establish trust of supplied candidate credential");
                if (this.evaluateTrust(candidateCredential, validationPair)) {
                    this.log.debug("Successfully established trust of supplied candidate credential");
                    return true;
                }
                this.log.debug("Failed to establish trust of supplied candidate credential");
            } else {
                this.log.debug("Cryptographic verification of raw signature failed with candidate credential");
            }
        }
        catch (SecurityException securityException) {
            // empty catch block
        }
        this.log.debug("PKIX validation of raw signature failed, unable to establish trust of supplied verification credential");
        return false;
    }

    @Override
    protected boolean evaluateTrust(@Nonnull Credential untrustedCredential, @Nullable Pair<Set<String>, Iterable<PKIXValidationInformation>> validationPair) throws SecurityException {
        if (!(untrustedCredential instanceof X509Credential)) {
            this.log.debug("Can not evaluate trust of non-X509Credential");
            return false;
        }
        X509Credential untrustedX509Credential = (X509Credential)untrustedCredential;
        Set<String> trustedNames = validationPair.getFirst();
        Iterable<PKIXValidationInformation> validationInfoSet = validationPair.getSecond();
        if (validationInfoSet == null) {
            this.log.debug("PKIX validation information not available. Aborting PKIX validation");
            return false;
        }
        if (!this.checkNames(trustedNames, untrustedX509Credential)) {
            this.log.debug("Evaluation of credential against trusted names failed. Aborting PKIX validation");
            return false;
        }
        for (PKIXValidationInformation validationInfo : validationInfoSet) {
            try {
                if (!this.pkixTrustEvaluator.validate(validationInfo, untrustedX509Credential)) continue;
                this.log.debug("Signature trust established via PKIX validation of signing credential");
                return true;
            }
            catch (SecurityException e) {
                this.log.debug("Error performing PKIX validation on untrusted credential", e);
            }
        }
        this.log.debug("Signature trust could not be established via PKIX validation of signing credential");
        return false;
    }

    @Nonnull
    protected Pair<Set<String>, Iterable<PKIXValidationInformation>> resolveValidationInfo(@Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        Iterable validationInfoSet;
        Set<String> trustedNames = null;
        if (this.pkixResolver.supportsTrustedNameResolution()) {
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
            validationInfoSet = this.pkixResolver.resolve(trustBasisCriteria);
        }
        catch (ResolverException e) {
            throw new SecurityException("Error resolving trusted PKIX validation information", e);
        }
        return new Pair<Set<String>, Iterable<PKIXValidationInformation>>(trustedNames, validationInfoSet);
    }

    protected boolean checkNames(@Nullable Set<String> trustedNames, @Nonnull X509Credential untrustedCredential) throws SecurityException {
        if (this.credNameEvaluator == null) {
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

