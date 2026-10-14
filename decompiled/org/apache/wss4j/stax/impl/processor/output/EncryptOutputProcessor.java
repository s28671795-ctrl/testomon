/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.EncryptEndingOutputProcessor;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.encryption.XMLCipherUtil;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.JCEAlgorithmMapper;
import org.apache.xml.security.stax.config.TransformerAlgorithmMapper;
import org.apache.xml.security.stax.ext.AbstractOutputProcessor;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.EncryptionPartDef;
import org.apache.xml.security.stax.impl.processor.output.AbstractEncryptOutputProcessor;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.stax.securityToken.OutboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenConstants;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EncryptOutputProcessor
extends AbstractEncryptOutputProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(EncryptOutputProcessor.class);

    @Override
    public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
        super.init(outputProcessorChain);
        EncryptEndingOutputProcessor encryptEndingOutputProcessor = new EncryptEndingOutputProcessor();
        encryptEndingOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
        encryptEndingOutputProcessor.setAction(this.getAction());
        encryptEndingOutputProcessor.init(outputProcessorChain);
    }

    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        if (xmlSecEvent.getEventType() == 1) {
            SecurePart securePart;
            XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
            if (this.getActiveInternalEncryptionOutputProcessor() == null && (securePart = this.securePartMatches(xmlSecStartElement, outputProcessorChain, "encryptionParts")) != null) {
                LOG.debug("Matched encryptionPart for encryption");
                String tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
                SecurityTokenProvider<OutboundSecurityToken> securityTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(tokenId);
                OutboundSecurityToken securityToken = securityTokenProvider.getSecurityToken();
                EncryptionPartDef encryptionPartDef = new EncryptionPartDef();
                encryptionPartDef.setSecurePart(securePart);
                encryptionPartDef.setModifier(securePart.getModifier());
                encryptionPartDef.setEncRefId(IDGenerator.generateID(null));
                Map dynamicSecureParts = outputProcessorChain.getSecurityContext().getAsMap("signatureParts");
                if (dynamicSecureParts != null && securePart.getName() != null && securePart.equals(dynamicSecureParts.get(securePart.getName()))) {
                    securePart.setIdToSign(encryptionPartDef.getEncRefId());
                    outputProcessorChain.getSecurityContext().putAsMap("signatureParts", securePart.getIdToSign(), securePart);
                }
                encryptionPartDef.setKeyId(securityTokenProvider.getId());
                encryptionPartDef.setSymmetricKey(securityToken.getSecretKey(this.getSecurityProperties().getEncryptionSymAlgorithm()));
                outputProcessorChain.getSecurityContext().putAsList(EncryptionPartDef.class, encryptionPartDef);
                InternalEncryptionOutputProcessor internalEncryptionOutputProcessor = new InternalEncryptionOutputProcessor(encryptionPartDef, xmlSecStartElement, outputProcessorChain.getDocumentContext().getEncoding(), securityToken);
                internalEncryptionOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                internalEncryptionOutputProcessor.setAction(this.getAction());
                internalEncryptionOutputProcessor.init(outputProcessorChain);
                this.setActiveInternalEncryptionOutputProcessor(internalEncryptionOutputProcessor);
                if ("Body".equals(xmlSecStartElement.getName().getLocalPart()) && WSSUtils.isInSOAPBody(xmlSecStartElement)) {
                    this.doFinalInternal(outputProcessorChain);
                    outputProcessorChain.removeProcessor(this);
                }
            }
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    @Override
    protected SecurePart securePartMatches(XMLSecStartElement xmlSecStartElement, Map<Object, SecurePart> secureParts) {
        if (!xmlSecStartElement.getOnElementDeclaredAttributes().isEmpty()) {
            SecurePart securePart;
            Attribute attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_WSU_ID);
            if (attribute != null && (securePart = secureParts.get(attribute.getValue())) != null) {
                return securePart;
            }
            attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_NULL_Id);
            if (attribute != null && (securePart = secureParts.get(attribute.getValue())) != null) {
                return securePart;
            }
            attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_NULL_ID);
            if (attribute != null && (securePart = secureParts.get(attribute.getValue())) != null) {
                return securePart;
            }
            attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_NULL_ASSERTION_ID);
            if (attribute != null && (securePart = secureParts.get(attribute.getValue())) != null) {
                return securePart;
            }
        }
        return secureParts.get(xmlSecStartElement.getName());
    }

    @Override
    public void doFinalInternal(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
        this.setupAttachmentEncryptionStreams(outputProcessorChain);
        super.doFinalInternal(outputProcessorChain);
    }

    protected void setupAttachmentEncryptionStreams(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
        SecurePart attachmentSecurePart = null;
        Map dynamicSecureParts = outputProcessorChain.getSecurityContext().getAsMap("encryptionParts");
        Iterator securePartsMapIterator = dynamicSecureParts.entrySet().iterator();
        String externalId = "";
        while (securePartsMapIterator.hasNext()) {
            Map.Entry securePartEntry = securePartsMapIterator.next();
            SecurePart securePart = (SecurePart)securePartEntry.getValue();
            String externalReference = securePart.getExternalReference();
            if (externalReference == null || !externalReference.startsWith("cid:")) continue;
            attachmentSecurePart = securePart;
            externalId = AttachmentUtils.getAttachmentId(externalReference);
            break;
        }
        if (attachmentSecurePart == null) {
            return;
        }
        CallbackHandler attachmentCallbackHandler = ((WSSSecurityProperties)this.getSecurityProperties()).getAttachmentCallbackHandler();
        if (attachmentCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"no attachment callbackhandler supplied"});
        }
        AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
        attachmentRequestCallback.setAttachmentId(externalId);
        try {
            attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
        }
        List<Attachment> attachments = attachmentRequestCallback.getAttachments();
        if (attachments != null) {
            for (int i = 0; i < attachments.size(); ++i) {
                Attachment attachment = attachments.get(i);
                String attachmentId = attachment.getId();
                String tokenId = (String)outputProcessorChain.getSecurityContext().get("PROP_USE_THIS_TOKEN_ID_FOR_ENCRYPTION");
                SecurityTokenProvider<OutboundSecurityToken> securityTokenProvider = outputProcessorChain.getSecurityContext().getSecurityTokenProvider(tokenId);
                OutboundSecurityToken securityToken = securityTokenProvider.getSecurityToken();
                EncryptionPartDef encryptionPartDef = new EncryptionPartDef();
                encryptionPartDef.setSecurePart(attachmentSecurePart);
                encryptionPartDef.setModifier(attachmentSecurePart.getModifier());
                encryptionPartDef.setCipherReferenceId(attachment.getId());
                encryptionPartDef.setMimeType(attachment.getMimeType());
                encryptionPartDef.setEncRefId(IDGenerator.generateID(null));
                encryptionPartDef.setKeyId(securityTokenProvider.getId());
                encryptionPartDef.setSymmetricKey(securityToken.getSecretKey(this.getSecurityProperties().getEncryptionSymAlgorithm()));
                outputProcessorChain.getSecurityContext().putAsList(EncryptionPartDef.class, encryptionPartDef);
                Attachment resultAttachment = new Attachment();
                resultAttachment.setId(attachmentId);
                resultAttachment.setMimeType("application/octet-stream");
                String encryptionSymAlgorithm = this.getSecurityProperties().getEncryptionSymAlgorithm();
                String jceAlgorithm = JCEAlgorithmMapper.translateURItoJCEID(encryptionSymAlgorithm);
                if (jceAlgorithm == null) {
                    throw new XMLSecurityException("algorithms.NoSuchMap", new Object[]{encryptionSymAlgorithm});
                }
                Cipher cipher = null;
                try {
                    cipher = Cipher.getInstance(jceAlgorithm);
                    int ivLen = JCEMapper.getIVLengthFromURI(encryptionSymAlgorithm) / 8;
                    byte[] iv = XMLSecurityConstants.generateBytes(ivLen);
                    AlgorithmParameterSpec paramSpec = XMLCipherUtil.constructBlockCipherParameters(encryptionSymAlgorithm, iv, this.getClass());
                    cipher.init(1, encryptionPartDef.getSymmetricKey(), paramSpec);
                }
                catch (Exception e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, e);
                }
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.putAll(attachment.getHeaders());
                resultAttachment.setSourceStream(AttachmentUtils.setupAttachmentEncryptionStream(cipher, SecurePart.Modifier.Element == encryptionPartDef.getModifier(), attachment, headers));
                resultAttachment.addHeaders(headers);
                AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
                attachmentResultCallback.setAttachmentId(attachmentId);
                attachmentResultCallback.setAttachment(resultAttachment);
                try {
                    attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
                    continue;
                }
                catch (Exception e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, e);
                }
            }
        }
    }

    class InternalEncryptionOutputProcessor
    extends AbstractEncryptOutputProcessor.AbstractInternalEncryptionOutputProcessor {
        private boolean doEncryptedHeader;
        private final OutboundSecurityToken securityToken;

        InternalEncryptionOutputProcessor(EncryptionPartDef encryptionPartDef, XMLSecStartElement xmlSecStartElement, String encoding, OutboundSecurityToken securityToken) throws XMLSecurityException, XMLStreamException {
            super(encryptionPartDef, xmlSecStartElement, encoding);
            this.doEncryptedHeader = false;
            this.addBeforeProcessor(EncryptEndingOutputProcessor.class.getName());
            this.addBeforeProcessor(InternalEncryptionOutputProcessor.class.getName());
            this.addAfterProcessor(EncryptOutputProcessor.class.getName());
            this.securityToken = securityToken;
        }

        @Override
        protected OutputStream applyTransforms(OutputStream outputStream2) throws XMLSecurityException {
            String compressionAlgorithm = ((WSSSecurityProperties)this.getSecurityProperties()).getEncryptionCompressionAlgorithm();
            if (compressionAlgorithm != null) {
                Class<?> transformerClass = TransformerAlgorithmMapper.getTransformerClass(compressionAlgorithm, XMLSecurityConstants.DIRECTION.OUT);
                try {
                    Constructor<?> constructor = transformerClass.getConstructor(OutputStream.class);
                    outputStream2 = (OutputStream)constructor.newInstance(outputStream2);
                }
                catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                    throw new XMLSecurityException(e);
                }
            }
            return outputStream2;
        }

        @Override
        protected void processEventInternal(XMLSecStartElement xmlSecStartElement, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            List<QName> elementPath = xmlSecStartElement.getElementPath();
            if (elementPath.size() == 3 && WSSUtils.isInSOAPHeader(elementPath) && SecurePart.Modifier.Content != super.getEncryptionPartDef().getModifier()) {
                boolean mustUnderstand;
                this.doEncryptedHeader = true;
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                String actor = ((WSSSecurityProperties)this.getSecurityProperties()).getActor();
                String soapMessageVersion = WSSUtils.getSOAPMessageVersionNamespace(xmlSecStartElement);
                if (actor != null && !actor.isEmpty()) {
                    if ("http://schemas.xmlsoap.org/soap/envelope/".equals(soapMessageVersion)) {
                        attributes.add(this.createAttribute(WSSConstants.ATT_SOAP11_ACTOR, actor));
                    } else {
                        attributes.add(this.createAttribute(WSSConstants.ATT_SOAP12_ROLE, actor));
                    }
                }
                if (mustUnderstand = ((WSSSecurityProperties)this.getSecurityProperties()).isMustUnderstand()) {
                    if ("http://schemas.xmlsoap.org/soap/envelope/".equals(soapMessageVersion)) {
                        attributes.add(this.createAttribute(WSSConstants.ATT_SOAP11_MUST_UNDERSTAND, "1"));
                    } else {
                        attributes.add(this.createAttribute(WSSConstants.ATT_SOAP12_MUST_UNDERSTAND, "true"));
                    }
                }
                this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_wsse11_EncryptedHeader, true, attributes);
            }
            super.processEventInternal(xmlSecStartElement, outputProcessorChain);
        }

        @Override
        protected void createKeyInfoStructure(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            this.createStartElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyInfo, true, null);
            if (this.securityToken.getCustomTokenReference() != null) {
                this.outputDOMElement(this.securityToken.getCustomTokenReference(), outputProcessorChain);
                this.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyInfo);
                return;
            }
            SecurityTokenConstants.KeyIdentifier keyIdentifier = ((WSSSecurityProperties)this.getSecurityProperties()).getEncryptionKeyIdentifier();
            if (WSSecurityTokenConstants.KEYIDENTIFIER_ENCRYPTED_KEY_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey"));
                this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
                if (this.securityToken.getSha1Identifier() != null) {
                    WSSUtils.createEncryptedKeySha1IdentifierStructure((AbstractOutputProcessor)this, outputProcessorChain, this.securityToken.getSha1Identifier());
                } else {
                    WSSUtils.createEncryptedKeySha1IdentifierStructure((AbstractOutputProcessor)this, outputProcessorChain, this.getEncryptionPartDef().getSymmetricKey());
                }
            } else if (WSSecurityTokenConstants.KEYIDENTIFIER_KERBEROS_SHA1_IDENTIFIER.equals(keyIdentifier)) {
                ArrayList<XMLSecAttribute> attributes = new ArrayList<XMLSecAttribute>(1);
                attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#Kerberosv5_AP_REQ"));
                this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
                WSSUtils.createKerberosSha1IdentifierStructure(this, outputProcessorChain, this.securityToken.getSha1Identifier());
            } else {
                ArrayList<XMLSecAttribute> attributes;
                boolean isSAMLToken = false;
                if (WSSecurityTokenConstants.KERBEROS_TOKEN.equals(this.securityToken.getTokenType())) {
                    attributes = new ArrayList<XMLSecAttribute>(2);
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ"));
                    this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
                } else if (WSSecurityTokenConstants.SAML_10_TOKEN.equals(this.securityToken.getTokenType()) || WSSecurityTokenConstants.SAML_11_TOKEN.equals(this.securityToken.getTokenType())) {
                    attributes = new ArrayList(2);
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV1.1"));
                    this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
                    isSAMLToken = true;
                } else if (WSSecurityTokenConstants.SAML_20_TOKEN.equals(this.securityToken.getTokenType())) {
                    attributes = new ArrayList(2);
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-saml-token-profile-1.1#SAMLV2.0"));
                    this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
                    isSAMLToken = true;
                } else if (WSSecurityTokenConstants.EncryptedKeyToken.equals(this.securityToken.getTokenType())) {
                    attributes = new ArrayList(2);
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSU_ID, IDGenerator.generateID(null)));
                    attributes.add(this.createAttribute(WSSConstants.ATT_WSSE11_TOKEN_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey"));
                    this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, false, attributes);
                } else {
                    this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE, true, null);
                }
                if (isSAMLToken) {
                    WSSUtils.createSAMLKeyIdentifierStructure(this, outputProcessorChain, this.securityToken.getTokenType(), this.getEncryptionPartDef().getKeyId());
                } else {
                    attributes = new ArrayList(1);
                    attributes.add(this.createAttribute(WSSConstants.ATT_NULL_URI, "#" + this.getEncryptionPartDef().getKeyId()));
                    if (WSSecurityTokenConstants.KERBEROS_TOKEN.equals(this.securityToken.getTokenType())) {
                        attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-kerberos-token-profile-1.1#GSS_Kerberosv5_AP_REQ"));
                    } else if (WSSecurityTokenConstants.DerivedKeyToken.equals(this.securityToken.getTokenType())) {
                        boolean use200512Namespace = ((WSSSecurityProperties)this.getSecurityProperties()).isUse200512Namespace();
                        if (use200512Namespace) {
                            attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE_TYPE, "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/dk"));
                        } else {
                            attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE_TYPE, "http://schemas.xmlsoap.org/ws/2005/02/sc/dk"));
                        }
                    } else if (WSSecurityTokenConstants.SPNEGO_CONTEXT_TOKEN.equals(this.securityToken.getTokenType()) || WSSecurityTokenConstants.SECURITY_CONTEXT_TOKEN.equals(this.securityToken.getTokenType()) || WSSecurityTokenConstants.SECURE_CONVERSATION_TOKEN.equals(this.securityToken.getTokenType())) {
                        boolean use200512Namespace = ((WSSSecurityProperties)this.getSecurityProperties()).isUse200512Namespace();
                        if (use200512Namespace) {
                            attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE_TYPE, "http://docs.oasis-open.org/ws-sx/ws-secureconversation/200512/sct"));
                        } else {
                            attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE_TYPE, "http://schemas.xmlsoap.org/ws/2005/02/sc/sct"));
                        }
                    } else if (WSSecurityTokenConstants.EncryptedKeyToken.equals(this.securityToken.getTokenType())) {
                        attributes.add(this.createAttribute(WSSConstants.ATT_NULL_VALUE_TYPE, "http://docs.oasis-open.org/wss/oasis-wss-soap-message-security-1.1#EncryptedKey"));
                    }
                    this.createStartElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_REFERENCE, false, attributes);
                    this.createEndElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_REFERENCE);
                }
            }
            this.createEndElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE);
            this.createEndElementAndOutputAsEvent(outputProcessorChain, XMLSecurityConstants.TAG_dsig_KeyInfo);
        }

        @Override
        protected void doFinalInternal(OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
            super.doFinalInternal(outputProcessorChain);
            if (this.doEncryptedHeader) {
                this.createEndElementAndOutputAsEvent(outputProcessorChain, WSSConstants.TAG_wsse11_EncryptedHeader);
            }
        }
    }
}

