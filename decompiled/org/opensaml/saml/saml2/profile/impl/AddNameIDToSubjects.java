/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import com.google.common.base.Predicate;
import com.google.common.base.Strings;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;
import net.shibboleth.utilities.java.support.security.SecureRandomIdentifierGenerationStrategy;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.InboundMessageContextLookup;
import org.opensaml.saml.common.SAMLException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.profile.logic.DefaultNameIDPolicyPredicate;
import org.opensaml.saml.common.profile.logic.MetadataNameIdentifierFormatStrategy;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.profile.SAML2ActionSupport;
import org.opensaml.saml.saml2.profile.SAML2NameIDGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddNameIDToSubjects
extends AbstractProfileAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddNameIDToSubjects.class);
    @Nonnull
    private SAMLObjectBuilder<Subject> subjectBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(Subject.DEFAULT_ELEMENT_NAME);
    @Nonnull
    private SAMLObjectBuilder<NameID> nameIdBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(NameID.DEFAULT_ELEMENT_NAME);
    private boolean overwriteExisting = true;
    @Nonnull
    private Function<ProfileRequestContext, AuthnRequest> requestLookupStrategy = Functions.compose(new MessageLookup(AuthnRequest.class), new InboundMessageContextLookup());
    @Nonnull
    private Function<ProfileRequestContext, List<Assertion>> assertionsLookupStrategy = new AssertionStrategy();
    @Nonnull
    private Function<ProfileRequestContext, IdentifierGenerationStrategy> idGeneratorLookupStrategy = new Function<ProfileRequestContext, IdentifierGenerationStrategy>(){

        @Override
        public IdentifierGenerationStrategy apply(ProfileRequestContext input) {
            return new SecureRandomIdentifierGenerationStrategy();
        }
    };
    @Nullable
    private Function<ProfileRequestContext, String> issuerLookupStrategy;
    @Nonnull
    private Predicate<ProfileRequestContext> nameIDPolicyPredicate = new DefaultNameIDPolicyPredicate();
    @Nonnull
    private Function<ProfileRequestContext, List<String>> formatLookupStrategy;
    @NonnullAfterInit
    private SAML2NameIDGenerator generator;
    @Nonnull
    @NonnullElements
    private List<String> formats;
    @Nullable
    private String requiredFormat;
    @Nullable
    private AuthnRequest request;
    @Nullable
    private List<Assertion> assertions;
    @Nullable
    private IdentifierGenerationStrategy idGenerator;
    @Nullable
    private String issuerId;

    public AddNameIDToSubjects() throws ComponentInitializationException {
        ((DefaultNameIDPolicyPredicate)this.nameIDPolicyPredicate).setRequesterIdLookupStrategy(new RequesterIdFromIssuerFunction());
        ((DefaultNameIDPolicyPredicate)this.nameIDPolicyPredicate).setObjectLookupStrategy(new NameIDPolicyLookupFunction());
        ((DefaultNameIDPolicyPredicate)this.nameIDPolicyPredicate).initialize();
        this.formatLookupStrategy = new MetadataNameIdentifierFormatStrategy();
        this.formats = Collections.emptyList();
    }

    public void setOverwriteExisting(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.overwriteExisting = flag;
    }

    public void setRequestLookupStrategy(@Nonnull Function<ProfileRequestContext, AuthnRequest> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.requestLookupStrategy = Constraint.isNotNull(strategy, "AuthnRequest lookup strategy cannot be null");
    }

    public void setAssertionsLookupStrategy(@Nonnull Function<ProfileRequestContext, List<Assertion>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.assertionsLookupStrategy = Constraint.isNotNull(strategy, "Assertions lookup strategy cannot be null");
    }

    public void setIdentifierGeneratorLookupStrategy(@Nonnull Function<ProfileRequestContext, IdentifierGenerationStrategy> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.idGeneratorLookupStrategy = Constraint.isNotNull(strategy, "IdentifierGenerationStrategy lookup strategy cannot be null");
    }

    public void setIssuerLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.issuerLookupStrategy = strategy;
    }

    public void setNameIDPolicyPredicate(@Nonnull Predicate<ProfileRequestContext> predicate) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.nameIDPolicyPredicate = Constraint.isNotNull(predicate, "NameIDPolicy predicate cannot be null");
    }

    public void setFormatLookupStrategy(@Nonnull Function<ProfileRequestContext, List<String>> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.formatLookupStrategy = Constraint.isNotNull(strategy, "Format lookup strategy cannot be null");
    }

    public void setNameIDGenerator(@Nullable SAML2NameIDGenerator theGenerator) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.generator = Constraint.isNotNull(theGenerator, "SAML2NameIDGenerator cannot be null");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.generator == null) {
            throw new ComponentInitializationException("SAML2NameIDGenerator cannot be null");
        }
    }

    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        if (!super.doPreExecute(profileRequestContext)) {
            return false;
        }
        this.log.debug("{} Attempting to add NameID to outgoing Assertion Subjects", (Object)this.getLogPrefix());
        this.idGenerator = this.idGeneratorLookupStrategy.apply(profileRequestContext);
        if (this.idGenerator == null) {
            this.log.debug("{} No identifier generation strategy", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidProfileContext");
            return false;
        }
        if (this.issuerLookupStrategy != null) {
            this.issuerId = this.issuerLookupStrategy.apply(profileRequestContext);
        }
        this.assertions = this.assertionsLookupStrategy.apply(profileRequestContext);
        if (this.assertions == null || this.assertions.isEmpty()) {
            this.log.debug("{} No assertions returned, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        if (!this.nameIDPolicyPredicate.apply(profileRequestContext)) {
            this.log.debug("{} NameIDPolicy was unacceptable", (Object)this.getLogPrefix());
            ActionSupport.buildEvent(profileRequestContext, "InvalidNameIDPolicy");
            return false;
        }
        this.request = this.requestLookupStrategy.apply(profileRequestContext);
        this.requiredFormat = this.getRequiredFormat(profileRequestContext);
        if (this.requiredFormat != null) {
            this.formats = Collections.singletonList(this.requiredFormat);
            this.log.debug("{} Request specified NameID format: {}", (Object)this.getLogPrefix(), (Object)this.requiredFormat);
        } else {
            this.formats = this.formatLookupStrategy.apply(profileRequestContext);
            if (this.formats == null || this.formats.isEmpty()) {
                this.log.debug("{} No candidate NameID formats, nothing to do", (Object)this.getLogPrefix());
                return false;
            }
            this.log.debug("{} Candidate NameID formats: {}", (Object)this.getLogPrefix(), (Object)this.formats);
        }
        return true;
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        NameID nameId = this.generateNameID(profileRequestContext);
        if (nameId == null) {
            if (this.requiredFormat != null) {
                this.log.warn("{} Request specified use of an unsupportable identifier format: {}", (Object)this.getLogPrefix(), (Object)this.requiredFormat);
                ActionSupport.buildEvent(profileRequestContext, "InvalidNameIDPolicy");
            } else {
                this.log.debug("{} Unable to generate a NameID, leaving empty", (Object)this.getLogPrefix());
            }
            return;
        }
        int count = 0;
        for (Assertion assertion : this.assertions) {
            Subject subject = this.getAssertionSubject(assertion);
            NameID existing = subject.getNameID();
            if (existing == null || this.overwriteExisting) {
                subject.setNameID(count > 0 ? this.cloneNameID(nameId) : nameId);
            }
            ++count;
        }
        if (count > 0) {
            this.log.debug("{} Added NameID to {} assertion subject(s)", (Object)this.getLogPrefix(), (Object)count);
        }
    }

    @Nullable
    private String getRequiredFormat(@Nonnull ProfileRequestContext profileRequestContext) {
        String format;
        NameIDPolicy policy;
        if (!(this.request == null || (policy = this.request.getNameIDPolicy()) == null || Strings.isNullOrEmpty(format = policy.getFormat()) || "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified".equals(format) || "urn:oasis:names:tc:SAML:2.0:nameid-format:encrypted".equals(format))) {
            return format;
        }
        return null;
    }

    @Nullable
    private NameID generateNameID(@Nonnull ProfileRequestContext profileRequestContext) {
        for (String format : this.formats) {
            this.log.debug("{} Trying to generate NameID with Format {}", (Object)this.getLogPrefix(), (Object)format);
            try {
                NameID nameId = this.generator.generate(profileRequestContext, format);
                if (nameId == null) continue;
                this.log.debug("{} Successfully generated NameID with Format {}", (Object)this.getLogPrefix(), (Object)format);
                return nameId;
            }
            catch (SAMLException e) {
                this.log.error("{} Error while generating NameID", (Object)this.getLogPrefix(), (Object)e);
            }
        }
        return null;
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
    private NameID cloneNameID(@Nonnull NameID nameId) {
        NameID clone = this.nameIdBuilder.buildObject();
        clone.setFormat(nameId.getFormat());
        clone.setNameQualifier(nameId.getNameQualifier());
        clone.setSPNameQualifier(nameId.getSPNameQualifier());
        clone.setSPProvidedID(nameId.getSPProvidedID());
        clone.setValue(nameId.getValue());
        return clone;
    }

    public static class RequesterIdFromIssuerFunction
    implements Function<ProfileRequestContext, String> {
        @Nonnull
        private Function<ProfileRequestContext, RequestAbstractType> requestLookupStrategy = Functions.compose(new MessageLookup(RequestAbstractType.class), new InboundMessageContextLookup());

        public void setRequestLookupStrategy(@Nonnull Function<ProfileRequestContext, RequestAbstractType> strategy) {
            this.requestLookupStrategy = Constraint.isNotNull(strategy, "Request lookup strategy cannot be null");
        }

        @Override
        @Nullable
        public String apply(@Nullable ProfileRequestContext profileRequestContext) {
            Issuer issuer;
            RequestAbstractType request = this.requestLookupStrategy.apply(profileRequestContext);
            if (request != null && request.getIssuer() != null && ((issuer = request.getIssuer()).getFormat() == null || "urn:oasis:names:tc:SAML:2.0:nameid-format:entity".equals(issuer.getFormat()))) {
                return issuer.getValue();
            }
            return null;
        }
    }

    public static class NameIDPolicyLookupFunction
    implements Function<ProfileRequestContext, SAMLObject> {
        @Nonnull
        private Function<ProfileRequestContext, AuthnRequest> requestLookupStrategy = Functions.compose(new MessageLookup(AuthnRequest.class), new InboundMessageContextLookup());

        public void setRequestLookupStrategy(@Nonnull Function<ProfileRequestContext, AuthnRequest> strategy) {
            this.requestLookupStrategy = Constraint.isNotNull(strategy, "AuthnRequest lookup strategy cannot be null");
        }

        @Override
        @Nullable
        public SAMLObject apply(@Nullable ProfileRequestContext profileRequestContext) {
            AuthnRequest request = this.requestLookupStrategy.apply(profileRequestContext);
            if (request != null) {
                return request.getNameIDPolicy();
            }
            return null;
        }
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
                    Assertion ret = SAML2ActionSupport.buildAssertion(AddNameIDToSubjects.this, AddNameIDToSubjects.this.idGenerator, AddNameIDToSubjects.this.issuerId);
                    input.getOutboundMessageContext().setMessage((Object)ret);
                    return Collections.singletonList(ret);
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

