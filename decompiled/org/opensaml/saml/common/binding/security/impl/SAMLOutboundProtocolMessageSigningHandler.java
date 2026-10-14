/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.common.binding.security.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.messaging.SAMLMessageSecuritySupport;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml2.core.StatusCode;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.security.SecurityException;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.signature.support.SignatureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLOutboundProtocolMessageSigningHandler
extends AbstractMessageHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SAMLOutboundProtocolMessageSigningHandler.class);
    private boolean signErrorResponses = true;

    public void setSignErrorResponses(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.signErrorResponses = flag;
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        SignatureSigningParameters signingParameters = SAMLMessageSecuritySupport.getContextSigningParameters((MessageContext<SAMLObject>)messageContext);
        if (signingParameters != null) {
            if (!this.signErrorResponses && this.isErrorResponse(messageContext.getMessage())) {
                this.log.debug("{} Message context contained signing parameters, but error response signatures are disabled", (Object)this.getLogPrefix());
            } else {
                try {
                    SAMLMessageSecuritySupport.signMessage((MessageContext<SAMLObject>)messageContext);
                }
                catch (MarshallingException | SecurityException | SignatureException e) {
                    throw new MessageHandlerException("Error signing outbound protocol message", e);
                }
            }
        } else {
            this.log.debug("{} Message context did not contain signing parameters, outbound message will not be signed", (Object)this.getLogPrefix());
        }
    }

    private boolean isErrorResponse(@Nullable Object message) {
        if (message != null) {
            if (message instanceof Response) {
                if (((Response)message).getStatus() != null) {
                    org.opensaml.saml.saml1.core.StatusCode s1 = ((Response)message).getStatus().getStatusCode();
                    return s1 != null && s1.getValue() != null && !org.opensaml.saml.saml1.core.StatusCode.SUCCESS.equals(s1.getValue());
                }
            } else if (message instanceof StatusResponseType && ((StatusResponseType)message).getStatus() != null) {
                StatusCode s2 = ((StatusResponseType)message).getStatus().getStatusCode();
                return s2 != null && s2.getValue() != null && !"urn:oasis:names:tc:SAML:2.0:status:Success".equals(s2.getValue());
            }
        }
        return false;
    }
}

