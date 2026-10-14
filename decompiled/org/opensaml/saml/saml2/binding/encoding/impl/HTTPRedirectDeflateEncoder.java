/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.servlet.http.HttpServletResponse
 *  org.opensaml.messaging.context.MessageContext
 *  org.opensaml.messaging.encoder.MessageEncodingException
 */
package org.opensaml.saml.saml2.binding.encoding.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.util.List;
import java.util.zip.Deflater;
import java.util.zip.DeflaterOutputStream;
import javax.servlet.http.HttpServletResponse;
import net.shibboleth.utilities.java.support.codec.Base64Support;
import net.shibboleth.utilities.java.support.collection.Pair;
import net.shibboleth.utilities.java.support.net.HttpServletSupport;
import net.shibboleth.utilities.java.support.net.URLBuilder;
import net.shibboleth.utilities.java.support.xml.SerializeSupport;
import org.opensaml.messaging.context.MessageContext;
import org.opensaml.messaging.encoder.MessageEncodingException;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.common.SignableSAMLObject;
import org.opensaml.saml.common.binding.SAMLBindingSupport;
import org.opensaml.saml.common.messaging.SAMLMessageSecuritySupport;
import org.opensaml.saml.saml2.binding.encoding.impl.BaseSAML2MessageEncoder;
import org.opensaml.saml.saml2.core.RequestAbstractType;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.opensaml.security.SecurityException;
import org.opensaml.security.credential.Credential;
import org.opensaml.security.credential.CredentialSupport;
import org.opensaml.xmlsec.SignatureSigningParameters;
import org.opensaml.xmlsec.crypto.XMLSigningUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HTTPRedirectDeflateEncoder
extends BaseSAML2MessageEncoder {
    private final Logger log = LoggerFactory.getLogger(HTTPRedirectDeflateEncoder.class);

    @Override
    public String getBindingURI() {
        return "urn:oasis:names:tc:SAML:2.0:bindings:HTTP-Redirect";
    }

    protected void doEncode() throws MessageEncodingException {
        MessageContext messageContext = this.getMessageContext();
        SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
        String endpointURL = this.getEndpointURL((MessageContext<SAMLObject>)messageContext).toString();
        this.removeSignature(outboundMessage);
        String encodedMessage = this.deflateAndBase64Encode(outboundMessage);
        String redirectURL = this.buildRedirectURL((MessageContext<SAMLObject>)messageContext, endpointURL, encodedMessage);
        HttpServletResponse response = this.getHttpServletResponse();
        HttpServletSupport.addNoCacheHeaders(response);
        HttpServletSupport.setUTF8Encoding(response);
        try {
            response.sendRedirect(redirectURL);
        }
        catch (IOException e) {
            throw new MessageEncodingException("Problem sending HTTP redirect", (Exception)e);
        }
    }

    protected void removeSignature(SAMLObject message) {
        SignableSAMLObject signableMessage;
        if (message instanceof SignableSAMLObject && (signableMessage = (SignableSAMLObject)message).isSigned()) {
            this.log.debug("Removing SAML protocol message signature");
            signableMessage.setSignature(null);
        }
    }

    protected String deflateAndBase64Encode(SAMLObject message) throws MessageEncodingException {
        this.log.debug("Deflating and Base64 encoding SAML message");
        try {
            String messageStr = SerializeSupport.nodeToString(this.marshallMessage(message));
            ByteArrayOutputStream bytesOut = new ByteArrayOutputStream();
            Deflater deflater = new Deflater(8, true);
            DeflaterOutputStream deflaterStream = new DeflaterOutputStream((OutputStream)bytesOut, deflater);
            deflaterStream.write(messageStr.getBytes("UTF-8"));
            deflaterStream.finish();
            return Base64Support.encode(bytesOut.toByteArray(), false);
        }
        catch (IOException e) {
            throw new MessageEncodingException("Unable to DEFLATE and Base64 encode SAML message", (Exception)e);
        }
    }

    protected String buildRedirectURL(MessageContext<SAMLObject> messageContext, String endpoint, String message) throws MessageEncodingException {
        SignatureSigningParameters signingParameters;
        this.log.debug("Building URL to redirect client to");
        URLBuilder urlBuilder = null;
        try {
            urlBuilder = new URLBuilder(endpoint);
        }
        catch (MalformedURLException e) {
            throw new MessageEncodingException("Endpoint URL " + endpoint + " is not a valid URL", (Exception)e);
        }
        List<Pair<String, String>> queryParams2 = urlBuilder.getQueryParams();
        queryParams2.clear();
        SAMLObject outboundMessage = (SAMLObject)messageContext.getMessage();
        if (outboundMessage instanceof RequestAbstractType) {
            queryParams2.add(new Pair<String, String>("SAMLRequest", message));
        } else if (outboundMessage instanceof StatusResponseType) {
            queryParams2.add(new Pair<String, String>("SAMLResponse", message));
        } else {
            throw new MessageEncodingException("SAML message is neither a SAML RequestAbstractType or StatusResponseType");
        }
        String relayState = SAMLBindingSupport.getRelayState(messageContext);
        if (SAMLBindingSupport.checkRelayState(relayState)) {
            queryParams2.add(new Pair<String, String>("RelayState", relayState));
        }
        if ((signingParameters = SAMLMessageSecuritySupport.getContextSigningParameters(messageContext)) != null && signingParameters.getSigningCredential() != null) {
            String sigAlgURI = this.getSignatureAlgorithmURI(signingParameters);
            Pair<String, String> sigAlg = new Pair<String, String>("SigAlg", sigAlgURI);
            queryParams2.add(sigAlg);
            String sigMaterial = urlBuilder.buildQueryString();
            queryParams2.add(new Pair<String, String>("Signature", this.generateSignature(signingParameters.getSigningCredential(), sigAlgURI, sigMaterial)));
        } else {
            this.log.debug("No signing credential was supplied, skipping HTTP-Redirect DEFLATE signing");
        }
        return urlBuilder.buildURL();
    }

    protected String getSignatureAlgorithmURI(SignatureSigningParameters signingParameters) throws MessageEncodingException {
        if (signingParameters.getSignatureAlgorithm() != null) {
            return signingParameters.getSignatureAlgorithm();
        }
        throw new MessageEncodingException("The signing algorithm URI could not be determined");
    }

    protected String generateSignature(Credential signingCredential, String algorithmURI, String queryString) throws MessageEncodingException {
        this.log.debug(String.format("Generating signature with key type '%s', algorithm URI '%s' over query string '%s'", CredentialSupport.extractSigningKey(signingCredential).getAlgorithm(), algorithmURI, queryString));
        String b64Signature = null;
        try {
            byte[] rawSignature = XMLSigningUtil.signWithURI(signingCredential, algorithmURI, queryString.getBytes("UTF-8"));
            b64Signature = Base64Support.encode(rawSignature, false);
            this.log.debug("Generated digital signature value (base64-encoded) {}", (Object)b64Signature);
        }
        catch (SecurityException e) {
            this.log.error("Error during URL signing process", e);
            throw new MessageEncodingException("Unable to sign URL query string", (Exception)e);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            // empty catch block
        }
        return b64Signature;
    }
}

