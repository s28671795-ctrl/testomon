/*
 * Decompiled with CFR 0.152.
 */
package org.apache.ws.commons.schema.utils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class DOMUtil {
    protected DOMUtil() {
    }

    public static Element getFirstChildElement(Node parent) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != 1) continue;
            return (Element)child;
        }
        return null;
    }

    public static Element getLastChildElement(Node parent) {
        for (Node child = parent.getLastChild(); child != null; child = child.getPreviousSibling()) {
            if (child.getNodeType() != 1) continue;
            return (Element)child;
        }
        return null;
    }

    public static Element getNextSiblingElement(Node node) {
        for (Node sibling = node.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            if (sibling.getNodeType() != 1) continue;
            return (Element)sibling;
        }
        return null;
    }

    public static Element getFirstChildElement(Node parent, String elemName) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != 1 || !child.getLocalName().equals(elemName)) continue;
            return (Element)child;
        }
        return null;
    }

    public static Element getLastChildElement(Node parent, String elemName) {
        for (Node child = parent.getLastChild(); child != null; child = child.getPreviousSibling()) {
            if (child.getNodeType() != 1 || !child.getLocalName().equals(elemName)) continue;
            return (Element)child;
        }
        return null;
    }

    public static Element getNextSiblingElement(Node node, String elemName) {
        for (Node sibling = node.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            if (sibling.getNodeType() != 1 || !sibling.getLocalName().equals(elemName)) continue;
            return (Element)sibling;
        }
        return null;
    }

    public static Element getFirstChildElementNS(Node parent, String uri, String localpart) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            String childURI;
            if (child.getNodeType() != 1 || (childURI = child.getNamespaceURI()) == null || !childURI.equals(uri) || !child.getLocalName().equals(localpart)) continue;
            return (Element)child;
        }
        return null;
    }

    public static Element getLastChildElementNS(Node parent, String uri, String localpart) {
        for (Node child = parent.getLastChild(); child != null; child = child.getPreviousSibling()) {
            String childURI;
            if (child.getNodeType() != 1 || (childURI = child.getNamespaceURI()) == null || !childURI.equals(uri) || !child.getLocalName().equals(localpart)) continue;
            return (Element)child;
        }
        return null;
    }

    public static Element getNextSiblingElementNS(Node node, String uri, String localpart) {
        for (Node sibling = node.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            String siblingURI;
            if (sibling.getNodeType() != 1 || (siblingURI = sibling.getNamespaceURI()) == null || !siblingURI.equals(uri) || !sibling.getLocalName().equals(localpart)) continue;
            return (Element)sibling;
        }
        return null;
    }

    public static Element getFirstChildElement(Node parent, String[] elemNames) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != 1) continue;
            for (String elemName : elemNames) {
                if (!child.getLocalName().equals(elemName)) continue;
                return (Element)child;
            }
        }
        return null;
    }

    public static Element getLastChildElement(Node parent, String[] elemNames) {
        for (Node child = parent.getLastChild(); child != null; child = child.getPreviousSibling()) {
            if (child.getNodeType() != 1) continue;
            for (String elemName : elemNames) {
                if (!child.getLocalName().equals(elemName)) continue;
                return (Element)child;
            }
        }
        return null;
    }

    public static Element getNextSiblingElement(Node node, String[] elemNames) {
        for (Node sibling = node.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            if (sibling.getNodeType() != 1) continue;
            for (String elemName : elemNames) {
                if (!sibling.getLocalName().equals(elemName)) continue;
                return (Element)sibling;
            }
        }
        return null;
    }

    public static Element getFirstChildElementNS(Node parent, String[][] elemNames) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            if (child.getNodeType() != 1) continue;
            for (String[] elemName : elemNames) {
                String uri = child.getNamespaceURI();
                if (uri == null || !uri.equals(elemName[0]) || !child.getLocalName().equals(elemName[1])) continue;
                return (Element)child;
            }
        }
        return null;
    }

    public static Element getLastChildElementNS(Node parent, String[][] elemNames) {
        for (Node child = parent.getLastChild(); child != null; child = child.getPreviousSibling()) {
            if (child.getNodeType() != 1) continue;
            for (String[] elemName : elemNames) {
                String uri = child.getNamespaceURI();
                if (uri == null || !uri.equals(elemName[0]) || !child.getLocalName().equals(elemName[1])) continue;
                return (Element)child;
            }
        }
        return null;
    }

    public static Element getNextSiblingElementNS(Node node, String[][] elemNames) {
        for (Node sibling = node.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            if (sibling.getNodeType() != 1) continue;
            for (String[] elemName : elemNames) {
                String uri = sibling.getNamespaceURI();
                if (uri == null || !uri.equals(elemName[0]) || !sibling.getLocalName().equals(elemName[1])) continue;
                return (Element)sibling;
            }
        }
        return null;
    }

    public static Element getFirstChildElement(Node parent, String elemName, String attrName, String attrValue) {
        for (Node child = parent.getFirstChild(); child != null; child = child.getNextSibling()) {
            Element element;
            if (child.getNodeType() != 1 || !(element = (Element)child).getLocalName().equals(elemName) || !element.getAttribute(attrName).equals(attrValue)) continue;
            return element;
        }
        return null;
    }

    public static Element getLastChildElement(Node parent, String elemName, String attrName, String attrValue) {
        for (Node child = parent.getLastChild(); child != null; child = child.getPreviousSibling()) {
            Element element;
            if (child.getNodeType() != 1 || !(element = (Element)child).getLocalName().equals(elemName) || !element.getAttribute(attrName).equals(attrValue)) continue;
            return element;
        }
        return null;
    }

    public static Element getNextSiblingElement(Node node, String elemName, String attrName, String attrValue) {
        for (Node sibling = node.getNextSibling(); sibling != null; sibling = sibling.getNextSibling()) {
            Element element;
            if (sibling.getNodeType() != 1 || !(element = (Element)sibling).getLocalName().equals(elemName) || !element.getAttribute(attrName).equals(attrValue)) continue;
            return element;
        }
        return null;
    }

    public static String getChildText(Node node) {
        if (node == null) {
            return null;
        }
        StringBuilder str = new StringBuilder();
        for (Node child = node.getFirstChild(); child != null; child = child.getNextSibling()) {
            short type = child.getNodeType();
            if (type == 3) {
                str.append(child.getNodeValue());
                continue;
            }
            if (type != 4) continue;
            str.append(DOMUtil.getChildText(child));
        }
        return str.toString();
    }

    public static String getName(Node node) {
        return node.getLocalName();
    }

    public static String getLocalName(Node node) {
        String name = node.getLocalName();
        return name != null ? name : node.getLocalName();
    }

    public static Element getParent(Element elem) {
        Node parent = elem.getParentNode();
        if (parent instanceof Element) {
            return (Element)parent;
        }
        return null;
    }

    public static Document getDocument(Node node) {
        return node.getOwnerDocument();
    }

    public static Element getRoot(Document doc) {
        return doc.getDocumentElement();
    }

    public static Attr getAttr(Element elem, String name) {
        return elem.getAttributeNode(name);
    }

    public static Attr getAttrNS(Element elem, String nsUri, String localName) {
        return elem.getAttributeNodeNS(nsUri, localName);
    }

    public static Attr[] getAttrs(Element elem) {
        NamedNodeMap attrMap = elem.getAttributes();
        Attr[] attrArray = new Attr[attrMap.getLength()];
        for (int i = 0; i < attrMap.getLength(); ++i) {
            attrArray[i] = (Attr)attrMap.item(i);
        }
        return attrArray;
    }

    public static String getValue(Attr attribute) {
        return attribute.getValue();
    }

    public static String getAttrValue(Element elem, String name) {
        return elem.getAttribute(name);
    }

    public static String getAttrValueNS(Element elem, String nsUri, String localName) {
        return elem.getAttributeNS(nsUri, localName);
    }

    public static String getNamespaceURI(Node node) {
        return node.getNamespaceURI();
    }
}

