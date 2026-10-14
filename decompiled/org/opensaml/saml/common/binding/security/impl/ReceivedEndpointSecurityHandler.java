/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.MessageException
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.handler.AbstractMessageHandler
 *  org.opensaml.messaging.handler.MessageHandlerException
 */
package org.opensaml.saml.common.binding.security.impl;

import javax.annotation.Nonnull;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.BasicURLComparator;
import net.shibboleth.utilities.java.support.net.URIComparator;
import net.shibboleth.utilities.java.support.net.URIException;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.MessageException;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.handler.AbstractMessageHandler;
import org.opensaml.messaging.handler.MessageHandlerException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReceivedEndpointSecurityHandler
extends AbstractMessageHandler {
    @Nonnull
    private Logger log = LoggerFactory.getLogger(ReceivedEndpointSecurityHandler.class);
    @Nonnull
    private URIComparator uriComparator = new BasicURLComparator();
    @NonnullAfterInit
    private HttpServletRequest httpServletRequest;

    @Nonnull
    public URIComparator getURIComparator() {
        return this.uriComparator;
    }

    public void setURIComparator(@Nonnull URIComparator comparator) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.uriComparator = Constraint.isNotNull(comparator, "URIComparator cannot be null");
    }

    @NonnullAfterInit
    public HttpServletRequest getHttpServletRequest() {
        return this.httpServletRequest;
    }

    public void setHttpServletRequest(@Nonnull HttpServletRequest request) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        this.httpServletRequest = Constraint.isNotNull(request, "HttpServletRequest cannot be null");
    }

    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.uriComparator == null) {
            throw new ComponentInitializationException("URIComparator cannot be null");
        }
        if (this.httpServletRequest == null) {
            throw new ComponentInitializationException("HttpServletRequest cannot be null");
        }
    }

    protected void doInvoke(@Nonnull MessageContext messageContext) throws MessageHandlerException {
        this.checkEndpointURI((MessageContext<SAMLObject>)messageContext, this.getURIComparator());
    }

    protected boolean compareEndpointURIs(@Nonnull @NotEmpty String messageDestination, @Nonnull @NotEmpty String receiverEndpoint, @Nonnull URIComparator comparator) throws URIException {
        Constraint.isNotNull(messageDestination, "Message destination URI was null");
        Constraint.isNotNull(receiverEndpoint, "Receiver endpoint URI was null");
        Constraint.isNotNull(comparator, "URIComparator was null");
        return comparator.compare(messageDestination, receiverEndpoint);
    }

    protected void checkEndpointURI(@Nonnull MessageContext<SAMLObject> messageContext, @Nonnull URIComparator comparator) throws MessageHandlerException {
        boolean matched;
        String receiverEndpoint;
        String messageDestination;
        Constraint.isNotNull(comparator, "URIComparator may not be null");
        this.log.debug("{} Checking SAML message intended destination endpoint against receiver endpoint", (Object)this.getLogPrefix());
        try {
            messageDestination = StringSupport.trimOrNull(SAMLBindingSupport.getIntendedDestinationEndpointURI(messageContext));
        }
        catch (MessageException e) {
            throw new MessageHandlerException("Error obtaining message intended destination endpoint URI", (Exception)((Object)e));
        }
        boolean bindingRequires = SAMLBindingSupport.isIntendedDestinationEndpointURIRequired(messageContext);
        if (messageDestination == null) {
            if (bindingRequires) {
                this.log.error("{} SAML message intended destination endpoint URI required by binding was empty", (Object)this.getLogPrefix());
                throw new MessageHandlerException("SAML message intended destination (required by binding) was not present");
            }
            this.log.debug("{} SAML message intended destination endpoint was empty, not required by binding, skipping", (Object)this.getLogPrefix());
            return;
        }
        try {
            receiverEndpoint = StringSupport.trimOrNull(SAMLBindingSupport.getActualReceiverEndpointURI(messageContext, this.getHttpServletRequest()));
        }
        catch (MessageException e) {
            throw new MessageHandlerException("Error obtaining message received endpoint URI", (Exception)((Object)e));
        }
        this.log.debug("{} Intended message destination endpoint: {}", (Object)this.getLogPrefix(), (Object)messageDestination);
        this.log.debug("{} Actual message receiver endpoint: {}", (Object)this.getLogPrefix(), (Object)receiverEndpoint);
        try {
            matched = this.compareEndpointURIs(messageDestination, receiverEndpoint, comparator);
        }
        catch (URIException e) {
            throw new MessageHandlerException("Error comparing endpoint URI's", (Exception)e);
        }
        if (!matched) {
            this.log.error("{} SAML message intended destination endpoint '{}' did not match the recipient endpoint '{}'", this.getLogPrefix(), messageDestination, receiverEndpoint);
            throw new MessageHandlerException("SAML message failed received endpoint check");
        }
        this.log.debug("{} SAML message intended destination endpoint matched recipient endpoint", (Object)this.getLogPrefix());
    }
}

