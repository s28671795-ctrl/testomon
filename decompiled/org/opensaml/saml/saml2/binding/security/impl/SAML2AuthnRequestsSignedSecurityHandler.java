/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.saml2.binding.security.impl;

import com.google.common.base.Strings;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAML2AuthnRequestsSignedSecurityHandler
extends AbstractMessageHandler<SAMLObject> {
    private final Logger log = LoggerFactory.getLogger(SAML2AuthnRequestsSignedSecurityHandler.class);

    public void doInvoke(MessageContext<SAMLObject> messageContext) throws MessageHandlerException {
        SAMLObject samlMessage = (SAMLObject)messageContext.getMessage();
        if (!(samlMessage instanceof AuthnRequest)) {
            this.log.debug("Inbound message is not an instance of AuthnRequest, skipping evaluation...");
            return;
        }
        SAMLPeerEntityContext peerContext = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class, true);
        if (peerContext == null || Strings.isNullOrEmpty(peerContext.getEntityId())) {
            this.log.warn("SAML peer entityID was not available, unable to evaluate rule");
            return;
        }
        String messageIssuer = peerContext.getEntityId();
        SAMLMetadataContext metadataContext = (SAMLMetadataContext)peerContext.getSubcontext(SAMLMetadataContext.class, false);
        if (metadataContext == null || metadataContext.getRoleDescriptor() == null) {
            this.log.warn("SAMLPeerContext did not contain either a SAMLMetadataContext or a RoleDescriptor, unable to evaluate rule");
            return;
        }
        if (!(metadataContext.getRoleDescriptor() instanceof SPSSODescriptor)) {
            this.log.warn("RoleDescriptor was not an SPSSODescriptor, it was a {}. Unable to evaluate rule", (Object)metadataContext.getRoleDescriptor().getClass().getName());
            return;
        }
        SPSSODescriptor spssoRole = (SPSSODescriptor)metadataContext.getRoleDescriptor();
        if (spssoRole.isAuthnRequestsSigned() == Boolean.TRUE) {
            if (!this.isMessageSigned(messageContext)) {
                this.log.error("SPSSODescriptor for entity ID '{}' indicates AuthnRequests must be signed, but inbound message was not signed", (Object)messageIssuer);
                throw new MessageHandlerException("Inbound AuthnRequest was required to be signed but was not");
            }
        } else {
            this.log.debug("SPSSODescriptor for entity ID '{}' does not require AuthnRequests to be signed", (Object)messageIssuer);
        }
    }

    protected boolean isMessageSigned(MessageContext<SAMLObject> messageContext) {
        return SAMLBindingSupport.isMessageSigned(messageContext);
    }
}

