/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.Text;

public final class ElementSupport {
    private ElementSupport() {
    }

    public static void adoptElement(@Nonnull Document adopter, @Nonnull Element adoptee) {
        Constraint.isNotNull(adoptee, "Adoptee Element may not be null");
        Constraint.isNotNull(adopter, "Adopter Element may not be null");
        if (!adoptee.getOwnerDocument().isSameNode(adopter) && adopter.adoptNode(adoptee) == null) {
            throw new RuntimeException("DOM Element node adoption failed. This is most likely caused by the Element and Document being produced by different DOM implementations.");
        }
    }

    public static void appendChildElement(@Nonnull Element parentElement, @Nullable Element childElement) {
        if (childElement == null) {
            return;
        }
        Constraint.isNotNull(parentElement, "Parent Element may not be null");
        Document parentDocument = parentElement.getOwnerDocument();
        if (!parentDocument.equals(childElement.getOwnerDocument())) {
            ElementSupport.adoptElement(parentDocument, childElement);
        }
        parentElement.appendChild(childElement);
    }

    public static void appendTextContent(@Nonnull Element element, @Nullable String textContent) {
        if (textContent == null) {
            return;
        }
        Constraint.isNotNull(element, "Element may not be null");
        Text textNode = element.getOwnerDocument().createTextNode(textContent);
        element.appendChild(textNode);
    }

    public static Element constructElement(@Nonnull Document document, @Nonnull QName elementName) {
        Constraint.isNotNull(elementName, "Element name can not be null");
        return ElementSupport.constructElement(document, elementName.getNamespaceURI(), elementName.getLocalPart(), elementName.getPrefix());
    }

    public static Element constructElement(@Nonnull Document document, @Nullable String namespaceURI, @Nonnull String localName, @Nullable String prefix) {
        Constraint.isNotNull(document, "Document may not be null");
        String trimmedLocalName = Constraint.isNotNull(StringSupport.trimOrNull(localName), "Element local name may not be null or empty");
        String trimmedPrefix = StringSupport.trimOrNull(prefix);
        String qualifiedName = trimmedPrefix != null ? trimmedPrefix + ":" + StringSupport.trimOrNull(trimmedLocalName) : StringSupport.trimOrNull(trimmedLocalName);
        return document.createElementNS(StringSupport.trimOrNull(namespaceURI), qualifiedName);
    }

    @Nonnull
    public static List<Element> getChildElements(@Nullable Node root) {
        if (root == null) {
            return Collections.emptyList();
        }
        ArrayList<Element> children = new ArrayList<Element>();
        Element childNode = ElementSupport.getFirstChildElement(root);
        while (childNode != null) {
            children.add(childNode);
            childNode = ElementSupport.getNextSiblingElement(childNode);
        }
        return children;
    }

    @Nonnull
    public static List<Element> getChildElements(@Nullable Node root, @Nullable QName name) {
        if (root == null || name == null) {
            return Collections.emptyList();
        }
        return ElementSupport.getChildElementsByTagNameNS(root, name.getNamespaceURI(), name.getLocalPart());
    }

    @Nullable
    public static Element getFirstChildElement(@Nullable Node root, @Nullable QName name) {
        List<Element> elements = ElementSupport.getChildElements(root, name);
        if (elements.size() > 0) {
            return elements.get(0);
        }
        return null;
    }

    @Nonnull
    public static List<Element> getChildElementsByTagName(@Nullable Node root, @Nullable String localName) {
        if (root == null) {
            return Collections.emptyList();
        }
        ArrayList<Element> children = new ArrayList<Element>();
        Element childNode = ElementSupport.getFirstChildElement(root);
        while (childNode != null) {
            if (Objects.equals(childNode.getLocalName(), localName)) {
                children.add(childNode);
            }
            childNode = ElementSupport.getNextSiblingElement(childNode);
        }
        return children;
    }

    @Nonnull
    public static List<Element> getChildElementsByTagNameNS(@Nullable Node root, @Nullable String namespaceURI, @Nullable String localName) {
        if (root == null || localName == null) {
            return Collections.emptyList();
        }
        ArrayList<Element> children = new ArrayList<Element>();
        Element childNode = ElementSupport.getFirstChildElement(root);
        while (childNode != null) {
            if (ElementSupport.isElementNamed(childNode, namespaceURI, localName)) {
                children.add(childNode);
            }
            childNode = ElementSupport.getNextSiblingElement(childNode);
        }
        return children;
    }

