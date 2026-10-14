/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.decoder.MessageDecodingException
 *  org.opensaml.messaging.decoder.servlet.BaseHttpServletRequestXMLMessageDecoder
 */
package org.opensaml.saml.saml2.binding.decoding.impl;

import com.google.common.base.Strings;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.Base64Support;
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

public class HTTPRedirectDeflateDecoder
extends BaseHttpServletRequestXMLMessageDecoder<SAMLObject>
implements SAMLMessageDecoder {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(HTTPRedirectDeflateDecoder.class);
    @Nullable
    private BindingDescriptor bindingDescriptor;

    @Override
    @Nonnull
    @NotEmpty
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
    }

    @Nullable
    public BindingDescriptor getBindingDescriptor() {
        return this.bindingDescriptor;
    }

    public void setBindingDescriptor(@Nullable BindingDescriptor descriptor) {
        this.bindingDescriptor = descriptor;
    }

    protected void doDecode() throws MessageDecodingException {
        InputStream samlMessageIns;
        MessageContext messageContext = new MessageContext();
        HttpServletRequest request = this.getHttpServletRequest();
        if (!"GET".equalsIgnoreCase(request.getMethod())) {
            throw new MessageDecodingException("This message decoder only supports the HTTP GET method");
        }
        String samlEncoding = StringSupport.trimOrNull(request.getParameter("SAMLEncoding"));
        if (samlEncoding != null && !"urn:oasis:names:tc:SAML:2.0:bindings:URL-Encoding:DEFLATE".equals(samlEncoding)) {
            throw new MessageDecodingException("Request indicated an unsupported SAMLEncoding: " + samlEncoding);
        }
        String relayState = request.getParameter("RelayState");
        this.log.debug("Decoded RelayState: {}", (Object)relayState);
        SAMLBindingSupport.setRelayState((MessageContext<SAMLObject>)messageContext, relayState);
        if (!Strings.isNullOrEmpty(request.getParameter("SAMLRequest"))) {
            samlMessageIns = this.decodeMessage(request.getParameter("SAMLRequest"));
        } else if (!Strings.isNullOrEmpty(request.getParameter("SAMLResponse"))) {
            samlMessageIns = this.decodeMessage(request.getParameter("SAMLResponse"));
        } else {
            throw new MessageDecodingException("No SAMLRequest or SAMLResponse query path parameter, invalid SAML 2 HTTP Redirect message");
        }
        SAMLObject samlMessage = (SAMLObject)this.unmarshallMessage(samlMessageIns);
        messageContext.setMessage((Object)samlMessage);
        this.log.debug("Decoded SAML message");
        this.populateBindingContext((MessageContext<SAMLObject>)messageContext);
        this.setMessageContext(messageContext);
    }

    protected InputStream decodeMessage(String message) throws MessageDecodingException {
        this.log.debug("Base64 decoding and inflating SAML message");
        byte[] decodedBytes = Base64Support.decode(message);
        if (decodedBytes == null) {
            this.log.error("Unable to Base64 decode incoming message");
            throw new MessageDecodingException("Unable to Base64 decode incoming message");
        }
        try {
            ByteArrayInputStream bytesIn = new ByteArrayInputStream(decodedBytes);
            InflaterInputStream inflater = new InflaterInputStream(bytesIn, new Inflater(true));
            return inflater;
        }
        catch (Exception e) {
            this.log.error("Unable to Base64 decode and inflate SAML message", e);
            throw new MessageDecodingException("Unable to Base64 decode and inflate SAML message", e);
        }
    }

    protected void populateBindingContext(MessageContext<SAMLObject> messageContext) {
        SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true);
        bindingContext.setBindingUri(this.getBindingURI());
        bindingContext.setBindingDescriptor(this.bindingDescriptor);
        bindingContext.setHasBindingSignature(!Strings.isNullOrEmpty(this.getHttpServletRequest().getParameter("Signature")));
        bindingContext.setIntendedDestinationEndpointURIRequired(SAMLBindingSupport.isMessageSigned(messageContext));
    }
}

