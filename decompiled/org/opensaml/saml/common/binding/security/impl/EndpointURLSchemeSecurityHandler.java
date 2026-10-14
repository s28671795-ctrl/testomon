/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.common.binding.security.impl;

import java.net.URI;
import javax.annotation.Nonnull;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.SAMLMessageSecuritySupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EndpointURLSchemeSecurityHandler
extends AbstractMessageHandler {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(EndpointURLSchemeSecurityHandler.class);

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        URI endpointUrl;
        try {
            endpointUrl = SAMLBindingSupport.getEndpointURL((MessageContext<SAMLObject>)messageContext);
        }
        catch (BindingException e) {
            throw new MessageHandlerException("Could not obtain message endpoint URL", (Exception)e);
        }
        this.log.debug("{} Checking outbound endpoint for allowed URL scheme: {}", (Object)this.getLogPrefix(), (Object)endpointUrl);
        if (!SAMLMessageSecuritySupport.checkURLScheme(endpointUrl.getScheme())) {
            throw new MessageHandlerException("Relying party endpoint used the untrusted URL scheme " + endpointUrl.getScheme());
        }
    }
}

