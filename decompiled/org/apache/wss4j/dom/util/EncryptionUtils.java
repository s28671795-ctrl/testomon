/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.util;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentRequestCallback;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.util.X509Util;
import org.apache.xml.security.algorithms.JCEMapper;
import org.apache.xml.security.encryption.Serializer;
import org.apache.xml.security.encryption.XMLCipher;
import org.apache.xml.security.encryption.XMLEncryptionException;
import org.apache.xml.security.utils.JavaUtils;
import org.apache.xml.security.utils.XMLUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

public final class EncryptionUtils {
    private EncryptionUtils() {
    }

    public static Element findEncryptedDataElement(Document doc, WSDocInfo wsDocInfo, String dataRefURI) throws WSSecurityException {
        CallbackLookup callbackLookup = wsDocInfo.getCallbackLookup();
        Element encryptedDataElement = callbackLookup.getElement(dataRefURI, null, true);
        if (encryptedDataElement == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "dataRef", new Object[]{dataRefURI});
        }
        if (encryptedDataElement.getLocalName().equals("EncryptedHeader") && encryptedDataElement.getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd")) {
            Node child;
            for (child = encryptedDataElement.getFirstChild(); child != null && child.getNodeType() != 1; child = child.getNextSibling()) {
            }
            return (Element)child;
        }
        return encryptedDataElement;
    }

    public static WSDataRef decryptEncryptedData(Document doc, String dataRefURI, Element encData, SecretKey symmetricKey, String symEncAlgo, CallbackHandler attachmentCallbackHandler) throws WSSecurityException {
        return EncryptionUtils.decryptEncryptedData(doc, dataRefURI, encData, symmetricKey, symEncAlgo, attachmentCallbackHandler, null);
    }

    public static WSDataRef decryptEncryptedData(Document doc, String dataRefURI, Element encData, SecretKey symmetricKey, String symEncAlgo, CallbackHandler attachmentCallbackHandler, Serializer encryptionSerializer) throws WSSecurityException {
        String typeStr = encData.getAttributeNS(null, "Type");
        String xopURI = EncryptionUtils.getXOPURIFromEncryptedData(encData);
        if (typeStr != null && ("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Only".equals(typeStr) || "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete".equals(typeStr))) {
            Element cipherData = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(encData, "CipherData", "http://www.w3.org/2001/04/xmlenc#");
            if (cipherData == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            Element cipherReference = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(cipherData, "CipherReference", "http://www.w3.org/2001/04/xmlenc#");
            if (cipherReference == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            String uri = cipherReference.getAttributeNS(null, "URI");
            return EncryptionUtils.decryptAttachment(dataRefURI, uri, encData, symmetricKey, symEncAlgo, attachmentCallbackHandler);
        }
        WSDataRef dataRef = new WSDataRef();
        dataRef.setEncryptedElement(encData);
        dataRef.setWsuId(dataRefURI);
        dataRef.setAlgorithm(symEncAlgo);
        boolean content = X509Util.isContent(encData);
        dataRef.setContent(content);
        Element encDataOrig = encData;
        Node parent = encData.getParentNode();
        Node previousSibling = encData.getPreviousSibling();
        if (content) {
            encData = (Element)encData.getParentNode();
            parent = encData.getParentNode();
        }
        XMLCipher xmlCipher = null;
        try {
            xmlCipher = XMLCipher.getInstance(symEncAlgo);
            if (encryptionSerializer != null) {
                xmlCipher.setSerializer(encryptionSerializer);
            }
            xmlCipher.setSecureValidation(true);
            xmlCipher.init(2, symmetricKey);
        }
        catch (XMLEncryptionException ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.UNSUPPORTED_ALGORITHM, (Exception)ex);
        }
        Node decryptedNode = null;
        try {
            if (xopURI != null) {
                Element tempEncData = content ? encDataOrig : encData;
                decryptedNode = EncryptionUtils.decryptXopAttachment(symmetricKey, symEncAlgo, attachmentCallbackHandler, xopURI, tempEncData);
            } else {
                xmlCipher.doFinal(doc, encData, content);
            }
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, ex);
        }
        if (parent.getLocalName().equals("EncryptedHeader") && parent.getNamespaceURI().equals("http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd") || parent.getLocalName().equals("EncryptedAssertion") && parent.getNamespaceURI().equals("urn:oasis:names:tc:SAML:2.0:assertion")) {
            Node decryptedHeader = parent.getFirstChild();
            Node soapHeader = parent.getParentNode();
            soapHeader.replaceChild(decryptedHeader, parent);
            dataRef.setProtectedElement((Element)decryptedHeader);
            dataRef.setXpath(EncryptionUtils.getXPath(decryptedHeader));
        } else if (content) {
            dataRef.setProtectedElement(encData);
            dataRef.setXpath(EncryptionUtils.getXPath(encData));
        } else {
            if (decryptedNode == null) {
                decryptedNode = previousSibling == null ? parent.getFirstChild() : previousSibling.getNextSibling();
            }
            if (decryptedNode != null && 1 == decryptedNode.getNodeType()) {
                dataRef.setProtectedElement((Element)decryptedNode);
            }
            dataRef.setXpath(EncryptionUtils.getXPath(decryptedNode));
        }
        return dataRef;
    }

    private static String getXOPURIFromEncryptedData(Element encData) {
        Element cipherValue = EncryptionUtils.getCipherValueFromEncryptedData(encData);
        if (cipherValue != null) {
            return EncryptionUtils.getXOPURIFromCipherValue(cipherValue);
        }
        return null;
    }

    public static Element getCipherValueFromEncryptedData(Element encData) {
        Element cipherData = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(encData, "CipherData", "http://www.w3.org/2001/04/xmlenc#");
        if (cipherData != null) {
            return org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(cipherData, "CipherValue", "http://www.w3.org/2001/04/xmlenc#");
        }
        return null;
    }

    public static String getXOPURIFromCipherValue(Element cipherValue) {
        Element cipherValueChild;
        if (cipherValue != null && (cipherValueChild = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(cipherValue, "Include", "http://www.w3.org/2004/08/xop/include")) != null && cipherValueChild.hasAttributeNS(null, "href")) {
            return cipherValueChild.getAttributeNS(null, "href");
        }
        return null;
    }

    private static WSDataRef decryptAttachment(String dataRefURI, String uri, Element encData, SecretKey symmetricKey, String symEncAlgo, CallbackHandler attachmentCallbackHandler) throws WSSecurityException {
        WSDataRef dataRef = new WSDataRef();
        dataRef.setWsuId(dataRefURI);
        dataRef.setAlgorithm(symEncAlgo);
        try {
            if (uri == null || uri.length() < 5 || !uri.startsWith("cid:")) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            dataRef.setWsuId(uri);
            dataRef.setAttachment(true);
            if (attachmentCallbackHandler == null) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
            }
            String attachmentId = AttachmentUtils.getAttachmentId(uri);
            AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
            attachmentRequestCallback.setAttachmentId(attachmentId);
            attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
            List<Attachment> attachments = attachmentRequestCallback.getAttachments();
            if (attachments == null || attachments.isEmpty() || !attachmentId.equals(attachments.get(0).getId())) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"Attachment not found"});
            }
            Attachment attachment = attachments.get(0);
            String encAlgo = X509Util.getEncAlgo(encData);
            String jceAlgorithm = JCEMapper.translateURItoJCEID(encAlgo);
            Cipher cipher = Cipher.getInstance(jceAlgorithm);
            InputStream attachmentInputStream = AttachmentUtils.setupAttachmentDecryptionStream(encAlgo, cipher, symmetricKey, attachment.getSourceStream());
            Attachment resultAttachment = new Attachment();
            resultAttachment.setId(attachment.getId());
            resultAttachment.setMimeType(encData.getAttributeNS(null, "MimeType"));
            resultAttachment.setSourceStream(attachmentInputStream);
            resultAttachment.addHeaders(attachment.getHeaders());
            String typeStr = encData.getAttributeNS(null, "Type");
            if ("http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete".equals(typeStr)) {
                AttachmentUtils.readAndReplaceEncryptedAttachmentHeaders(resultAttachment.getHeaders(), attachmentInputStream);
            }
            AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
            attachmentResultCallback.setAttachment(resultAttachment);
            attachmentResultCallback.setAttachmentId(resultAttachment.getId());
            attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
        }
        catch (IOException | NoSuchAlgorithmException | NoSuchPaddingException | UnsupportedCallbackException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, e);
        }
        dataRef.setContent(true);
        encData.getParentNode().removeChild(encData);
        return dataRef;
    }

    private static Node decryptXopAttachment(SecretKey symmetricKey, String symEncAlgo, CallbackHandler attachmentCallbackHandler, String xopURI, Element encData) throws WSSecurityException, IOException, UnsupportedCallbackException, NoSuchAlgorithmException, NoSuchPaddingException, ParserConfigurationException, SAXException {
        if (attachmentCallbackHandler == null) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK);
        }
        String attachmentId = AttachmentUtils.getAttachmentId(xopURI);
        AttachmentRequestCallback attachmentRequestCallback = new AttachmentRequestCallback();
        attachmentRequestCallback.setAttachmentId(attachmentId);
        attachmentCallbackHandler.handle(new Callback[]{attachmentRequestCallback});
        List<Attachment> attachments = attachmentRequestCallback.getAttachments();
        if (attachments == null || attachments.isEmpty() || !attachmentId.equals(attachments.get(0).getId())) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY, "empty", new Object[]{"Attachment not found"});
        }
        Attachment attachment = attachments.get(0);
        String jceAlgorithm = JCEMapper.translateURItoJCEID(symEncAlgo);
        Cipher cipher = Cipher.getInstance(jceAlgorithm);
        InputStream attachmentInputStream = AttachmentUtils.setupAttachmentDecryptionStream(symEncAlgo, cipher, symmetricKey, attachment.getSourceStream());
        byte[] bytes = JavaUtils.getBytesFromStream(attachmentInputStream);
        Document document = null;
        try {
            document = XMLUtils.read(new ByteArrayInputStream(bytes));
        }
        catch (SAXException ex) {
            String fixedElementStr = EncryptionUtils.setParentPrefixes(encData, new String(bytes));
            document = XMLUtils.read(new ByteArrayInputStream(fixedElementStr.getBytes()));
        }
        Node decryptedNode = encData.getOwnerDocument().importNode(document.getDocumentElement(), true);
        encData.getParentNode().appendChild(decryptedNode);
        encData.getParentNode().removeChild(encData);
        return decryptedNode;
    }

    private static String setParentPrefixes(Element target, String str) {
        Node parent = target;
        int insertionIndex = str.indexOf(62);
        StringBuilder prefix = new StringBuilder(str.substring(0, insertionIndex));
        StringBuilder suffix = new StringBuilder(str.substring(insertionIndex, str.length()));
        int prefixAddedCount = 0;
        block0: while (parent.getParentNode() != null && prefixAddedCount < 20 && 9 != parent.getParentNode().getNodeType()) {
            parent = parent.getParentNode();
            NamedNodeMap attributes = parent.getAttributes();
            int length = attributes.getLength();
            for (int i = 0; i < length; ++i) {
                Node attribute = attributes.item(i);
                String attrDef = "xmlns:" + attribute.getLocalName();
                if ("http://www.w3.org/2000/xmlns/".equals(attribute.getNamespaceURI()) && !prefix.toString().contains(attrDef)) {
                    attrDef = attrDef + "=\"" + attribute.getNodeValue() + "\"";
                    prefix.append(" " + attrDef);
                    ++prefixAddedCount;
                }
                if (prefixAddedCount >= 20) continue block0;
            }
        }
        return prefix.toString() + suffix.toString();
    }

    public static String getXPath(Node decryptedNode) {
        if (decryptedNode == null) {
            return null;
        }
        String result = "";
        if (1 == decryptedNode.getNodeType()) {
            result = decryptedNode.getNodeName();
            result = EncryptionUtils.prependFullPath(result, decryptedNode.getParentNode());
        } else if (2 == decryptedNode.getNodeType()) {
            result = "@" + decryptedNode.getNodeName();
            result = EncryptionUtils.prependFullPath(result, ((Attr)decryptedNode).getOwnerElement());
        } else {
            return null;
        }
        return result;
    }

    private static String prependFullPath(String xpath, Node node) {
        if (node == null) {
            return null;
        }
        if (1 == node.getNodeType()) {
            xpath = node.getNodeName() + "/" + xpath;
            return EncryptionUtils.prependFullPath(xpath, node.getParentNode());
        }
        if (9 == node.getNodeType()) {
            return "/" + xpath;
        }
        return EncryptionUtils.prependFullPath(xpath, node.getParentNode());
    }

    public static String getDigestAlgorithm(Node encBodyData) throws WSSecurityException {
        Element digestElement;
        Element tmpE = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(encBodyData, "EncryptionMethod", "http://www.w3.org/2001/04/xmlenc#");
        if (tmpE != null && (digestElement = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(tmpE, "DigestMethod", "http://www.w3.org/2000/09/xmldsig#")) != null) {
            return digestElement.getAttributeNS(null, "Algorithm");
        }
        return null;
    }

    public static String getMGFAlgorithm(Node encBodyData) throws WSSecurityException {
        Element mgfElement;
        Element tmpE = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(encBodyData, "EncryptionMethod", "http://www.w3.org/2001/04/xmlenc#");
        if (tmpE != null && (mgfElement = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(tmpE, "MGF", "http://www.w3.org/2009/xmlenc11#")) != null) {
            return mgfElement.getAttributeNS(null, "Algorithm");
        }
        return null;
    }

    public static byte[] getPSource(Node encBodyData) throws WSSecurityException {
        Element pSourceElement;
        Element tmpE = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(encBodyData, "EncryptionMethod", "http://www.w3.org/2001/04/xmlenc#");
        if (tmpE != null && (pSourceElement = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(tmpE, "OAEPparams", "http://www.w3.org/2001/04/xmlenc#")) != null) {
            return EncryptionUtils.getDecodedBase64EncodedData(pSourceElement);
        }
        return null;
    }

    public static byte[] getDecodedBase64EncodedData(Element element) throws WSSecurityException {
        String text = org.apache.wss4j.common.util.XMLUtils.getElementText(element);
        if (text == null) {
            return null;
        }
        return XMLUtils.decode(text);
    }
}

