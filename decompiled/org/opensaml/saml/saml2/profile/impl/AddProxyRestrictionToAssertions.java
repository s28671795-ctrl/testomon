/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Collection;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.core.ProxyRestriction;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddProxyRestrictionToAssertions
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddProxyRestrictionToAssertions.class);
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nullable
    private Function<ProfileRequestContext, Collection<String>> proxyAudiencesLookupStrategy;
    @Nullable
    private Function<ProfileRequestContext, Long> proxyCountLookupStrategy;
    @Nullable
    private Response response;
    @Nullable
    private Collection<String> audiences;

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public void setProxyAudiencesLookupStrategy(@Nonnull Function<ProfileRequestContext, Collection<String>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.proxyAudiencesLookupStrategy = Constraint.isNotNull(strategy, "Proxy restriction audiences lookup strategy cannot be null");
    }

    public void setProxyCountLookupStrategy(@Nonnull Function<ProfileRequestContext, Long> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.proxyCountLookupStrategy = Constraint.isNotNull(strategy, "Proxy count lookup strategy cannot be null");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.proxyAudiencesLookupStrategy == null) {
            throw new ComponentInitializationException("Proxy restriction audience lookup strategy cannot be null");
        }
        if (this.proxyCountLookupStrategy == null) {
            throw new ComponentInitializationException("Proxy count lookup strategy cannot be null");
        }
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.audiences = this.proxyAudiencesLookupStrategy.apply(profileRequestContext);
        if (this.audiences == null || this.audiences.isEmpty()) {
            this.log.debug("{} No audiences to add, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        this.log.debug("{} Attempting to add an ProxyRestriction to every Assertion in Response", (Object)this.getLogPrefix());
        this.response = this.responseLookupStrategy.apply(profileRequestContext);
        if (this.response == null) {
            this.log.debug("{} No response located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        if (this.response.getAssertions().isEmpty()) {
            this.log.debug("{} No assertions found in response, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        return super.doPreExecute(profileRequestContext);
    }

    @Override
    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        for (Assertion assertion : this.response.getAssertions()) {
            this.addProxyRestriction(profileRequestContext, SAML2ActionSupport.addConditionsToAssertion(this, assertion));
            this.log.debug("{} Added ProxyRestriction to Assertion {}", (Object)this.getLogPrefix(), (Object)assertion.getID());
        }
    }

    private void addProxyRestriction(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Conditions conditions) {
        ProxyRestriction condition = this.getProxyRestriction(conditions);
        SAMLObjectBuilder audienceBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Audience.DEFAULT_ELEMENT_NAME);
        for (String audienceId : this.audiences) {
            this.log.debug("{} Adding {} as an Audience of the ProxyRestriction", (Object)this.getLogPrefix(), (Object)audienceId);
            Audience audience = (Audience)audienceBuilder.buildObject();
            audience.setAudienceURI(audienceId);
            condition.getAudiences().add(audience);
        }
        Long count = this.proxyCountLookupStrategy.apply(profileRequestContext);
        condition.setProxyCount(count != null ? count.intValue() : 0);
    }

    @Nonnull
    private ProxyRestriction getProxyRestriction(@Nonnull Conditions conditions) {
        ProxyRestriction condition;
        if (conditions.getProxyRestriction() == null) {
            SAMLObjectBuilder conditionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(ProxyRestriction.DEFAULT_ELEMENT_NAME);
            this.log.debug("{} Adding new ProxyRestriction", (Object)this.getLogPrefix());
            condition = (ProxyRestriction)conditionBuilder.buildObject();
            conditions.getConditions().add(condition);
        } else {
            this.log.debug("{} Conditions already contained an ProxyRestriction, using it", (Object)this.getLogPrefix());
            condition = conditions.getProxyRestriction();
        }
        return condition;
    }
}

