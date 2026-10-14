/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml1.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicates;
import com.google.common.collect.Collections2;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.ConfirmationMethod;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectConfirmation;
import org.opensaml.saml.saml1.core.SubjectStatement;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddSubjectConfirmationToSubjects
extends AbstractProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddSubjectConfirmationToSubjects.class);
    @Nonnull
    private final SAMLObjectBuilder<Subject> subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private final SAMLObjectBuilder<SubjectConfirmation> confirmationBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(SubjectConfirmation.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private final SAMLObjectBuilder<ConfirmationMethod> confirmationMethodBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(ConfirmationMethod.DEFAULT_ELEMENT_NAME);
    private boolean overwriteExisting = true;
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nonnull
    @NonnullElements
    private Collection<String> confirmationMethods = Collections.emptyList();
    @Nullable
    private Response response;
    private boolean artifactProfile;

    public void setOverwriteExisting(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.overwriteExisting = flag;
    }

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public void setMethods(@Nonnull @NonnullElements Collection<String> methods) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        Constraint.isNotEmpty(methods, "Confirmation method collection cannot be null or empty");
        this.confirmationMethods = new ArrayList<String>(Collections2.filter(methods, Predicates.notNull()));
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.confirmationMethods.isEmpty()) {
            throw new ComponentInitializationException("Confirmation method list cannot be empty");
        }
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.log.debug("{} Attempting to add SubjectConfirmation to assertions in outgoing Response", (Object)this.getLogPrefix());
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
        SAMLBindingContext bindingCtx = (SAMLBindingContext)profileRequestContext.getOutboundMessageContext().getSubcontext(SAMLBindingContext.class, false);
        this.artifactProfile = bindingCtx != null && Objects.equals(bindingCtx.getBindingUri(), "urn:oasis:names:tc:SAML:1.0:profiles:artifact-01");
        return super.doPreExecute(profileRequestContext);
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        SubjectConfirmation confirmation = this.confirmationBuilder.buildObject();
        for (String method2 : this.confirmationMethods) {
            if (this.artifactProfile && "urn:oasis:names:tc:SAML:1.0:cm:bearer".equals(method2)) {
                method2 = "urn:oasis:names:tc:SAML:1.0:cm:artifact";
            }
            ConfirmationMethod newMethod = this.confirmationMethodBuilder.buildObject();
            newMethod.setConfirmationMethod(method2);
            confirmation.getConfirmationMethods().add(newMethod);
        }
        int count = 0;
        for (Assertion assertion : this.response.getAssertions()) {
            for (Statement statement : assertion.getStatements()) {
                if (!(statement instanceof SubjectStatement)) continue;
                Subject subject = this.getStatementSubject((SubjectStatement)statement);
                SubjectConfirmation existing = subject.getSubjectConfirmation();
                if (existing == null || this.overwriteExisting) {
                    subject.setSubjectConfirmation(count > 0 ? this.cloneConfirmation(confirmation) : confirmation);
                }
                ++count;
            }
        }
        if (count > 0) {
            this.log.debug("{} Added SubjectConfirmation with methods {} to {} statement subject(s)", this.getLogPrefix(), this.confirmationMethods, count);
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
    private SubjectConfirmation cloneConfirmation(@Nonnull SubjectConfirmation confirmation) {
        SubjectConfirmation clone = this.confirmationBuilder.buildObject();
        for (ConfirmationMethod method2 : confirmation.getConfirmationMethods()) {
            ConfirmationMethod newMethod = this.confirmationMethodBuilder.buildObject();
            newMethod.setConfirmationMethod(method2.getConfirmationMethod());
            clone.getConfirmationMethods().add(newMethod);
        }
        return clone;
    }
}

