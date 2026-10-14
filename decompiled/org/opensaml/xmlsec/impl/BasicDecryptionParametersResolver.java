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
import org.opensaml.xmlsec.DecryptionConfiguration;
import org.opensaml.xmlsec.DecryptionParameters;
import org.opensaml.xmlsec.DecryptionParametersResolver;
import org.opensaml.xmlsec.criterion.DecryptionConfigurationCriterion;
import org.opensaml.xmlsec.encryption.support.EncryptedKeyResolver;
import org.opensaml.xmlsec.impl.AbstractSecurityParametersResolver;
import org.opensaml.xmlsec.keyinfo.KeyInfoCredentialResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicDecryptionParametersResolver
extends AbstractSecurityParametersResolver<DecryptionParameters>
implements DecryptionParametersResolver {
    private Logger log = LoggerFactory.getLogger(BasicDecryptionParametersResolver.class);

    @Override
    @Nonnull
    public Iterable<DecryptionParameters> resolve(@Nonnull CriteriaSet criteria) throws ResolverException {
        DecryptionParameters params = this.resolveSingle(criteria);
        if (params != null) {
            return Collections.singletonList(params);
        }
        return Collections.emptyList();
    }

    @Override
    @Nullable
    public DecryptionParameters resolveSingle(@Nonnull CriteriaSet criteria) throws ResolverException {
        Constraint.isNotNull(criteria, "CriteriaSet was null");
        Constraint.isNotNull(criteria.get(DecryptionConfigurationCriterion.class), "Resolver requires an instance of DecryptionConfigurationCriterion");
        DecryptionParameters params = new DecryptionParameters();
        this.resolveAndPopulateWhiteAndBlacklists(params, criteria, criteria.get(DecryptionConfigurationCriterion.class).getConfigurations());
        params.setDataKeyInfoCredentialResolver(this.resolveDataKeyInfoCredentialResolver(criteria));
        params.setKEKKeyInfoCredentialResolver(this.resolveKEKKeyInfoCredentialResolver(criteria));
        params.setEncryptedKeyResolver(this.resolveEncryptedKeyResolver(criteria));
        this.logResult(params);
        return params;
    }

    protected void logResult(@Nonnull DecryptionParameters params) {
        if (this.log.isDebugEnabled()) {
            this.log.debug("Resolved DecryptionParameters:");
            this.log.debug("\tAlgorithm whitelist: {}", (Object)params.getWhitelistedAlgorithms());
            this.log.debug("\tAlgorithm blacklist: {}", (Object)params.getBlacklistedAlgorithms());
            this.log.debug("\tData KeyInfoCredentialResolver: {}", (Object)(params.getDataKeyInfoCredentialResolver() != null ? "present" : "null"));
            this.log.debug("\tKEK KeyInfoCredentialResolver: {}", (Object)(params.getKEKKeyInfoCredentialResolver() != null ? "present" : "null"));
            this.log.debug("\tEncryptedKeyResolver: {}", (Object)(params.getEncryptedKeyResolver() != null ? "present" : "null"));
        }
    }

    @Nullable
    protected EncryptedKeyResolver resolveEncryptedKeyResolver(@Nonnull CriteriaSet criteria) {
        for (DecryptionConfiguration config : criteria.get(DecryptionConfigurationCriterion.class).getConfigurations()) {
            if (config.getEncryptedKeyResolver() == null) continue;
            return config.getEncryptedKeyResolver();
        }
        return null;
    }

    @Nullable
    protected KeyInfoCredentialResolver resolveKEKKeyInfoCredentialResolver(@Nonnull CriteriaSet criteria) {
        for (DecryptionConfiguration config : criteria.get(DecryptionConfigurationCriterion.class).getConfigurations()) {
            if (config.getKEKKeyInfoCredentialResolver() == null) continue;
            return config.getKEKKeyInfoCredentialResolver();
        }
        return null;
    }

    @Nullable
    protected KeyInfoCredentialResolver resolveDataKeyInfoCredentialResolver(@Nonnull CriteriaSet criteria) {
        for (DecryptionConfiguration config : criteria.get(DecryptionConfigurationCriterion.class).getConfigurations()) {
            if (config.getDataKeyInfoCredentialResolver() == null) continue;
            return config.getDataKeyInfoCredentialResolver();
        }
        return null;
    }
}

