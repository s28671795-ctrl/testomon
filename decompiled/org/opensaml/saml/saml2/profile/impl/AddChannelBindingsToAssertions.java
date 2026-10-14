/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddChannelBindingsToAssertions
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddChannelBindingsToAssertions.class);
    @Nonnull
    private Function<ProfileRequestContext, ChannelBindingsContext> channelBindingsContextLookupStrategy = Functions.compose(new ChildContextLookup(ChannelBindingsContext.class), new OutboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nullable
    private ChannelBindingsContext channelBindingsContext;
    @Nullable
    private Response response;

    public void setChannelBindingsContextLookupStrategy(@Nonnull Function<ProfileRequestContext, ChannelBindingsContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.channelBindingsContextLookupStrategy = Constraint.isNotNull(strategy, "ChannelBindingsContext lookup strategy cannot be null");
    }

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        if (!super.doPreExecute(profileRequestContext)) {
            return false;
        }
        this.channelBindingsContext = this.channelBindingsContextLookupStrategy.apply(profileRequestContext);
        if (this.channelBindingsContext == null || this.channelBindingsContext.getChannelBindings().isEmpty()) {
            this.log.debug("{} No ChannelBindings to add, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        this.log.debug("{} Attempting to add ChannelBindings to every Assertion in Response", (Object)this.getLogPrefix());
        this.response = this.responseLookupStrategy.apply(profileRequestContext);
        if (this.response == null) {
            this.log.debug("{} No SAML response located in current profile request context", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        if (this.response.getAssertions().isEmpty()) {
            this.log.debug("{} No assertions in response message, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        return true;
    }

    @Override
    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        SAMLObjectBuilder cbBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(ChannelBindings.DEFAULT_ELEMENT_NAME);
        for (Assertion assertion : this.response.getAssertions()) {
            Advice advice = SAML2ActionSupport.addAdviceToAssertion(this, assertion);
            for (ChannelBindings cb : this.channelBindingsContext.getChannelBindings()) {
                ChannelBindings newCB = (ChannelBindings)cbBuilder.buildObject();
                newCB.setType(cb.getType());
                advice.getChildren().add(newCB);
            }
        }
        this.log.debug("{} Added ChannelBindings indicator(s) to Advice", (Object)this.getLogPrefix());
    }
}

