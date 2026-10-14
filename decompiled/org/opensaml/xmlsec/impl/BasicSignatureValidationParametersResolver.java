/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.impl;

import java.util.Collections;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.xmlsec.SignatureValidationConfiguration;
import org.opensaml.xmlsec.SignatureValidationParameters;
import org.opensaml.xmlsec.SignatureValidationParametersResolver;
import org.opensaml.xmlsec.criterion.SignatureValidationConfigurationCriterion;
import org.opensaml.xmlsec.impl.AbstractSecurityParametersResolver;
import org.opensaml.xmlsec.signature.support.SignatureTrustEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicSignatureValidationParametersResolver
extends AbstractSecurityParametersResolver<SignatureValidationParameters>
implements SignatureValidationParametersResolver {
    private Logger log = LoggerFactory.getLogger(BasicSignatureValidationParametersResolver.class);

    @Override
    @Nonnull
    public Iterable<SignatureValidationParameters> resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
        SignatureValidationParameters params = this.resolveSingle(criteria);
        if (params != null) {
            return Collections.singletonList(params);
        }
        return Collections.emptyList();
    }

    @Override
    @Nullable
    public SignatureValidationParameters resolveSingle(@Nonnull CriteriaSet criteria) throws ResolverException {
        Constraint.isNotNull(criteria, "CriteriaSet was null");
        Constraint.isNotNull(criteria.get(SignatureValidationConfigurationCriterion.class), "Resolver requires an instance of SignatureValidationConfigurationCriterion");
        SignatureValidationParameters params = new SignatureValidationParameters();
        this.resolveAndPopulateWhiteAndBlacklists(params, criteria, criteria.get(SignatureValidationConfigurationCriterion.class).getConfigurations());
        params.setSignatureTrustEngine(this.resolveSignatureTrustEngine(criteria));
        this.logResult(params);
        return params;
    }

    protected void logResult(@Nonnull SignatureValidationParameters params) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Resolved SignatureValidationParameters:");
            this.log.debug("\tAlgorithm whitelist: {}", (Object)params.getWhitelistedAlgorithms());
            this.log.debug("\tAlgorithm blacklist: {}", (Object)params.getBlacklistedAlgorithms());
            this.log.debug("\tSignatureTrustEngine: {}", (Object)(params.getSignatureTrustEngine() != null ? "present" : "null"));
        }
    }

    @Nullable
    protected SignatureTrustEngine resolveSignatureTrustEngine(@Nonnull CriteriaSet criteria) {
        for (SignatureValidationConfiguration config : criteria.get(SignatureValidationConfigurationCriterion.class).getConfigurations()) {
            if (config.getSignatureTrustEngine() == null) continue;
            return config.getSignatureTrustEngine();
        }
        return null;
    }
}

