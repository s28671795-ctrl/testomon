/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.BaseContext
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.common.messaging.context;

import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.messaging.context.BaseContext;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;

public class SAMLMessageInfoContext
extends BaseContext {
    @Nullable
    @NotEmpty
    private String messageId;
    @Nullable
    private DateTime issueInstant;

    @Nullable
    @NotEmpty
    public String getMessageId() {
        if (this.messageId == null) {
            this.messageId = this.resolveMessageId();
        }
        return this.messageId;
    }

    public void setMessageId(@Nullable String newMessageId) {
        this.messageId = StringSupport.trimOrNull(newMessageId);
    }

    @Nullable
    public DateTime getMessageIssueInstant() {
        if (this.issueInstant == null) {
            this.issueInstant = this.resolveIssueInstant();
        }
        return this.issueInstant;
    }

    public void setMessageIssueInstant(@Nullable DateTime messageIssueInstant) {
        this.issueInstant = messageIssueInstant;
    }

    @Nullable
    protected String resolveMessageId() {
        SAMLObject samlMessage = this.resolveSAMLMessage();
        if (samlMessage instanceof org.opensaml.saml.saml2.core.RequestAbstractType) {
            org.opensaml.saml.saml2.core.RequestAbstractType request = (org.opensaml.saml.saml2.core.RequestAbstractType)samlMessage;
            return request.getID();
        }
        if (samlMessage instanceof StatusResponseType) {
            StatusResponseType response = (StatusResponseType)samlMessage;
            return response.getID();
        }
        if (samlMessage instanceof ResponseAbstractType) {
            ResponseAbstractType response = (ResponseAbstractType)samlMessage;
            return response.getID();
        }
        if (samlMessage instanceof RequestAbstractType) {
            RequestAbstractType request = (RequestAbstractType)samlMessage;
            return request.getID();
        }
        return null;
    }

    @Nullable
    protected DateTime resolveIssueInstant() {
        SAMLObject samlMessage = this.resolveSAMLMessage();
        if (samlMessage instanceof org.opensaml.saml.saml2.core.RequestAbstractType) {
            org.opensaml.saml.saml2.core.RequestAbstractType request = (org.opensaml.saml.saml2.core.RequestAbstractType)samlMessage;
            return request.getIssueInstant();
        }
        if (samlMessage instanceof StatusResponseType) {
            StatusResponseType response = (StatusResponseType)samlMessage;
            return response.getIssueInstant();
        }
        if (samlMessage instanceof ResponseAbstractType) {
            ResponseAbstractType response = (ResponseAbstractType)samlMessage;
            return response.getIssueInstant();
        }
        if (samlMessage instanceof RequestAbstractType) {
            RequestAbstractType request = (RequestAbstractType)samlMessage;
            return request.getIssueInstant();
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

