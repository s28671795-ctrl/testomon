/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.xml.crypto.dom.DOMStructure;
import javax.xml.crypto.dsig.DigestMethod;
import javax.xml.crypto.dsig.Reference;
import javax.xml.crypto.dsig.Transform;
import javax.xml.crypto.dsig.XMLSignatureFactory;
import javax.xml.crypto.dsig.spec.ExcC14NParameterSpec;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.callback.DOMCallbackLookup;
import org.apache.wss4j.dom.message.WSSecBase;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.transform.AttachmentTransformParameterSpec;
import org.apache.wss4j.dom.util.SignatureUtils;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public class WSSecSignatureBase
extends WSSecBase {
    private static final Logger LOG = LoggerFactory.getLogger(WSSecSignatureBase.class);
    private List<Element> clonedElements = new ArrayList<Element>();

    public WSSecSignatureBase(WSSecHeader securityHeader) {
        super(securityHeader);
    }

    public WSSecSignatureBase(Document doc) {
        super(doc);
    }

    public List<Reference> addReferencesToSign(Document doc, List<WSEncryptionPart> references, WSDocInfo wsDocInfo, XMLSignatureFactory signatureFactory, boolean addInclusivePrefixes, String digestAlgo) throws WSSecurityException {
        DigestMethod digestMethod;
        try {
            digestMethod = signatureFactory.newDigestMethod(digestAlgo, null);
        }
        catch (Exception ex) {
            LOG.error("", ex);
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex, "noXMLSig");
        }
        List<Reference> attachmentReferenceList = null;
        ArrayList<Reference> referenceList = new ArrayList<Reference>();
        for (WSEncryptionPart encPart : references) {
            String idToSign = encPart.getId();
            String elemName = encPart.getName();
            Element element = encPart.getElement();
            try {
                if ("cid:Attachments".equals(idToSign) && attachmentReferenceList == null) {
                    attachmentReferenceList = this.addAttachmentReferences(encPart, digestMethod, signatureFactory);
                    continue;
                }
                if (idToSign != null) {
                    Transform transform = null;
                    if ("STRTransform".equals(elemName)) {
                        Element ctx = this.createSTRParameter(doc);
                        DOMStructure structure = new DOMStructure(ctx);
                        transform = signatureFactory.newTransform("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#STR-Transform", structure);
                    } else {
                        List<String> prefixes;
                        ExcC14NParameterSpec transformSpec = null;
                        if (element == null) {
                            if (this.callbackLookup == null) {
                                this.callbackLookup = new DOMCallbackLookup(doc);
                            }
                            element = this.callbackLookup.getElement(idToSign, null, false);
                        }
                        if (addInclusivePrefixes && !(prefixes = this.getInclusivePrefixes(element)).isEmpty()) {
                            transformSpec = new ExcC14NParameterSpec(prefixes);
                        }
                        transform = signatureFactory.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", transformSpec);
                    }
                    if (element != null) {
                        this.cloneElement(element);
                        wsDocInfo.addTokenElement(element, false);
                    } else if (!encPart.isRequired()) continue;
                    Reference reference = signatureFactory.newReference("#" + idToSign, digestMethod, Collections.singletonList(transform), null, null);
                    referenceList.add(reference);
                    continue;
                }
                String nmSpace = encPart.getNamespace();
                List<Element> elementsToSign = null;
                if (element != null) {
                    elementsToSign = Collections.singletonList(element);
                } else {
                    if (this.callbackLookup == null) {
                        this.callbackLookup = new DOMCallbackLookup(doc);
                    }
                    elementsToSign = WSSecurityUtil.findElements(encPart, this.callbackLookup, doc);
                }
                if (elementsToSign == null || elementsToSign.isEmpty()) {
                    if (!encPart.isRequired()) continue;
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noEncElement", new Object[]{nmSpace + ", " + elemName});
                }
                for (Element elementToSign : elementsToSign) {
                    List<String> prefixes;
                    String wsuId = this.setWsuId(elementToSign);
                    this.cloneElement(elementToSign);
                    ExcC14NParameterSpec transformSpec = null;
                    if (addInclusivePrefixes && !(prefixes = this.getInclusivePrefixes(elementToSign)).isEmpty()) {
                        transformSpec = new ExcC14NParameterSpec(prefixes);
                    }
                    Transform transform = signatureFactory.newTransform("http://www.w3.org/2001/10/xml-exc-c14n#", transformSpec);
                    Reference reference = signatureFactory.newReference("#" + wsuId, digestMethod, Collections.singletonList(transform), null, null);
                    referenceList.add(reference);
                    wsDocInfo.addTokenElement(elementToSign, false);
                }
            }
            catch (Exception ex) {
                LOG.error("", ex);
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_SIGNATURE, ex, "noXMLSig");
            }
        }
        if (attachmentReferenceList != null) {
            referenceList.addAll(attachmentReferenceList);
        }
        return referenceList;
    }

    private void cloneElement(Element element) throws WSSecurityException {
        List<Element> includeElements;
        if (this.expandXopInclude && (includeElements = XMLUtils.findElements(element.getFirstChild(), "Include", "http://www.w3.org/2004/08/xop/include")) != null && !includeElements.isEmpty()) {
            this.clonedElements.add(element);
            Document doc = this.getSecurityHeader().getSecurityHeaderDoc();
            element.getParentNode().appendChild(WSSecurityUtil.cloneElement(doc, element));
            WSSecurityUtil.inlineAttachments(includeElements, this.attachmentCallbackHandler, false);
        }
    }

    private List<Reference> addAttachmentReferences(WSEncryptionPart encPart, DigestMethod digestMethod, XMLSignatureFactory signatureFactory) throws WSSecurityException {
        if (this.attachmentCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"no attachment callbackhandler supplied"});
        }
        AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
        String id = AttachmentUtils.getAttachmentId(encPart.getId());
        attachmentRequestCallback.setAttachmentId(id);
        try {
            this.attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
        }
        ArrayList<Reference> attachmentReferenceList = new ArrayList<Reference>();
        if (attachmentRequestCallback.getAttachments() != null) {
            for (Attachment attachment : attachmentRequestCallback.getAttachments()) {
                try {
                    ArrayList<Transform> transforms = new ArrayList<Transform>();
                    AttachmentTransformParameterSpec attachmentTransformParameterSpec = new AttachmentTransformParameterSpec(this.attachmentCallbackHandler, attachment);
                    String attachmentSignatureTransform = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform";
                    if ("Element".equals(encPart.getEncModifier())) {
                        attachmentSignatureTransform = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform";
                    }
                    transforms.add(signatureFactory.newTransform(attachmentSignatureTransform, attachmentTransformParameterSpec));
                    Reference reference = signatureFactory.newReference("cid:" + attachment.getId(), digestMethod, transforms, null, null);
                    attachmentReferenceList.add(reference);
                }
                catch (InvalidAlgorithmParameterException | NoSuchAlgorithmException e) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
                }
            }
        }
        return attachmentReferenceList;
    }

    public List<String> getInclusivePrefixes(Element target) {
        return this.getInclusivePrefixes(target, true);
    }

    public List<String> getInclusivePrefixes(Element target, boolean excludeVisible) {
        return SignatureUtils.getInclusivePrefixes(target, excludeVisible);
    }

    public Element createSTRParameter(Document doc) {
        Element transformParam = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:TransformationParameters");
        Element canonElem = doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:CanonicalizationMethod");
        canonElem.setAttributeNS(null, "Algorithm", "http://www.w3.org/2001/10/xml-exc-c14n#");
        transformParam.appendChild(canonElem);
        return transformParam;
    }

    protected void cleanup() {
        if (!this.clonedElements.isEmpty()) {
            for (Element clonedElement : this.clonedElements) {
                clonedElement.getParentNode().removeChild(clonedElement);
            }
            this.clonedElements.clear();
        }
    }
}

