/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Request;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SubjectQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLSubjectNameIdentifierContext
extends BaseContext {
    @Nullable
    private Logger log = LoggerFactory.getLogger(SAMLSubjectNameIdentifierContext.class);
    @Nullable
    private SAMLObject nameID;

    @Nullable
    public SAMLObject getSubjectNameIdentifier() {
        if (this.nameID == null) {
            this.nameID = this.resolveNameIdentifier();
        }
        return this.nameID;
    }

    @Nullable
    public NameIdentifier getSAML1SubjectNameIdentifier() {
        SAMLObject samlObject = this.getSubjectNameIdentifier();
        if (samlObject instanceof NameIdentifier) {
            return (NameIdentifier)samlObject;
        }
        return null;
    }

    @Nullable
    public NameID getSAML2SubjectNameID() {
        SAMLObject samlObject = this.getSubjectNameIdentifier();
        if (samlObject instanceof NameID) {
            return (NameID)samlObject;
        }
        return null;
    }

    public void setSubjectNameIdentifier(@Nullable SAMLObject newNameID) {
        if (newNameID != null && !(newNameID instanceof NameIdentifier) && !(newNameID instanceof NameID)) {
            throw new IllegalArgumentException("Argument was not a valid SAML 1 or 2 name identifier type or null: " + newNameID.getClass().getName());
        }
        this.nameID = newNameID;
    }

    @Nullable
    protected SAMLObject resolveNameIdentifier() {
        SAMLObject samlMessage = this.resolveSAMLMessage();
        if (samlMessage == null) {
            this.log.debug("SAML message could not be dynamically resolved from parent context");
            return null;
        }
        if (samlMessage instanceof SubjectQuery) {
            SubjectQuery query = (SubjectQuery)samlMessage;
            if (query.getSubject() != null) {
                return query.getSubject().getNameID();
            }
            return null;
        }
        if (samlMessage instanceof AuthnRequest) {
            AuthnRequest request = (AuthnRequest)samlMessage;
            if (request.getSubject() != null) {
                return request.getSubject().getNameID();
            }
            return null;
        }
        if (samlMessage instanceof Request && ((Request)samlMessage).getSubjectQuery() != null) {
            org.opensaml.saml.saml1.core.SubjectQuery query = ((Request)samlMessage).getSubjectQuery();
            if (query.getSubject() != null) {
                return query.getSubject().getNameIdentifier();
            }
            return null;
        }
        if (samlMessage instanceof LogoutRequest) {
            this.log.debug("Ignoring LogoutRequest, Subject does not require processing");
        } else {
            this.log.debug("Message in resolved parent message context was not a supported instance of SAMLObject: {}", (Object)samlMessage.getClass().getName());
        }
        return null;
    }

    @Nullable
    protected SAMLObject resolveSAMLMessage() {
        MessageContext parent;
        if (this.getParent() instanceof MessageContext && (parent = (MessageContext)this.getParent()).getMessage() instanceof SAMLObject) {
            return (SAMLObject)parent.getMessage();
        }
        return null;
    }
}

