/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.navigate.MessageLookup
 */
package org.opensaml.saml.saml2.profile.impl;

import com.google.common.base.Function;
import com.google.common.base.Functions;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullElements;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.messaging.context.navigate.MessageLookup;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.profile.context.navigate.OutboundMessageContextLookup;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.EncryptedElementType;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml.saml2.core.NameIDMappingResponse;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectQuery;
import org.opensaml.saml.saml2.profile.context.EncryptionContext;
import org.opensaml.saml.saml2.profile.impl.AbstractEncryptAction;
import org.opensaml.xmlsec.EncryptionParameters;
import org.opensaml.xmlsec.encryption.support.EncryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class EncryptNameIDs
extends AbstractEncryptAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(EncryptNameIDs.class);
    @Nonnull
    private Function<ProfileRequestContext, SAMLObject> messageLookupStrategy = Functions.compose(new MessageLookup(SAMLObject.class), new OutboundMessageContextLookup());
    @Nonnull
    @NonnullElements
    private Set<String> excludedFormats = Collections.singleton("urn:oasis:names:tc:SAML:2.0:nameid-format:entity");
    @Nullable
    private SAMLObject message;

    public void setMessageLookupStrategy(@Nonnull Function<ProfileRequestContext, SAMLObject> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException(this);
        this.messageLookupStrategy = Constraint.isNotNull(strategy, "Message lookup strategy cannot be null");
    }

    public void setExcludedFormats(@Nonnull @NonnullElements Collection<String> formats) {
        this.excludedFormats = new HashSet<String>(StringSupport.normalizeStringCollection(formats));
    }

    @Override
    @Nullable
    protected EncryptionParameters getApplicableParameters(@Nullable EncryptionContext ctx) {
        if (ctx != null) {
            return ctx.getIdentifierEncryptionParameters();
        }
        return null;
    }

    @Override
    protected boolean doPreExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        this.message = this.messageLookupStrategy.apply(profileRequestContext);
        if (this.message != null && this.message instanceof ArtifactResponse) {
            this.message = ((ArtifactResponse)this.message).getMessage();
        }
        if (this.message == null) {
            this.log.debug("{} Message was not present, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        return super.doPreExecute(profileRequestContext);
    }

    @Override
    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        block11: {
            try {
                if (this.message instanceof AuthnRequest) {
                    this.processSubject(((AuthnRequest)this.message).getSubject());
                    break block11;
                }
                if (this.message instanceof SubjectQuery) {
                    this.processSubject(((SubjectQuery)this.message).getSubject());
                    break block11;
                }
                if (this.message instanceof Response) {
                    for (Assertion a : ((Response)this.message).getAssertions()) {
                        this.processAssertion(a);
                    }
                    break block11;
                }
                if (this.message instanceof LogoutRequest) {
                    this.processLogoutRequest((LogoutRequest)this.message);
                    break block11;
                }
                if (this.message instanceof ManageNameIDRequest) {
                    this.processManageNameIDRequest((ManageNameIDRequest)this.message);
                    break block11;
                }
                if (this.message instanceof NameIDMappingRequest) {
                    this.processNameIDMappingRequest((NameIDMappingRequest)this.message);
                    break block11;
                }
                if (this.message instanceof NameIDMappingResponse) {
                    this.processNameIDMappingResponse((NameIDMappingResponse)this.message);
                    break block11;
                }
                if (this.message instanceof Assertion) {
                    this.processAssertion((Assertion)this.message);
                    break block11;
                }
                this.log.debug("{} Message was of unrecognized type {}, nothing to do", (Object)this.getLogPrefix(), (Object)this.message.getClass().getName());
                return;
            }
            catch (EncryptionException e) {
                this.log.warn("{} Error encrypting NameID", (Object)this.getLogPrefix(), (Object)e);
                ActionSupport.buildEvent(profileRequestContext, "UnableToEncrypt");
            }
        }
    }

    private boolean shouldEncrypt(@Nullable NameID name) {
        if (name != null) {
            String format = name.getFormat();
            if (format == null) {
                format = "urn:oasis:names:tc:SAML:1.1:nameid-format:unspecified";
            }
            if (!this.excludedFormats.contains(format)) {
                if (this.log.isDebugEnabled()) {
                    try {
                        Element dom = XMLObjectSupport.marshall(name);
                        this.log.debug("{} NameID before encryption:\n{}", (Object)this.getLogPrefix(), (Object)SerializeSupport.prettyPrintXML(dom));
                    }
                    catch (MarshallingException e) {
                        this.log.error("{} Unable to marshall NameID for logging purposes", (Object)this.getLogPrefix(), (Object)e);
                    }
                }
                return true;
            }
        }
        return false;
    }

    private void processSubject(@Nullable Subject subject) throws EncryptionException {
        if (subject != null) {
            if (this.shouldEncrypt(subject.getNameID())) {
                this.log.debug("{} Encrypt NameID in Subject", (Object)this.getLogPrefix());
                EncryptedID encrypted = this.getEncrypter().encrypt(subject.getNameID());
                subject.setEncryptedID(encrypted);
                subject.setNameID(null);
            }
            for (SubjectConfirmation sc : subject.getSubjectConfirmations()) {
                if (!this.shouldEncrypt(sc.getNameID())) continue;
                this.log.debug("{} Encrypt NameID in SubjectConfirmation", (Object)this.getLogPrefix());
                EncryptedID encrypted = this.getEncrypter().encrypt(sc.getNameID());
                sc.setEncryptedID(encrypted);
                sc.setNameID(null);
            }
        }
    }

    private void processLogoutRequest(@Nonnull LogoutRequest request) throws EncryptionException {
        if (this.shouldEncrypt(request.getNameID())) {
            this.log.debug("{} Encrypting NameID in LogoutRequest", (Object)this.getLogPrefix());
            EncryptedID encrypted = this.getEncrypter().encrypt(request.getNameID());
            request.setEncryptedID(encrypted);
            request.setNameID(null);
        }
    }

    private void processManageNameIDRequest(@Nonnull ManageNameIDRequest request) throws EncryptionException {
        EncryptedElementType encrypted;
        if (this.shouldEncrypt(request.getNameID())) {
            this.log.debug("{} Encrypting NameID in ManageNameIDRequest", (Object)this.getLogPrefix());
            encrypted = this.getEncrypter().encrypt(request.getNameID());
            request.setEncryptedID((EncryptedID)encrypted);
            request.setNameID(null);
        }
        if (request.getNewID() != null) {
            this.log.debug("{} Encrypting NewID in ManageNameIDRequest", (Object)this.getLogPrefix());
            encrypted = this.getEncrypter().encrypt(request.getNewID());
            request.setNewEncryptedID((NewEncryptedID)encrypted);
            request.setNewID(null);
        }
    }

    private void processNameIDMappingRequest(@Nonnull NameIDMappingRequest request) throws EncryptionException {
        if (this.shouldEncrypt(request.getNameID())) {
            this.log.debug("{} Encrypting NameID in NameIDMappingRequest", (Object)this.getLogPrefix());
            EncryptedID encrypted = this.getEncrypter().encrypt(request.getNameID());
            request.setEncryptedID(encrypted);
            request.setNameID(null);
        }
    }

    private void processNameIDMappingResponse(@Nonnull NameIDMappingResponse response) throws EncryptionException {
        if (this.shouldEncrypt(response.getNameID())) {
            this.log.debug("{} Encrypting NameID in NameIDMappingResponse", (Object)this.getLogPrefix());
            EncryptedID encrypted = this.getEncrypter().encrypt(response.getNameID());
            response.setEncryptedID(encrypted);
            response.setNameID(null);
        }
    }

    private void processAssertion(@Nonnull Assertion assertion) throws EncryptionException {
        this.processSubject(assertion.getSubject());
        if (assertion.getConditions() != null) {
            for (Condition c : assertion.getConditions().getConditions()) {
                if (!(c instanceof DelegationRestrictionType)) continue;
                for (Delegate d : ((DelegationRestrictionType)c).getDelegates()) {
                    if (!this.shouldEncrypt(d.getNameID())) continue;
                    this.log.debug("{} Encrypting NameID in Delegate", (Object)this.getLogPrefix());
                    EncryptedID encrypted = this.getEncrypter().encrypt(d.getNameID());
                    d.setEncryptedID(encrypted);
                    d.setNameID(null);
                }
            }
        }
    }
}

