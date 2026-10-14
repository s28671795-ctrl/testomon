/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.apache.velocity.VelocityContext
 *  org.apache.velocity.app.VelocityEngine
 *  org.apache.velocity.context.Context
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.encoder.MessageEncodingException
 */
package org.opensaml.saml.saml2.binding.encoding.impl;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.codec.HTMLEncoder;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.net.HttpServletSupport;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.saml2.binding.encoding.impl.BaseSAML2MessageEncoder;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Element;

public class HTTPPostEncoder
extends BaseSAML2MessageEncoder {
    public static final String DEFAULT_TEMPLATE_ID = "/templates/saml2-post-binding.vm";
    private final Logger log = LoggerFactory.getLogger(HTTPPostEncoder.class);
    private VelocityEngine velocityEngine;
    private String velocityTemplateId;

    public HTTPPostEncoder() {
        this.setVelocityTemplateId(DEFAULT_TEMPLATE_ID);
    }

    @Override
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-POST";
    }

    public VelocityEngine getVelocityEngine() {
        return this.velocityEngine;
    }

    public void setVelocityEngine(VelocityEngine newVelocityEngine) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.velocityEngine = newVelocityEngine;
    }

    public String getVelocityTemplateId() {
        return this.velocityTemplateId;
    }

    public void setVelocityTemplateId(String newVelocityTemplateId) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.velocityTemplateId = newVelocityTemplateId;
    }

    protected void doDestroy() {
        this.velocityEngine = null;
        this.velocityTemplateId = null;
        super.doDestroy();
    }

    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.velocityEngine == null) {
            throw new ComponentInitializationException("VelocityEngine must be supplied");
        }
        if (this.velocityTemplateId == null) {
            throw new ComponentInitializationException("Velocity template id must be supplied");
        }
    }

    protected void doEncode() throws MessageEncodingException {
        MessageContext messageContext = this.getMessageContext();
        SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
        if (outboundMessage == null) {
            throw new MessageEncodingException("No outbound SAML message contained in message context");
        }
        String endpointURL = this.getEndpointURL((MessageContext<SAMLObject>)messageContext).toString();
        this.postEncode((MessageContext<SAMLObject>)messageContext, endpointURL);
    }

    protected void postEncode(MessageContext<SAMLObject> messageContext, String endpointURL) throws MessageEncodingException {
        this.log.debug("Invoking Velocity template to create POST body");
        try {
            VelocityContext context = new VelocityContext();
            this.populateVelocityContext(context, messageContext, endpointURL);
            HttpServletResponse response = this.getHttpServletResponse();
            HttpServletSupport.addNoCacheHeaders(response);
            HttpServletSupport.setUTF8Encoding(response);
            HttpServletSupport.setContentType(response, "text/html");
            OutputStreamWriter out = new OutputStreamWriter((OutputStream)response.getOutputStream(), "UTF-8");
            this.velocityEngine.mergeTemplate(this.velocityTemplateId, "UTF-8", (Context)context, (Writer)out);
            ((Writer)out).flush();
        }
        catch (Exception e) {
            this.log.error("Error invoking Velocity template", e);
            throw new MessageEncodingException("Error creating output document", e);
        }
    }

    protected void populateVelocityContext(VelocityContext velocityContext, MessageContext<SAMLObject> messageContext, String endpointURL) throws MessageEncodingException {
        block5: {
            String encodedEndpointURL = HTMLEncoder.encodeForHTMLAttribute(endpointURL);
            this.log.debug("Encoding action url of '{}' with encoded value '{}'", (Object)endpointURL, (Object)encodedEndpointURL);
            velocityContext.put("action", (Object)encodedEndpointURL);
            velocityContext.put("binding", (Object)this.getBindingURI());
            SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
            this.log.debug("Marshalling and Base64 encoding SAML message");
            Element domMessage = this.marshallMessage(outboundMessage);
            try {
                String messageXML = SerializeSupport.nodeToString(domMessage);
                String encodedMessage = Base64Support.encode(messageXML.getBytes("UTF-8"), false);
                if (outboundMessage instanceof RequestAbstractType) {
                    velocityContext.put("SAMLRequest", (Object)encodedMessage);
                    break block5;
                }
                if (outboundMessage instanceof StatusResponseType) {
                    velocityContext.put("SAMLResponse", (Object)encodedMessage);
                    break block5;
                }
                throw new MessageEncodingException("SAML message is neither a SAML RequestAbstractType or StatusResponseType");
            }
            catch (UnsupportedEncodingException e) {
                this.log.error("UTF-8 encoding is not supported, this VM is not Java compliant.");
                throw new MessageEncodingException("Unable to encode message, UTF-8 encoding is not supported");
            }
        }
        String relayState = SAMLBindingSupport.getRelayState(messageContext);
        if (SAMLBindingSupport.checkRelayState(relayState)) {
            String encodedRelayState = HTMLEncoder.encodeForHTMLAttribute(relayState);
            this.log.debug("Setting RelayState parameter to: '{}', encoded as '{}'", (Object)relayState, (Object)encodedRelayState);
            velocityContext.put("RelayState", (Object)encodedRelayState);
        }
    }
}

