/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.policy;

import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.neethi.Constants;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public final class SPUtils {
    private SPUtils() {
    }

    public static boolean hasChildElements(Element element) {
        for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
            if (firstChild.getNodeType() != 1) continue;
            return true;
        }
        return false;
    }

    public static Element getFirstPolicyChildElement(Element element) {
        Element policy = SPUtils.getFirstChildElement((Node)element, "Policy");
        if (policy != null && Constants.isPolicyNS(policy.getNamespaceURI())) {
            return policy;
        }
        return null;
    }

    public static boolean hasChildElementWithName(Element element, QName elementName) {
        Element child = SPUtils.getFirstChildElement((Node)element, elementName);
        return child != null;
    }

    public static Element getFirstChildElement(Node parent, String childNodeName) {
        Node node;
        for (node = parent.getFirstChild(); !(node == null || 1 == node.getNodeType() && node.getLocalName().equals(childNodeName)); node = node.getNextSibling()) {
        }
        return (Element)node;
    }

    public static Element getFirstChildElement(Node parent, QName childNodeName) {
        Node node;
        for (node = parent.getFirstChild(); !(node == null || 1 == node.getNodeType() && SPUtils.isNodeEqualToQName(node, childNodeName)); node = node.getNextSibling()) {
        }
        return (Element)node;
    }

    private static boolean isNodeEqualToQName(Node node, QName nodeName) {
        return (node.getNamespaceURI() == null && nodeName.getNamespaceURI() == null || node.getNamespaceURI() != null && node.getNamespaceURI().equals(nodeName.getNamespaceURI())) && node.getLocalName().equals(nodeName.getLocalPart());
    }

    public static String getFirstChildElementText(Node parent, QName childNodeName) {
        Element element = SPUtils.getFirstChildElement(parent, childNodeName);
        return element != null ? element.getTextContent() : null;
    }

    public static Element getFirstChildElement(Node parent) {
        Node node;
        for (node = parent.getFirstChild(); node != null && 1 != node.getNodeType(); node = node.getNextSibling()) {
        }
        return (Element)node;
    }

    public static Element getNextSiblingElement(Node node) {
        Node n;
        for (n = node.getNextSibling(); n != null && 1 != n.getNodeType(); n = n.getNextSibling()) {
        }
        return (Element)n;
    }

    public static boolean isOptional(Element element) {
        Attr attr = SPUtils.findOptionalAttribute(element);
        if (attr != null) {
            String v = attr.getValue();
            return "true".equalsIgnoreCase(v) || "1".equals(v);
        }
        return false;
    }

    public static Attr findOptionalAttribute(Element element) {
        NamedNodeMap attributes = element.getAttributes();
        for (int x = 0; x < attributes.getLength(); ++x) {
            Attr attr = (Attr)attributes.item(x);
            QName qName = new QName(attr.getNamespaceURI(), attr.getLocalName());
            if (!Constants.isOptionalAttribute(qName)) continue;
            return attr;
        }
        return null;
    }

    public static boolean isIgnorable(Element element) throws IllegalArgumentException {
        String value;
        Attr attr = SPUtils.findIgnorableAttribute(element);
        if (attr != null && ("true".equalsIgnoreCase(value = attr.getValue()) || "1".equals(value))) {
            if ("http://docs.oasis-open.org/ws-sx/ws-securitypolicy/200802".equals(element.getNamespaceURI())) {
                throw new IllegalArgumentException("Ignorable attribute not allowed.");
            }
            return true;
        }
        return false;
    }

    public static Attr findIgnorableAttribute(Element element) {
        NamedNodeMap attributes = element.getAttributes();
        for (int x = 0; x < attributes.getLength(); ++x) {
            Attr attr = (Attr)attributes.item(x);
            QName qName = new QName(attr.getNamespaceURI(), attr.getLocalName());
            if (!Constants.isIgnorableAttribute(qName)) continue;
            return attr;
        }
        return null;
    }

    public static String getAttribute(Element element, QName attName) {
        Attr attr = attName.getNamespaceURI() == null || "".equals(attName.getNamespaceURI()) ? element.getAttributeNode(attName.getLocalPart()) : element.getAttributeNodeNS(attName.getNamespaceURI(), attName.getLocalPart());
        return attr == null ? null : attr.getValue().trim();
    }

    public static QName getElementQName(Element element) {
        return new QName(element.getNamespaceURI(), element.getLocalName(), element.getPrefix());
    }

    public static void serialize(Node node, XMLStreamWriter xmlStreamWriter) throws XMLStreamException {
        if (node.getNodeType() == 9) {
            Document document = (Document)node;
            SPUtils.serialize(document.getDocumentElement(), xmlStreamWriter);
        }
        if (node.getNodeType() == 1) {
            String ns;
            Element element = (Element)node;
            xmlStreamWriter.writeStartElement(element.getPrefix(), element.getLocalName(), element.getNamespaceURI());
            NamedNodeMap namedNodeMap = element.getAttributes();
            for (int i = 0; i < namedNodeMap.getLength(); ++i) {
                Attr attr = (Attr)namedNodeMap.item(i);
                String prefix = attr.getPrefix();
                if (prefix != null && "xmlns".equals(prefix)) {
                    xmlStreamWriter.writeNamespace(attr.getLocalName(), attr.getValue());
                    continue;
                }
                if (prefix == null && "xmlns".equals(attr.getLocalName())) {
                    xmlStreamWriter.writeDefaultNamespace(attr.getValue());
                    continue;
                }
                xmlStreamWriter.writeAttribute(prefix, attr.getNamespaceURI(), attr.getLocalName(), attr.getValue());
            }
            if (element.getPrefix() != null && (ns = xmlStreamWriter.getNamespaceContext().getNamespaceURI(element.getPrefix())) == null) {
                xmlStreamWriter.writeNamespace(element.getPrefix(), element.getNamespaceURI());
            }
            for (Node firstChild = element.getFirstChild(); firstChild != null; firstChild = firstChild.getNextSibling()) {
                SPUtils.serialize(firstChild, xmlStreamWriter);
            }
            xmlStreamWriter.writeEndElement();
        } else if (node.getNodeType() == 3) {
            Text text = (Text)node;
            xmlStreamWriter.writeCharacters(text.getData());
        }
    }
}

