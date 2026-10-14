/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.support.impl;

import com.google.common.base.Strings;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCriterion;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.opensaml.xmlsec.signature.support.SignatureValidationParametersCriterion;
import org.opensaml.xmlsec.signature.support.SignatureValidator;
import org.opensaml.xmlsec.signature.support.impl.SignatureAlgorithmValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class BaseSignatureTrustEngine<TrustBasisType>
implements SignatureTrustEngine {
    private final Logger log = LoggerFactory.getLogger(BaseSignatureTrustEngine.class);
    private final KeyInfoCredentialResolver keyInfoCredentialResolver;

    public BaseSignatureTrustEngine(@Nonnull KeyInfoCredentialResolver keyInfoResolver) {
        this.keyInfoCredentialResolver = Constraint.isNotNull(keyInfoResolver, "KeyInfo credential resolver cannot be null");
    }

    @Override
    @Nullable
    public KeyInfoCredentialResolver getKeyInfoResolver() {
        return this.keyInfoCredentialResolver;
    }

    @Override
    public final boolean validate(@Nonnull Signature signature, @Nullable CriteriaSet trustBasisCriteria) throws SecurityException {
        this.checkParams(signature, trustBasisCriteria);
        SignatureValidationParametersCriterion validationCriterion = trustBasisCriteria.get(SignatureValidationParametersCriterion.class);
        if (validationCriterion != null) {
            this.log.debug("Performing signature algorithm whitelist/blacklist validation using params from CriteriaSet");
            SignatureAlgorithmValidator algorithmValidator = new SignatureAlgorithmValidator(validationCriterion.getSignatureValidationParameters());
            try {
                algorithmValidator.validate(signature);
            }
            catch (SignatureException e) {
                this.log.warn("XML signature failed algorithm whitelist/blacklist validation");
                return false;
            }
        }
        return this.doValidate(signature, trustBasisCriteria);
    }

    protected abstract boolean doValidate(@Nonnull Signature var1, @Nullable CriteriaSet var2) throws SecurityException;

    @Override
    public final boolean validate(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nullable CriteriaSet trustBasisCriteria, @Nullable Credential candidateCredential) throws SecurityException {
        this.checkParamsRaw(signature, content, algorithmURI, trustBasisCriteria);
        SignatureValidationParametersCriterion validationCriterion = trustBasisCriteria.get(SignatureValidationParametersCriterion.class);
        if (validationCriterion != null) {
            this.log.debug("Performing signature algorithm whitelist/blacklist validation using params from CriteriaSet");
            SignatureValidationParameters params = validationCriterion.getSignatureValidationParameters();
            if (!AlgorithmSupport.validateAlgorithmURI(algorithmURI, params.getWhitelistedAlgorithms(), params.getBlacklistedAlgorithms())) {
                this.log.warn("Simple/raw signature failed algorithm whitelist/blacklist validation");
                return false;
            }
        }
        return this.doValidate(signature, content, algorithmURI, trustBasisCriteria, candidateCredential);
    }

    protected abstract boolean doValidate(@Nonnull byte[] var1, @Nonnull byte[] var2, @Nonnull String var3, @Nullable CriteriaSet var4, @Nullable Credential var5) throws SecurityException;

    protected boolean validate(@Nonnull Signature signature, @Nullable TrustBasisType trustBasis) throws SecurityException {
        this.log.debug("Attempting to verify signature and establish trust using KeyInfo-derived credentials");
        if (signature.getKeyInfo() != null) {
            KeyInfoCriterion keyInfoCriteria = new KeyInfoCriterion(signature.getKeyInfo());
            CriteriaSet keyInfoCriteriaSet = new CriteriaSet(keyInfoCriteria);
            try {
                for (Credential kiCred : this.getKeyInfoResolver().resolve(keyInfoCriteriaSet)) {
                    if (!this.verifySignature(signature, kiCred)) continue;
                    this.log.debug("Successfully verified signature using KeyInfo-derived credential");
                    this.log.debug("Attempting to establish trust of KeyInfo-derived credential");
                    if (this.evaluateTrust(kiCred, trustBasis)) {
                        this.log.debug("Successfully established trust of KeyInfo-derived credential");
                        return true;
                    }
                    this.log.debug("Failed to establish trust of KeyInfo-derived credential");
                }
            }
            catch (ResolverException e) {
                throw new SecurityException("Error resolving KeyInfo from KeyInfoResolver", e);
            }
        } else {
            this.log.debug("Signature contained no KeyInfo element, could not resolve verification credentials");
        }
        this.log.debug("Failed to verify signature and/or establish trust using any KeyInfo-derived credentials");
        return false;
    }

    protected abstract boolean evaluateTrust(@Nonnull Credential var1, @Nullable TrustBasisType var2) throws SecurityException;

    protected boolean verifySignature(@Nonnull Signature signature, @Nonnull Credential credential) {
        try {
            SignatureValidator.validate(signature, credential);
        }
        catch (SignatureException e) {
            this.log.debug("Signature validation using candidate validation credential failed", e);
            return false;
        }
        this.log.debug("Signature validation using candidate credential was successful");
        return true;
    }

    protected void checkParams(@Nonnull Signature signature, @Nonnull CriteriaSet trustBasisCriteria) throws SecurityException {
        if (signature == null) {
            throw new SecurityException("Signature cannot be null");
        }
        if (trustBasisCriteria == null) {
            throw new SecurityException("Trust basis criteria set cannot be null");
        }
        if (trustBasisCriteria.isEmpty()) {
            throw new SecurityException("Trust basis criteria set cannot be empty");
        }
    }

    protected void checkParamsRaw(@Nonnull byte[] signature, @Nonnull byte[] content, @Nonnull String algorithmURI, @Nonnull CriteriaSet trustBasisCriteria) throws SecurityException {
        if (signature == null || signature.length == 0) {
            throw new SecurityException("Signature byte array cannot be null or empty");
        }
        if (content == null || content.length == 0) {
            throw new SecurityException("Content byte array cannot be null or empty");
        }
        if (Strings.isNullOrEmpty(algorithmURI)) {
            throw new SecurityException("Signature algorithm cannot be null or empty");
        }
        if (trustBasisCriteria == null) {
            throw new SecurityException("Trust basis criteria set cannot be null");
        }
        if (trustBasisCriteria.isEmpty()) {
            throw new SecurityException("Trust basis criteria set cannot be empty");
        }
    }
}

