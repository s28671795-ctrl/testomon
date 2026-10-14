/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.context.navigate.ChildContextLookup
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.saml2.binding.impl;

import com.google.common.base.Function;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.logic.Constraint;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.context.navigate.ChildContextLookup;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.messaging.context.SAMLConsentContext;
import org.opensaml.saml.saml2.core.StatusResponseType;

public class AddConsentToResponseHandler
extends AbstractMessageHandler {
    @Nonnull
    private Function<MessageContext, SAMLConsentContext> consentContextStrategy = new ChildContextLookup(SAMLConsentContext.class);

    public void setConsentContextLookupStrategy(@Nonnull Function<MessageContext, SAMLConsentContext> strategy) {
        this.consentContextStrategy = Constraint.isNotNull(strategy, "SAMLConsentContext lookup strategy cannot be null");
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        Object response = messageContext.getMessage();
        if (response == null) {
            throw new MessageHandlerException("Message not found");
        }
        if (!(response instanceof StatusResponseType)) {
            throw new MessageHandlerException("Message was not a StatusResponseType");
        }
        SAMLConsentContext consentContext = this.consentContextStrategy.apply(messageContext);
        if (consentContext == null || consentContext.getConsent() == null) {
            throw new MessageHandlerException("Consent value not found");
        }
        ((StatusResponseType)response).setConsent(consentContext.getConsent());
    }
}

