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
import com.google.common.base.Functions;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.AttributeConsumingServiceContext;
import org.opensaml.saml.common.messaging.context.SAMLMetadataContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SAMLAddAttributeConsumingServiceHandler
extends AbstractMessageHandler {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(SAMLAddAttributeConsumingServiceHandler.class);
    @Nonnull
    private Function<MessageContext, SAMLMetadataContext> metadataContextLookupStrategy = Functions.compose(new ChildContextLookup(SAMLMetadataContext.class), new ChildContextLookup(SAMLPeerEntityContext.class));
    @Nullable
    private Function<MessageContext, Integer> indexLookupStrategy = new AuthnRequestIndexLookup();
    @Nullable
    private Integer index;

    public void setMetadataContextLookupStrategy(@Nonnull Function<MessageContext, SAMLMetadataContext> strategy) {
        this.metadataContextLookupStrategy = Constraint.isNotNull(strategy, "SAMLMetadataContext lookup strategy cannot be null");
    }

    public void setIndexLookupStrategy(@Nullable Function<MessageContext, Integer> strategy) {
        this.indexLookupStrategy = Constraint.isNotNull(strategy, "AttributeConsumingService index lookup strategy cannot be null");
    }

    protected boolean doPreInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        if (!super.doPreInvoke(messageContext)) {
            return false;
        }
        if (this.indexLookupStrategy != null) {
            this.index = this.indexLookupStrategy.apply(messageContext);
        }
        return true;
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        SAMLMetadataContext metadataContext = this.metadataContextLookupStrategy.apply(messageContext);
        if (metadataContext == null) {
            this.log.debug("{} No metadata context found, nothing to do", (Object)this.getLogPrefix());
            return;
        }
        if (!(metadataContext.getRoleDescriptor() instanceof SPSSODescriptor)) {
            this.log.debug("{} Metadata context did not contain an SPSSODescriptor, nothing to do", (Object)this.getLogPrefix());
            return;
        }
        SPSSODescriptor ssoDescriptor = (SPSSODescriptor)metadataContext.getRoleDescriptor();
        AttributeConsumingService acs = null;
        if (null != this.index) {
            this.log.debug("{} Request specified AttributeConsumingService index {}", (Object)this.getLogPrefix(), (Object)this.index);
            for (AttributeConsumingService acsEntry : ssoDescriptor.getAttributeConsumingServices()) {
                if (this.index.intValue() != acsEntry.getIndex()) continue;
                acs = acsEntry;
                break;
            }
        }
        if (null == acs) {
            this.log.debug("{} Selecting default AttributeConsumingService, if any", (Object)this.getLogPrefix());
            acs = ssoDescriptor.getDefaultAttributeConsumingService();
        }
        if (null != acs) {
            this.log.debug("{} Selected AttributeConsumingService with index {}", (Object)this.getLogPrefix(), (Object)acs.getIndex());
            ((AttributeConsumingServiceContext)metadataContext.getSubcontext(AttributeConsumingServiceContext.class, true)).setAttributeConsumingService(acs);
        } else {
            this.log.debug("{} No AttributeConsumingService selected", (Object)this.getLogPrefix());
        }
    }

    private class AuthnRequestIndexLookup
    implements Function<MessageContext, Integer> {
        private AuthnRequestIndexLookup() {
        }

        @Override
        public Integer apply(@Nullable MessageContext input) {
            Object message;
            if (input != null && (message = input.getMessage()) != null && message instanceof AuthnRequest) {
                return ((AuthnRequest)message).getAttributeConsumingServiceIndex();
            }
            return null;
        }
    }
}

