/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml1.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Conditions;
import org.opensaml.saml.saml1.core.DoNotCacheCondition;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.profile.SAML1ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddDoNotCacheConditionToAssertions
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddDoNotCacheConditionToAssertions.class);
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nullable
    private Response response;

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.log.debug("{} Attempting to add DoNotCache condition to every Assertion in Response", (Object)this.getLogPrefix());
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
        return super.doPreExecute(profileRequestContext);
    }

    @Override
    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        SAMLObjectBuilder dncConditionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(DoNotCacheCondition.DEFAULT_ELEMENT_NAME);
        for (Assertion assertion : this.response.getAssertions()) {
            Conditions conditions = SAML1ActionSupport.addConditionsToAssertion(this, assertion);
            List<DoNotCacheCondition> dncConditions = conditions.getDoNotCacheConditions();
            if (dncConditions.isEmpty()) {
                dncConditions.add((DoNotCacheCondition)dncConditionBuilder.buildObject());
                this.log.debug("{} Added DoNotCache condition to Assertion {}", (Object)this.getLogPrefix(), (Object)assertion.getID());
                continue;
            }
            this.log.debug("{} Assertion {} already contained DoNotCache condition, another was not added", (Object)this.getLogPrefix(), (Object)assertion.getID());
        }
    }
}

