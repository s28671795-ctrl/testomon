/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 */
package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.resolver.CriteriaSet;
import net.shibboleth.utilities.java.support.resolver.ResolverException;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.criterion.RoleDescriptorCriterion;
import org.opensaml.xmlsec.SecurityConfigurationSupport;
import org.opensaml.xmlsec.SignatureSigningConfiguration;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.SignatureSigningParametersResolver;
import org.opensaml.xmlsec.context.SecurityParametersContext;
import org.opensaml.xmlsec.criterion.SignatureSigningConfigurationCriterion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PopulateSignatureSigningParameters
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(PopulateSignatureSigningParameters.class);
    @Nonnull
    private Function<ProfileRequestContext, SecurityParametersContext> securityParametersContextLookupStrategy = Functions.compose(new ChildContextLookup(SecurityParametersContext.class, true), new OutboundMessageContextLookup());
    @Nullable
    private Function<ProfileRequestContext, SecurityParametersContext> existingParametersContextLookupStrategy;
    @NonnullAfterInit
    private Function<ProfileRequestContext, List<SignatureSigningConfiguration>> configurationLookupStrategy;
    @Nullable
    private Function<ProfileRequestContext, SAMLMetadataContext> metadataContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLMetadataContext.class), Functions.compose(new ChildContextLookup(SAMLPeerEntityContext.class), new OutboundMessageContextLookup()));
    @NonnullAfterInit
    private SignatureSigningParametersResolver resolver;

    public void setSecurityParametersContextLookupStrategy(@Nonnull Function<ProfileRequestContext, SecurityParametersContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.securityParametersContextLookupStrategy = Constraint.isNotNull(strategy, "SecurityParametersContext lookup strategy cannot be null");
    }

    public void setExistingParametersContextLookupStrategy(@Nullable Function<ProfileRequestContext, SecurityParametersContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.existingParametersContextLookupStrategy = strategy;
    }

    public void setMetadataContextLookupStrategy(@Nullable Function<ProfileRequestContext, SAMLMetadataContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.metadataContextLookupStrategy = strategy;
    }

    public void setConfigurationLookupStrategy(@Nonnull Function<ProfileRequestContext, List<SignatureSigningConfiguration>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.configurationLookupStrategy = Constraint.isNotNull(strategy, "SignatureSigningConfiguration lookup strategy cannot be null");
    }

    public void setSignatureSigningParametersResolver(@Nonnull SignatureSigningParametersResolver newResolver) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.resolver = Constraint.isNotNull(newResolver, "SignatureSigningParametersResolver cannot be null");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.resolver == null) {
            throw new ComponentInitializationException("SignatureSigningParametersResolver cannot be null");
        }
        if (this.configurationLookupStrategy == null) {
            this.configurationLookupStrategy = new Function<ProfileRequestContext, List<SignatureSigningConfiguration>>(){

                @Override
                public List<SignatureSigningConfiguration> apply(ProfileRequestContext input) {
                    return Collections.singletonList(SecurityConfigurationSupport.getGlobalSignatureSigningConfiguration());
                }
            };
        }
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        if (super.doPreExecute(profileRequestContext)) {
            this.log.debug("{} Signing enabled", (Object)this.getLogPrefix());
            return true;
        }
        this.log.debug("{} Signing not enabled", (Object)this.getLogPrefix());
        return false;
    }

    @Override
    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        SAMLMetadataContext metadataCtx;
        SecurityParametersContext existingCtx;
        this.log.debug("{} Resolving SignatureSigningParameters for request", (Object)this.getLogPrefix());
        SecurityParametersContext paramsCtx = this.securityParametersContextLookupStrategy.apply(profileRequestContext);
        if (paramsCtx == null) {
            this.log.debug("{} No SecurityParametersContext returned by lookup strategy", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
            return;
        }
        if (this.existingParametersContextLookupStrategy != null && (existingCtx = this.existingParametersContextLookupStrategy.apply(profileRequestContext)) != null && existingCtx.getSignatureSigningParameters() != null) {
            this.log.debug("{} Found existing SecurityParametersContext to copy from", (Object)this.getLogPrefix());
            paramsCtx.setSignatureSigningParameters(existingCtx.getSignatureSigningParameters());
            return;
        }
        List<SignatureSigningConfiguration> configs = this.configurationLookupStrategy.apply(profileRequestContext);
        if (configs == null || configs.isEmpty()) {
            this.log.error("{} No SignatureSigningConfiguration returned by lookup strategy", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidSecurityConfiguration");
            return;
        }
        CriteriaSet criteria = new CriteriaSet(new SignatureSigningConfigurationCriterion(configs));
        if (this.metadataContextLookupStrategy != null && (metadataCtx = this.metadataContextLookupStrategy.apply(profileRequestContext)) != null && metadataCtx.getRoleDescriptor() != null) {
            this.log.debug("{} Adding metadata to resolution criteria for signing/digest algorithms", (Object)this.getLogPrefix());
            criteria.add(new RoleDescriptorCriterion(metadataCtx.getRoleDescriptor()));
        }
        try {
            SignatureSigningParameters params = (SignatureSigningParameters)this.resolver.resolveSingle(criteria);
            paramsCtx.setSignatureSigningParameters(params);
            this.log.debug("{} {} SignatureSigningParameters", (Object)this.getLogPrefix(), (Object)(params != null ? "Resolved" : "Failed to resolve"));
        }
        catch (ResolverException e) {
            this.log.error("{} Error resolving SignatureSigningParameters", (Object)this.getLogPrefix(), (Object)e);
            ActionSupport.buildEvent(profileRequestContext, "InvalidSecurityConfiguration");
        }
    }
}

