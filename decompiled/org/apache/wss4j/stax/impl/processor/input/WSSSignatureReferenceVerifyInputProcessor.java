/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.input;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoField;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import org.apache.wss4j.binding.wss10.TransformationParametersType;
import org.apache.wss4j.common.bsp.BSPRule;
import org.apache.wss4j.common.cache.ReplayCache;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.stax.ext.WSInboundSecurityContext;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.transformer.AttachmentContentSignatureTransform;
import org.apache.wss4j.stax.securityEvent.SignedPartSecurityEvent;
import org.apache.wss4j.stax.securityEvent.TimestampSecurityEvent;
import org.apache.wss4j.stax.securityToken.SecurityTokenReference;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.binding.excc14n.InclusiveNamespaces;
import org.apache.xml.security.binding.xmldsig.CanonicalizationMethodType;
import org.apache.xml.security.binding.xmldsig.ReferenceType;
import org.apache.xml.security.binding.xmldsig.SignatureType;
import org.apache.xml.security.binding.xmldsig.TransformType;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.DocumentContext;
import org.apache.xml.security.stax.ext.InputProcessorChain;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.stax.ext.XMLSecurityProperties;
import org.apache.xml.security.stax.ext.XMLSecurityUtils;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.processor.input.AbstractSignatureReferenceVerifyInputProcessor;
import org.apache.xml.security.stax.impl.util.DigestOutputStream;
import org.apache.xml.security.stax.securityEvent.AlgorithmSuiteSecurityEvent;
import org.apache.xml.security.stax.securityEvent.SignedElementSecurityEvent;
import org.apache.xml.security.stax.securityToken.InboundSecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityToken;
import org.apache.xml.security.stax.securityToken.SecurityTokenProvider;
import org.apache.xml.security.utils.UnsyncBufferedOutputStream;

