/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.security.impl;

import com.google.common.base.Function;
import com.google.common.collect.Iterables;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialResolver;
import org.opensaml.security.credential.UsageType;
import org.opensaml.security.criteria.UsageCriterion;
import org.opensaml.xmlsec.EncryptionConfiguration;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.EncryptionParametersResolver;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.criterion.EncryptionConfigurationCriterion;
import org.opensaml.xmlsec.impl.BasicEncryptionConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InlineSelfEncryptionParametersStrategy
implements Function<Pair<ProfileRequestContext, EncryptionParameters>, List<EncryptionParameters>> {
    private Logger log = LoggerFactory.getLogger(InlineSelfEncryptionParametersStrategy.class);
    @Nonnull
    private CredentialResolver credentialResolver;
    @Nonnull
    private EncryptionParametersResolver encParamsresolver;
    @Nullable
    private Function<ProfileRequestContext, List<EncryptionConfiguration>> configurationLookupStrategy;

    public InlineSelfEncryptionParametersStrategy(@Nonnull CredentialResolver credResolver, @Nonnull EncryptionParametersResolver paramsResolver) {
        this(credResolver, paramsResolver, null);
    }

    public InlineSelfEncryptionParametersStrategy(@Nonnull CredentialResolver credResolver, @Nonnull EncryptionParametersResolver paramsResolver, @Nullable Function<ProfileRequestContext, List<EncryptionConfiguration>> configStrategy) {
        this.credentialResolver = Constraint.isNotNull(credResolver, "CredentialResolver was null");
        this.encParamsresolver = Constraint.isNotNull(paramsResolver, "EncryptionParametersResolver was null");
        this.configurationLookupStrategy = configStrategy;
    }

    @Override
    @Nullable
    public List<EncryptionParameters> apply(@Nullable Pair<ProfileRequestContext, EncryptionParameters> input) {
        if (input == null || input.getFirst() == null) {
            this.log.debug("Input Pair or ProfileRequestContext was null, skipping");
            return Collections.emptyList();
        }
        List<Credential> credentials = this.resolveCredentials(input.getFirst());
        if (credentials.isEmpty()) {
            this.log.debug("No self-encryption credentials were resolved, skipping further processing");
            return Collections.emptyList();
        }
        this.log.debug("Resolved {} self-encryption credentials", (Object)credentials.size());
        List<EncryptionConfiguration> baseConfigs = this.resolveBaseConfigurations(input.getFirst());
        this.log.debug("Resolved {} base EncryptionConfigurations", (Object)baseConfigs.size());
        ArrayList<EncryptionParameters> encParams = new ArrayList<EncryptionParameters>();
        for (Credential cred : credentials) {
            BasicEncryptionConfiguration selfConfig = new BasicEncryptionConfiguration();
            selfConfig.setKeyTransportEncryptionCredentials(Collections.singletonList(cred));
            if (input.getSecond() != null && input.getSecond().getDataEncryptionAlgorithm() != null) {
                selfConfig.setDataEncryptionAlgorithms(Collections.singletonList(input.getSecond().getDataEncryptionAlgorithm()));
            }
            ArrayList<EncryptionConfiguration> configs = new ArrayList<EncryptionConfiguration>();
            configs.add(selfConfig);
            configs.addAll(baseConfigs);
            try {
                Iterables.addAll(encParams, this.encParamsresolver.resolve(new CriteriaSet(new EncryptionConfigurationCriterion(configs))));
            }
            catch (ResolverException e) {
                this.log.error("Error resolving self-encryption parameters for Credential '{}', params from other Credentials may still succeed", (Object)cred, (Object)e);
            }
        }
        this.log.debug("Resolved {} self-encryption EncryptionParameters", (Object)encParams.size());
        return encParams;
    }

    @Nonnull
    protected List<Credential> resolveCredentials(@Nonnull ProfileRequestContext profileRequestContext) {
        try {
            ArrayList<Credential> credentials = new ArrayList<Credential>();
            Iterables.addAll(credentials, this.credentialResolver.resolve(new CriteriaSet(new UsageCriterion(UsageType.ENCRYPTION))));
            return credentials;
        }
        catch (ResolverException e) {
            this.log.error("Error resolving IdP encryption credentials", e);
            return Collections.emptyList();
        }
    }

    @Nonnull
    protected List<EncryptionConfiguration> resolveBaseConfigurations(@Nonnull ProfileRequestContext profileRequestContext) {
        List<EncryptionConfiguration> baseConfigs = null;
        if (this.configurationLookupStrategy != null) {
            this.log.debug("Self-encryption EncryptionConfiguration lookup strategy was non-null");
            baseConfigs = this.configurationLookupStrategy.apply(profileRequestContext);
        } else {
            this.log.debug("Self-encryption EncryptionConfiguration lookup strategy was null");
        }
        if (baseConfigs != null) {
            return baseConfigs;
        }
        this.log.debug("No self-encryption EncryptionConfigurations were resolved, returning global configuration");
        return Collections.singletonList(SecurityConfigurationSupport.getGlobalEncryptionConfiguration());
    }
}

