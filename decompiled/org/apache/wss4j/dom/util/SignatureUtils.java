/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.dom.util;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import org.apache.wss4j.common.ext.WSSecurityException;
import org.apache.wss4j.dom.WSDataRef;
import org.apache.wss4j.dom.WSDocInfo;
import org.apache.wss4j.dom.engine.WSSecurityEngineResult;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class SignatureUtils {
    private SignatureUtils() {
    }

    public static void verifySignedElement(Element elem, WSDocInfo wsDocInfo) throws WSSecurityException {
        SignatureUtils.verifySignedElement(elem, wsDocInfo.getResultsByTag(2));
    }

    public static void verifySignedElement(Element elem, List<WSSecurityEngineResult> signedResults) throws WSSecurityException {
        if (signedResults != null) {
            for (WSSecurityEngineResult signedResult : signedResults) {
                List dataRefs = (List)signedResult.get("data-ref-uris");
                if (dataRefs == null) continue;
                for (WSDataRef dataRef : dataRefs) {
                    if (!SignatureUtils.isElementOrAncestorSigned(elem, dataRef.getProtectedElement())) continue;
                    return;
                }
            }
        }
        throw new WSSecurityException(WSSecurityException.ErrorCode.FAILED_CHECK, "elementNotSigned", new Object[]{elem});
    }

    public static List<String> getInclusivePrefixes(Element target, boolean excludeVisible) {
        Node attribute;
        int i;
        NamedNodeMap attributes;
        LinkedHashSet<String> result = new LinkedHashSet<String>();
        Node parent = target;
        while (parent.getParentNode() != null && 9 != parent.getParentNode().getNodeType()) {
            parent = parent.getParentNode();
            attributes = parent.getAttributes();
            for (i = 0; i < attributes.getLength(); ++i) {
                attribute = attributes.item(i);
                if (!"http://www.w3.org/2000/xmlns/".equals(attribute.getNamespaceURI())) continue;
                if ("xmlns".equals(attribute.getNodeName())) {
                    result.add("#default");
                    continue;
                }
                result.add(attribute.getLocalName());
            }
        }
        if (excludeVisible) {
            attributes = target.getAttributes();
            for (i = 0; i < attributes.getLength(); ++i) {
                attribute = attributes.item(i);
                if ("http://www.w3.org/2000/xmlns/".equals(attribute.getNamespaceURI())) {
                    if ("xmlns".equals(attribute.getNodeName())) {
                        result.remove("#default");
                    } else {
                        result.remove(attribute.getLocalName());
                    }
                }
                if (attribute.getPrefix() == null) continue;
                result.remove(attribute.getPrefix());
            }
            if (target.getPrefix() == null) {
                result.remove("#default");
            } else {
                result.remove(target.getPrefix());
            }
        }
        return new ArrayList<String>(result);
    }

    private static boolean isElementOrAncestorSigned(Element elem, Element signedElement) throws WSSecurityException {
        Element envelope = elem.getOwnerDocument().getDocumentElement();
        Node cur = elem;
        while (!cur.isSameNode(envelope)) {
            if (cur.getNodeType() == 1 && cur.equals(signedElement)) {
                return true;
            }
            cur = cur.getParentNode();
        }
        return false;
    }
}

