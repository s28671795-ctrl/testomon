/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml1.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.io.IOException;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.messaging.context.navigate.SAMLMessageContextIssuerFunction;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.AssertionArtifact;
import org.opensaml.saml.saml1.core.Request;
import org.opensaml.saml.saml1.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveArtifacts
extends AbstractProfileAction {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(ResolveArtifacts.class);
    @Nonnull
    private Function<ProfileRequestContext, Request> requestLookupStrategy = Functions.compose(new MessageLookup(Request.class), new InboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @NonnullAfterInit
    private Function<ProfileRequestContext, String> issuerLookupStrategy;
    @Nonnull
    private Function<ProfileRequestContext, String> requesterLookupStrategy = Functions.compose(new SAMLMessageContextIssuerFunction(), new InboundMessageContextLookup());
    @NonnullAfterInit
    private SAMLArtifactMap artifactMap;
    @Nullable
    private Request request;
    @Nullable
    private Response response;
    @Nullable
    private String issuerId;
    @Nullable
    private String requesterId;

    public synchronized void setRequestLookupStrategy(@Nonnull Function<ProfileRequestContext, Request> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.requestLookupStrategy = Constraint.isNotNull(strategy, "Request lookup strategy cannot be null");
    }

    public synchronized void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public synchronized void setIssuerLookupStrategy(@Nonnull Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.issuerLookupStrategy = Constraint.isNotNull(strategy, "Issuer lookup strategy cannot be null");
    }

    public synchronized void setRequesterLookupStrategy(@Nonnull Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.requesterLookupStrategy = Constraint.isNotNull(strategy, "Requester lookup strategy cannot be null");
    }

    public synchronized void setArtifactMap(@Nonnull SAMLArtifactMap map) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.artifactMap = Constraint.isNotNull(map, "SAMLArtifactMap cannot be null");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.artifactMap == null) {
            throw new ComponentInitializationException("SAMLArtifactMap cannot be null");
        }
        if (this.issuerLookupStrategy == null) {
            throw new ComponentInitializationException("Issuer lookup strategy cannot be null");
        }
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.request = this.requestLookupStrategy.apply(profileRequestContext);
        if (this.request == null) {
            this.log.debug("{} No request located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        if (this.request.getAssertionArtifacts().isEmpty()) {
            this.log.debug("{} No AssertionArtifact elements found in request, nothing to do", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        this.response = this.responseLookupStrategy.apply(profileRequestContext);
        if (this.response == null) {
            this.log.debug("{} No response located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        this.issuerId = this.issuerLookupStrategy.apply(profileRequestContext);
        if (this.issuerId == null) {
            this.log.debug("{} No issuer identity located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
            return false;
        }
        this.requesterId = this.requesterLookupStrategy.apply(profileRequestContext);
        if (this.requesterId == null) {
            this.log.debug("{} No requester identity located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        return super.doPreExecute(profileRequestContext);
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        boolean success = true;
        try {
            for (AssertionArtifact artifact : this.request.getAssertionArtifacts()) {
                SAMLArtifactMap.SAMLArtifactMapEntry entry = this.artifactMap.get(artifact.getAssertionArtifact());
                if (entry == null) {
                    this.log.warn("{} Unresolvable AssertionArtifact '{}' from relying party '{}'", this.getLogPrefix(), artifact.getAssertionArtifact(), this.requesterId);
                    success = false;
                    break;
                }
                this.artifactMap.remove(artifact.getAssertionArtifact());
                if (!entry.getIssuerId().equals(this.issuerId)) {
                    this.log.warn("{} Artifact issuer mismatch, issued by '{}' but IdP has entityID of '{}'", this.getLogPrefix(), entry.getIssuerId(), this.issuerId);
                    success = false;
                    break;
                }
                if (!entry.getRelyingPartyId().equals(this.requesterId)) {
                    this.log.warn("{} Artifact relying party mismatch, issued to '{}' but requested by '{}'", this.getLogPrefix(), entry.getRelyingPartyId(), this.requesterId);
                    success = false;
                    break;
                }
                if (!(entry.getSamlMessage() instanceof Assertion)) {
                    this.log.warn("{} Artifact '{}' resolved to a non-Assertion object", (Object)this.getLogPrefix(), (Object)artifact.getAssertionArtifact());
                    success = false;
                    break;
                }
                this.response.getAssertions().add((Assertion)entry.getSamlMessage());
            }
        }
        catch (IOException e) {
            this.log.error("{} Error resolving artifact", (Object)this.getLogPrefix(), (Object)e);
            success = false;
        }
        if (!success) {
            this.response.getAssertions().clear();
            for (AssertionArtifact artifact : this.request.getAssertionArtifacts()) {
                try {
                    this.artifactMap.remove(artifact.getAssertionArtifact());
                }
                catch (IOException e) {
                    this.log.error("{} Error removing mapping for artifact '{}'", (Object)this.getLogPrefix(), (Object)artifact.getAssertionArtifact());
                }
            }
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
        }
    }
}

