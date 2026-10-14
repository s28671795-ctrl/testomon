/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.encoder.MessageEncodingException
 */
package org.opensaml.saml.saml1.binding.encoding.impl;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.annotation.constraint.NonnullAfterInit;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.component.ComponentInitializationException;
import net.shibboleth.utilities.java.support.component.ComponentSupport;
import net.shibboleth.utilities.java.support.component.DestructableComponent;
import net.shibboleth.utilities.java.support.component.InitializableComponent;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.net.URLBuilder;
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
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactBuilder;
import org.opensaml.saml.saml1.binding.artifact.SAML1ArtifactType0001;
import org.opensaml.saml.saml1.binding.encoding.impl.BaseSAML1MessageEncoder;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPArtifactEncoder
extends BaseSAML1MessageEncoder {
    @Nonnull
    private final Logger log = LoggerFactory.getLogger(HTTPArtifactEncoder.class);
    @NonnullAfterInit
    private SAMLArtifactMap artifactMap;
    @Nonnull
    @NotEmpty
    private byte[] defaultArtifactType = SAML1ArtifactType0001.TYPE_CODE;

    @Override
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:1.0:profiles:artifact-01";
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
    }

    protected void doDestroy() {
        this.artifactMap = null;
        super.doDestroy();
    }

    protected void doEncode() throws MessageEncodingException {
        SAML1ArtifactBuilder artifactBuilder;
        byte[] artifactType;
        URLBuilder urlBuilder;
        MessageContext messageContext = this.getMessageContext();
        String requester = this.getInboundMessageIssuer((MessageContext<SAMLObject>)messageContext);
        String issuer = this.getOutboundMessageIssuer((MessageContext<SAMLObject>)messageContext);
        if (requester == null || issuer == null) {
            throw new MessageEncodingException("Unable to obtain issuer or relying party for message encoding");
        }
        String endpointUrl = this.getEndpointURL((MessageContext<SAMLObject>)messageContext).toString();
        try {
            urlBuilder = new URLBuilder(endpointUrl);
        }
        catch (MalformedURLException e) {
            throw new MessageEncodingException("Endpoint URL " + endpointUrl + " is not a valid URL", (Exception)e);
        }
        List<Pair<String, String>> queryParams2 = urlBuilder.getQueryParams();
        queryParams2.clear();
        String relayState = SAMLBindingSupport.getRelayState((MessageContext<SAMLObject>)messageContext);
        if (SAMLBindingSupport.checkRelayState(relayState)) {
            queryParams2.add(new Pair<String, String>("TARGET", relayState));
        }
        if ((artifactType = this.getSAMLArtifactType((MessageContext<SAMLObject>)messageContext)) != null) {
            artifactBuilder = SAMLConfigurationSupport.getSAML1ArtifactBuilderFactory().getArtifactBuilder(artifactType);
        } else {
            artifactBuilder = SAMLConfigurationSupport.getSAML1ArtifactBuilderFactory().getArtifactBuilder(this.defaultArtifactType);
            this.storeSAMLArtifactType((MessageContext<SAMLObject>)messageContext, this.defaultArtifactType);
        }
        SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
        if (!(outboundMessage instanceof Response)) {
            throw new MessageEncodingException("Outbound message was not a SAML 1 Response");
        }
        Response samlResponse = (Response)outboundMessage;
        for (Assertion assertion : samlResponse.getAssertions()) {
            Object artifact = artifactBuilder.buildArtifact((MessageContext<SAMLObject>)messageContext, assertion);
            if (artifact == null) {
                this.log.error("Unable to build artifact for message to relying party {}", (Object)requester);
                throw new MessageEncodingException("Unable to build artifact for message to relying party");
            }
            try {
                this.artifactMap.put(((AbstractSAMLArtifact)artifact).base64Encode(), requester, issuer, assertion);
            }
            catch (IOException e) {
                this.log.error("Unable to store assertion mapping for artifact", e);
                throw new MessageEncodingException("Unable to store assertion mapping for artifact", (Exception)e);
            }
            String artifactString = ((AbstractSAMLArtifact)artifact).base64Encode();
            queryParams2.add(new Pair<String, String>("SAMLart", artifactString));
        }
        String encodedEndpoint = urlBuilder.buildURL();
        this.log.debug("Sending redirect to URL {} for relying party {}", (Object)encodedEndpoint, (Object)requester);
        HttpServletResponse response = this.getHttpServletResponse();
        if (response == null) {
            throw new MessageEncodingException("HttpServletResponse was null");
        }
        try {
            response.sendRedirect(encodedEndpoint);
        }
        catch (IOException e) {
            throw new MessageEncodingException("Problem sending HTTP redirect", (Exception)e);
        }
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

