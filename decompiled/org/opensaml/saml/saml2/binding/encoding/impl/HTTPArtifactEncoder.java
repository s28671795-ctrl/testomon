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

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.MalformedURLException;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.codec.HTMLEncoder;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.binding.artifact.AbstractSAMLArtifact;
import org.opensaml.saml.common.binding.artifact.SAMLArtifactMap;
import org.opensaml.saml.common.messaging.context.SAMLArtifactContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.common.messaging.context.SAMLSelfEntityContext;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.binding.artifact.AbstractSAML2Artifact;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactBuilder;
import org.opensaml.saml.saml2.binding.artifact.SAML2ArtifactType0004;
import org.opensaml.saml.saml2.binding.encoding.impl.BaseSAML2MessageEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPArtifactEncoder
extends BaseSAML2MessageEncoder {
    @Nonnull
    @NotEmpty
    public static final String DEFAULT_TEMPLATE_ID = "/templates/saml2-post-artifact-binding.vm";
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(HTTPArtifactEncoder.class);
    private boolean postEncoding;
    @Nullable
    private VelocityEngine velocityEngine;
    @Nonnull
    @NotEmpty
    private String velocityTemplateId;
    @NonnullAfterInit
    private SAMLArtifactMap artifactMap;
    @Nonnull
    @NotEmpty
    private byte[] defaultArtifactType = SAML2ArtifactType0004.TYPE_CODE;

    public HTTPArtifactEncoder() {
        this.setVelocityTemplateId(DEFAULT_TEMPLATE_ID);
    }

    @Override
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Artifact";
    }

    public boolean isPostEncoding() {
        return this.postEncoding;
    }

    public void setPostEncoding(boolean post) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.postEncoding = post;
    }

    @Nullable
    public VelocityEngine getVelocityEngine() {
        return this.velocityEngine;
    }

    public void setVelocityEngine(@Nullable VelocityEngine newVelocityEngine) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.velocityEngine = newVelocityEngine;
    }

    @Nonnull
    @NotEmpty
    public String getVelocityTemplateId() {
        return this.velocityTemplateId;
    }

    public void setVelocityTemplateId(@Nonnull @NotEmpty String newVelocityTemplateId) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.velocityTemplateId = Constraint.isNotNull(StringSupport.trimOrNull(newVelocityTemplateId), "Velocity template ID cannot be null or empty");
    }

    @NonnullAfterInit
    public SAMLArtifactMap getArtifactMap() {
        return this.artifactMap;
    }

    public void setArtifactMap(@Nonnull SAMLArtifactMap newArtifactMap) {
        ComponentSupport.ifInitializedThrowUnmodifiabledComponentException((InitializableComponent)((Object)this));
        ComponentSupport.ifDestroyedThrowDestroyedComponentException((DestructableComponent)((Object)this));
        this.artifactMap = Constraint.isNotNull(newArtifactMap, "SAMLArtifactMap cannot be null");
    }

    protected void doInitialize() throws ComponentInitializationException {
        super.doInitialize();
        if (this.artifactMap == null) {
            throw new ComponentInitializationException("SAMLArtifactMap cannot be null");
        }
        if (this.isPostEncoding() && this.velocityEngine == null) {
            throw new ComponentInitializationException("VelocityEngine cannot be null when POST is used");
        }
    }

    protected void doDestroy() {
        this.velocityEngine = null;
        this.velocityTemplateId = null;
        this.artifactMap = null;
        super.doDestroy();
    }

    protected void doEncode() throws MessageEncodingException {
        HttpServletResponse response = this.getHttpServletResponse();
        if (response == null) {
            throw new MessageEncodingException("HttpServletResponse was null");
        }
        response.setCharacterEncoding("UTF-8");
        if (this.postEncoding) {
            this.postEncode();
        } else {
            this.getEncode();
        }
    }

    protected void postEncode() throws MessageEncodingException {
        this.log.debug("Performing HTTP POST SAML 2 artifact encoding");
        MessageContext messageContext = this.getMessageContext();
        this.log.debug("Creating velocity context");
        VelocityContext context = new VelocityContext();
        String endpointURL = this.getEndpointURL((MessageContext<SAMLObject>)messageContext).toString();
        String encodedEndpointURL = HTMLEncoder.encodeForHTMLAttribute(endpointURL);
        this.log.debug("Setting action parameter to: '{}', encoded as '{}'", (Object)endpointURL, (Object)encodedEndpointURL);
        context.put("action", (Object)encodedEndpointURL);
        context.put("SAMLArt", (Object)this.buildArtifact((MessageContext<SAMLObject>)messageContext).base64Encode());
        context.put("binding", (Object)this.getBindingURI());
        String relayState = SAMLBindingSupport.getRelayState((MessageContext<SAMLObject>)messageContext);
        if (SAMLBindingSupport.checkRelayState(relayState)) {
            String encodedRelayState = HTMLEncoder.encodeForHTMLAttribute(relayState);
            this.log.debug("Setting RelayState parameter to: '{}', encoded as '{}'", (Object)relayState, (Object)encodedRelayState);
            context.put("RelayState", (Object)encodedRelayState);
        }
        try {
            this.log.debug("Invoking velocity template");
            HttpServletResponse response = this.getHttpServletResponse();
            OutputStreamWriter outWriter = new OutputStreamWriter((OutputStream)response.getOutputStream());
            this.velocityEngine.mergeTemplate(this.velocityTemplateId, "UTF-8", (Context)context, (Writer)outWriter);
            outWriter.flush();
        }
        catch (Exception e) {
            this.log.error("Error invoking velocity template to create POST form", e);
            throw new MessageEncodingException("Error creating output document", e);
        }
    }

    protected void getEncode() throws MessageEncodingException {
        URLBuilder urlBuilder;
        this.log.debug("Performing HTTP GET SAML 2 artifact encoding");
        MessageContext messageContext = this.getMessageContext();
        String endpointUrl = this.getEndpointURL((MessageContext<SAMLObject>)messageContext).toString();
        try {
            urlBuilder = new URLBuilder(endpointUrl);
        }
        catch (MalformedURLException e) {
            throw new MessageEncodingException("Endpoint URL " + endpointUrl + " is not a valid URL", (Exception)e);
        }
        List<Pair<String, String>> queryParams2 = urlBuilder.getQueryParams();
        queryParams2.clear();
        AbstractSAML2Artifact artifact = this.buildArtifact((MessageContext<SAMLObject>)messageContext);
        if (artifact == null) {
            this.log.error("Unable to build artifact for message to relying party");
            throw new MessageEncodingException("Unable to build artifact for message to relying party");
        }
        queryParams2.add(new Pair<String, String>("SAMLart", artifact.base64Encode()));
        String relayState = SAMLBindingSupport.getRelayState((MessageContext<SAMLObject>)messageContext);
        if (SAMLBindingSupport.checkRelayState(relayState)) {
            queryParams2.add(new Pair<String, String>("RelayState", relayState));
        }
        HttpServletResponse response = this.getHttpServletResponse();
        try {
            response.sendRedirect(urlBuilder.buildURL());
        }
        catch (IOException e) {
            throw new MessageEncodingException("Problem sending HTTP redirect", (Exception)e);
        }
    }

    @Nonnull
    protected AbstractSAML2Artifact buildArtifact(@Nonnull MessageContext<SAMLObject> messageContext) throws MessageEncodingException {
        SAML2ArtifactBuilder artifactBuilder;
        String requester = this.getInboundMessageIssuer(messageContext);
        String issuer = this.getOutboundMessageIssuer(messageContext);
        if (requester == null || issuer == null) {
            throw new MessageEncodingException("Unable to obtain issuer or relying party for message encoding");
        }
        byte[] artifactType = this.getSAMLArtifactType(messageContext);
        if (artifactType != null) {
            artifactBuilder = SAMLConfigurationSupport.getSAML2ArtifactBuilderFactory().getArtifactBuilder(artifactType);
        } else {
            artifactBuilder = SAMLConfigurationSupport.getSAML2ArtifactBuilderFactory().getArtifactBuilder(this.defaultArtifactType);
            this.storeSAMLArtifactType(messageContext, this.defaultArtifactType);
        }
        Object artifact = artifactBuilder.buildArtifact(messageContext);
        if (artifact == null) {
            this.log.error("Unable to build artifact for message to relying party");
            throw new MessageEncodingException("Unable to build artifact for message to relying party");
        }
        String encodedArtifact = ((AbstractSAMLArtifact)artifact).base64Encode();
        try {
            this.artifactMap.put(encodedArtifact, requester, issuer, (SAMLObject)messageContext.getMessage());
        }
        catch (IOException e) {
            this.log.error("Unable to store message mapping for artifact", e);
            throw new MessageEncodingException("Unable to store message mapping for artifact", (Exception)e);
        }
        return artifact;
    }

    @Nullable
    private String getOutboundMessageIssuer(@Nonnull MessageContext<SAMLObject> messageContext) {
        SAMLSelfEntityContext selfCtx = (SAMLSelfEntityContext)messageContext.getSubcontext(SAMLSelfEntityContext.class);
        if (selfCtx == null) {
            return null;
        }
        return selfCtx.getEntityId();
    }

    @Nullable
    private String getInboundMessageIssuer(@Nonnull MessageContext<SAMLObject> messageContext) {
        SAMLPeerEntityContext peerCtx = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class);
        if (peerCtx == null) {
            return null;
        }
        return peerCtx.getEntityId();
    }

    private void storeSAMLArtifactType(@Nonnull MessageContext<SAMLObject> messageContext, @Nonnull @NotEmpty byte[] artifactType) {
        ((SAMLArtifactContext)messageContext.getSubcontext(SAMLArtifactContext.class, true)).setArtifactType(artifactType);
    }

    @Nullable
    private byte[] getSAMLArtifactType(@Nonnull MessageContext<SAMLObject> messageContext) {
        return ((SAMLArtifactContext)messageContext.getSubcontext(SAMLArtifactContext.class, true)).getArtifactType();
    }
}

