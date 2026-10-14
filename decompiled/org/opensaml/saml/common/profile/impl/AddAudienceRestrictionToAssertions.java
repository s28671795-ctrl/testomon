/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.common.profile.impl;

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
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AudienceRestrictionCondition;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.profile.SAML1ActionSupport;
import org.opensaml.saml.saml2.core.Audience;
import org.opensaml.saml.saml2.core.AudienceRestriction;
import org.opensaml.saml.saml2.core.Conditions;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddAudienceRestrictionToAssertions
extends AbstractConditionalProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddAudienceRestrictionToAssertions.class);
    private boolean addingAudiencesToExistingRestriction = true;
    @Nonnull
    private Function<ProfileRequestContext, SAMLObject> responseLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
    @Nullable
    private Function<ProfileRequestContext, Collection<String>> audienceRestrictionsLookupStrategy;
    @Nullable
    private SAMLObject response;
    @Nullable
    private Collection<String> audiences;

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLObject> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public void setAddingAudiencesToExistingRestriction(boolean addingToExistingRestriction) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.addingAudiencesToExistingRestriction = addingToExistingRestriction;
    }

    public void setAudienceRestrictionsLookupStrategy(@Nonnull Function<ProfileRequestContext, Collection<String>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.audienceRestrictionsLookupStrategy = Constraint.isNotNull(strategy, "Audience restriction lookup strategy cannot be null");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.audienceRestrictionsLookupStrategy == null) {
            throw new ComponentInitializationException("Audience restriction lookup strategy cannot be null");
        }
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.audiences = this.audienceRestrictionsLookupStrategy.apply(profileRequestContext);
        if (this.audiences == null || this.audiences.isEmpty()) {
            this.log.debug("{} No audiences to add, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        this.log.debug("{} Attempting to add an AudienceRestrictionCondition to every Assertion in Response", (Object)this.getLogPrefix());
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
                    this.addAudienceRestriction(profileRequestContext, SAML1ActionSupport.addConditionsToAssertion(this, assertion));
                    this.log.debug("{} Added AudienceRestrictionCondition to Assertion {}", (Object)this.getLogPrefix(), (Object)assertion.getID());
                }
                break block3;
            }
            if (!(this.response instanceof org.opensaml.saml.saml2.core.Response)) break block3;
            for (org.opensaml.saml.saml2.core.Assertion assertion : ((org.opensaml.saml.saml2.core.Response)this.response).getAssertions()) {
                this.addAudienceRestriction(profileRequestContext, SAML2ActionSupport.addConditionsToAssertion(this, assertion));
                this.log.debug("{} Added AudienceRestrictionCondition to Assertion {}", (Object)this.getLogPrefix(), (Object)assertion.getID());
            }
        }
    }

    private void addAudienceRestriction(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull org.opensaml.saml.saml1.core.Conditions conditions) {
        AudienceRestrictionCondition condition = this.getAudienceRestrictionCondition(conditions);
        SAMLObjectBuilder audienceBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(org.opensaml.saml.saml1.core.Audience.DEFAULT_ELEMENT_NAME);
        for (String audienceId : this.audiences) {
            this.log.debug("{} Adding {} as an Audience of the AudienceRestrictionCondition", (Object)this.getLogPrefix(), (Object)audienceId);
            org.opensaml.saml.saml1.core.Audience audience = (org.opensaml.saml.saml1.core.Audience)audienceBuilder.buildObject();
            audience.setUri(audienceId);
            condition.getAudiences().add(audience);
        }
    }

    private void addAudienceRestriction(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Conditions conditions) {
        AudienceRestriction condition = this.getAudienceRestriction(conditions);
        SAMLObjectBuilder audienceBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Audience.DEFAULT_ELEMENT_NAME);
        for (String audienceId : this.audiences) {
            this.log.debug("{} Adding {} as an Audience of the AudienceRestriction", (Object)this.getLogPrefix(), (Object)audienceId);
            Audience audience = (Audience)audienceBuilder.buildObject();
            audience.setAudienceURI(audienceId);
            condition.getAudiences().add(audience);
        }
    }

    @Nonnull
    private AudienceRestrictionCondition getAudienceRestrictionCondition(@Nonnull org.opensaml.saml.saml1.core.Conditions conditions) {
        AudienceRestrictionCondition condition;
        if (!this.addingAudiencesToExistingRestriction || conditions.getAudienceRestrictionConditions().isEmpty()) {
            SAMLObjectBuilder conditionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(AudienceRestrictionCondition.DEFAULT_ELEMENT_NAME);
            this.log.debug("{} Adding new AudienceRestrictionCondition", (Object)this.getLogPrefix());
            condition = (AudienceRestrictionCondition)conditionBuilder.buildObject();
            conditions.getAudienceRestrictionConditions().add(condition);
        } else {
            this.log.debug("{} Conditions already contained an AudienceRestrictionCondition, using it", (Object)this.getLogPrefix());
            condition = conditions.getAudienceRestrictionConditions().get(0);
        }
        return condition;
    }

    @Nonnull
    private AudienceRestriction getAudienceRestriction(@Nonnull Conditions conditions) {
        AudienceRestriction condition;
        if (!this.addingAudiencesToExistingRestriction || conditions.getAudienceRestrictions().isEmpty()) {
            SAMLObjectBuilder conditionBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(AudienceRestriction.DEFAULT_ELEMENT_NAME);
            this.log.debug("{} Adding new AudienceRestriction", (Object)this.getLogPrefix());
            condition = (AudienceRestriction)conditionBuilder.buildObject();
            conditions.getAudienceRestrictions().add(condition);
        } else {
            this.log.debug("{} Conditions already contained an AudienceRestriction, using it", (Object)this.getLogPrefix());
            condition = conditions.getAudienceRestrictions().get(0);
        }
        return condition;
    }
}

