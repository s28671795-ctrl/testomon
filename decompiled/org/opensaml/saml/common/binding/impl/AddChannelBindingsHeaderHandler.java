/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.common.binding.impl;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObjectBuilder;
import org.opensaml.saml.common.messaging.context.ChannelBindingsContext;
import org.opensaml.saml.ext.saml2cb.ChannelBindings;
import org.opensaml.soap.messaging.SOAPMessagingSupport;
import org.opensaml.soap.util.SOAPSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AddChannelBindingsHeaderHandler
extends AbstractMessageHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(AddChannelBindingsHeaderHandler.class);
    @Nonnull
    private Function<MessageContext, ChannelBindingsContext> channelBindingsContextLookupStrategy = new ChildContextLookup(ChannelBindingsContext.class);
    @Nullable
    private ChannelBindingsContext channelBindingsContext;

    public void setChannelBindingsContextLookupStrategy(@Nonnull Function<MessageContext, ChannelBindingsContext> strategy) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.channelBindingsContextLookupStrategy = Constraint.isNotNull(strategy, "ChannelBindingsContext lookup strategy cannot be null");
    }

    protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        if (!super.doPreInvoke(messageContext)) {
            return false;
        }
        this.channelBindingsContext = this.channelBindingsContextLookupStrategy.apply(messageContext);
        if (this.channelBindingsContext == null || this.channelBindingsContext.getChannelBindings().isEmpty()) {
            this.log.debug("{} No ChannelBindings to add, nothing to do", (Object)this.getLogPrefix());
            return false;
        }
        return true;
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        SAMLObjectBuilder cbBuilder = (SAMLObjectBuilder)XMLObjectProviderRegistrySupport.getBuilderFactory().getBuilderOrThrow(ChannelBindings.DEFAULT_ELEMENT_NAME);
        for (ChannelBindings cb : this.channelBindingsContext.getChannelBindings()) {
            ChannelBindings header = (ChannelBindings)cbBuilder.buildObject();
            header.setType(cb.getType());
            SOAPSupport.addSOAP11MustUnderstandAttribute(header, true);
            SOAPSupport.addSOAP11ActorAttribute(header, "http://schemas.xmlsoap.org/soap/actor/next");
            try {
                SOAPMessagingSupport.addHeaderBlock(messageContext, header);
            }
            catch (Exception e) {
                throw new MessageHandlerException(e);
            }
        }
    }
}

