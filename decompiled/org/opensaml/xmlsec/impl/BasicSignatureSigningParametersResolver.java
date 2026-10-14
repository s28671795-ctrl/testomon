/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.security.Key;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.SignatureSigningParametersResolver;
import org.opensaml.xmlsec.algorithm.AlgorithmRegistry;
import org.opensaml.xmlsec.algorithm.AlgorithmSupport;
import org.opensaml.xmlsec.criterion.KeyInfoGenerationProfileCriterion;
import org.opensaml.xmlsec.criterion.SignatureSigningConfigurationCriterion;
import org.opensaml.xmlsec.impl.AbstractSecurityParametersResolver;
import org.opensaml.xmlsec.impl.AlgorithmRuntimeSupportedPredicate;
import org.opensaml.xmlsec.keyinfo.KeyInfoGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSignatureSigningParametersResolver
extends AbstractSecurityParametersResolver<SignatureSigningParameters>
implements SignatureSigningParametersResolver {
    private Logger log = LoggerFactory.getLogger(BasicSignatureSigningParametersResolver.class);
    private AlgorithmRegistry algorithmRegistry = AlgorithmSupport.getGlobalAlgorithmRegistry();

    public AlgorithmRegistry getAlgorithmRegistry() {
        if (this.algorithmRegistry == null) {
            return AlgorithmSupport.getGlobalAlgorithmRegistry();
        }
        return this.algorithmRegistry;
    }

    public void setAlgorithmRegistry(@Nonnull AlgorithmRegistry registry) {
        this.algorithmRegistry = Constraint.isNotNull(registry, "AlgorithmRegistry was null");
    }

    @Override
    @Nonnull
    public Iterable<SignatureSigningParameters> resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
        SignatureSigningParameters params = this.resolveSingle(criteria);
        if (params != null) {
            return Collections.singletonList(params);
        }
        return Collections.emptyList();
    }

    @Override
    @Nullable
    public SignatureSigningParameters resolveSingle(@Nonnull CriteriaSet criteria) throws ResolverException {
        Constraint.isNotNull(criteria, "CriteriaSet was null");
        Constraint.isNotNull(criteria.get(SignatureSigningConfigurationCriterion.class), "Resolver requires an instance of SignatureSigningConfigurationCriterion");
        Predicate<String> whitelistBlacklistPredicate = this.getWhitelistBlacklistPredicate(criteria);
        SignatureSigningParameters params = new SignatureSigningParameters();
        this.resolveAndPopulateCredentialAndSignatureAlgorithm(params, criteria, whitelistBlacklistPredicate);
        params.setSignatureReferenceDigestMethod(this.resolveReferenceDigestMethod(criteria, whitelistBlacklistPredicate));
        params.setSignatureCanonicalizationAlgorithm(this.resolveCanonicalizationAlgorithm(criteria));
        if (params.getSigningCredential() != null) {
            params.setKeyInfoGenerator(this.resolveKeyInfoGenerator(criteria, params.getSigningCredential()));
            params.setSignatureHMACOutputLength(this.resolveHMACOutputLength(criteria, params.getSigningCredential(), params.getSignatureAlgorithm()));
        }
        if (this.validate(params)) {
            this.logResult(params);
            return params;
        }
        return null;
    }

    protected void logResult(@Nonnull SignatureSigningParameters params) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Resolved SignatureSigningParameters:");
            Key signingKey = CredentialSupport.extractSigningKey(params.getSigningCredential());
            if (signingKey != null) {
                this.log.debug("\tSigning credential with key algorithm: {}", (Object)signingKey.getAlgorithm());
            } else {
                this.log.debug("\tSigning credential: null");
            }
            this.log.debug("\tSignature algorithm URI: {}", (Object)params.getSignatureAlgorithm());
            this.log.debug("\tSignature KeyInfoGenerator: {}", (Object)(params.getKeyInfoGenerator() != null ? "present" : "null"));
            this.log.debug("\tReference digest method algorithm URI: {}", (Object)params.getSignatureReferenceDigestMethod());
            this.log.debug("\tCanonicalization algorithm URI: {}", (Object)params.getSignatureCanonicalizationAlgorithm());
            this.log.debug("\tHMAC output length: {}", (Object)params.getSignatureHMACOutputLength());
        }
    }

    protected boolean validate(@Nonnull SignatureSigningParameters params) {
        if (params.getSigningCredential() == null) {
            this.log.warn("Validation failure: Unable to resolve signing credential");
            return false;
        }
        if (params.getSignatureAlgorithm() == null) {
            this.log.warn("Validation failure: Unable to resolve signing algorithm URI");
            return false;
        }
        if (params.getSignatureCanonicalizationAlgorithm() == null) {
            this.log.warn("Validation failure: Unable to resolve signing canonicalization algorithm URI");
            return false;
        }
        if (params.getSignatureReferenceDigestMethod() == null) {
            this.log.warn("Validation failure: Unable to resolve reference digest algorithm URI");
            return false;
        }
        return true;
    }

    @Nonnull
    protected Predicate<String> getWhitelistBlacklistPredicate(@Nonnull CriteriaSet criteria) {
        return this.resolveWhitelistBlacklistPredicate(criteria, criteria.get(SignatureSigningConfigurationCriterion.class).getConfigurations());
    }

    protected void resolveAndPopulateCredentialAndSignatureAlgorithm(@Nonnull SignatureSigningParameters params, @Nonnull CriteriaSet criteria, Predicate<String> whitelistBlacklistPredicate) {
        List<Credential> credentials = this.getEffectiveSigningCredentials(criteria);
        List<String> algorithms = this.getEffectiveSignatureAlgorithms(criteria, whitelistBlacklistPredicate);
        this.log.trace("Resolved effective signature algorithms: {}", (Object)algorithms);
        for (Credential credential : credentials) {
            if (this.log.isTraceEnabled()) {
                Key key = CredentialSupport.extractSigningKey(credential);
                this.log.trace("Evaluating credential of type: {}", (Object)(key != null ? key.getAlgorithm() : "n/a"));
            }
            for (String algorithm : algorithms) {
                this.log.trace("Evaluating credential against algorithm: {}", (Object)algorithm);
                if (this.credentialSupportsAlgorithm(credential, algorithm)) {
                    this.log.trace("Credential passed eval against algorithm: {}", (Object)algorithm);
                    params.setSigningCredential(credential);
                    params.setSignatureAlgorithm(algorithm);
                    return;
                }
                this.log.trace("Credential failed eval against algorithm: {}", (Object)algorithm);
            }
        }
    }

    @Nonnull
    protected Predicate<String> getAlgorithmRuntimeSupportedPredicate() {
        return new AlgorithmRuntimeSupportedPredicate(this.getAlgorithmRegistry());
    }

    protected boolean credentialSupportsAlgorithm(@Nonnull Credential credential, @Nonnull @NotEmpty String algorithm) {
        return AlgorithmSupport.credentialSupportsAlgorithmForSigning(credential, this.getAlgorithmRegistry().get(algorithm));
    }

    @Nonnull
    protected List<Credential> getEffectiveSigningCredentials(@Nonnull CriteriaSet criteria) {
        ArrayList<Credential> accumulator = new ArrayList<Credential>();
        for (SignatureSigningConfiguration config : criteria.get(SignatureSigningConfigurationCriterion.class).getConfigurations()) {
            accumulator.addAll(config.getSigningCredentials());
        }
        return accumulator;
    }

    @Nonnull
    protected List<String> getEffectiveSignatureAlgorithms(@Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        ArrayList<String> accumulator = new ArrayList<String>();
        for (SignatureSigningConfiguration config : criteria.get(SignatureSigningConfigurationCriterion.class).getConfigurations()) {
            accumulator.addAll(Collections2.filter(config.getSignatureAlgorithms(), Predicates.and(this.getAlgorithmRuntimeSupportedPredicate(), whitelistBlacklistPredicate)));
        }
        return accumulator;
    }

    @Nullable
    protected String resolveReferenceDigestMethod(@Nonnull CriteriaSet criteria, @Nonnull Predicate<String> whitelistBlacklistPredicate) {
        for (SignatureSigningConfiguration config : criteria.get(SignatureSigningConfigurationCriterion.class).getConfigurations()) {
            for (String digestMethod : config.getSignatureReferenceDigestMethods()) {
                if (!this.getAlgorithmRuntimeSupportedPredicate().apply(digestMethod) || !whitelistBlacklistPredicate.apply(digestMethod)) continue;
                return digestMethod;
            }
        }
        return null;
    }

    @Nullable
    protected String resolveCanonicalizationAlgorithm(@Nonnull CriteriaSet criteria) {
        for (SignatureSigningConfiguration config : criteria.get(SignatureSigningConfigurationCriterion.class).getConfigurations()) {
            if (config.getSignatureCanonicalizationAlgorithm() == null) continue;
            return config.getSignatureCanonicalizationAlgorithm();
        }
        return null;
    }

    @Nullable
    protected KeyInfoGenerator resolveKeyInfoGenerator(@Nonnull CriteriaSet criteria, @Nonnull Credential signingCredential) {
        String name = null;
        if (criteria.get(KeyInfoGenerationProfileCriterion.class) != null) {
            name = criteria.get(KeyInfoGenerationProfileCriterion.class).getName();
        }
        for (SignatureSigningConfiguration config : criteria.get(SignatureSigningConfigurationCriterion.class).getConfigurations()) {
            KeyInfoGenerator kig = this.lookupKeyInfoGenerator(signingCredential, config.getKeyInfoGeneratorManager(), name);
            if (kig == null) continue;
            return kig;
        }
        return null;
    }

    @Nullable
    protected Integer resolveHMACOutputLength(@Nonnull CriteriaSet criteria, @Nonnull Credential signingCredential, @Nonnull @NotEmpty String algorithmURI) {
        if (AlgorithmSupport.isHMAC(algorithmURI)) {
            for (SignatureSigningConfiguration config : criteria.get(SignatureSigningConfigurationCriterion.class).getConfigurations()) {
                if (config.getSignatureHMACOutputLength() == null) continue;
                return config.getSignatureHMACOutputLength();
            }
        }
        return null;
    }
}

