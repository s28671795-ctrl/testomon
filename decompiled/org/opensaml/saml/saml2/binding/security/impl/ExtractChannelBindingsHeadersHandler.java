/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.saml2.binding.security.impl;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.messaging.context.SOAP11Context;
import org.opensaml.soap.soap11.ActorBearing;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtractChannelBindingsHeadersHandler
extends AbstractMessageHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(ExtractChannelBindingsHeadersHandler.class);
    private boolean finalDestination;
    private boolean nextDestination = true;

    public void setFinalDestination(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.finalDestination = flag;
    }

    public void setNextDestination(boolean flag) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.nextDestination = flag;
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        ArrayList<ChannelBindings> channelBindings = new ArrayList<ChannelBindings>();
        List<XMLObject> headers = SOAPMessagingSupport.getHeaderBlock(messageContext, ChannelBindings.DEFAULT_ELEMENT_NAME, null, this.finalDestination);
        for (XMLObject header : headers) {
            if (!(header instanceof ChannelBindings) || null != ((ActorBearing)((Object)header)).getSOAP11Actor() && !this.nextDestination) continue;
            channelBindings.add((ChannelBindings)header);
        }
        if (channelBindings.isEmpty()) {
            this.log.debug("{} No ChannelBindings header blocks found", (Object)this.getLogPrefix());
        } else {
            this.log.debug("{} {} ChannelBindings header block(s) found", (Object)this.getLogPrefix(), (Object)channelBindings.size());
            ((ChannelBindingsContext)((SOAP11Context)messageContext.getSubcontext(SOAP11Context.class)).getSubcontext(ChannelBindingsContext.class, true)).getChannelBindings().addAll(channelBindings);
        }
    }
}