public class WSSSignatureReferenceVerifyInputProcessor
extends AbstractSignatureReferenceVerifyInputProcessor {
    private boolean replayChecked = false;

    public WSSSignatureReferenceVerifyInputProcessor(InputProcessorChain inputProcessorChain, SignatureType signatureType, InboundSecurityToken inboundSecurityToken, XMLSecurityProperties securityProperties) throws XMLSecurityException {
        super(inputProcessorChain, signatureType, inboundSecurityToken, securityProperties);
        this.addAfterProcessor(WSSSignatureReferenceVerifyInputProcessor.class.getName());
        this.checkBSPCompliance((WSInboundSecurityContext)inputProcessorChain.getSecurityContext());
    }

    @Override
    protected void verifyExternalReference(InputProcessorChain inputProcessorChain, InputStream inputStream2, ReferenceType referenceType) throws XMLSecurityException, XMLStreamException {
        if (referenceType.getURI().startsWith("cid:")) {
            CallbackHandler attachmentCallbackHandler = ((WSSSecurityProperties)this.getSecurityProperties()).getAttachmentCallbackHandler();
            if (attachmentCallbackHandler == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"no attachment callbackhandler supplied"});
            }
            String attachmentId = AttachmentUtils.getAttachmentId(referenceType.getURI());
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
            InputStream attachmentInputStream = attachment.getSourceStream();
            if (!attachmentInputStream.markSupported()) {
                attachmentInputStream = new BufferedInputStream(attachmentInputStream);
            }
            attachmentInputStream.mark(Integer.MAX_VALUE);
            try {
                DigestOutputStream digestOutputStream = this.createMessageDigestOutputStream(referenceType, inputProcessorChain.getSecurityContext());
                UnsyncBufferedOutputStream bufferedDigestOutputStream = new UnsyncBufferedOutputStream(digestOutputStream);
                if (referenceType.getTransforms() != null) {
                    Transformer transformer = this.buildTransformerChain(referenceType, bufferedDigestOutputStream, inputProcessorChain, null);
                    if (!(transformer instanceof AttachmentContentSignatureTransform)) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"First transform must be Attachment[Content|Complete]SignatureTransform"});
                    }
                    HashMap<String, Object> transformerProperties = new HashMap<String, Object>(2);
                    transformerProperties.put("attachment", attachment);
                    transformer.setProperties(transformerProperties);
                    transformer.transform(attachmentInputStream);
                    bufferedDigestOutputStream.close();
                } else {
                    XMLSecurityUtils.copy(attachmentInputStream, bufferedDigestOutputStream);
                    bufferedDigestOutputStream.close();
                }
                this.compareDigest(digestOutputStream.getDigestValue(), referenceType);
                attachmentInputStream.reset();
            }
            catch (IOException e) {
                throw new XMLSecurityException(e);
            }
            Attachment resultAttachment = new Attachment();
            resultAttachment.setId(attachmentId);
            resultAttachment.setMimeType(attachment.getMimeType());
            resultAttachment.addHeaders(attachment.getHeaders());
            resultAttachment.setSourceStream(attachmentInputStream);
            AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
            attachmentResultCallback.setAttachmentId(attachmentId);
            attachmentResultCallback.setAttachment(resultAttachment);
            try {
                attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, e);
            }
            DocumentContext documentContext = inputProcessorChain.getDocumentContext();
            SignedPartSecurityEvent signedPartSecurityEvent = new SignedPartSecurityEvent(this.getInboundSecurityToken(), true, documentContext.getProtectionOrder());
            signedPartSecurityEvent.setAttachment(true);
            signedPartSecurityEvent.setCorrelationID(referenceType.getId());
            inputProcessorChain.getSecurityContext().registerSecurityEvent(signedPartSecurityEvent);
        } else {
            super.verifyExternalReference(inputProcessorChain, inputStream2, referenceType);
        }
    }

    private void checkBSPCompliance(WSInboundSecurityContext securityContext) throws WSSecurityException {
        List<ReferenceType> references = this.getSignatureType().getSignedInfo().getReference();
        for (int i = 0; i < references.size(); ++i) {
            ReferenceType referenceType = references.get(i);
            if (referenceType.getTransforms() == null) {
                securityContext.handleBSPRule(BSPRule.R5416);
            } else if (referenceType.getTransforms().getTransform().isEmpty()) {
                securityContext.handleBSPRule(BSPRule.R5411);
            } else {
                List<TransformType> transformTypes = referenceType.getTransforms().getTransform();
                for (int j = 0; j < transformTypes.size(); ++j) {
                    TransformationParametersType transformationParametersType;
                    TransformType transformType = transformTypes.get(j);
                    String algorithm = transformType.getAlgorithm();
                    if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(algorithm) || "http://www.w3.org/2002/06/xmldsig-filter2".equals(algorithm) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(algorithm) || "http://www.w3.org/2000/09/xmldsig#enveloped-signature".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform".equals(algorithm)) continue;
                    securityContext.handleBSPRule(BSPRule.R5423);
                    if (!(j != transformTypes.size() - 1 || "http://www.w3.org/2001/10/xml-exc-c14n#".equals(algorithm) || "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform".equals(algorithm) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform".equals(algorithm))) {
                        securityContext.handleBSPRule(BSPRule.R5412);
                    }
                    InclusiveNamespaces inclusiveNamespacesType = (InclusiveNamespaces)XMLSecurityUtils.getQNameType(transformType.getContent(), XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
                    if ("http://www.w3.org/2001/10/xml-exc-c14n#".equals(algorithm) && inclusiveNamespacesType != null && inclusiveNamespacesType.getPrefixList().isEmpty()) {
                        securityContext.handleBSPRule(BSPRule.R5407);
                    }
                    if (!"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(algorithm)) continue;
                    if (inclusiveNamespacesType != null && inclusiveNamespacesType.getPrefixList().isEmpty()) {
                        securityContext.handleBSPRule(BSPRule.R5413);
                    }
                    if ((transformationParametersType = (TransformationParametersType)XMLSecurityUtils.getQNameType(transformType.getContent(), WSSConstants.TAG_WSSE_TRANSFORMATION_PARAMETERS)) == null) {
                        securityContext.handleBSPRule(BSPRule.R3065);
                        continue;
                    }
                    CanonicalizationMethodType canonicalizationMethodType = (CanonicalizationMethodType)XMLSecurityUtils.getQNameType(transformationParametersType.getAny(), WSSConstants.TAG_dsig_CanonicalizationMethod);
                    if (canonicalizationMethodType != null) continue;
                    securityContext.handleBSPRule(BSPRule.R3065);
                }
            }
            if ("http://www.w3.org/2000/09/xmldsig#sha1".equals(referenceType.getDigestMethod().getAlgorithm()) || "http://www.w3.org/2001/04/xmlenc#sha256".equals(referenceType.getDigestMethod().getAlgorithm()) || "http://www.w3.org/2001/04/xmlenc#sha512".equals(referenceType.getDigestMethod().getAlgorithm())) continue;
            securityContext.handleBSPRule(BSPRule.R5420);
        }
    }

    @Override
    public XMLSecEvent processNextEvent(InputProcessorChain inputProcessorChain) throws XMLStreamException, XMLSecurityException {
        if (!this.replayChecked) {
            this.replayChecked = true;
            this.detectReplayAttack(inputProcessorChain);
        }
        return super.processNextEvent(inputProcessorChain);
    }

    @Override
    protected void processElementPath(List<QName> elementPath, InputProcessorChain inputProcessorChain, XMLSecEvent xmlSecEvent, ReferenceType referenceType) throws XMLSecurityException {
        DocumentContext documentContext = inputProcessorChain.getDocumentContext();
        if (elementPath.size() == 3 && WSSUtils.isInSOAPHeader(elementPath) || elementPath.size() == 2 && WSSUtils.isInSOAPBody(elementPath)) {
            SignedPartSecurityEvent signedPartSecurityEvent = new SignedPartSecurityEvent(this.getInboundSecurityToken(), true, documentContext.getProtectionOrder());
            signedPartSecurityEvent.setElementPath(elementPath);
            signedPartSecurityEvent.setXmlSecEvent(xmlSecEvent);
            signedPartSecurityEvent.setCorrelationID(referenceType.getId());
            inputProcessorChain.getSecurityContext().registerSecurityEvent(signedPartSecurityEvent);
        } else {
            SignedElementSecurityEvent signedElementSecurityEvent = new SignedElementSecurityEvent(this.getInboundSecurityToken(), true, documentContext.getProtectionOrder());
            signedElementSecurityEvent.setElementPath(elementPath);
            signedElementSecurityEvent.setXmlSecEvent(xmlSecEvent);
            signedElementSecurityEvent.setCorrelationID(referenceType.getId());
            inputProcessorChain.getSecurityContext().registerSecurityEvent(signedElementSecurityEvent);
        }
    }

    @Override
    protected InternalSignatureReferenceVerifier getSignatureReferenceVerifier(XMLSecurityProperties securityProperties, InputProcessorChain inputProcessorChain, ReferenceType referenceType, XMLSecStartElement startElement) throws XMLSecurityException {
        return new InternalSignatureReferenceVerifier((WSSSecurityProperties)securityProperties, inputProcessorChain, referenceType, startElement);
    }

    private void detectReplayAttack(InputProcessorChain inputProcessorChain) throws WSSecurityException {
        TimestampSecurityEvent timestampSecurityEvent = (TimestampSecurityEvent)inputProcessorChain.getSecurityContext().get("PROP_TIMESTAMP");
        ReplayCache replayCache = ((WSSSecurityProperties)this.getSecurityProperties()).getTimestampReplayCache();
        if (timestampSecurityEvent != null && replayCache != null) {
            String cacheKey = timestampSecurityEvent.getCreated().get(ChronoField.MILLI_OF_SECOND) + "" + Arrays.hashCode(this.getSignatureType().getSignatureValue().getValue());
            if (replayCache.contains(cacheKey)) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.MESSAGE_EXPIRED);
            }
            Instant expires = timestampSecurityEvent.getExpires();
            if (expires != null) {
                Instant currentTime = Instant.now();
                replayCache.add(cacheKey, 1L + Duration.between(currentTime, expires).getSeconds());
            } else {
                replayCache.add(cacheKey);
            }
        }
    }

    @Override
    protected Transformer buildTransformerChain(ReferenceType referenceType, OutputStream outputStream2, InputProcessorChain inputProcessorChain, AbstractSignatureReferenceVerifyInputProcessor.InternalSignatureReferenceVerifier internalSignatureReferenceVerifier) throws XMLSecurityException {
        if (referenceType.getTransforms() == null || referenceType.getTransforms().getTransform().isEmpty()) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
        }
        List<TransformType> transformTypeList = referenceType.getTransforms().getTransform();
        if (transformTypeList.size() > maximumAllowedTransformsPerReference) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "secureProcessing.MaximumAllowedTransformsPerReference", new Object[]{transformTypeList.size(), maximumAllowedTransformsPerReference});
        }
        String algorithm = null;
        Transformer parentTransformer = null;
        for (int i = transformTypeList.size() - 1; i >= 0; --i) {
            HashMap<String, Object> transformerProperties;
            InclusiveNamespaces inclusiveNamespacesType;
            CanonicalizationMethodType canonicalizationMethodType;
            TransformType transformType = transformTypeList.get(i);
            TransformationParametersType transformationParametersType = (TransformationParametersType)XMLSecurityUtils.getQNameType(transformType.getContent(), WSSConstants.TAG_WSSE_TRANSFORMATION_PARAMETERS);
            if (transformationParametersType != null && (canonicalizationMethodType = (CanonicalizationMethodType)XMLSecurityUtils.getQNameType(transformationParametersType.getAny(), WSSConstants.TAG_dsig_CanonicalizationMethod)) != null) {
                algorithm = canonicalizationMethodType.getAlgorithm();
                inclusiveNamespacesType = (InclusiveNamespaces)XMLSecurityUtils.getQNameType(canonicalizationMethodType.getContent(), XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
                transformerProperties = null;
                if (inclusiveNamespacesType != null) {
                    transformerProperties = new HashMap<String, Object>();
                    transformerProperties.put("inclusiveNamespacePrefixList", inclusiveNamespacesType.getPrefixList());
                }
                parentTransformer = WSSUtils.getTransformer(null, outputStream2, transformerProperties, algorithm, XMLSecurityConstants.DIRECTION.IN);
            }
            algorithm = transformType.getAlgorithm();
            AlgorithmSuiteSecurityEvent algorithmSuiteSecurityEvent = new AlgorithmSuiteSecurityEvent();
            algorithmSuiteSecurityEvent.setAlgorithmURI(algorithm);
            algorithmSuiteSecurityEvent.setAlgorithmUsage(WSSConstants.SigTransform);
            algorithmSuiteSecurityEvent.setCorrelationID(referenceType.getId());
            inputProcessorChain.getSecurityContext().registerSecurityEvent(algorithmSuiteSecurityEvent);
            inclusiveNamespacesType = (InclusiveNamespaces)XMLSecurityUtils.getQNameType(transformType.getContent(), XMLSecurityConstants.TAG_c14nExcl_InclusiveNamespaces);
            transformerProperties = null;
            if (inclusiveNamespacesType != null) {
                transformerProperties = new HashMap();
                transformerProperties.put("inclusiveNamespacePrefixList", inclusiveNamespacesType.getPrefixList());
            }
            parentTransformer = parentTransformer != null ? WSSUtils.getTransformer(parentTransformer, null, transformerProperties, algorithm, XMLSecurityConstants.DIRECTION.IN) : WSSUtils.getTransformer(null, outputStream2, transformerProperties, algorithm, XMLSecurityConstants.DIRECTION.IN);
        }
        if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(algorithm)) {
            internalSignatureReferenceVerifier.setTransformer(parentTransformer);
            String uri = XMLSecurityUtils.dropReferenceMarker(referenceType.getURI());
            SecurityTokenProvider<? extends InboundSecurityToken> securityTokenProvider = inputProcessorChain.getSecurityContext().getSecurityTokenProvider(uri);
            if (securityTokenProvider == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY_TOKEN, "noReference");
            }
            SecurityToken securityToken = securityTokenProvider.getSecurityToken();
            if (!(securityToken instanceof SecurityTokenReference)) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_SECURITY_TOKEN);
            }
            SecurityTokenReference securityTokenReference = (SecurityTokenReference)securityToken;
            int index = inputProcessorChain.getProcessors().indexOf(internalSignatureReferenceVerifier);
            inputProcessorChain.getDocumentContext().setIsInSignedContent(index, internalSignatureReferenceVerifier);
            XMLSecStartElement xmlSecStartElement = securityTokenReference.getXmlSecEvents().getLast().asStartElement();
            internalSignatureReferenceVerifier.setStartElement(xmlSecStartElement);
            Iterator<XMLSecEvent> xmlSecEventIterator = securityTokenReference.getXmlSecEvents().descendingIterator();
            try {
                while (xmlSecEventIterator.hasNext()) {
                    internalSignatureReferenceVerifier.processEvent(xmlSecEventIterator.next(), inputProcessorChain);
                }
            }
            catch (XMLStreamException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, (Exception)e);
            }
        }
        return parentTransformer;
    }

    class InternalSignatureReferenceVerifier
    extends AbstractSignatureReferenceVerifyInputProcessor.InternalSignatureReferenceVerifier {
        InternalSignatureReferenceVerifier(WSSSecurityProperties securityProperties, InputProcessorChain inputProcessorChain, ReferenceType referenceType, XMLSecStartElement startElement) throws XMLSecurityException {
            super(securityProperties, inputProcessorChain, referenceType, startElement);
            this.addAfterProcessor(WSSSignatureReferenceVerifyInputProcessor.class.getName());
        }
    }
}

