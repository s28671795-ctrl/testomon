/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.message;

import java.io.IOException;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.SecretKey;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.common.util.XMLUtils;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.WsuIdAllocator;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.callback.DOMCallbackLookup;
import org.apache.wss4j.dom.message.WSSecHeader;
import org.apache.wss4j.dom.util.WSSecurityUtil;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.c14n.Canonicalizer;
import org.apache.xml.security.encryption.EncryptedData;
import org.apache.xml.security.encryption.Serializer;
import org.apache.xml.security.encryption.TransformSerializer;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLCipherUtil;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.keys.KeyInfo;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class Encryptor {
    private Document doc;
    private WSSecHeader securityHeader;
    private WsuIdAllocator idAllocator;
    private CallbackLookup callbackLookup;
    private CallbackHandler attachmentCallbackHandler;
    private boolean storeBytesInAttachment;
    private Serializer encryptionSerializer;
    private boolean expandXopInclude;
    private WSDocInfo wsDocInfo;

    public List<String> doEncryption(KeyInfo keyInfo, SecretKey secretKey, String encryptionAlgorithm, List<WSEncryptionPart> references, List<Element> attachmentEncryptedDataElements) throws WSSecurityException {
        XMLCipher xmlCipher = null;
        try {
            xmlCipher = XMLCipher.getInstance(encryptionAlgorithm);
        }
        catch (XMLEncryptionException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, (Exception)ex);
        }
        if (this.encryptionSerializer != null) {
            xmlCipher.setSerializer(this.encryptionSerializer);
        }
        ArrayList<String> encDataRef = new ArrayList<String>();
        WSEncryptionPart attachmentEncryptionPart = null;
        for (int part = 0; part < references.size(); ++part) {
            String id;
            List<Element> elementsToEncrypt;
            WSEncryptionPart encPart = references.get(part);
            if (encPart.getId() != null && encPart.getId().startsWith("cid:")) {
                attachmentEncryptionPart = encPart;
                continue;
            }
            if (this.callbackLookup == null) {
                this.callbackLookup = new DOMCallbackLookup(this.doc);
            }
            if ((elementsToEncrypt = WSSecurityUtil.findElements(encPart, this.callbackLookup, this.doc)) == null || elementsToEncrypt.isEmpty()) {
                if (!encPart.isRequired()) continue;
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "noEncElement", new Object[]{"{" + encPart.getNamespace() + "}" + encPart.getName()});
            }
            if (this.expandXopInclude) {
                Iterator<Element> iterator2 = elementsToEncrypt.iterator();
                while (iterator2.hasNext()) {
                    String id2;
                    Element elementToEncrypt;
                    Element encrElement = elementToEncrypt = iterator2.next();
                    List<Element> includeElements = XMLUtils.findElements(elementToEncrypt.getFirstChild(), "Include", "http://www.w3.org/2004/08/xop/include");
                    if (includeElements != null && !includeElements.isEmpty()) {
                        Element matchingElement = this.findMatchingExpandedElement(encrElement);
                        if (matchingElement != null && matchingElement != encrElement) {
                            encrElement.getParentNode().replaceChild(matchingElement, encrElement);
                            encrElement = matchingElement;
                            for (Element includeElement : includeElements) {
                                String xopURI = includeElement.getAttributeNS(null, "href");
                                if (xopURI == null) continue;
                                AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
                                attachmentRequestCallback.setAttachmentId(WSSecurityUtil.getAttachmentId(xopURI));
                                try {
                                    this.attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
                                }
                                catch (IOException | UnsupportedCallbackException e) {
                                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, e);
                                }
                            }
                        } else {
                            WSSecurityUtil.inlineAttachments(includeElements, this.attachmentCallbackHandler, true);
                        }
                    }
                    if (this.storeBytesInAttachment) {
                        try {
                            id2 = this.encryptElementInAttachment(keyInfo, secretKey, encryptionAlgorithm, encPart, encrElement);
                            encPart.setEncId(id2);
                            encDataRef.add("#" + id2);
                            continue;
                        }
                        catch (Exception ex) {
                            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, ex);
                        }
                    }
                    id2 = this.encryptElement(encrElement, encPart.getEncModifier(), xmlCipher, secretKey, keyInfo);
                    encPart.setEncId(id2);
                    encDataRef.add("#" + id2);
                }
                continue;
            }
            if (this.storeBytesInAttachment) {
                for (Element elementToEncrypt : elementsToEncrypt) {
                    try {
                        id = this.encryptElementInAttachment(keyInfo, secretKey, encryptionAlgorithm, encPart, elementToEncrypt);
                        encPart.setEncId(id);
                        encDataRef.add("#" + id);
                    }
                    catch (Exception ex) {
                        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, ex);
                    }
                }
                continue;
            }
            for (Element elementToEncrypt : elementsToEncrypt) {
                id = this.encryptElement(elementToEncrypt, encPart.getEncModifier(), xmlCipher, secretKey, keyInfo);
                encPart.setEncId(id);
                encDataRef.add("#" + id);
            }
        }
        if (attachmentEncryptionPart != null) {
            this.encryptAttachment(keyInfo, secretKey, encryptionAlgorithm, attachmentEncryptionPart, encDataRef, attachmentEncryptedDataElements);
        }
        return encDataRef;
    }

    private Element findMatchingExpandedElement(Element element) {
        String id;
        Node matchingElement = null;
        if (element.hasAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id")) {
            id = element.getAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "Id");
            matchingElement = this.wsDocInfo.getTokenElement(id);
        }
        if (matchingElement == null && element.hasAttributeNS(null, "Id")) {
            id = element.getAttributeNS(null, "Id");
            matchingElement = this.wsDocInfo.getTokenElement(id);
        }
        if (matchingElement != null && matchingElement.getNamespaceURI().equals(element.getNamespaceURI()) && matchingElement.getLocalName().equals(element.getLocalName())) {
            return matchingElement;
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private String encryptElementInAttachment(KeyInfo keyInfo, SecretKey secretKey, String encryptionAlgorithm, WSEncryptionPart encryptionPart, Element elementToEncrypt) throws Exception {
        String type = "http://www.w3.org/2001/04/xmlenc#Element";
        if ("Content".equals(encryptionPart.getEncModifier())) {
            type = "http://www.w3.org/2001/04/xmlenc#Content";
        }
        String attachmentId = this.idAllocator.createId("", this.doc);
        String encEncryptedDataId = this.idAllocator.createId("ED-", attachmentId);
        if ("Header".equals(encryptionPart.getEncModifier()) && elementToEncrypt.getParentNode().equals(WSSecurityUtil.getSOAPHeader(this.doc))) {
            Encryptor.createEncryptedHeaderElement(this.securityHeader, elementToEncrypt, this.idAllocator);
        }
        Element encryptedData = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:EncryptedData");
        encryptedData.setAttributeNS(null, "Id", encEncryptedDataId);
        encryptedData.setAttributeNS(null, "Type", type);
        Element encryptionMethod = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:EncryptionMethod");
        encryptionMethod.setAttributeNS(null, "Algorithm", encryptionAlgorithm);
        encryptedData.appendChild(encryptionMethod);
        encryptedData.appendChild(WSSecurityUtil.cloneElement(this.doc, keyInfo.getElement()));
        Element cipherData = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:CipherData");
        Element cipherValue = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:CipherValue");
        cipherData.appendChild(cipherValue);
        encryptedData.appendChild(cipherData);
        Cipher cipher = this.createCipher(encryptionAlgorithm, secretKey);
        TransformSerializer serializer = new TransformSerializer();
        serializer.setCanonicalizer(Canonicalizer.getInstance("http://santuario.apache.org/c14n/physical"));
        serializer.setSecureValidation(true);
        byte[] serializedOctets = null;
        if (type.equals("http://www.w3.org/2001/04/xmlenc#Content")) {
            NodeList children = elementToEncrypt.getChildNodes();
            if (null == children) throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, "Element has no content.");
            serializedOctets = serializer.serializeToByteArray(children);
        } else {
            serializedOctets = serializer.serializeToByteArray(elementToEncrypt);
        }
        byte[] encryptedBytes = null;
        try {
            encryptedBytes = cipher.doFinal(serializedOctets);
        }
        catch (IllegalBlockSizeException ibse) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, (Exception)ibse);
        }
        catch (BadPaddingException bpe) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, (Exception)bpe);
        }
        byte[] iv = cipher.getIV();
        byte[] finalEncryptedBytes = new byte[iv.length + encryptedBytes.length];
        System.arraycopy(iv, 0, finalEncryptedBytes, 0, iv.length);
        System.arraycopy(encryptedBytes, 0, finalEncryptedBytes, iv.length, encryptedBytes.length);
        if ("Content".equals(encryptionPart.getEncModifier())) {
            Node child = elementToEncrypt.getFirstChild();
            while (child != null) {
                Node sibling = child.getNextSibling();
                elementToEncrypt.removeChild(child);
                child = sibling;
            }
            elementToEncrypt.appendChild(encryptedData);
        } else {
            elementToEncrypt.getParentNode().replaceChild(encryptedData, elementToEncrypt);
        }
        WSSecurityUtil.storeBytesInAttachment(cipherValue, this.doc, attachmentId, finalEncryptedBytes, this.attachmentCallbackHandler);
        return encEncryptedDataId;
    }

    private void encryptAttachment(KeyInfo keyInfo, SecretKey secretKey, String encryptionAlgorithm, WSEncryptionPart attachmentEncryptionPart, List<String> encDataRef, List<Element> attachmentEncryptedDataElements) throws WSSecurityException {
        if (this.attachmentCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"no attachment callbackhandler supplied"});
        }
        AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
        String id = AttachmentUtils.getAttachmentId(attachmentEncryptionPart.getId());
        attachmentRequestCallback.setAttachmentId(id);
        try {
            this.attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, e);
        }
        String attachmentEncryptedDataType = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Only";
        if ("Element".equals(attachmentEncryptionPart.getEncModifier())) {
            attachmentEncryptedDataType = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete";
        }
        for (Attachment attachment : attachmentRequestCallback.getAttachments()) {
            String attachmentId = attachment.getId();
            String encEncryptedDataId = this.idAllocator.createId("ED-", attachmentId);
            encDataRef.add("#" + encEncryptedDataId);
            Element encryptedData = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:EncryptedData");
            encryptedData.setAttributeNS(null, "Id", encEncryptedDataId);
            encryptedData.setAttributeNS(null, "MimeType", attachment.getMimeType());
            encryptedData.setAttributeNS(null, "Type", attachmentEncryptedDataType);
            Element encryptionMethod = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:EncryptionMethod");
            encryptionMethod.setAttributeNS(null, "Algorithm", encryptionAlgorithm);
            encryptedData.appendChild(encryptionMethod);
            encryptedData.appendChild(WSSecurityUtil.cloneElement(this.doc, keyInfo.getElement()));
            Element cipherData = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:CipherData");
            Element cipherReference = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:CipherReference");
            cipherReference.setAttributeNS(null, "URI", "cid:" + attachmentId);
            Element transforms = this.doc.createElementNS("http://www.w3.org/2001/04/xmlenc#", "xenc:Transforms");
            Element transform = this.doc.createElementNS("http://www.w3.org/2000/09/xmldsig#", "ds:Transform");
            transform.setAttributeNS(null, "Algorithm", "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Ciphertext-Transform");
            transforms.appendChild(transform);
            cipherReference.appendChild(transforms);
            cipherData.appendChild(cipherReference);
            encryptedData.appendChild(cipherData);
            attachmentEncryptedDataElements.add(encryptedData);
            Attachment resultAttachment = new Attachment();
            resultAttachment.setId(attachmentId);
            resultAttachment.setMimeType("application/octet-stream");
            Cipher cipher = this.createCipher(encryptionAlgorithm, secretKey);
            HashMap<String, String> headers = new HashMap<String, String>(attachment.getHeaders());
            resultAttachment.setSourceStream(AttachmentUtils.setupAttachmentEncryptionStream(cipher, "Element".equals(attachmentEncryptionPart.getEncModifier()), attachment, headers));
            resultAttachment.addHeaders(headers);
            AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
            attachmentResultCallback.setAttachmentId(attachmentId);
            attachmentResultCallback.setAttachment(resultAttachment);
            try {
                this.attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
            }
            catch (Exception e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, e);
            }
        }
    }

    private Cipher createCipher(String encryptionAlgorithm, SecretKey secretKey) throws WSSecurityException {
        String jceAlgorithm = JCEMapper.translateURItoJCEID(encryptionAlgorithm);
        try {
            Cipher cipher = Cipher.getInstance(jceAlgorithm);
            int ivLen = JCEMapper.getIVLengthFromURI(encryptionAlgorithm) / 8;
            byte[] iv = XMLSecurityConstants.generateBytes(ivLen);
            AlgorithmParameterSpec paramSpec = XMLCipherUtil.constructBlockCipherParameters(encryptionAlgorithm, iv, Encryptor.class);
            cipher.init(1, (Key)secretKey, paramSpec);
            return cipher;
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, e);
        }
    }

    private String encryptElement(Element elementToEncrypt, String modifier, XMLCipher xmlCipher, SecretKey secretKey, KeyInfo keyInfo) throws WSSecurityException {
        boolean content = "Content".equals(modifier);
        String xencEncryptedDataId = this.idAllocator.createId("ED-", elementToEncrypt);
        try {
            if ("Header".equals(modifier)) {
                String soapNamespace = WSSecurityUtil.getSOAPNamespace(this.doc.getDocumentElement());
                if (elementToEncrypt.getParentNode().getNamespaceURI().equals(soapNamespace) && "Header".equals(elementToEncrypt.getParentNode().getLocalName())) {
                    Encryptor.createEncryptedHeaderElement(this.securityHeader, elementToEncrypt, this.idAllocator);
                }
            }
            xmlCipher.init(1, secretKey);
            EncryptedData encData = xmlCipher.getEncryptedData();
            encData.setId(xencEncryptedDataId);
            encData.setKeyInfo(keyInfo);
            xmlCipher.doFinal(this.doc, elementToEncrypt, content);
            return xencEncryptedDataId;
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_ENCRYPTION, ex);
        }
    }

    private static void createEncryptedHeaderElement(WSSecHeader securityHeader, Element elementToEncrypt, WsuIdAllocator idAllocator) {
        Element elem = elementToEncrypt.getOwnerDocument().createElementNS("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11:EncryptedHeader");
        XMLUtils.setNamespace(elem, "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd", "wsse11");
        String wsuPrefix = XMLUtils.setNamespace(elem, "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", "wsu");
        String headerId = idAllocator.createId("EH-", elementToEncrypt);
        elem.setAttributeNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd", wsuPrefix + ":Id", headerId);
        Node parent = elementToEncrypt.getParentNode();
        elementToEncrypt = (Element)parent.replaceChild(elem, elementToEncrypt);
        elem.appendChild(elementToEncrypt);
        if (securityHeader != null) {
            NamedNodeMap map = securityHeader.getSecurityHeaderElement().getAttributes();
            for (int i = 0; i < map.getLength(); ++i) {
                Attr attr = (Attr)map.item(i);
                if (!"http://schemas.xmlsoap.org/soap/envelope/".equals(attr.getNamespaceURI()) && !"http://www.w3.org/2003/05/soap-envelope".equals(attr.getNamespaceURI())) continue;
                String soapEnvPrefix = XMLUtils.setNamespace(elem, attr.getNamespaceURI(), "soapenv");
                elem.setAttributeNS(attr.getNamespaceURI(), soapEnvPrefix + ":" + attr.getLocalName(), attr.getValue());
            }
        }
    }

    public Document getDoc() {
        return this.doc;
    }

    public void setDoc(Document doc) {
        this.doc = doc;
    }

    public WSSecHeader getSecurityHeader() {
        return this.securityHeader;
    }

    public void setSecurityHeader(WSSecHeader securityHeader) {
        this.securityHeader = securityHeader;
    }

    public WsuIdAllocator getIdAllocator() {
        return this.idAllocator;
    }

    public void setIdAllocator(WsuIdAllocator idAllocator) {
        this.idAllocator = idAllocator;
    }

    public CallbackLookup getCallbackLookup() {
        return this.callbackLookup;
    }

    public void setCallbackLookup(CallbackLookup callbackLookup) {
        this.callbackLookup = callbackLookup;
    }

    public CallbackHandler getAttachmentCallbackHandler() {
        return this.attachmentCallbackHandler;
    }

    public void setAttachmentCallbackHandler(CallbackHandler attachmentCallbackHandler) {
        this.attachmentCallbackHandler = attachmentCallbackHandler;
    }

    public boolean isStoreBytesInAttachment() {
        return this.storeBytesInAttachment;
    }

    public void setStoreBytesInAttachment(boolean storeBytesInAttachment) {
        this.storeBytesInAttachment = storeBytesInAttachment;
    }

    public Serializer getEncryptionSerializer() {
        return this.encryptionSerializer;
    }

    public void setEncryptionSerializer(Serializer encryptionSerializer) {
        this.encryptionSerializer = encryptionSerializer;
    }

    public boolean isExpandXopInclude() {
        return this.expandXopInclude;
    }

    public void setExpandXopInclude(boolean expandXopInclude) {
        this.expandXopInclude = expandXopInclude;
    }

    public WSDocInfo getWsDocInfo() {
        return this.wsDocInfo;
    }

    public void setWsDocInfo(WSDocInfo wsDocInfo) {
        this.wsDocInfo = wsDocInfo;
    }
}

