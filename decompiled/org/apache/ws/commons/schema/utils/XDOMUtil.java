/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import org.apache.ws.commons.schema.utils.DOMUtil;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public final class XDOMUtil
extends DOMUtil {
    private XDOMUtil() {
    }

    public static Element getFirstChildElementNS(Node parent, String uri) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            String childURI;
            if (child.getNodeType() != 1 || (childURI = child.getNamespaceURI()) == null || !childURI.equals(uri)) continue;
            return (Element)child;
        }
        return null;
    }

    public static Element getNextSiblingElementNS(Node node, String uri) {
        for (Node sibling = node.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            String siblingURI;
            if (sibling.getNodeType() != 1 || (siblingURI = sibling.getNamespaceURI()) == null || !siblingURI.equals(uri)) continue;
            return (Element)sibling;
        }
        return null;
    }

    public static boolean anyElementsWithNameNS(Element element, String uri, String name) {
        Element el = XDOMUtil.getFirstChildElementNS((Node)element, uri);
        while (el != null) {
            if (el.getLocalName().equals(name) && el.getNamespaceURI().equals(uri)) {
                return true;
            }
            el = XDOMUtil.getNextSiblingElementNS((Node)el, uri);
        }
        return false;
    }
}

