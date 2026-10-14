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
import net.shibboleth.utilities.java.support.codec.Base64Support;
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
import org.opensaml.saml.common.messaging.context.ECPContext;
import org.opensaml.saml.ext.samlec.GeneratedKey;
import org.opensaml.saml.saml2.core.Advice;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddGeneratedKeyToAssertions
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddGeneratedKeyToAssertions.class);
    @Nonnull
    private Function<ProfileRequestContext, ECPContext> ecpContextLookupStrategy = Functions.compose(new ChildContextLookup(ECPContext.class), new OutboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nullable
    private ECPContext ecpContext;
    @Nullable
    private Response response;

    public void setECPContextLookupStrategy(@Nonnull Function<ProfileRequestContext, ECPContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.ecpContextLookupStrategy = Constraint.isNotNull(strategy, "ECPContext lookup strategy cannot be null");
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
        this.ecpContext = this.ecpContextLookupStrategy.apply(profileRequestContext);
        if (this.ecpContext == null || this.ecpContext.getSessionKey() == null) {
            this.log.debug("{} No session key to add, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        this.log.debug("{} Attempting to add GeneratedKey to every Assertion in Response", (Object)this.getLogPrefix());
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
        SAMLObjectBuilder keyBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(GeneratedKey.DEFAULT_ELEMENT_NAME);
        String key = Base64Support.encode(this.ecpContext.getSessionKey(), false);
        for (Assertion assertion : this.response.getAssertions()) {
            Advice advice = SAML2ActionSupport.addAdviceToAssertion(this, assertion);
            GeneratedKey gk = (GeneratedKey)keyBuilder.buildObject();
            gk.setValue(key);
            advice.getChildren().add(gk);
        }
        this.log.debug("{} Added GeneratedKey to Advice", (Object)this.getLogPrefix());
    }
}

