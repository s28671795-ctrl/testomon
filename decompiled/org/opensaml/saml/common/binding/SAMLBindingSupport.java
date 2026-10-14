/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletRequest
 *  org.opensaml.messaging.MessageException
 *  org.opensaml.messaging.context.MessageContext
 */
package org.opensaml.saml.common.binding;

import com.google.common.base.Strings;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.servlet.http.HttpServletRequest;
import net.shibboleth.utilities.java.support.annotation.constraint.NotEmpty;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.opensaml.messaging.MessageException;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.common.binding.BindingException;
import org.opensaml.saml.common.messaging.context.SAMLBindingContext;
import org.opensaml.saml.common.messaging.context.SAMLEndpointContext;
import org.opensaml.saml.common.messaging.context.SAMLPeerEntityContext;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class SAMLBindingSupport {
    @Nonnull
    private static final Logger LOG = LoggerFactory.getLogger(SAMLBindingSupport.class);

    private SAMLBindingSupport() {
    }

    @Nullable
    @NotEmpty
    public static String getRelayState(@Nonnull MessageContext<SAMLObject> messageContext) {
        SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class);
        if (bindingContext == null) {
            return null;
        }
        return bindingContext.getRelayState();
    }

    public static void setRelayState(@Nonnull MessageContext<SAMLObject> messageContext, @Nullable String relayState) {
        ((SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, true)).setRelayState(relayState);
    }

    public static boolean checkRelayState(@Nullable String relayState) {
        if (!Strings.isNullOrEmpty(relayState)) {
            if (relayState.getBytes().length > 80) {
                LOG.warn("Relay state exceeds 80 bytes: {}", (Object)relayState);
            }
            return true;
        }
        return false;
    }

    @Nonnull
    public static URI getEndpointURL(@Nonnull MessageContext<SAMLObject> messageContext) throws BindingException {
        SAMLPeerEntityContext peerContext = (SAMLPeerEntityContext)messageContext.getSubcontext(SAMLPeerEntityContext.class, false);
        if (peerContext == null) {
            throw new BindingException("Message context contained no PeerEntityContext");
        }
        SAMLEndpointContext endpointContext = (SAMLEndpointContext)peerContext.getSubcontext(SAMLEndpointContext.class, false);
        if (endpointContext == null) {
            throw new BindingException("PeerEntityContext contained no SAMLEndpointContext");
        }
        Endpoint endpoint = endpointContext.getEndpoint();
        if (endpoint == null) {
            throw new BindingException("Endpoint for relying party was null.");
        }
        SAMLObject message = (SAMLObject)messageContext.getMessage();
        if ((message instanceof StatusResponseType || message instanceof Response) && !Strings.isNullOrEmpty(endpoint.getResponseLocation())) {
            try {
                return new URI(endpoint.getResponseLocation());
            }
            catch (URISyntaxException e) {
                throw new BindingException("The endpoint response location " + endpoint.getResponseLocation() + " is not a valid URL", e);
            }
        }
        if (Strings.isNullOrEmpty(endpoint.getLocation())) {
            throw new BindingException("Relying party endpoint location was null or empty.");
        }
        try {
            return new URI(endpoint.getLocation());
        }
        catch (URISyntaxException e) {
            throw new BindingException("The endpoint location " + endpoint.getLocation() + " is not a valid URL", e);
        }
    }

    public static void setSAML1ResponseRecipient(@Nonnull SAMLObject outboundMessage, @Nonnull @NotEmpty String endpointURL) {
        if (outboundMessage instanceof ResponseAbstractType) {
            ((ResponseAbstractType)outboundMessage).setRecipient(endpointURL);
        }
    }

    public static void setSAML2Destination(@Nonnull SAMLObject outboundMessage, @Nonnull @NotEmpty String endpointURL) {
        if (outboundMessage instanceof org.opensaml.saml.saml2.core.RequestAbstractType) {
            ((org.opensaml.saml.saml2.core.RequestAbstractType)outboundMessage).setDestination(endpointURL);
        } else if (outboundMessage instanceof StatusResponseType) {
            ((StatusResponseType)outboundMessage).setDestination(endpointURL);
        }
    }

    public static boolean isMessageSigned(@Nonnull MessageContext<SAMLObject> messageContext) {
        SAMLObject samlMessage = (SAMLObject)Constraint.isNotNull(messageContext.getMessage(), "SAML message was not present in message context");
        if (samlMessage instanceof SignableSAMLObject && ((SignableSAMLObject)samlMessage).isSigned()) {
            return true;
        }
        SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, false);
        if (bindingContext != null) {
            return bindingContext.hasBindingSignature();
        }
        return false;
    }

    public static boolean isSigningCapableBinding(@Nonnull MessageContext<SAMLObject> messageContext) {
        SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class);
        if (bindingContext != null && bindingContext.getBindingDescriptor() != null) {
            return bindingContext.getBindingDescriptor().isSignatureCapable();
        }
        return false;
    }

    public static boolean isIntendedDestinationEndpointURIRequired(@Nonnull MessageContext<SAMLObject> messageContext) {
        SAMLBindingContext bindingContext = (SAMLBindingContext)messageContext.getSubcontext(SAMLBindingContext.class, false);
        if (bindingContext == null) {
            return false;
        }
        return bindingContext.isIntendedDestinationEndpointURIRequired();
    }

    @Nullable
    public static String getIntendedDestinationEndpointURI(@Nonnull MessageContext<SAMLObject> messageContext) throws MessageException {
        SAMLObject samlMessage = (SAMLObject)Constraint.isNotNull(messageContext.getMessage(), "SAML message was not present in message context");
        String messageDestination = null;
        if (samlMessage instanceof org.opensaml.saml.saml2.core.RequestAbstractType) {
            org.opensaml.saml.saml2.core.RequestAbstractType request = (org.opensaml.saml.saml2.core.RequestAbstractType)samlMessage;
            messageDestination = StringSupport.trimOrNull(request.getDestination());
        } else if (samlMessage instanceof StatusResponseType) {
            StatusResponseType response = (StatusResponseType)samlMessage;
            messageDestination = StringSupport.trimOrNull(response.getDestination());
        } else if (samlMessage instanceof ResponseAbstractType) {
            ResponseAbstractType response = (ResponseAbstractType)samlMessage;
            messageDestination = StringSupport.trimOrNull(response.getRecipient());
        } else {
            if (samlMessage instanceof RequestAbstractType) {
                return null;
            }
            LOG.error("Unknown SAML message type encountered: {}", (Object)samlMessage.getElementQName().toString());
            throw new MessageException("Invalid SAML message type encountered");
        }
        return messageDestination;
    }

    @Nonnull
    public static String getActualReceiverEndpointURI(@Nonnull MessageContext<SAMLObject> messageContext, @Nonnull HttpServletRequest request) throws MessageException {
        Constraint.isNotNull(request, "HttpServletRequest cannot be null");
        return request.getRequestURL().toString();
    }

    @Nonnull
    public static int convertSAML2ArtifactEndpointIndex(@Nonnull byte[] artifactEndpointIndex) {
        Constraint.isNotNull(artifactEndpointIndex, "Artifact endpoint index cannot be null");
        Constraint.isTrue(artifactEndpointIndex.length == 2, "Artifact endpoint index length was not 2, was: " + artifactEndpointIndex.length);
        short value = ByteBuffer.wrap(artifactEndpointIndex).order(ByteOrder.BIG_ENDIAN).getShort();
        return (int)Constraint.isGreaterThanOrEqual(0L, value, "Input value was too large, resulting in a negative 16-bit short");
    }
}

