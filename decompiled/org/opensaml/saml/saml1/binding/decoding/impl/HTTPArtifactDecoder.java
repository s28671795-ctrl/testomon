/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.decoder.MessageDecodingException
 *  org.opensaml.messaging.decoder.servlet.BaseHttpServletRequestXMLMessageDecoder
 */
package org.opensaml.saml.saml1.binding.decoding.impl;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.decoder.MessageDecodingException;
import org.opensaml.messaging.decoder.servlet.BaseHttpServletRequestXMLMessageDecoder;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.BindingDescriptor;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.decoding.SAMLMessageDecoder;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPArtifactDecoder
extends BaseHttpServletRequestXMLMessageDecoder<SAMLObject>
implements SAMLMessageDecoder {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(HTTPArtifactDecoder.class);
    @Nullable
    private BindingDescriptor bindingDescriptor;

    @Override
    @Nonnull
    @NotEmpty
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:1.0:profiles:artifact-01";
    }

    @Nullable
    public BindingDescriptor getBindingDescriptor() {
        return this.bindingDescriptor;
    }

    public void setBindingDescriptor(@Nullable BindingDescriptor descriptor) {
        this.bindingDescriptor = descriptor;
    }

    protected void doDecode() throws MessageDecodingException {
        MessageContext messageContext = new MessageContext();
        HttpServletRequest request = this.getHttpServletRequest();
        this.decodeTarget(messageContext, request);
        this.processArtifacts(messageContext, request);
        this.populateBindingContext((MessageContext<SAMLObject>)messageContext);
        this.setMessageContext(messageContext);
    }

    protected void decodeTarget(MessageContext messageContext, HttpServletRequest request) throws MessageDecodingException {
        String target = StringSupport.trim(request.getParameter("TARGET"));
        if (target == null) {
            this.log.error("URL TARGET parameter was missing or did not contain a value.");
            throw new MessageDecodingException("URL TARGET parameter was missing or did not contain a value.");
        }
        SAMLBindingSupport.setRelayState((MessageContext<SAMLObject>)messageContext, target);
    }

    protected void processArtifacts(MessageContext messageContext, HttpServletRequest request) throws MessageDecodingException {
        String[] encodedArtifacts = request.getParameterValues("SAMLart");
        if (encodedArtifacts == null || encodedArtifacts.length == 0) {
            this.log.error("URL SAMLart parameter was missing or did not contain a value.");
            throw new MessageDecodingException("URL SAMLart parameter was missing or did not contain a value.");
        }
    }

    protected void populateBindingContext(MessageContext<SAMLObject> messageContext) {
        SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
        bindingContext.setBindingUri(this.getBindingURI());
        bindingContext.setBindingDescriptor(this.bindingDescriptor);
        bindingContext.setHasBindingSignature(false);
        bindingContext.setIntendedDestinationEndpointURIRequired(false);
    }
}

