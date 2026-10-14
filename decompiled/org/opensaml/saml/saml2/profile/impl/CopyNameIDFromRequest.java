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
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.SAMLSubjectNameIdentifierContext;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyNameIDFromRequest
extends AbstractProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(CopyNameIDFromRequest.class);
    @Nonnull
    private final SAMLObjectBuilder<Subject> subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private final SAMLObjectBuilder<NameID> nameIdBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameID.DEFAULT_ELEMENT_NAME);
    private boolean overwriteExisting = true;
    @Nonnull
    private Function<ProfileRequestContext, SAMLSubjectNameIdentifierContext> nameIdentifierContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLSubjectNameIdentifierContext.class, true), new InboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nullable
    private NameID nameId;
    @Nullable
    private Response response;

    public void setOverwriteExisting(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.overwriteExisting = flag;
    }

    public void setNameIDContextLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLSubjectNameIdentifierContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.nameIdentifierContextLookupStrategy = Constraint.isNotNull(strategy, "SAMLSubjectNameIdentifierContext lookup strategy cannot be null");
    }

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.log.debug("{} Attempting to add NameID to statements in outgoing Response", (Object)this.getLogPrefix());
        this.response = this.responseLookupStrategy.apply(profileRequestContext);
        if (this.response == null) {
            this.log.debug("{} No SAML response located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        if (this.response.getAssertions().isEmpty()) {
            this.log.debug("{} No assertions in response message, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        SAMLSubjectNameIdentifierContext idCtx = this.nameIdentifierContextLookupStrategy.apply(profileRequestContext);
        if (idCtx == null) {
            this.log.debug("{} No SAMLSubjectNameIdentifierContext located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        this.nameId = idCtx.getSAML2SubjectNameID();
        if (this.nameId == null) {
            this.log.debug("{} No SAMLSubjectNameIdentifierContext located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        return super.doPreExecute(profileRequestContext);
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        int count = 0;
        for (Assertion assertion : this.response.getAssertions()) {
            Subject subject = this.getAssertionSubject(assertion);
            NameID existing = subject.getNameID();
            if (existing != null && !this.overwriteExisting) continue;
            subject.setNameID(this.cloneNameID());
            ++count;
        }
        if (count > 0) {
            this.log.debug("{} Added NameID to {} assertion(s)", (Object)this.getLogPrefix(), (Object)count);
        }
    }

    @Nonnull
    private Subject getAssertionSubject(@Nonnull Assertion assertion) {
        if (assertion.getSubject() != null) {
            return assertion.getSubject();
        }
        Subject subject = this.subjectBuilder.buildObject();
        assertion.setSubject(subject);
        return subject;
    }

    @Nonnull
    private NameID cloneNameID() {
        NameID clone = this.nameIdBuilder.buildObject();
        clone.setFormat(this.nameId.getFormat());
        clone.setNameQualifier(this.nameId.getNameQualifier());
        clone.setSPNameQualifier(this.nameId.getSPNameQualifier());
        clone.setSPProvidedID(this.nameId.getSPProvidedID());
        clone.setValue(this.nameId.getValue());
        return clone;
    }
}

