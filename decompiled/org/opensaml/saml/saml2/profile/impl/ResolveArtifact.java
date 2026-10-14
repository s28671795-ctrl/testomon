/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile.impl;

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
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolveArtifact
extends AbstractProfileAction {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(ResolveArtifact.class);
    @Nonnull
    private Function<ProfileRequestContext, ArtifactResolve> requestLookupStrategy = Functions.compose(new MessageLookup(ArtifactResolve.class), new InboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, ArtifactResponse> responseLookupStrategy = Functions.compose(new MessageLookup(ArtifactResponse.class), new OutboundMessageContextLookup());
    @NonnullAfterInit
    private Function<ProfileRequestContext, String> issuerLookupStrategy;
    @Nonnull
    private Function<ProfileRequestContext, String> requesterLookupStrategy = Functions.compose(new SAMLMessageContextIssuerFunction(), new InboundMessageContextLookup());
    @NonnullAfterInit
    private SAMLArtifactMap artifactMap;
    @Nullable
    private ArtifactResolve request;
    @Nullable
    private ArtifactResponse response;
    @Nullable
    private String issuerId;
    @Nullable
    private String requesterId;

    public void setRequestLookupStrategy(@Nonnull Function<ProfileRequestContext, ArtifactResolve> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.requestLookupStrategy = Constraint.isNotNull(strategy, "Request lookup strategy cannot be null");
    }

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, ArtifactResponse> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public void setIssuerLookupStrategy(@Nonnull Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.issuerLookupStrategy = Constraint.isNotNull(strategy, "Issuer lookup strategy cannot be null");
    }

    public void setRequesterLookupStrategy(@Nonnull Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.requesterLookupStrategy = Constraint.isNotNull(strategy, "Requester lookup strategy cannot be null");
    }

    public void setArtifactMap(@Nonnull SAMLArtifactMap map) {
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
        if (this.request.getArtifact() == null || this.request.getArtifact().getArtifact() == null) {
            this.log.debug("{} No Artifact element found in request, nothing to do", (Object)this.getLogPrefix());
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
        String artifact = this.request.getArtifact().getArtifact();
        SAMLArtifactMap.SAMLArtifactMapEntry entry = null;
        try {
            entry = this.artifactMap.get(artifact);
        }
        catch (IOException e) {
            this.log.error("{} Error resolving artifact", (Object)this.getLogPrefix(), (Object)e);
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
            return;
        }
        if (entry == null) {
            this.log.warn("{} Unresolvable Artifact '{}' from relying party '{}'", this.getLogPrefix(), artifact, this.requesterId);
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
            return;
        }
        try {
            this.artifactMap.remove(artifact);
        }
        catch (IOException e) {
            this.log.error("{} Error removing artifact from map", (Object)this.getLogPrefix(), (Object)e);
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
            return;
        }
        if (!entry.getIssuerId().equals(this.issuerId)) {
            this.log.warn("{} Artifact issuer mismatch, issued by '{}' but IdP has entityID of '{}'", this.getLogPrefix(), entry.getIssuerId(), this.issuerId);
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
        } else if (!entry.getRelyingPartyId().equals(this.requesterId)) {
            this.log.warn("{} Artifact relying party mismatch, issued to '{}' but requested by '{}'", this.getLogPrefix(), entry.getRelyingPartyId(), this.requesterId);
            ActionSupport.buildEvent(profileRequestContext, "UnableToResolveArtifact");
        } else {
            this.response.setMessage(entry.getSamlMessage());
        }
    }
}

