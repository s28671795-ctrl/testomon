/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.security.Key;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.SecurityHeaderOrder;
import org.apache.wss4j.stax.impl.processor.output.OutputProcessorUtils;
import org.apache.wss4j.stax.impl.processor.output.UsernameTokenOutputProcessor;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureOutputProcessor;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.SignaturePartDef;
import org.apache.xml.security.stax.impl.algorithms.SignatureAlgorithm;
import org.apache.xml.security.stax.impl.processor.output.AbstractSignatureEndingOutputProcessor;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityEvent.SignatureValueSecurityEvent;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;

public class WSSSignatureEndingOutputProcessor
extends AbstractSignatureEndingOutputProcessor {
    private AbstractSignatureEndingOutputProcessor.SignedInfoProcessor signedInfoProcessor;

    public WSSSignatureEndingOutputProcessor(WSSSignatureOutputProcessor signatureOutputProcessor) throws XMLSecurityException {
        super(signatureOutputProcessor);
        this.addAfterProcessor(WSSSignatureOutputProcessor.class.getName());
        this.addAfterProcessor(UsernameTokenOutputProcessor.class.getName());
    }

    @Override
    protected AbstractSignatureEndingOutputProcessor.SignedInfoProcessor newSignedInfoProcessor(SignatureAlgorithm signatureAlgorithm, String signatureId, XMLSecStartElement xmlSecStartElement, OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
        while (!WSSConstants.TAG_WSSE_SECURITY.equals(xmlSecStartElement.getName())) {
            xmlSecStartElement = xmlSecStartElement.getParentXMLSecStartElement();
        }
        this.signedInfoProcessor = new AbstractSignatureEndingOutputProcessor.SignedInfoProcessor(signatureAlgorithm, signatureId, xmlSecStartElement);
        this.signedInfoProcessor.setXMLSecurityProperties(this.getSecurityProperties());
        this.signedInfoProcessor.setAction(this.getAction());
        this.signedInfoProcessor.addAfterProcessor(WSSSignatureEndingOutputProcessor.class.getName());
        this.signedInfoProcessor.init(outputProcessorChain);
        return this.signedInfoProcessor;
    }

    @Override
    public void processHeaderEvent(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        super.processHeaderEvent(outputProcessorChain);
        SignatureValueSecurityEvent signatureValueSecurityEvent = new SignatureValueSecurityEvent();
        signatureValueSecurityEvent.setSignatureValue(this.signedInfoProcessor.getSignatureValue());
        signatureValueSecurityEvent.setCorrelationID(this.signedInfoProcessor.getSignatureId());
        outputProcessorChain.getSecurityContext().registerSecurityEvent(signatureValueSecurityEvent);
    }

    @Override
    protected void createKeyInfoStructureForSignature(OutputProcessorChain outputProcessorChain, OutboundSecurityToken securityToken, boolean useSingleCertificate) throws XMLStreamException, XMLSecurityException {
        if (securityToken.getCustomTokenReference() != null) {
            this.outputDOMElement(securityToken.getCustomTokenReference(), outputProcessorChain);
            return;
        }
        SecurityTokenConstants.KeyIdentifier keyIdentifier = null;
        if (!this.getSecurityProperties().getSignatureKeyIdentifiers().isEmpty()) {
            keyIdentifier = this.getSecurityProperties().getSignatureKeyIdentifiers().get(0);
        }
        X509Certificate[] x509Certificates = securityToken.getX509Certificates();
        if (WSSecurityTokenConstants.KeyIdentifier_KeyValue.equals(keyIdentifier)) {
            WSSUtils.createKeyValueTokenStructure((AbstractOutputProcessor)this, outputProcessorChain, x509Certificates);
        } else {
            boolean isSAMLToken = false;
            ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(2);
            attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
            if (WSSecurityTokenConstants.SAML_10_TOKEN.equals(securityToken.getTokenType()) || WSSecurityTokenConstants.SAML_11_TOKEN.equals(securityToken.getTokenType())) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1"));
                isSAMLToken = true;
            } else if (WSSecurityTokenConstants.SAML_20_TOKEN.equals(securityToken.getTokenType())) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0"));
                isSAMLToken = true;
            } else if (WSSecurityTokenConstants.KERBEROS_TOKEN.equals(securityToken.getTokenType())) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ"));
            } else if (WSSecurityTokenConstants.EncryptedKeyToken.equals(securityToken.getTokenType()) || WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER.equals(keyIdentifier) || WSSecurityTokenConstants.KeyIdentifier_EncryptedKey.equals(keyIdentifier)) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey"));
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier) && !useSingleCertificate) {
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1"));
            }
            this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
            String tokenId = securityToken.getId();
            if (isSAMLToken) {
                WSSUtils.createSAMLKeyIdentifierStructure(this, outputProcessorChain, securityToken.getTokenType(), tokenId);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                String identifier = securityToken.getSha1Identifier();
                if (identifier != null) {
                    WSSUtils.createEncryptedKeySha1IdentifierStructure((AbstractOutputProcessor)this, outputProcessorChain, identifier);
                } else {
                    Key key = securityToken.getSecretKey(this.getSecurityProperties().getSignatureAlgorithm());
                    WSSUtils.createEncryptedKeySha1IdentifierStructure((AbstractOutputProcessor)this, outputProcessorChain, key);
                }
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                String identifier = securityToken.getSha1Identifier();
                WSSUtils.createKerberosSha1IdentifierStructure(this, outputProcessorChain, identifier);
            } else if (WSSecurityTokenConstants.EncryptedKeyToken.equals(securityToken.getTokenType()) || WSSecurityTokenConstants.KeyIdentifier_EncryptedKey.equals(keyIdentifier)) {
                String id = securityToken.getId();
                WSSUtils.createBSTReferenceStructure(this, outputProcessorChain, id, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey", true);
            } else if (WSSecurityTokenConstants.KeyIdentifier_IssuerSerial.equals(keyIdentifier)) {
                WSSUtils.createX509IssuerSerialStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KeyIdentifier_SkiKeyIdentifier.equals(keyIdentifier)) {
                WSSUtils.createX509SubjectKeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KeyIdentifier_X509KeyIdentifier.equals(keyIdentifier)) {
                WSSUtils.createX509KeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_THUMBPRINT_IDENTIFIER.equals(keyIdentifier)) {
                WSSUtils.createThumbprintKeyIdentifierStructure(this, outputProcessorChain, x509Certificates);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_SECURITY_TOKEN_DIRECT_REFERENCE.equals(keyIdentifier)) {
                String valueType;
                boolean included = true;
                if (WSSecurityTokenConstants.SAML_20_TOKEN.equals(securityToken.getTokenType())) {
                    valueType = null;
                } else if (WSSecurityTokenConstants.KERBEROS_TOKEN.equals(securityToken.getTokenType())) {
                    valueType = "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ";
                } else if (WSSecurityTokenConstants.DerivedKeyToken.equals(securityToken.getTokenType())) {
                    boolean use200512Namespace = ((WSSSecurityProperties)this.getSecurityProperties()).isUse200512Namespace();
                    valueType = use200512Namespace ? "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk" : "http://schemas.xmlsoap.org/ws/2005/02/sc/dk";
                } else if (WSSecurityTokenConstants.SPNEGO_CONTEXT_TOKEN.equals(securityToken.getTokenType()) || WSSecurityTokenConstants.SECURITY_CONTEXT_TOKEN.equals(securityToken.getTokenType()) || WSSecurityTokenConstants.SECURE_CONVERSATION_TOKEN.equals(securityToken.getTokenType())) {
                    boolean use200512Namespace = ((WSSSecurityProperties)this.getSecurityProperties()).isUse200512Namespace();
                    valueType = use200512Namespace ? "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct" : "http://schemas.xmlsoap.org/ws/2005/02/sc/sct";
                    included = ((WSSSecurityProperties)this.getSecurityProperties()).isIncludeSignatureToken();
                } else {
                    valueType = useSingleCertificate ? "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509v3" : "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-x509-token-profile-1.0#X509PKIPathv1";
                }
                WSSUtils.createBSTReferenceStructure(this, outputProcessorChain, tokenId, valueType, included);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_EMBEDDED_KEY_IDENTIFIER_REF.equals(keyIdentifier)) {
                WSSUtils.createEmbeddedKeyIdentifierStructure(this, outputProcessorChain, securityToken.getTokenType(), tokenId);
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_USERNAME_TOKEN_REFERENCE.equals(keyIdentifier)) {
                WSSUtils.createUsernameTokenReferenceStructure(this, outputProcessorChain, tokenId);
            } else {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, "unsupportedSecurityToken", new Object[]{keyIdentifier});
            }
            this.createEndElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE);
        }
    }

    @Override
    protected void createTransformsStructureForSignature(OutputProcessorChain subOutputProcessorChain, SignaturePartDef signaturePartDef) throws XMLStreamException, XMLSecurityException {
        String[] transforms = signaturePartDef.getTransforms();
        if (transforms != null && transforms.length > 0) {
            this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_Transforms, false, null);
            if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(transforms[0])) {
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_NULL_Algorithm, transforms[0]));
                this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_Transform, false, attributes);
                if (transforms.length >= 2) {
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_TRANSFORMATION_PARAMETERS, false, null);
                    attributes = new ArrayList(1);
                    attributes.add(this.createAttribute(WSSConstants.ATT_NULL_Algorithm, transforms[1]));
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_CanonicalizationMethod, false, attributes);
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_CanonicalizationMethod);
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_WSSE_TRANSFORMATION_PARAMETERS);
                }
                this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_Transform);
            } else {
                for (int i = 0; i < transforms.length; ++i) {
                    String transform = transforms[i];
                    ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                    attributes.add(this.createAttribute(WSSConstants.ATT_NULL_Algorithm, transform));
                    this.createStartElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_Transform, false, attributes);
                    if (this.getSecurityProperties().isAddExcC14NInclusivePrefixes() && !"http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(transform) && !"http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform".equals(transform)) {
                        attributes = new ArrayList(1);
                        attributes.add(this.createAttribute(XMLSecurityConstants.ATT_NULL_PrefixList, signaturePartDef.getInclusiveNamespacesPrefixes()));
                        this.createStartElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces, true, attributes);
                        this.createEndElementAndOutputAsEvent(subOutputProcessorChain, XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
                    }
                    this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_Transform);
                }
            }
            this.createEndElementAndOutputAsEvent(subOutputProcessorChain, WSSConstants.TAG_dsig_Transforms);
        }
    }

    @Override
    public void flushBufferAndCallbackAfterHeader(OutputProcessorChain outputProcessorChain, Deque<XMLSecEvent> xmlSecEventDeque) throws XMLStreamException, XMLSecurityException {
        String actor = ((WSSSecurityProperties)this.getSecurityProperties()).getActor();
        block3: while (!xmlSecEventDeque.isEmpty()) {
            XMLSecEvent xmlSecEvent = xmlSecEventDeque.pop();
            switch (xmlSecEvent.getEventType()) {
                case 1: {
                    if (WSSUtils.isSecurityHeaderElement(xmlSecEvent, actor)) {
                        OutputProcessorUtils.updateSecurityHeaderOrder(outputProcessorChain, WSSConstants.TAG_dsig_Signature, this.getAction(), true);
                        List securityHeaderOrderList = outputProcessorChain.getSecurityContext().getAsList(SecurityHeaderOrder.class);
                        ArrayList tmpList = null;
                        if (securityHeaderOrderList != null) {
                            tmpList = new ArrayList(securityHeaderOrderList);
                            securityHeaderOrderList.clear();
                        }
                        outputProcessorChain.reset();
                        outputProcessorChain.processEvent(xmlSecEvent);
                        if (securityHeaderOrderList == null) break block3;
                        securityHeaderOrderList.addAll(tmpList);
                        break block3;
                    }
                }
                default: {
                    outputProcessorChain.reset();
                    outputProcessorChain.processEvent(xmlSecEvent);
                    continue block3;
                }
            }
        }
        super.flushBufferAndCallbackAfterHeader(outputProcessorChain, xmlSecEventDeque);
    }
}

