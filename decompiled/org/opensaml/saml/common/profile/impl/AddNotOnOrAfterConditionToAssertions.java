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
import net.shibboleth.utilities.java.support.annotation.Duration;
import net.shibboleth.utilities.java.support.annotation.constraint.NonNegative;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.joda.time.DateTime;
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

public class AddNotOnOrAfterConditionToAssertions
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddNotOnOrAfterConditionToAssertions.class);
    @Nonnull
    private Function<ProfileRequestContext, SAMLObject> responseLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
    @Nullable
    private Function<ProfileRequestContext, Long> assertionLifetimeStrategy;
    @Duration
    @NonNegative
    private long defaultAssertionLifetime = 300000L;
    @Nullable
    private SAMLObject response;

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLObject> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public void setAssertionLifetimeStrategy(@Nullable Function<ProfileRequestContext, Long> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.assertionLifetimeStrategy = strategy;
    }

    @Duration
    public void setDefaultAssertionLifetime(@Duration @NonNegative long lifetime) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.defaultAssertionLifetime = Constraint.isGreaterThanOrEqual(0L, lifetime, "Default assertion lifetime must be greater than or equal to 0");
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.log.debug("{} Attempting to add NotOnOrAfter condition to every Assertion in outgoing Response", (Object)this.getLogPrefix());
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
        block4: {
            Long lifetime;
            block3: {
                Long l = lifetime = this.assertionLifetimeStrategy != null ? this.assertionLifetimeStrategy.apply(profileRequestContext) : null;
                if (lifetime == null) {
                    this.log.debug("{} No assertion lifetime supplied, using default", (Object)this.getLogPrefix());
                }
                if (!(this.response instanceof Response)) break block3;
                for (Assertion assertion : ((Response)this.response).getAssertions()) {
                    DateTime expiration = new DateTime(assertion.getIssueInstant()).plus(lifetime != null ? lifetime : this.defaultAssertionLifetime);
                    this.log.debug("{} Added NotOnOrAfter condition, indicating an expiration of {}, to Assertion {}", this.getLogPrefix(), expiration, assertion.getID());
                    SAML1ActionSupport.addConditionsToAssertion(this, assertion).setNotOnOrAfter(expiration);
                }
                break block4;
            }
            if (!(this.response instanceof org.opensaml.saml.saml2.core.Response)) break block4;
            for (org.opensaml.saml.saml2.core.Assertion assertion : ((org.opensaml.saml.saml2.core.Response)this.response).getAssertions()) {
                DateTime expiration = new DateTime(assertion.getIssueInstant()).plus(lifetime != null ? lifetime : this.defaultAssertionLifetime);
                this.log.debug("{} Added NotOnOrAfter condition, indicating an expiration of {}, to Assertion {}", this.getLogPrefix(), expiration, assertion.getID());
                SAML2ActionSupport.addConditionsToAssertion(this, assertion).setNotOnOrAfter(expiration);
            }
        }
    }
}

