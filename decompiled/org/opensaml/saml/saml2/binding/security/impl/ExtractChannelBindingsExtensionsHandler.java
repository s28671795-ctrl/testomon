/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.saml2.binding.security.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import javax.annotation.Nonnull;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractChannelBindingsExtensionsHandler
extends AbstractMessageHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ExtractChannelBindingsExtensionsHandler.class);

    protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        if (!super.doPreInvoke(messageContext) || messageContext.getMessage() == null) {
            return false;
        }
        if (!SAMLBindingSupport.isMessageSigned((MessageContext<SAMLObject>)messageContext)) {
            this.log.debug("Message was not signed, cannot extract ChannelBindings from it");
            return false;
        }
        return true;
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        List<XMLObject> bindings;
        Extensions extensions = null;
        if (messageContext.getMessage() instanceof RequestAbstractType) {
            extensions = ((RequestAbstractType)messageContext.getMessage()).getExtensions();
        } else if (messageContext.getMessage() instanceof StatusResponseType) {
            extensions = ((StatusResponseType)messageContext.getMessage()).getExtensions();
        } else {
            this.log.debug("{} Message was not of a supported type", (Object)this.getLogPrefix());
            return;
        }
        List<XMLObject> list = bindings = extensions != null ? extensions.getUnknownXMLObjects(ChannelBindings.DEFAULT_ELEMENT_NAME) : Collections.emptyList();
        if (bindings.isEmpty()) {
            this.log.debug("{} Message did not contain any ChannelBindings extensions", (Object)this.getLogPrefix());
            return;
        }
        Collection<ChannelBindings> channelBindings = ((ChannelBindingsContext)messageContext.getSubcontext(ChannelBindingsContext.class, true)).getChannelBindings();
        for (XMLObject cb : bindings) {
            if (!(cb instanceof ChannelBindings)) continue;
            channelBindings.add((ChannelBindings)cb);
        }
        this.log.debug("{} {} ChannelBindings extension(s) found", (Object)this.getLogPrefix(), (Object)channelBindings.size());
    }
}

