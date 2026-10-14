/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.ParameterName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.KeyAlgorithmCriterion;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.security.trust.TrustedCredentialTrustEngine;
import org.opensaml.security.trust.impl.ExplicitKeyTrustEvaluator;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.crypto.XMLSigningUtil;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.impl.BaseSignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExplicitKeySignatureTrustEngine
extends BaseSignatureTrustEngine<Iterable<Credential>>
implements TrustedCredentialTrustEngine<Signature> {
    private final Logger log = LoggerFactory.getLogger(ExplicitKeySignatureTrustEngine.class);
    private final CredentialResolver credentialResolver;
    private final ExplicitKeyTrustEvaluator keyTrust;

    public ExplicitKeySignatureTrustEngine(@Nonnull @ParameterName(name="resolver") CredentialResolver resolver2, @Nonnull @ParameterName(name="keyInfoResolver") KeyInfoCredentialResolver keyInfoResolver) {
        super(keyInfoResolver);
        this.credentialResolver = Constraint.isNotNull(resolver2, "Credential resolver cannot be null");
        this.keyTrust = new ExplicitKeyTrustEvaluator();
    }

    @Override
    @Nonnull
    public CredentialResolver getCredentialResolver() {
        return this.credentialResolver;
    }

    @Override
    protected boolean doValidate(@Nonnull Signature signature, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        Iterable trustedCredentials;
        String jcaAlgorithm;
        CriteriaSet criteriaSet = new CriteriaSet();
        criteriaSet.addAll(trustBasisCriteria);
        if (!criteriaSet.contains(UsageCriterion.class)) {
            criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
        }
        if (!Strings.isNullOrEmpty(jcaAlgorithm = AlgorithmSupport.getKeyAlgorithm(signature.getSignatureAlgorithm()))) {
            criteriaSet.add(new KeyAlgorithmCriterion(jcaAlgorithm), true);
        }
        try {
            trustedCredentials = this.getCredentialResolver().resolve(criteriaSet);
        }
        catch (ResolverException e) {
            throw new SecurityException("Error resolving trusted credentials", e);
        }
        if (this.validate(signature, trustedCredentials)) {
            return true;
        }
        this.log.debug("Attempting to verify signature using trusted credentials");
        for (Credential trustedCredential : trustedCredentials) {
            if (!this.verifySignature(signature, trustedCredential)) continue;
            this.log.debug("Successfully verified signature using resolved trusted credential");
            return true;
        }
        this.log.debug("Failed to verify signature using either KeyInfo-derived or directly trusted credentials");
        return false;
    }

    @Override
    protected boolean doValidate(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nullable CriteriaSet trustBasisCriteria, @Nullable Credential candidateCredential) throws SecurityException {
        Iterable<Credential> trustedCredentials;
        String jcaAlgorithm;
        CriteriaSet criteriaSet = new CriteriaSet();
        criteriaSet.addAll(trustBasisCriteria);
        if (!criteriaSet.contains(UsageCriterion.class)) {
            criteriaSet.add(new UsageCriterion(UsageType.SIGNING));
        }
        if (!Strings.isNullOrEmpty(jcaAlgorithm = AlgorithmSupport.getKeyAlgorithm(algorithmURI))) {
            criteriaSet.add(new KeyAlgorithmCriterion(jcaAlgorithm), true);
        }
        try {
            trustedCredentials = this.getCredentialResolver().resolve(criteriaSet);
        }
        catch (ResolverException e) {
            throw new SecurityException("Error resolving trusted credentials", e);
        }
        if (candidateCredential != null) {
            this.log.debug("Attempting to verify raw signature using supplied candidate credential");
            try {
                if (XMLSigningUtil.verifyWithURI(candidateCredential, algorithmURI, signature, content)) {
                    this.log.debug("Successfully verified signature using supplied candidate credential");
                    this.log.debug("Attempting to establish trust of supplied candidate credential");
                    if (this.evaluateTrust(candidateCredential, trustedCredentials)) {
                        this.log.debug("Successfully established trust of supplied candidate credential");
                        return true;
                    }
                    this.log.debug("Failed to establish trust of supplied candidate credential");
                }
            }
            catch (SecurityException e) {
                this.log.debug("Saw fatal error attempting to verify raw signature with supplied candidate credential", e);
            }
        }
        this.log.debug("Attempting to verify signature using trusted credentials");
        for (Credential trustedCredential : trustedCredentials) {
            try {
                if (!XMLSigningUtil.verifyWithURI(trustedCredential, algorithmURI, signature, content)) continue;
                this.log.debug("Successfully verified signature using resolved trusted credential");
                return true;
            }
            catch (SecurityException e) {
                this.log.debug("Saw fatal error attempting to verify raw signature with trusted credential", e);
            }
        }
        this.log.debug("Failed to verify signature using either supplied candidate credential or directly trusted credentials");
        return false;
    }

    @Override
    protected boolean evaluateTrust(@Nonnull Credential untrustedCredential, @Nullable Iterable<Credential> trustedCredentials) throws SecurityException {
        return this.keyTrust.validate(untrustedCredential, trustedCredentials);
    }
}