    @Nullable
    public static Element getElementAncestor(@Nullable Node currentNode) {
        if (currentNode == null) {
            return null;
        }
        Node parent = currentNode.getParentNode();
        if (parent != null) {
            if (parent.getNodeType() == 1) {
                return (Element)parent;
            }
            return ElementSupport.getElementAncestor(parent);
        }
        return null;
    }

    @Nonnull
    public static String getElementContentAsString(@Nullable Element element) {
        if (element == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        boolean first = true;
        for (Node node = element.getFirstChild(); node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != 3 && node.getNodeType() != 4) continue;
            if (first) {
                first = false;
            } else {
                builder.append(" \n\r\t".charAt(0));
            }
            builder.append(((Text)node).getWholeText());
        }
        return builder.toString();
    }

    @Nonnull
    public static List<String> getElementContentAsList(@Nullable Element element) {
        if (element == null) {
            return Collections.emptyList();
        }
        return StringSupport.stringToList(ElementSupport.getElementContentAsString(element), " \n\r\t");
    }

    @Nullable
    public static QName getElementContentAsQName(@Nullable Element element) {
        if (element == null) {
            return null;
        }
        String elementContent = StringSupport.trimOrNull(ElementSupport.getElementContentAsString(element));
        if (elementContent == null) {
            return null;
        }
        QName result = null;
        String[] valueComponents = elementContent.split(":");
        if (valueComponents.length == 1) {
            result = QNameSupport.constructQName(element.lookupNamespaceURI(null), valueComponents[0], null);
        } else if (valueComponents.length == 2) {
            result = QNameSupport.constructQName(element.lookupNamespaceURI(valueComponents[0]), valueComponents[1], valueComponents[0]);
        }
        return result;
    }

    @Nullable
    public static Element getFirstChildElement(@Nullable Node n) {
        Node child;
        if (n == null) {
            return null;
        }
        for (child = n.getFirstChild(); child != null && child.getNodeType() != 1; child = child.getNextSibling()) {
        }
        if (child != null) {
            return (Element)child;
        }
        return null;
    }

    @Nonnull
    public static Map<QName, List<Element>> getIndexedChildElements(@Nullable Element root) {
        if (root == null) {
            return Collections.emptyMap();
        }
        HashMap<QName, List<Element>> children = new HashMap<QName, List<Element>>();
        Element e = ElementSupport.getFirstChildElement(root);
        while (e != null) {
            QName qname = QNameSupport.getNodeQName(e);
            ArrayList<Element> elements = (ArrayList<Element>)children.get(qname);
            if (elements == null) {
                elements = new ArrayList<Element>();
                children.put(qname, elements);
            }
            elements.add(e);
            e = ElementSupport.getNextSiblingElement(e);
        }
        return children;
    }

    @Nullable
    public static Element getNextSiblingElement(@Nullable Node n) {
        Node sib;
        if (n == null) {
            return null;
        }
        for (sib = n.getNextSibling(); sib != null && sib.getNodeType() != 1; sib = sib.getNextSibling()) {
        }
        if (sib != null) {
            return (Element)sib;
        }
        return null;
    }

    public static boolean isElementNamed(@Nullable Element e, @Nullable QName name) {
        if (name == null) {
            return false;
        }
        return ElementSupport.isElementNamed(e, name.getNamespaceURI(), name.getLocalPart());
    }

    public static boolean isElementNamed(@Nullable Element e, @Nullable String ns, @Nullable String localName) {
        return e != null && Objects.equals(ns, e.getNamespaceURI()) && Objects.equals(localName, e.getLocalName());
    }

    public static void setDocumentElement(@Nonnull Document document, @Nonnull Element element) {
        Constraint.isNotNull(document, "Document may not be null");
        Constraint.isNotNull(element, "Element may not be null");
        Element rootElement = document.getDocumentElement();
        if (rootElement == null) {
            ElementSupport.adoptElement(document, element);
            document.appendChild(element);
        } else if (!rootElement.isSameNode(element)) {
            ElementSupport.adoptElement(document, element);
            document.replaceChild(element, rootElement);
        }
    }
}

