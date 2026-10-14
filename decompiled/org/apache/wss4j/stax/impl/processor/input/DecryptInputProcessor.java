/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.security.Key;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.crypto.Cipher;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.binding.wss10.SecurityTokenReferenceType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.securityEvent.EncryptedPartSecurityEvent;
import org.apache.wss4j.stax.securityToken.WSSecurityTokenConstants;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.binding.xmldsig.KeyInfoType;
import org.apache.xml.security.binding.xmldsig.TransformType;
import org.apache.xml.security.binding.xmldsig.TransformsType;
import org.apache.xml.security.binding.xmlenc.CipherReferenceType;
import org.apache.xml.security.binding.xmlenc.EncryptedDataType;
import org.apache.xml.security.binding.xmlenc.ReferenceList;
import org.apache.xml.security.binding.xmlenc.ReferenceType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.config.ConfigurationProperties;
import org.apache.xml.security.stax.config.TransformerAlgorithmMapper;
import org.apache.xml.security.stax.ext.DocumentContext;
import org.apache.xml.security.stax.ext.InboundSecurityContext;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.processor.input.AbstractDecryptInputProcessor;
import org.apache.xml.security.stax.impl.util.LimitingInputStream;
import org.apache.xml.security.stax.securityEvent.ContentEncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.EncryptedElementSecurityEvent;
import org.apache.xml.security.stax.securityEvent.TokenSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DecryptInputProcessor
extends AbstractDecryptInputProcessor {
    private static final transient Logger LOG = LoggerFactory.getLogger(DecryptInputProcessor.class);
    private static final Long MAX_ALLOWED_DECOMPRESSED_BYTES = Long.valueOf(ConfigurationProperties.getProperty("MaximumAllowedDecompressedBytes"));
    private List<DeferredAttachment> attachmentReferences = new ArrayList<DeferredAttachment>();

    public DecryptInputProcessor(KeyInfoType keyInfoType, ReferenceList referenceList, WSSSecurityProperties securityProperties, WSInboundSecurityContext securityContext) throws XMLSecurityException {
        super(keyInfoType, referenceList, securityProperties);
        this.checkBSPCompliance(keyInfoType, referenceList, securityContext, BSPRule.R3006);
    }

    private void checkBSPCompliance(KeyInfoType keyInfoType, ReferenceList referenceList, WSInboundSecurityContext securityContext, BSPRule bspRule) throws WSSecurityException {
        if (keyInfoType != null) {
            SecurityTokenReferenceType securityTokenReferenceType;
            if (keyInfoType.getContent().size() != 1) {
                securityContext.handleBSPRule(BSPRule.R5424);
            }
            if ((securityTokenReferenceType = (SecurityTokenReferenceType)XMLSecurityUtils.getQNameType(keyInfoType.getContent(), WSSConstants.TAG_WSSE_SECURITY_TOKEN_REFERENCE)) == null) {
                securityContext.handleBSPRule(BSPRule.R5426);
            }
        }
        if (referenceList != null) {
            List<JAXBElement<ReferenceType>> references = referenceList.getDataReferenceOrKeyReference();
            Iterator<JAXBElement<ReferenceType>> referenceTypeIterator = references.iterator();
            while (referenceTypeIterator.hasNext()) {
                ReferenceType referenceType = referenceTypeIterator.next().getValue();
                if (referenceType.getURI().startsWith("#")) continue;
                securityContext.handleBSPRule(bspRule);
            }
        }
    }

    @Override
    protected InputStream applyTransforms(ReferenceType referenceType, InputStream inputStream2) throws XMLSecurityException {
        TransformsType transformsType;
        if (referenceType != null && (transformsType = (TransformsType)XMLSecurityUtils.getQNameType(referenceType.getAny(), XMLSecurityConstants.TAG_dsig_Transforms)) != null) {
            List<TransformType> transformTypes = transformsType.getTransform();
            if (transformTypes.size() > 1) {
                throw new XMLSecurityException("stax.encryption.Transforms.NotYetImplemented");
            }
            TransformType transformType = transformTypes.get(0);
            Class<?> transformerClass = TransformerAlgorithmMapper.getTransformerClass(transformType.getAlgorithm(), XMLSecurityConstants.DIRECTION.IN);
            try {
                Constructor<?> constructor = transformerClass.getConstructor(InputStream.class);
                inputStream2 = new LimitingInputStream((InputStream)constructor.newInstance(inputStream2), MAX_ALLOWED_DECOMPRESSED_BYTES);
            }
            catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
                throw new XMLSecurityException(e);
            }
        }
        return inputStream2;
    }

    @Override
    protected void handleEncryptedContent(InputProcessorChain inputProcessorChain, XMLSecStartElement parentStartXMLEvent, InboundSecurityToken inboundSecurityToken, EncryptedDataType encryptedDataType) throws XMLSecurityException {
        DocumentContext documentContext = inputProcessorChain.getDocumentContext();
        List<QName> elementPath = parentStartXMLEvent.getElementPath();
        if (elementPath.size() == 2 && WSSUtils.isInSOAPBody(elementPath)) {
            EncryptedPartSecurityEvent encryptedPartSecurityEvent = new EncryptedPartSecurityEvent(inboundSecurityToken, true, documentContext.getProtectionOrder());
            encryptedPartSecurityEvent.setElementPath(elementPath);
            encryptedPartSecurityEvent.setXmlSecEvent(parentStartXMLEvent);
            encryptedPartSecurityEvent.setCorrelationID(encryptedDataType.getId());
            inputProcessorChain.getSecurityContext().registerSecurityEvent(encryptedPartSecurityEvent);
        } else {
            ContentEncryptedElementSecurityEvent contentEncryptedElementSecurityEvent = new ContentEncryptedElementSecurityEvent(inboundSecurityToken, true, documentContext.getProtectionOrder());
            contentEncryptedElementSecurityEvent.setElementPath(elementPath);
            contentEncryptedElementSecurityEvent.setXmlSecEvent(parentStartXMLEvent);
            contentEncryptedElementSecurityEvent.setCorrelationID(encryptedDataType.getId());
            inputProcessorChain.getSecurityContext().registerSecurityEvent(contentEncryptedElementSecurityEvent);
        }
    }

    @Override
    protected void handleCipherReference(InputProcessorChain inputProcessorChain, EncryptedDataType encryptedDataType, Cipher cipher, InboundSecurityToken inboundSecurityToken) throws XMLSecurityException {
        String typeStr = encryptedDataType.getType();
        if (typeStr != null && ("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Only".equals(typeStr) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete".equals(typeStr))) {
            CipherReferenceType cipherReferenceType = encryptedDataType.getCipherData().getCipherReference();
            if (cipherReferenceType == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            String uri = cipherReferenceType.getURI();
            if (uri == null || uri.length() < 5) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            if (!uri.startsWith("cid:")) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            this.attachmentReferences.add(new DeferredAttachment(encryptedDataType, cipher, inboundSecurityToken));
        }
    }

    @Override
    protected InputStream handleXOPInclude(InputProcessorChain inputProcessorChain, EncryptedDataType encryptedDataType, String href, Cipher cipher, InboundSecurityToken inboundSecurityToken) throws XMLSecurityException {
        if (href == null || href.length() < 5) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
        }
        if (!href.startsWith("cid:")) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
        }
        String attachmentId = AttachmentUtils.getAttachmentId(href);
        CallbackHandler attachmentCallbackHandler = ((WSSSecurityProperties)this.getSecurityProperties()).getAttachmentCallbackHandler();
        if (attachmentCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"no attachment callbackhandler supplied"});
        }
        AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
        attachmentRequestCallback.setAttachmentId(attachmentId);
        try {
            attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e);
        }
        List<Attachment> attachments = attachmentRequestCallback.getAttachments();
        if (attachments == null || attachments.isEmpty() || !attachmentId.equals(attachments.get(0).getId())) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"Attachment not found"});
        }
        Attachment attachment = attachments.get(0);
        String encAlgo = encryptedDataType.getEncryptionMethod().getAlgorithm();
        Key symmetricKey = inboundSecurityToken.getSecretKey(encAlgo, XMLSecurityConstants.Enc, encryptedDataType.getId());
        return AttachmentUtils.setupAttachmentDecryptionStream(encAlgo, cipher, symmetricKey, attachment.getSourceStream());
    }

    @Override
    protected AbstractDecryptInputProcessor.AbstractDecryptedEventReaderInputProcessor newDecryptedEventReaderInputProcessor(boolean encryptedHeader, XMLSecStartElement xmlSecStartElement, EncryptedDataType encryptedDataType, InboundSecurityToken inboundSecurityToken, InboundSecurityContext inboundSecurityContext) throws XMLSecurityException {
        String encryptionAlgorithm = encryptedDataType.getEncryptionMethod().getAlgorithm();
        if (this.getSecurityProperties().getEncryptionSymAlgorithm() != null && !this.getSecurityProperties().getEncryptionSymAlgorithm().equals(encryptionAlgorithm)) {
            LOG.debug("The Key encryption method does not match the requirement");
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
        if (!("http://www.w3.org/2001/04/xmlenc#tripledes-cbc".equals(encryptionAlgorithm) || "http://www.w3.org/2001/04/xmlenc#aes128-cbc".equals(encryptionAlgorithm) || "http://www.w3.org/2009/xmlenc11#aes128-gcm".equals(encryptionAlgorithm) || "http://www.w3.org/2001/04/xmlenc#aes256-cbc".equals(encryptionAlgorithm) || "http://www.w3.org/2009/xmlenc11#aes256-gcm".equals(encryptionAlgorithm))) {
            ((WSInboundSecurityContext)inboundSecurityContext).handleBSPRule(BSPRule.R5620);
        }
        return new DecryptedEventReaderInputProcessor(this.getSecurityProperties(), SecurePart.Modifier.getModifier(encryptedDataType.getType()), encryptedHeader, xmlSecStartElement, encryptedDataType, this, inboundSecurityToken);
    }

    @Override
    protected void handleSecurityToken(InboundSecurityToken inboundSecurityToken, InboundSecurityContext inboundSecurityContext, EncryptedDataType encryptedDataType) throws XMLSecurityException {
        inboundSecurityToken.addTokenUsage(WSSecurityTokenConstants.TokenUsage_Encryption);
        TokenSecurityEvent<? extends InboundSecurityToken> tokenSecurityEvent = WSSUtils.createTokenSecurityEvent(inboundSecurityToken, encryptedDataType.getId());
        inboundSecurityContext.registerSecurityEvent(tokenSecurityEvent);
    }

    @Override
    public void doFinal(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        String uri;
        List encryptedDataRefs = inputProcessorChain.getSecurityContext().getAsList("PROP_ENCRYPTED_DATA_REFS");
        if (encryptedDataRefs != null && !encryptedDataRefs.isEmpty()) {
            Map<String, ReferenceType> references = this.getReferences();
            List<ReferenceType> processedReferences = this.getProcessedReferences();
            if (references != null) {
                for (Map.Entry<String, ReferenceType> next : references.entrySet()) {
                    ReferenceType referenceType = next.getValue();
                    uri = WSSUtils.dropReferenceMarker(referenceType.getURI());
                    for (String s2 : encryptedDataRefs) {
                        if (!s2.equals(uri)) continue;
                        processedReferences.add(referenceType);
                    }
                }
            }
        }
        super.doFinal(inputProcessorChain);
        for (int i = 0; i < this.attachmentReferences.size(); ++i) {
            DeferredAttachment deferredAttachment = this.attachmentReferences.get(i);
            EncryptedDataType encryptedDataType = deferredAttachment.getEncryptedDataType();
            InboundSecurityToken inboundSecurityToken = deferredAttachment.getInboundSecurityToken();
            Cipher cipher = deferredAttachment.getCipher();
            uri = encryptedDataType.getCipherData().getCipherReference().getURI();
            String attachmentId = AttachmentUtils.getAttachmentId(uri);
            CallbackHandler attachmentCallbackHandler = ((WSSSecurityProperties)this.getSecurityProperties()).getAttachmentCallbackHandler();
            if (attachmentCallbackHandler == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"no attachment callbackhandler supplied"});
            }
            AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
            attachmentRequestCallback.setAttachmentId(attachmentId);
            try {
                attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e);
            }
            List<Attachment> attachments = attachmentRequestCallback.getAttachments();
            if (attachments == null || attachments.isEmpty() || !attachmentId.equals(attachments.get(0).getId())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"Attachment not found"});
            }
            Attachment attachment = attachments.get(0);
            String encAlgo = encryptedDataType.getEncryptionMethod().getAlgorithm();
            Key symmetricKey = inboundSecurityToken.getSecretKey(encAlgo, XMLSecurityConstants.Enc, encryptedDataType.getId());
            InputStream attachmentInputStream = AttachmentUtils.setupAttachmentDecryptionStream(encAlgo, cipher, symmetricKey, attachment.getSourceStream());
            Attachment resultAttachment = new Attachment();
            resultAttachment.setId(attachment.getId());
            resultAttachment.setMimeType(encryptedDataType.getMimeType());
            resultAttachment.setSourceStream(attachmentInputStream);
            resultAttachment.addHeaders(attachment.getHeaders());
            if ("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete".equals(encryptedDataType.getType())) {
                try {
                    AttachmentUtils.readAndReplaceEncryptedAttachmentHeaders(resultAttachment.getHeaders(), attachmentInputStream);
                }
                catch (IOException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
                }
            }
            AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
            attachmentResultCallback.setAttachment(resultAttachment);
            attachmentResultCallback.setAttachmentId(resultAttachment.getId());
            try {
                attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e);
            }
            DocumentContext documentContext = inputProcessorChain.getDocumentContext();
            EncryptedPartSecurityEvent encryptedPartSecurityEvent = new EncryptedPartSecurityEvent(inboundSecurityToken, true, documentContext.getProtectionOrder());
            encryptedPartSecurityEvent.setAttachment(true);
            encryptedPartSecurityEvent.setCorrelationID(encryptedDataType.getId());
            inputProcessorChain.getSecurityContext().registerSecurityEvent(encryptedPartSecurityEvent);
        }
    }

    class DecryptedEventReaderInputProcessor
    extends AbstractDecryptInputProcessor.AbstractDecryptedEventReaderInputProcessor {
        DecryptedEventReaderInputProcessor(XMLSecurityProperties securityProperties, SecurePart.Modifier encryptionModifier, boolean encryptedHeader, XMLSecStartElement xmlSecStartElement, EncryptedDataType encryptedDataType, DecryptInputProcessor decryptInputProcessor, InboundSecurityToken inboundSecurityToken) {
            super(securityProperties, encryptionModifier, encryptedHeader, xmlSecStartElement, encryptedDataType, decryptInputProcessor, inboundSecurityToken);
        }

        @Override
        protected void handleEncryptedElement(InputProcessorChain inputProcessorChain, XMLSecStartElement xmlSecStartElement, InboundSecurityToken inboundSecurityToken, EncryptedDataType encryptedDataType) throws XMLSecurityException {
            DocumentContext documentContext = inputProcessorChain.getDocumentContext();
            List<QName> elementPath = xmlSecStartElement.getElementPath();
            if (elementPath.size() == 3 && WSSUtils.isInSOAPHeader(elementPath)) {
                EncryptedPartSecurityEvent encryptedPartSecurityEvent = new EncryptedPartSecurityEvent(inboundSecurityToken, true, documentContext.getProtectionOrder());
                encryptedPartSecurityEvent.setElementPath(elementPath);
                encryptedPartSecurityEvent.setXmlSecEvent(xmlSecStartElement);
                encryptedPartSecurityEvent.setCorrelationID(encryptedDataType.getId());
                inputProcessorChain.getSecurityContext().registerSecurityEvent(encryptedPartSecurityEvent);
            } else {
                EncryptedElementSecurityEvent encryptedElementSecurityEvent = new EncryptedElementSecurityEvent(inboundSecurityToken, true, documentContext.getProtectionOrder());
                encryptedElementSecurityEvent.setElementPath(elementPath);
                encryptedElementSecurityEvent.setXmlSecEvent(xmlSecStartElement);
                encryptedElementSecurityEvent.setCorrelationID(encryptedDataType.getId());
                inputProcessorChain.getSecurityContext().registerSecurityEvent(encryptedElementSecurityEvent);
            }
        }
    }

    private static final class DeferredAttachment {
        private EncryptedDataType encryptedDataType;
        private Cipher cipher;
        private InboundSecurityToken inboundSecurityToken;

        private DeferredAttachment(EncryptedDataType encryptedDataType, Cipher cipher, InboundSecurityToken inboundSecurityToken) {
            this.encryptedDataType = encryptedDataType;
            this.cipher = cipher;
            this.inboundSecurityToken = inboundSecurityToken;
        }

        private EncryptedDataType getEncryptedDataType() {
            return this.encryptedDataType;
        }

        private Cipher getCipher() {
            return this.cipher;
        }

        private InboundSecurityToken getInboundSecurityToken() {
            return this.inboundSecurityToken;
        }
    }
}

