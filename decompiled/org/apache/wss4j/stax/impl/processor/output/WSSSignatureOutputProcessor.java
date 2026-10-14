/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.stax.impl.processor.output;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Attribute;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.stax.ext.WSSConstants;
import org.apache.wss4j.stax.ext.WSSSecurityProperties;
import org.apache.wss4j.stax.impl.processor.output.WSSSignatureEndingOutputProcessor;
import org.apache.wss4j.stax.utils.WSSUtils;
import org.apache.xml.security.exceptions.XMLSecurityException;
import org.apache.xml.security.stax.ext.OutputProcessorChain;
import org.apache.xml.security.stax.ext.SecurePart;
import org.apache.xml.security.stax.ext.Transformer;
import org.apache.xml.security.stax.ext.stax.XMLSecAttribute;
import org.apache.xml.security.stax.ext.stax.XMLSecEvent;
import org.apache.xml.security.stax.ext.stax.XMLSecStartElement;
import org.apache.xml.security.stax.impl.SignaturePartDef;
import org.apache.xml.security.stax.impl.processor.output.AbstractSignatureOutputProcessor;
import org.apache.xml.security.stax.impl.util.DigestOutputStream;
import org.apache.xml.security.stax.impl.util.IDGenerator;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WSSSignatureOutputProcessor
extends AbstractSignatureOutputProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(WSSSignatureOutputProcessor.class);

    @Override
    public void init(OutputProcessorChain outputProcessorChain) throws XMLSecurityException {
        super.init(outputProcessorChain);
        WSSSignatureEndingOutputProcessor signatureEndingOutputProcessor = new WSSSignatureEndingOutputProcessor(this);
        signatureEndingOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
        signatureEndingOutputProcessor.setAction(this.getAction());
        signatureEndingOutputProcessor.init(outputProcessorChain);
    }

    @Override
    public void processEvent(XMLSecEvent xmlSecEvent, OutputProcessorChain outputProcessorChain) throws XMLStreamException, XMLSecurityException {
        if (xmlSecEvent.getEventType() == 1) {
            SecurePart securePart;
            XMLSecStartElement xmlSecStartElement = xmlSecEvent.asStartElement();
            if (this.getActiveInternalSignatureOutputProcessor() == null && (securePart = this.securePartMatches(xmlSecStartElement, outputProcessorChain, "signatureParts")) != null) {
                LOG.debug("Matched securePart for signature");
                SignaturePartDef signaturePartDef = new SignaturePartDef();
                signaturePartDef.setSecurePart(securePart);
                signaturePartDef.setTransforms(securePart.getTransforms());
                if (signaturePartDef.getTransforms() == null) {
                    signaturePartDef.setTransforms(new String[]{"http://www.w3.org/2001/10/xml-exc-c14n#"});
                }
                signaturePartDef.setExcludeVisibleC14Nprefixes(true);
                signaturePartDef.setDigestAlgo(securePart.getDigestMethod());
                if (signaturePartDef.getDigestAlgo() == null) {
                    signaturePartDef.setDigestAlgo(this.getSecurityProperties().getSignatureDigestAlgorithm());
                }
                if (securePart.getIdToSign() == null) {
                    signaturePartDef.setGenerateXPointer(securePart.isGenerateXPointer());
                    signaturePartDef.setSigRefId(IDGenerator.generateID(null));
                    Attribute attribute = xmlSecStartElement.getAttributeByName(WSSConstants.ATT_WSU_ID);
                    if (attribute != null) {
                        signaturePartDef.setSigRefId(attribute.getValue());
                    } else {
                        ArrayList<XMLSecAttribute> attributeList = new ArrayList<XMLSecAttribute>(1);
                        attributeList.add(this.createAttribute(WSSConstants.ATT_WSU_ID, signaturePartDef.getSigRefId()));
                        xmlSecEvent = this.addAttributes(xmlSecStartElement, attributeList);
                    }
                } else if ("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform".equals(securePart.getName().getLocalPart())) {
                    signaturePartDef.setSigRefId(securePart.getIdToReference());
                    String[] transforms = new String[]{"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform", "http://www.w3.org/2001/10/xml-exc-c14n#"};
                    signaturePartDef.setTransforms(transforms);
                } else {
                    signaturePartDef.setSigRefId(securePart.getIdToSign());
                }
                this.getSignaturePartDefList().add(signaturePartDef);
                InternalWSSSignatureOutputProcessor internalSignatureOutputProcessor = new InternalWSSSignatureOutputProcessor(signaturePartDef, xmlSecStartElement);
                internalSignatureOutputProcessor.setXMLSecurityProperties(this.getSecurityProperties());
                internalSignatureOutputProcessor.setAction(this.getAction());
                internalSignatureOutputProcessor.addAfterProcessor(WSSSignatureOutputProcessor.class.getName());
                internalSignatureOutputProcessor.addBeforeProcessor(WSSSignatureEndingOutputProcessor.class.getName());
                internalSignatureOutputProcessor.init(outputProcessorChain);
                this.setActiveInternalSignatureOutputProcessor(internalSignatureOutputProcessor);
                if ("Body".equals(xmlSecStartElement.getName().getLocalPart()) && WSSUtils.isInSOAPBody(xmlSecStartElement)) {
                    this.doFinalInternal(outputProcessorChain);
                    outputProcessorChain.removeProcessor(this);
                }
            }
        }
        outputProcessorChain.processEvent(xmlSecEvent);
    }

    @Override
    protected void digestExternalReference(OutputProcessorChain outputProcessorChain, SecurePart securePart) throws XMLSecurityException, XMLStreamException {
        if (securePart.getExternalReference() != null && securePart.getExternalReference().startsWith("cid:")) {
            CallbackHandler attachmentCallbackHandler = ((WSSSecurityProperties)this.getSecurityProperties()).getAttachmentCallbackHandler();
            if (attachmentCallbackHandler == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, "empty", new Object[]{"no attachment callbackhandler supplied"});
            }
            AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
            String id = AttachmentUtils.getAttachmentId(securePart.getExternalReference());
            attachmentRequestCallback.setAttachmentId(id);
            try {
                attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, e);
            }
            List<Attachment> attachments = attachmentRequestCallback.getAttachments();
            if (attachments != null) {
                for (int i = 0; i < attachments.size(); ++i) {
                    Attachment attachment = attachments.get(i);
                    SignaturePartDef signaturePartDef = new SignaturePartDef();
                    signaturePartDef.setSecurePart(securePart);
                    signaturePartDef.setSigRefId("cid:" + attachment.getId());
                    signaturePartDef.setExternalResource(true);
                    signaturePartDef.setTransforms(securePart.getTransforms());
                    if (signaturePartDef.getTransforms() == null) {
                        if (securePart.getModifier() == SecurePart.Modifier.Element) {
                            signaturePartDef.setTransforms(new String[]{"http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform"});
                        } else {
                            signaturePartDef.setTransforms(new String[]{"http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform"});
                        }
                    }
                    signaturePartDef.setExcludeVisibleC14Nprefixes(true);
                    signaturePartDef.setDigestAlgo(securePart.getDigestMethod());
                    if (signaturePartDef.getDigestAlgo() == null) {
                        signaturePartDef.setDigestAlgo(this.getSecurityProperties().getSignatureDigestAlgorithm());
                    }
                    DigestOutputStream digestOutputStream = this.createMessageDigestOutputStream(signaturePartDef.getDigestAlgo());
                    InputStream inputStream2 = attachment.getSourceStream();
                    if (!inputStream2.markSupported()) {
                        inputStream2 = new BufferedInputStream(inputStream2);
                    }
                    inputStream2.mark(Integer.MAX_VALUE);
                    try {
                        Transformer transformer = this.buildTransformerChain(digestOutputStream, signaturePartDef, null);
                        HashMap<String, Object> transformerProperties = new HashMap<String, Object>(2);
                        transformerProperties.put("attachment", attachment);
                        transformer.setProperties(transformerProperties);
                        transformer.transform(inputStream2);
                        transformer.doFinal();
                        digestOutputStream.close();
                        inputStream2.reset();
                    }
                    catch (IOException | XMLStreamException e) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, e);
                    }
                    String calculatedDigest = XMLUtils.encodeToString(digestOutputStream.getDigestValue());
                    LOG.debug("Calculated Digest: {}", (Object)calculatedDigest);
                    signaturePartDef.setDigestValue(calculatedDigest);
                    Attachment resultAttachment = new Attachment();
                    resultAttachment.setId(attachment.getId());
                    resultAttachment.setMimeType(attachment.getMimeType());
                    resultAttachment.addHeaders(attachment.getHeaders());
                    resultAttachment.setSourceStream(inputStream2);
                    AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
                    attachmentResultCallback.setAttachmentId(resultAttachment.getId());
                    attachmentResultCallback.setAttachment(resultAttachment);
                    try {
                        attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
                    }
                    catch (Exception e) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, e);
                    }
                    this.getSignaturePartDefList().add(signaturePartDef);
                }
            }
        } else {
            super.digestExternalReference(outputProcessorChain, securePart);
        }
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

    class InternalWSSSignatureOutputProcessor
    extends AbstractSignatureOutputProcessor.InternalSignatureOutputProcessor {
        InternalWSSSignatureOutputProcessor(SignaturePartDef signaturePartDef, XMLSecStartElement xmlSecStartElement) throws XMLSecurityException {
            super(signaturePartDef, xmlSecStartElement);
            this.addBeforeProcessor(InternalWSSSignatureOutputProcessor.class.getName());
        }
    }
}

