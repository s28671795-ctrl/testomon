/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml1.profile.impl;

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
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CopyNameIdentifierFromRequest
extends AbstractProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(CopyNameIdentifierFromRequest.class);
    @Nonnull
    private final SAMLObjectBuilder<Subject> subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private final SAMLObjectBuilder<NameIdentifier> nameIdentifierBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameIdentifier.DEFAULT_ELEMENT_NAME);
    private boolean overwriteExisting = true;
    @Nonnull
    private Function<ProfileRequestContext, SAMLSubjectNameIdentifierContext> nameIdentifierContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLSubjectNameIdentifierContext.class, true), new InboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nullable
    private NameIdentifier nameIdentifier;
    @Nullable
    private Response response;

    public void setOverwriteExisting(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.overwriteExisting = flag;
    }

    public void setNameIdentifierContextLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLSubjectNameIdentifierContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.nameIdentifierContextLookupStrategy = Constraint.isNotNull(strategy, "SAMLSubjectNameIdentifierContext lookup strategy cannot be null");
    }

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.log.debug("{} Attempting to add NameIdentifier to statements in outgoing Response", (Object)this.getLogPrefix());
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
        this.nameIdentifier = idCtx.getSAML1SubjectNameIdentifier();
        if (this.nameIdentifier == null) {
            this.log.debug("{} No SAMLSubjectNameIdentifierContext located", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidMessageContext");
            return false;
        }
        return super.doPreExecute(profileRequestContext);
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        int count = 0;
        for (Assertion assertion : this.response.getAssertions()) {
            for (Statement statement : assertion.getStatements()) {
                Subject subject;
                NameIdentifier existing;
                if (!(statement instanceof SubjectStatement) || (existing = (subject = this.getStatementSubject((SubjectStatement)statement)).getNameIdentifier()) != null && !this.overwriteExisting) continue;
                subject.setNameIdentifier(this.cloneNameIdentifier());
                ++count;
            }
        }
        if (count > 0) {
            this.log.debug("{} Added NameIdentifier to {} statement subject(s)", (Object)this.getLogPrefix(), (Object)count);
        }
    }

    @Nonnull
    private Subject getStatementSubject(@Nonnull SubjectStatement statement) {
        if (statement.getSubject() != null) {
            return statement.getSubject();
        }
        Subject subject = this.subjectBuilder.buildObject();
        statement.setSubject(subject);
        return subject;
    }

    @Nonnull
    private NameIdentifier cloneNameIdentifier() {
        NameIdentifier clone = this.nameIdentifierBuilder.buildObject();
        clone.setFormat(this.nameIdentifier.getFormat());
        clone.setNameQualifier(this.nameIdentifier.getNameQualifier());
        clone.setValue(this.nameIdentifier.getValue());
        return clone;
    }
}

