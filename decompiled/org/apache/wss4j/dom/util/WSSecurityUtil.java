/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.util;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import org.apache.wss4j.common.WSEncryptionPart;
import org.apache.wss4j.common.ext.Attachment;
import org.apache.wss4j.common.ext.AttachmentResultCallback;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.common.util.AttachmentUtils;
import org.apache.wss4j.dom.SOAP11Constants;
import org.apache.wss4j.dom.SOAP12Constants;
import org.apache.wss4j.dom.SOAPConstants;
import org.apache.wss4j.dom.callback.CallbackLookup;
import org.apache.wss4j.dom.engine.WSSConfig;
import org.apache.wss4j.dom.handler.HandlerAction;
import org.apache.wss4j.dom.handler.RequestData;
import org.apache.xml.security.stax.ext.XMLSecurityConstants;
import org.apache.xml.security.utils.XMLUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public final class WSSecurityUtil {
    private static boolean isSAAJ14 = false;
    private static final Logger LOG = LoggerFactory.getLogger(WSSecurityUtil.class);
    private static final ClassValue<Method> GET_DOM_ELEMENTS_METHODS = new ClassValue<Method>(){

        @Override
        protected Method computeValue(Class<?> type) {
            try {
                return WSSecurityUtil.getMethod(type, "getDomElement", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                return null;
            }
        }
    };
    private static final ClassValue<Method> GET_ENVELOPE_METHODS = new ClassValue<Method>(){

        @Override
        protected Method computeValue(Class<?> type) {
            try {
                return WSSecurityUtil.getMethod(type, "getEnvelope", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                return null;
            }
        }
    };

    private WSSecurityUtil() {
    }

    private static Method getMethod(final Class<?> clazz, final String name, final Class<?> ... parameterTypes) throws NoSuchMethodException {
        try {
            return AccessController.doPrivileged(new PrivilegedExceptionAction<Method>(){

                @Override
                public Method run() throws Exception {
                    return clazz.getMethod(name, parameterTypes);
                }
            });
        }
        catch (PrivilegedActionException pae) {
            Exception e = pae.getException();
            if (e instanceof NoSuchMethodException) {
                throw (NoSuchMethodException)e;
            }
            throw new SecurityException(e);
        }
    }

    private static <T extends AccessibleObject> T setAccessible(final T o) {
        return (T)((AccessibleObject)AccessController.doPrivileged(new PrivilegedAction<T>(){

            @Override
            public T run() {
                o.setAccessible(true);
                return o;
            }
        }));
    }

    public static Element getSOAPHeader(Document doc) {
        String soapNamespace = WSSecurityUtil.getSOAPNamespace(doc.getDocumentElement());
        return org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(doc.getDocumentElement(), "Header", soapNamespace);
    }

    public static Element getSecurityHeader(Document doc, String actor) throws WSSecurityException {
        Element soapHeaderElement = WSSecurityUtil.getSOAPHeader(doc);
        if (soapHeaderElement == null) {
            return null;
        }
        String soapNamespace = WSSecurityUtil.getSOAPNamespace(doc.getDocumentElement());
        return WSSecurityUtil.getSecurityHeader(soapHeaderElement, actor, "http://www.w3.org/2003/05/soap-envelope".equals(soapNamespace));
    }

    public static Element getSecurityHeader(Element soapHeader, String actor, boolean soap12) throws WSSecurityException {
        String actorLocal = "actor";
        String soapNamespace = "http://schemas.xmlsoap.org/soap/envelope/";
        if (soap12) {
            actorLocal = "role";
            soapNamespace = "http://www.w3.org/2003/05/soap-envelope";
        }
        Element foundSecurityHeader = null;
        for (Node currentChild = soapHeader.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            String hActor;
            if (1 != currentChild.getNodeType() || !"Security".equals(currentChild.getLocalName()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(currentChild.getNamespaceURI()) && !"http://schemas.xmlsoap.org/ws/2002/04/secext".equals(currentChild.getNamespaceURI())) continue;
            Element elem = (Element)currentChild;
            Attr attr = elem.getAttributeNodeNS(soapNamespace, actorLocal);
            String string = hActor = attr != null ? attr.getValue() : null;
            if (!WSSecurityUtil.isActorEqual(actor, hActor)) continue;
            if (foundSecurityHeader != null) {
                LOG.debug("Two or more security headers have the same actor name: {}", (Object)actor);
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
            foundSecurityHeader = elem;
        }
        return foundSecurityHeader;
    }

    public static boolean isActorEqual(String actor, String hActor) {
        if (!(hActor != null && hActor.length() != 0 || actor != null && actor.length() != 0)) {
            return true;
        }
        return hActor != null && actor != null && hActor.equalsIgnoreCase(actor);
    }

    public static List<Element> getDirectChildElements(Node fNode, String localName, String namespace) {
        ArrayList<Element> children = new ArrayList<Element>();
        for (Node currentChild = fNode.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            if (1 != currentChild.getNodeType() || !localName.equals(currentChild.getLocalName()) || !namespace.equals(currentChild.getNamespaceURI())) continue;
            children.add((Element)currentChild);
        }
        return children;
    }

    public static Element findBodyElement(Document doc) {
        Element docElement = doc.getDocumentElement();
        String ns = docElement.getNamespaceURI();
        return org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(docElement, "Body", ns);
    }

    public static List<Element> findElements(WSEncryptionPart part, CallbackLookup callbackLookup, Document doc) throws WSSecurityException {
        if (part.getElement() != null) {
            return Collections.singletonList(part.getElement());
        }
        String id = part.getId();
        if (id != null) {
            Element foundElement = callbackLookup.getElement(id, null, false);
            return Collections.singletonList(foundElement);
        }
        return callbackLookup.getElements(part.getName(), part.getNamespace());
    }

    public static WSEncryptionPart getDefaultEncryptionPart(Document doc) {
        String soapNamespace = WSSecurityUtil.getSOAPNamespace(doc.getDocumentElement());
        return new WSEncryptionPart("Body", soapNamespace, "Content");
    }

    private static Element createElementInSameNamespace(Node parent, String localName) {
        String qName = localName;
        String prefix = parent.getPrefix();
        if (prefix != null && prefix.length() > 0) {
            qName = prefix + ":" + localName;
        }
        String nsUri = parent.getNamespaceURI();
        return parent.getOwnerDocument().createElementNS(nsUri, qName);
    }

    public static Element prependChildElement(Element parent, Element child) {
        Node firstChild = parent.getFirstChild();
        Element domChild = null;
        try {
            domChild = (Element)WSSecurityUtil.getDomElement(child);
        }
        catch (WSSecurityException e) {
            LOG.debug("Error when try to get Dom Element from the child", e);
        }
        if (firstChild == null) {
            return (Element)parent.appendChild(domChild);
        }
        return (Element)parent.insertBefore(domChild, firstChild);
    }

    public static Element findWsseSecurityHeaderBlock(Document doc, Element envelope, boolean doCreate) throws WSSecurityException {
        return WSSecurityUtil.findWsseSecurityHeaderBlock(doc, envelope, null, doCreate);
    }

    public static Element findWsseSecurityHeaderBlock(Document doc, Element envelope, String actor, boolean doCreate) throws WSSecurityException {
        String soapNamespace = WSSecurityUtil.getSOAPNamespace(doc.getDocumentElement());
        Element header = org.apache.wss4j.common.util.XMLUtils.getDirectChildElement(doc.getDocumentElement(), "Header", soapNamespace);
        if (header == null) {
            if (doCreate) {
                if (isSAAJ14) {
                    try {
                        Node node = null;
                        Method method2 = GET_ENVELOPE_METHODS.get(doc.getClass());
                        if (method2 != null) {
                            try {
                                node = (Node)WSSecurityUtil.setAccessible(method2).invoke((Object)doc, new Object[0]);
                            }
                            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
                            }
                        }
                        header = node != null ? WSSecurityUtil.createElementInSameNamespace(node, "Header") : WSSecurityUtil.createElementInSameNamespace(doc.getDocumentElement(), "Header");
                        header = (Element)doc.importNode(header, true);
                        header = (Element)WSSecurityUtil.getDomElement(header);
                        header = WSSecurityUtil.prependChildElement(envelope, header);
                    }
                    catch (Exception e) {
                        e.printStackTrace();
                        throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
                    }
                }
                header = WSSecurityUtil.createElementInSameNamespace(envelope, "Header");
                header = WSSecurityUtil.prependChildElement(envelope, header);
            } else {
                return null;
            }
        }
        String actorLocal = "actor";
        if ("http://www.w3.org/2003/05/soap-envelope".equals(soapNamespace)) {
            actorLocal = "role";
        }
        Element foundSecurityHeader = null;
        for (Node currentChild = header.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            String hActor;
            if (1 != currentChild.getNodeType() || !"Security".equals(currentChild.getLocalName()) || !"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd".equals(currentChild.getNamespaceURI())) continue;
            Element elem = (Element)currentChild;
            Attr attr = elem.getAttributeNodeNS(soapNamespace, actorLocal);
            String string = hActor = attr != null ? attr.getValue() : null;
            if (!WSSecurityUtil.isActorEqual(actor, hActor)) continue;
            if (foundSecurityHeader != null) {
                LOG.debug("Two or more security headers have the same actor name: {}", (Object)actor);
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
            foundSecurityHeader = elem;
        }
        if (foundSecurityHeader != null) {
            return foundSecurityHeader;
        }
        if (doCreate) {
            foundSecurityHeader = doc.createElementNS("http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd", "wsse:Security");
            foundSecurityHeader.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:wsse", "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd");
            foundSecurityHeader = (Element)doc.importNode(foundSecurityHeader, true);
            foundSecurityHeader = (Element)WSSecurityUtil.getDomElement(foundSecurityHeader);
            return WSSecurityUtil.prependChildElement(header, foundSecurityHeader);
        }
        return null;
    }

    public static Text createBase64EncodedTextNode(Document doc, byte[] data) {
        return doc.createTextNode(XMLUtils.encodeToString(data));
    }

    public static SOAPConstants getSOAPConstants(Element startElement) {
        Document doc = startElement.getOwnerDocument();
        String ns = doc.getDocumentElement().getNamespaceURI();
        if ("http://www.w3.org/2003/05/soap-envelope".equals(ns)) {
            return new SOAP12Constants();
        }
        return new SOAP11Constants();
    }

    public static String getSOAPNamespace(Element startElement) {
        return WSSecurityUtil.getSOAPConstants(startElement).getEnvelopeURI();
    }

    public static List<Integer> decodeAction(String action) throws WSSecurityException {
        String actionToParse = action;
        if (actionToParse == null) {
            return Collections.emptyList();
        }
        if ("".equals(actionToParse = actionToParse.trim())) {
            return Collections.emptyList();
        }
        ArrayList<Integer> actions = new ArrayList<Integer>();
        String[] single = actionToParse.split("\\s");
        for (int i = 0; i < single.length; ++i) {
            if (single[i].equals("NoSecurity")) {
                return Collections.emptyList();
            }
            if (single[i].equals("UsernameToken")) {
                actions.add(1);
                continue;
            }
            if (single[i].equals("UsernameTokenNoPassword")) {
                actions.add(8192);
                continue;
            }
            if (single[i].equals("Signature")) {
                actions.add(2);
                continue;
            }
            if (single[i].equals("SignatureDerived")) {
                actions.add(32768);
                continue;
            }
            if (single[i].equals("Encrypt")) {
                actions.add(4);
                continue;
            }
            if (single[i].equals("EncryptDerived")) {
                actions.add(65536);
                continue;
            }
            if (single[i].equals("SAMLTokenUnsigned")) {
                actions.add(8);
                continue;
            }
            if (single[i].equals("SAMLTokenSigned")) {
                actions.add(16);
                continue;
            }
            if (single[i].equals("Timestamp")) {
                actions.add(32);
                continue;
            }
            if (single[i].equals("UsernameTokenSignature")) {
                actions.add(64);
                continue;
            }
            if (single[i].equals("enableSignatureConfirmation")) {
                actions.add(128);
                continue;
            }
            if (single[i].equals("CustomToken")) {
                actions.add(16384);
                continue;
            }
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Unknown action defined: " + single[i]});
        }
        return actions;
    }

    public static List<HandlerAction> decodeHandlerAction(String action, WSSConfig wssConfig) throws WSSecurityException {
        if (action == null) {
            return Collections.emptyList();
        }
        ArrayList<HandlerAction> actions = new ArrayList<HandlerAction>();
        String[] single = action.split(" ");
        for (int i = 0; i < single.length; ++i) {
            if (single[i].equals("NoSecurity")) {
                return actions;
            }
            if (single[i].equals("UsernameToken")) {
                actions.add(new HandlerAction(1));
                continue;
            }
            if (single[i].equals("UsernameTokenNoPassword")) {
                actions.add(new HandlerAction(8192));
                continue;
            }
            if (single[i].equals("Signature")) {
                actions.add(new HandlerAction(2));
                continue;
            }
            if (single[i].equals("SignatureDerived")) {
                actions.add(new HandlerAction(32768));
                continue;
            }
            if (single[i].equals("Encrypt")) {
                actions.add(new HandlerAction(4));
                continue;
            }
            if (single[i].equals("EncryptDerived")) {
                actions.add(new HandlerAction(65536));
                continue;
            }
            if (single[i].equals("SAMLTokenUnsigned")) {
                actions.add(new HandlerAction(8));
                continue;
            }
            if (single[i].equals("SAMLTokenSigned")) {
                actions.add(new HandlerAction(16));
                continue;
            }
            if (single[i].equals("Timestamp")) {
                actions.add(new HandlerAction(32));
                continue;
            }
            if (single[i].equals("UsernameTokenSignature")) {
                actions.add(new HandlerAction(64));
                continue;
            }
            if (single[i].equals("enableSignatureConfirmation")) {
                actions.add(new HandlerAction(128));
                continue;
            }
            if (single[i].equals("CustomToken")) {
                actions.add(new HandlerAction(16384));
                continue;
            }
            try {
                int parsedAction = Integer.parseInt(single[i]);
                if (wssConfig == null || wssConfig.getAction(parsedAction) == null) {
                    throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Unknown action defined: " + single[i]});
                }
                actions.add(new HandlerAction(parsedAction));
                continue;
            }
            catch (NumberFormatException ex) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, "empty", new Object[]{"Unknown action defined: " + single[i]});
            }
        }
        return actions;
    }

    public static byte[] generateNonce(int length) throws WSSecurityException {
        try {
            return XMLSecurityConstants.generateBytes(length);
        }
        catch (Exception ex) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, ex, "empty", new Object[]{"Error in generating nonce of length " + length});
        }
    }

    public static void inlineAttachments(List<Element> includeElements, CallbackHandler attachmentCallbackHandler, boolean removeAttachments) throws WSSecurityException {
        for (Element includeElement : includeElements) {
            String xopURI = includeElement.getAttributeNS(null, "href");
            if (xopURI == null) continue;
            byte[] attachmentBytes = WSSecurityUtil.getBytesFromAttachment(xopURI, attachmentCallbackHandler, removeAttachments);
            String encodedBytes = XMLUtils.encodeToString(attachmentBytes);
            Text encodedChild = includeElement.getOwnerDocument().createTextNode(encodedBytes);
            includeElement.getParentNode().replaceChild(encodedChild, includeElement);
        }
    }

    public static Element cloneElement(Document doc, Element clonedElement) throws WSSecurityException {
        clonedElement = (Element)clonedElement.cloneNode(true);
        if (isSAAJ14) {
            clonedElement = (Element)doc.importNode(clonedElement, true);
            clonedElement = (Element)WSSecurityUtil.getDomElement(clonedElement);
        }
        return clonedElement;
    }

    private static Node getDomElement(Node node) throws WSSecurityException {
        Method method2;
        if (node != null && isSAAJ14 && (method2 = GET_DOM_ELEMENTS_METHODS.get(node.getClass())) != null) {
            try {
                return (Node)WSSecurityUtil.setAccessible(method2).invoke((Object)node, new Object[0]);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new WSSecurityException(WSSecurityException.ErrorCode.INVALID_SECURITY);
            }
        }
        return node;
    }

    public static byte[] getBytesFromAttachment(String xopUri, RequestData data) throws WSSecurityException {
        return WSSecurityUtil.getBytesFromAttachment(xopUri, data.getAttachmentCallbackHandler());
    }

    public static byte[] getBytesFromAttachment(String xopUri, CallbackHandler attachmentCallbackHandler) throws WSSecurityException {
        return WSSecurityUtil.getBytesFromAttachment(xopUri, attachmentCallbackHandler, true);
    }

    public static byte[] getBytesFromAttachment(String xopUri, CallbackHandler attachmentCallbackHandler, boolean removeAttachments) throws WSSecurityException {
        return AttachmentUtils.getBytesFromAttachment(xopUri, attachmentCallbackHandler, removeAttachments);
    }

    public static String getAttachmentId(String xopUri) throws WSSecurityException {
        return AttachmentUtils.getAttachmentId(xopUri);
    }

    public static void storeBytesInAttachment(Element parentElement, Document doc, String attachmentId, byte[] bytes, CallbackHandler attachmentCallbackHandler) throws WSSecurityException {
        parentElement.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:xop", "http://www.w3.org/2004/08/xop/include");
        Element xopInclude = doc.createElementNS("http://www.w3.org/2004/08/xop/include", "xop:Include");
        try {
            xopInclude.setAttributeNS(null, "href", "cid:" + URLEncoder.encode(attachmentId, StandardCharsets.UTF_8.name()));
        }
        catch (UnsupportedEncodingException e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, (Exception)e);
        }
        parentElement.appendChild(xopInclude);
        Attachment resultAttachment = new Attachment();
        resultAttachment.setId(attachmentId);
        resultAttachment.setMimeType("application/ciphervalue");
        resultAttachment.setSourceStream(new ByteArrayInputStream(bytes));
        AttachmentResultCallback attachmentResultCallback = new AttachmentResultCallback();
        attachmentResultCallback.setAttachmentId(attachmentId);
        attachmentResultCallback.setAttachment(resultAttachment);
        try {
            attachmentCallbackHandler.handle(new Callback[]{attachmentResultCallback});
        }
        catch (Exception e) {
            throw new WSSecurityException(WSSecurityException.ErrorCode.FAILURE, e);
        }
    }

    static {
        try {
            Method[] methods;
            for (Method method2 : methods = WSSecurityUtil.class.getClassLoader().loadClass("com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl").getMethods()) {
                if (!method2.getName().equals("register")) continue;
                isSAAJ14 = true;
                break;
            }
        }
        catch (ClassNotFoundException cnfe) {
            LOG.debug("Can't load class com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl", cnfe);
            try {
                Method[] methods;
                for (Method method3 : methods = WSSecurityUtil.class.getClassLoader().loadClass("com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl").getMethods()) {
                    if (!method3.getName().equals("register")) continue;
                    isSAAJ14 = true;
                }
            }
            catch (ClassNotFoundException cnfe1) {
                LOG.debug("can't load class com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl", cnfe1);
            }
        }
    }
}

