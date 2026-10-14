/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.profile.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.collection.Pair;
import org.opensaml.profile.action.ActionSupport;
import org.opensaml.profile.context.ProfileRequestContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.ext.saml2delrestrict.DelegationRestrictionType;
import org.opensaml.saml.saml2.core.Assertion;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.Condition;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml.saml2.core.NameIDMappingResponse;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.saml.saml2.core.Response;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectQuery;
import org.opensaml.saml.saml2.profile.impl.AbstractDecryptAction;
import org.opensaml.xmlsec.encryption.support.DecryptionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptNameIDs
extends AbstractDecryptAction {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(DecryptNameIDs.class);

    protected void doExecute(@Nonnull ProfileRequestContext profileRequestContext) {
        block11: {
            SAMLObject message = this.getSAMLObject();
            try {
                if (message instanceof AuthnRequest) {
                    this.processSubject(profileRequestContext, ((AuthnRequest)message).getSubject());
                    break block11;
                }
                if (message instanceof SubjectQuery) {
                    this.processSubject(profileRequestContext, ((SubjectQuery)message).getSubject());
                    break block11;
                }
                if (message instanceof Response) {
                    for (Assertion a : ((Response)message).getAssertions()) {
                        this.processAssertion(profileRequestContext, a);
                    }
                    break block11;
                }
                if (message instanceof LogoutRequest) {
                    this.processLogoutRequest(profileRequestContext, (LogoutRequest)message);
                    break block11;
                }
                if (message instanceof ManageNameIDRequest) {
                    this.processManageNameIDRequest(profileRequestContext, (ManageNameIDRequest)message);
                    break block11;
                }
                if (message instanceof NameIDMappingRequest) {
                    this.processNameIDMappingRequest(profileRequestContext, (NameIDMappingRequest)message);
                    break block11;
                }
                if (message instanceof NameIDMappingResponse) {
                    this.processNameIDMappingResponse(profileRequestContext, (NameIDMappingResponse)message);
                    break block11;
                }
                if (message instanceof Assertion) {
                    this.processAssertion(profileRequestContext, (Assertion)message);
                    break block11;
                }
                this.log.debug("{} Message was of unrecognized type {}, nothing to do", (Object)this.getLogPrefix(), (Object)message.getClass().getName());
                return;
            }
            catch (DecryptionException e) {
                this.log.warn("{} Failure performing decryption", (Object)this.getLogPrefix(), (Object)e);
                if (!this.isErrorFatal()) break block11;
                ActionSupport.buildEvent(profileRequestContext, "DecryptNameIDFailed");
            }
        }
    }

    @Nullable
    private NameID processEncryptedID(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull EncryptedID encID) throws DecryptionException {
        if (!this.getDecryptionPredicate().apply(new Pair<ProfileRequestContext, EncryptedID>(profileRequestContext, encID))) {
            return null;
        }
        if (this.getDecrypter() == null) {
            throw new DecryptionException("No decryption parameters, unable to decrypt EncryptedID");
        }
        SAMLObject object = this.getDecrypter().decrypt(encID);
        if (object instanceof NameID) {
            return (NameID)object;
        }
        throw new DecryptionException("Decrypted EncryptedID was not a NameID, was a " + object.getElementQName().toString());
    }

    @Nullable
    private NewID processNewEncryptedID(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull NewEncryptedID encID) throws DecryptionException {
        if (!this.getDecryptionPredicate().apply(new Pair<ProfileRequestContext, NewEncryptedID>(profileRequestContext, encID))) {
            return null;
        }
        if (this.getDecrypter() == null) {
            throw new DecryptionException("No decryption parameters, unable to decrypt NewEncryptedID");
        }
        return this.getDecrypter().decrypt(encID);
    }

    private void processSubject(@Nonnull ProfileRequestContext profileRequestContext, @Nullable Subject subject) throws DecryptionException {
        if (subject != null) {
            if (subject.getEncryptedID() != null) {
                this.log.debug("{} Decrypting EncryptedID in Subject", (Object)this.getLogPrefix());
                try {
                    NameID decrypted = this.processEncryptedID(profileRequestContext, subject.getEncryptedID());
                    if (decrypted != null) {
                        subject.setNameID(decrypted);
                        subject.setEncryptedID(null);
                    }
                }
                catch (DecryptionException e) {
                    if (this.isErrorFatal()) {
                        throw e;
                    }
                    this.log.warn("{} Trapped failure decrypting EncryptedID in Subject", (Object)this.getLogPrefix(), (Object)e);
                }
            }
            for (SubjectConfirmation sc : subject.getSubjectConfirmations()) {
                if (sc.getEncryptedID() == null) continue;
                this.log.debug("{} Decrypting EncryptedID in SubjectConfirmation", (Object)this.getLogPrefix());
                try {
                    NameID decrypted = this.processEncryptedID(profileRequestContext, subject.getEncryptedID());
                    if (decrypted == null) continue;
                    sc.setNameID(decrypted);
                    sc.setEncryptedID(null);
                }
                catch (DecryptionException e) {
                    if (this.isErrorFatal()) {
                        throw e;
                    }
                    this.log.warn("{} Trapped failure decrypting EncryptedID in SubjectConfirmation", (Object)this.getLogPrefix(), (Object)e);
                }
            }
        }
    }

    private void processLogoutRequest(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull LogoutRequest request) throws DecryptionException {
        if (request.getEncryptedID() != null) {
            this.log.debug("{} Decrypting EncryptedID in LogoutRequest", (Object)this.getLogPrefix());
            NameID decrypted = this.processEncryptedID(profileRequestContext, request.getEncryptedID());
            if (decrypted != null) {
                request.setNameID(decrypted);
                request.setEncryptedID(null);
            }
        }
    }

    private void processManageNameIDRequest(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull ManageNameIDRequest request) throws DecryptionException {
        SAMLObject decrypted;
        if (request.getEncryptedID() != null) {
            this.log.debug("{} Decrypting EncryptedID in ManageNameIDRequest", (Object)this.getLogPrefix());
            decrypted = this.processEncryptedID(profileRequestContext, request.getEncryptedID());
            if (decrypted != null) {
                request.setNameID((NameID)decrypted);
                request.setEncryptedID(null);
            }
        }
        if (request.getNewEncryptedID() != null) {
            this.log.debug("{} Decrypting NewEncryptedID in ManageNameIDRequest", (Object)this.getLogPrefix());
            decrypted = this.processNewEncryptedID(profileRequestContext, request.getNewEncryptedID());
            if (decrypted != null) {
                request.setNewID((NewID)decrypted);
                request.setNewEncryptedID(null);
            }
        }
    }

    private void processNameIDMappingRequest(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull NameIDMappingRequest request) throws DecryptionException {
        if (request.getEncryptedID() != null) {
            this.log.debug("{} Decrypting EncryptedID in NameIDMappingRequest", (Object)this.getLogPrefix());
            NameID decrypted = this.processEncryptedID(profileRequestContext, request.getEncryptedID());
            if (decrypted != null) {
                request.setNameID(decrypted);
                request.setEncryptedID(null);
            }
        }
    }

    private void processNameIDMappingResponse(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull NameIDMappingResponse response) throws DecryptionException {
        if (response.getEncryptedID() != null) {
            this.log.debug("{} Decrypting EncryptedID in NameIDMappingRequest", (Object)this.getLogPrefix());
            NameID decrypted = this.processEncryptedID(profileRequestContext, response.getEncryptedID());
            if (decrypted != null) {
                response.setNameID(decrypted);
                response.setEncryptedID(null);
            }
        }
    }

    private void processAssertion(@Nonnull ProfileRequestContext profileRequestContext, @Nonnull Assertion assertion) throws DecryptionException {
        try {
            this.processSubject(profileRequestContext, assertion.getSubject());
        }
        catch (DecryptionException e) {
            if (this.isErrorFatal()) {
                throw e;
            }
            this.log.warn("{} Trapped failure decrypting EncryptedIDs in Subject", (Object)this.getLogPrefix(), (Object)e);
        }
        if (assertion.getConditions() != null) {
            for (Condition c : assertion.getConditions().getConditions()) {
                if (!(c instanceof DelegationRestrictionType)) continue;
                for (Delegate d : ((DelegationRestrictionType)c).getDelegates()) {
                    if (d.getEncryptedID() == null) continue;
                    this.log.debug("{} Decrypting EncryptedID in Delegate", (Object)this.getLogPrefix());
                    try {
                        NameID decrypted = this.processEncryptedID(profileRequestContext, d.getEncryptedID());
                        if (decrypted == null) continue;
                        d.setNameID(decrypted);
                        d.setEncryptedID(null);
                    }
                    catch (DecryptionException e) {
                        if (this.isErrorFatal()) {
                            throw e;
                        }
                        this.log.warn("{} Trapped failure decrypting EncryptedID in Delegate", (Object)this.getLogPrefix(), (Object)e);
                    }
                }
            }
        }
    }
}

