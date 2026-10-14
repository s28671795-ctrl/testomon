/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.profile.impl;

import com.google.common.base.Function;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.profile.logic.MetadataNameIdentifierFormatStrategy;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Statement;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectStatement;
import org.opensaml.saml.saml1.profile.SAML1NameIdentifierGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNameIdentifierToSubjects
extends AbstractProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddNameIdentifierToSubjects.class);
    @Nonnull
    private final SAMLObjectBuilder<Subject> subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private final SAMLObjectBuilder<NameIdentifier> nameIdentifierBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameIdentifier.DEFAULT_ELEMENT_NAME);
    private boolean overwriteExisting = true;
    @Nonnull
    private Function<ProfileRequestContext, List<Assertion>> assertionsLookupStrategy = new AssertionStrategy();
    @Nonnull
    private Function<ProfileRequestContext, List<String>> formatLookupStrategy = new MetadataNameIdentifierFormatStrategy();
    @NonnullAfterInit
    private SAML1NameIdentifierGenerator generator;
    @Nonnull
    @NonnullElements
    private List<String> formats = Collections.emptyList();
    @Nonnull
    @NonnullElements
    private List<Assertion> assertions;

    public void setOverwriteExisting(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.overwriteExisting = flag;
    }

    public void setAssertionsLookupStrategy(@Nonnull Function<ProfileRequestContext, List<Assertion>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.assertionsLookupStrategy = Constraint.isNotNull(strategy, "Assertions lookup strategy cannot be null");
    }

    public void setFormatLookupStrategy(@Nonnull Function<ProfileRequestContext, List<String>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.formatLookupStrategy = Constraint.isNotNull(strategy, "Format lookup strategy cannot be null");
    }

    public void setNameIdentifierGenerator(@Nonnull SAML1NameIdentifierGenerator theGenerator) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.generator = Constraint.isNotNull(theGenerator, "SAML1NameIdentifierGenerator cannot be null");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.generator == null) {
            throw new ComponentInitializationException("SAML1NameIdentifierGenerator cannot be null");
        }
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        if (!super.doPreExecute(profileRequestContext)) {
            return false;
        }
        this.log.debug("{} Attempting to add NameIdentifier to statements in outgoing Assertions", (Object)this.getLogPrefix());
        this.assertions = this.assertionsLookupStrategy.apply(profileRequestContext);
        if (this.assertions == null || this.assertions.isEmpty()) {
            this.log.debug("{} No assertions returned, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        this.formats = this.formatLookupStrategy.apply(profileRequestContext);
        if (this.formats == null || this.formats.isEmpty()) {
            this.log.debug("{} No candidate NameIdentifier formats, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        this.log.debug("{} Candidate NameIdentifier formats: {}", (Object)this.getLogPrefix(), (Object)this.formats);
        return true;
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        NameIdentifier nameIdentifier = this.generateNameIdentifier(profileRequestContext);
        if (nameIdentifier == null) {
            this.log.debug("{} Unable to generate a NameIdentifier, leaving empty", (Object)this.getLogPrefix());
            return;
        }
        int count = 0;
        for (Assertion assertion : this.assertions) {
            for (Statement statement : assertion.getStatements()) {
                Subject subject;
                NameIdentifier existing;
                if (!(statement instanceof SubjectStatement) || (existing = (subject = this.getStatementSubject((SubjectStatement)statement)).getNameIdentifier()) != null && !this.overwriteExisting) continue;
                subject.setNameIdentifier(count > 0 ? this.cloneNameIdentifier(nameIdentifier) : nameIdentifier);
                ++count;
            }
        }
        if (count > 0) {
            this.log.debug("{} Added NameIdentifier to {} statement subject(s)", (Object)this.getLogPrefix(), (Object)count);
        }
    }

    @Nullable
    private NameIdentifier generateNameIdentifier(@Nonnull ProfileRequestContext profileRequestContext) {
        for (String format : this.formats) {
            this.log.debug("{} Trying to generate NameIdentifier with Format {}", (Object)this.getLogPrefix(), (Object)format);
            try {
                NameIdentifier nameIdentifier = this.generator.generate(profileRequestContext, format);
                if (nameIdentifier == null) continue;
                this.log.debug("{} Successfully generated NameIdentifier with Format {}", (Object)this.getLogPrefix(), (Object)format);
                return nameIdentifier;
            }
            catch (SAMLException e) {
                this.log.error("{} Error while generating NameIdentifier", (Object)this.getLogPrefix(), (Object)e);
            }
        }
        return null;
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
    private NameIdentifier cloneNameIdentifier(@Nonnull NameIdentifier nameIdentifier) {
        NameIdentifier clone = this.nameIdentifierBuilder.buildObject();
        clone.setFormat(nameIdentifier.getFormat());
        clone.setNameQualifier(nameIdentifier.getNameQualifier());
        clone.setValue(nameIdentifier.getValue());
        return clone;
    }

    private class AssertionStrategy
    implements Function<ProfileRequestContext, List<Assertion>> {
        private AssertionStrategy() {
        }

        @Override
        @Nullable
        public List<Assertion> apply(@Nullable ProfileRequestContext input) {
            if (input != null && input.getOutboundMessageContext() != null) {
                Object outboundMessage = input.getOutboundMessageContext().getMessage();
                if (outboundMessage == null) {
                    return null;
                }
                if (outboundMessage instanceof Assertion) {
                    return Collections.singletonList((Assertion)outboundMessage);
                }
                if (outboundMessage instanceof Response) {
                    return ((Response)outboundMessage).getAssertions();
                }
            }
            return null;
        }
    }
}

