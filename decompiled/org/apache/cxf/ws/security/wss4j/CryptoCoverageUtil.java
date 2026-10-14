/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.ws.security.wss4j;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;
import javax.xml.xpath.XPathFactoryConfigurationException;
import org.apache.cxf.helpers.DOMUtils;
import org.apache.cxf.helpers.MapNamespaceContext;
import org.apache.cxf.message.Attachment;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSDataRef;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public final class CryptoCoverageUtil {
    private CryptoCoverageUtil() {
    }

    public static void reconcileEncryptedSignedRefs(Collection<WSDataRef> signedRefs, Collection<WSDataRef> encryptedRefs) {
        LinkedList<WSDataRef> encryptedSignedRefs = new LinkedList<WSDataRef>();
        block0: for (WSDataRef signedRef : signedRefs) {
            Element protectedElement = signedRef.getProtectedElement();
            if (protectedElement == null || !("EncryptedData".equals(protectedElement.getLocalName()) && "http://www.w3.org/2001/04/xmlenc#".equals(protectedElement.getNamespaceURI()) || "EncryptedHeader".equals(protectedElement.getLocalName()) && "http://docs.oasis-open.org/wss/oasis-wss-wssecurity-secext-1.1.xsd".equals(protectedElement.getNamespaceURI())) && (!"EncryptedAssertion".equals(protectedElement.getLocalName()) || !"urn:oasis:names:tc:SAML:2.0:assertion".equals(protectedElement.getNamespaceURI()))) continue;
            for (WSDataRef encryptedRef : encryptedRefs) {
                if (protectedElement != encryptedRef.getEncryptedElement()) continue;
                WSDataRef encryptedSignedRef = new WSDataRef();
                encryptedSignedRef.setWsuId(signedRef.getWsuId());
                encryptedSignedRef.setContent(false);
                encryptedSignedRef.setName(encryptedRef.getName());
                encryptedSignedRef.setProtectedElement(encryptedRef.getProtectedElement());
                encryptedSignedRef.setXpath(encryptedRef.getXpath());
                encryptedSignedRefs.add(encryptedSignedRef);
                continue block0;
            }
        }
        signedRefs.addAll(encryptedSignedRefs);
    }

    public static void checkBodyCoverage(Element soapBody, Collection<WSDataRef> refs, CoverageType type, CoverageScope scope) throws WSSecurityException {
        if (!CryptoCoverageUtil.matchElement(refs, scope, soapBody)) {
            Exception ex = new Exception("The " + CryptoCoverageUtil.getCoverageTypeString(type) + " does not cover the required elements (soap:Body).");
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex);
        }
    }

    public static void checkAttachmentsCoverage(Collection<Attachment> attachments, Collection<WSDataRef> refs, CoverageType type, CoverageScope scope) throws WSSecurityException {
        String requiredTransform = null;
        if (type == CoverageType.SIGNED && scope == CoverageScope.CONTENT) {
            requiredTransform = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Content-Signature-Transform";
        } else if (type == CoverageType.SIGNED) {
            requiredTransform = "http://docs.oasis-open.org/wss/oasis-wss-SwAProfile-1.1#Attachment-Complete-Signature-Transform";
        }
        if (attachments != null) {
            for (Attachment attachment : attachments) {
                boolean matched = false;
                for (WSDataRef r : refs) {
                    String id = r.getWsuId();
                    if (id != null && id.startsWith("cid:")) {
                        id = id.substring(4);
                    }
                    if (!r.isAttachment() || attachment.getId() == null || !attachment.getId().equals(id) || CoverageType.ENCRYPTED != type && (r.getTransformAlgorithms() == null || !r.getTransformAlgorithms().contains(requiredTransform))) continue;
                    matched = true;
                    break;
                }
                if (matched) continue;
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The " + CryptoCoverageUtil.getCoverageTypeString(type) + " does not cover the required elements"));
            }
        }
    }

    public static void checkHeaderCoverage(Element soapHeader, Collection<WSDataRef> refs, String namespace, String name, CoverageType type, CoverageScope scope) throws WSSecurityException {
        List<Element> elements = name == null ? DOMUtils.getChildrenWithNamespace(soapHeader, namespace) : DOMUtils.getChildrenWithName(soapHeader, namespace, name);
        for (Element el : elements) {
            if (CryptoCoverageUtil.matchElement(refs, scope, el)) continue;
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The " + CryptoCoverageUtil.getCoverageTypeString(type) + " does not cover the required elements ({" + namespace + "}" + name + ")."));
        }
    }

    public static void checkCoverage(Element soapEnvelope, Collection<WSDataRef> refs, Map<String, String> namespaces, String xPath, CoverageType type, CoverageScope scope) throws WSSecurityException {
        CryptoCoverageUtil.checkCoverage(soapEnvelope, refs, namespaces, Arrays.asList(xPath), type, scope);
    }

    public static void checkCoverage(Element soapEnvelope, Collection<WSDataRef> refs, Map<String, String> namespaces, Collection<String> xPaths, CoverageType type, CoverageScope scope) throws WSSecurityException {
        XPathFactory factory2 = XPathFactory.newInstance();
        try {
            factory2.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", Boolean.TRUE);
        }
        catch (XPathFactoryConfigurationException xPathFactoryConfigurationException) {
            // empty catch block
        }
        XPath xpath = factory2.newXPath();
        if (namespaces != null) {
            xpath.setNamespaceContext(new MapNamespaceContext(namespaces));
        }
        CryptoCoverageUtil.checkCoverage(soapEnvelope, refs, xpath, xPaths, type, scope);
    }

    public static void checkCoverage(Element soapEnvelope, Collection<WSDataRef> refs, XPath xpath, Collection<String> xPaths, CoverageType type, CoverageScope scope) throws WSSecurityException {
        for (String xpathString : xPaths) {
            NodeList list;
            try {
                list = (NodeList)xpath.evaluate(xpathString, soapEnvelope, XPathConstants.NODESET);
            }
            catch (XPathExpressionException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE);
            }
            if (list.getLength() == 0) continue;
            for (int x = 0; x < list.getLength(); ++x) {
                Element el = (Element)list.item(x);
                boolean instanceMatched = CryptoCoverageUtil.matchElement(refs, scope, el);
                if (instanceMatched) continue;
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, new Exception("The " + CryptoCoverageUtil.getCoverageTypeString(type) + " does not cover the required elements (" + xpathString + ")."));
            }
        }
    }

    private static boolean matchElement(Collection<WSDataRef> refs, CoverageScope scope, Element el) {
        boolean content;
        if (el == null) {
            return false;
        }
        switch (scope) {
            case CONTENT: {
                content = true;
                break;
            }
            default: {
                content = false;
            }
        }
        for (WSDataRef r : refs) {
            if (r.getProtectedElement() != el || r.isContent() != content) continue;
            return true;
        }
        return false;
    }

    private static String getCoverageTypeString(CoverageType type) {
        String typeString;
        switch (type) {
            case SIGNED: {
                typeString = "signature";
                break;
            }
            case ENCRYPTED: {
                typeString = "encryption";
                break;
            }
            default: {
                typeString = "crpytography";
            }
        }
        return typeString;
    }

    public static enum CoverageScope {
        CONTENT,
        ELEMENT;

    }

    public static enum CoverageType {
        ENCRYPTED,
        SIGNED;

    }
}

