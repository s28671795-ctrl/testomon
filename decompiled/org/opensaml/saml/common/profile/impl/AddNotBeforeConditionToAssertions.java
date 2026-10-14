/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.common.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractConditionalProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.profile.SAML1ActionSupport;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNotBeforeConditionToAssertions
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddNotBeforeConditionToAssertions.class);
    @Nonnull
    private Function<ProfileRequestContext, SAMLObject> responseLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
    @Nullable
    private SAMLObject response;

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLObject> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.log.debug("{} Attempting to add NotBefore condition to every Assertion in outgoing Response", (Object)this.getLogPrefix());
        this.response = this.responseLookupStrategy.apply(profileRequestContext);
        if (this.response == null) {
            this.log.debug("{} No SAML Response located in current profile request context", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        if (this.response instanceof Response) {
            if (((Response)this.response).getAssertions().isEmpty()) {
                this.log.debug("{} No assertions available, nothing to do", (Object)this.getLogPrefix());
                return false;
            }
        } else if (this.response instanceof org.opensaml.saml.saml2.core.Response) {
            if (((org.opensaml.saml.saml2.core.Response)this.response).getAssertions().isEmpty()) {
                this.log.debug("{} No assertions available, nothing to do", (Object)this.getLogPrefix());
                return false;
            }
        } else {
            this.log.debug("{} Message returned by lookup strategy was not a SAML Response", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        return super.doPreExecute(profileRequestContext);
    }

    @Override
    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        block3: {
            block2: {
                if (!(this.response instanceof Response)) break block2;
                for (Assertion assertion : ((Response)this.response).getAssertions()) {
                    this.log.debug("{} Added NotBefore condition to Assertion {}", (Object)this.getLogPrefix(), (Object)assertion.getID());
                    SAML1ActionSupport.addConditionsToAssertion(this, assertion).setNotBefore(((Response)this.response).getIssueInstant());
                }
                break block3;
            }
            if (!(this.response instanceof org.opensaml.saml.saml2.core.Response)) break block3;
            for (org.opensaml.saml.saml2.core.Assertion assertion : ((org.opensaml.saml.saml2.core.Response)this.response).getAssertions()) {
                this.log.debug("{} Added NotBefore condition to Assertion {}", (Object)this.getLogPrefix(), (Object)assertion.getID());
                SAML2ActionSupport.addConditionsToAssertion(this, assertion).setNotBefore(((org.opensaml.saml.saml2.core.Response)this.response).getIssueInstant());
            }
        }
    }
}

