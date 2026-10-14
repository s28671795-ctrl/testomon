/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.common.binding.security.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.common.binding.security.impl.BaseSAMLXMLSignatureSecurityHandler;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.security.impl.SAMLSignatureProfileValidator;
import org.opensaml.xmlsec.signature.Signature;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.opensaml.xmlsec.signature.support.SignaturePrevalidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLProtocolMessageXMLSignatureSecurityHandler
extends BaseSAMLXMLSignatureSecurityHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SAMLProtocolMessageXMLSignatureSecurityHandler.class);
    @Nullable
    private SignaturePrevalidator signaturePrevalidator;

    public SAMLProtocolMessageXMLSignatureSecurityHandler() {
        this.setSignaturePrevalidator(new SAMLSignatureProfileValidator());
    }

    @Nullable
    public SignaturePrevalidator getSignaturePrevalidator() {
        return this.signaturePrevalidator;
    }

    public void setSignaturePrevalidator(@Nullable SignaturePrevalidator validator) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.signaturePrevalidator = validator;
    }

    public void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        Object samlMsg = messageContext.getMessage();
        if (!(samlMsg instanceof SignableSAMLObject)) {
            this.log.debug("{} Extracted SAML message was not a SignableSAMLObject, cannot process signature", (Object)this.getLogPrefix());
            return;
        }
        SignableSAMLObject signableObject = (SignableSAMLObject)samlMsg;
        if (!signableObject.isSigned()) {
            this.log.debug("{} SAML protocol message was not signed, skipping XML signature processing", (Object)this.getLogPrefix());
            return;
        }
        Signature signature = signableObject.getSignature();
        this.performPrevalidation(signature);
        this.doEvaluate(signature, signableObject, messageContext);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void doEvaluate(@Nonnull Signature signature, @Nonnull SignableSAMLObject signableObject, @Nonnull MessageContext messageContext) throws MessageHandlerException {
        SAMLPeerEntityContext peerContext = this.getSAMLPeerEntityContext();
        if (peerContext.getEntityId() == null) {
            this.log.debug("{} Context issuer unavailable, cannot attempt SAML protocol message signature validation", (Object)this.getLogPrefix());
            throw new MessageHandlerException("Context issuer unavailable, cannot validate signature");
        }
        String contextEntityID = peerContext.getEntityId();
        String msgType = signableObject.getElementQName().toString();
        this.log.debug("{} Attempting to verify signature on signed SAML protocol message type: {}", (Object)this.getLogPrefix(), (Object)msgType);
        if (this.evaluate(signature, contextEntityID, messageContext)) {
            this.log.debug("{} Validation of protocol message signature succeeded, message type: {}", (Object)this.getLogPrefix(), (Object)msgType);
            if (peerContext.isAuthenticated()) return;
            this.log.debug("{} Authentication via protocol message signature succeeded for context issuer entity ID {}", (Object)this.getLogPrefix(), (Object)contextEntityID);
            peerContext.setAuthenticated(true);
            return;
        }
        this.log.debug("{} Validation of protocol message signature failed for context issuer '{}', message type: {}", this.getLogPrefix(), contextEntityID, msgType);
        throw new MessageHandlerException("Validation of protocol message signature failed");
    }

    protected void performPrevalidation(@Nonnull Signature signature) throws MessageHandlerException {
        if (this.getSignaturePrevalidator() != null) {
            try {
                this.getSignaturePrevalidator().validate(signature);
            }
            catch (SignatureException e) {
                this.log.debug("{} Protocol message signature failed signature pre-validation", (Object)this.getLogPrefix(), (Object)e);
                throw new MessageHandlerException("Protocol message signature failed signature pre-validation", (Exception)e);
            }
        }
    }
}

