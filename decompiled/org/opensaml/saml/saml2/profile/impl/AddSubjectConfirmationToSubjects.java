/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.net.URI;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.AbstractProfileAction;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
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
    private final SAMLObjectBuilder<SubjectConfirmationData> confirmationDataBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(SubjectConfirmationData.DEFAULT_ELEMENT_NAME);
    private boolean overwriteExisting = true;
    @Nonnull
    private Function<ProfileRequestContext, Response> responseLookupStrategy = Functions.compose(new MessageLookup(Response.class), new OutboundMessageContextLookup());
    @Nullable
    private Function<ProfileRequestContext, String> addressLookupStrategy = new Function<ProfileRequestContext, String>(){

        @Override
        public String apply(ProfileRequestContext input) {
            String address = AddSubjectConfirmationToSubjects.this.getHttpServletRequest() != null ? AddSubjectConfirmationToSubjects.this.getHttpServletRequest().getRemoteAddr() : null;
            AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data Address to {}", (Object)AddSubjectConfirmationToSubjects.this.getLogPrefix(), (Object)(address != null ? address : "(none)"));
            return address;
        }
    };
    @Nullable
    private Function<ProfileRequestContext, String> inResponseToLookupStrategy = new Function<ProfileRequestContext, String>(){

        @Override
        public String apply(ProfileRequestContext input) {
            if (AddSubjectConfirmationToSubjects.this.response != null && AddSubjectConfirmationToSubjects.this.response.getInResponseTo() != null) {
                AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data InResponseTo to {}", (Object)AddSubjectConfirmationToSubjects.this.getLogPrefix(), (Object)AddSubjectConfirmationToSubjects.this.response.getInResponseTo());
                return AddSubjectConfirmationToSubjects.this.response.getInResponseTo();
            }
            AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data InResponseTo to (none)", (Object)AddSubjectConfirmationToSubjects.this.getLogPrefix());
            return null;
        }
    };
    @Nullable
    private Function<ProfileRequestContext, String> recipientLookupStrategy = new Function<ProfileRequestContext, String>(){

        @Override
        public String apply(ProfileRequestContext input) {
            if (input.getOutboundMessageContext() != null) {
                try {
                    URI uri = SAMLBindingSupport.getEndpointURL((MessageContext<SAMLObject>)input.getOutboundMessageContext());
                    if (uri != null) {
                        String url2 = uri.toString();
                        AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data Recipient to {}", (Object)AddSubjectConfirmationToSubjects.this.getLogPrefix(), (Object)url2);
                        return url2;
                    }
                }
                catch (BindingException e) {
                    AddSubjectConfirmationToSubjects.this.log.debug("{} Error getting response endpoint", (Object)AddSubjectConfirmationToSubjects.this.getLogPrefix(), (Object)e);
                }
            }
            AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data Recipient to (none)", (Object)AddSubjectConfirmationToSubjects.this.getLogPrefix());
            return null;
        }
    };
    @Nullable
    private Function<ProfileRequestContext, Long> lifetimeLookupStrategy = new Function<ProfileRequestContext, Long>(){

        @Override
        public Long apply(ProfileRequestContext input) {
            AddSubjectConfirmationToSubjects.this.log.debug("{} Setting confirmation data NotOnOrAfter to 5 minutes from now", (Object)AddSubjectConfirmationToSubjects.this.getLogPrefix());
            return 300000L;
        }
    };
    @NonnullAfterInit
    private String confirmationMethod;
    @Nullable
    private Response response;

    public void setOverwriteExisting(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.overwriteExisting = flag;
    }

    public void setResponseLookupStrategy(@Nonnull Function<ProfileRequestContext, Response> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.responseLookupStrategy = Constraint.isNotNull(strategy, "Response lookup strategy cannot be null");
    }

    public void setAddressLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.addressLookupStrategy = strategy;
    }

    public void setInResponseToLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.inResponseToLookupStrategy = strategy;
    }

    public void setRecipientLookupStrategy(@Nullable Function<ProfileRequestContext, String> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.recipientLookupStrategy = strategy;
    }

    public void setLifetimeLookupStrategy(@Nullable Function<ProfileRequestContext, Long> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.lifetimeLookupStrategy = strategy;
    }

    public void setMethod(@Nonnull @NotEmpty String method2) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.confirmationMethod = Constraint.isNotNull(StringSupport.trimOrNull(method2), "Confirmation method cannot be null or empty");
    }

    @Override
    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.confirmationMethod == null) {
            throw new ComponentInitializationException("Confirmation method cannot be null or empty");
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
        return super.doPreExecute(profileRequestContext);
    }

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        Long lifetime;
        String recipient;
        String inResponseTo;
        String address;
        SubjectConfirmation confirmation = this.confirmationBuilder.buildObject();
        confirmation.setMethod(this.confirmationMethod);
        SubjectConfirmationData confirmationData = null;
        String string = address = this.addressLookupStrategy != null ? this.addressLookupStrategy.apply(profileRequestContext) : null;
        if (address != null) {
            confirmationData = confirmationData != null ? confirmationData : this.confirmationDataBuilder.buildObject();
            confirmationData.setAddress(address);
        }
        String string2 = inResponseTo = this.inResponseToLookupStrategy != null ? this.inResponseToLookupStrategy.apply(profileRequestContext) : null;
        if (inResponseTo != null) {
            confirmationData = confirmationData != null ? confirmationData : this.confirmationDataBuilder.buildObject();
            confirmationData.setInResponseTo(inResponseTo);
        }
        String string3 = recipient = this.recipientLookupStrategy != null ? this.recipientLookupStrategy.apply(profileRequestContext) : null;
        if (recipient != null) {
            confirmationData = confirmationData != null ? confirmationData : this.confirmationDataBuilder.buildObject();
            confirmationData.setRecipient(recipient);
        }
        Long l = lifetime = this.lifetimeLookupStrategy != null ? this.lifetimeLookupStrategy.apply(profileRequestContext) : null;
        if (lifetime != null) {
            confirmationData = confirmationData != null ? confirmationData : this.confirmationDataBuilder.buildObject();
            confirmationData.setNotOnOrAfter(new DateTime().plus(lifetime));
        }
        if (confirmationData != null) {
            confirmation.setSubjectConfirmationData(confirmationData);
        }
        int count = 0;
        for (Assertion assertion : this.response.getAssertions()) {
            Subject subject = this.getAssertionSubject(assertion);
            if (this.overwriteExisting) {
                subject.getSubjectConfirmations().clear();
            }
            subject.getSubjectConfirmations().add(count > 0 ? this.cloneConfirmation(confirmation) : confirmation);
            ++count;
        }
        if (count > 0) {
            this.log.debug("{} Added SubjectConfirmation with method {} to {} assertion(s)", this.getLogPrefix(), this.confirmationMethod, count);
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
    private SubjectConfirmation cloneConfirmation(@Nonnull SubjectConfirmation confirmation) {
        SubjectConfirmation clone = this.confirmationBuilder.buildObject();
        clone.setMethod(confirmation.getMethod());
        SubjectConfirmationData data = confirmation.getSubjectConfirmationData();
        if (data != null) {
            SubjectConfirmationData cloneData = this.confirmationDataBuilder.buildObject();
            cloneData.setAddress(data.getAddress());
            cloneData.setInResponseTo(data.getInResponseTo());
            cloneData.setRecipient(data.getRecipient());
            cloneData.setNotBefore(data.getNotBefore());
            cloneData.setNotOnOrAfter(data.getNotOnOrAfter());
            clone.setSubjectConfirmationData(cloneData);
        }
        return clone;
    }
}

