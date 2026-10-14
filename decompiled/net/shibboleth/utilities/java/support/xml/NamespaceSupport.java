/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.xml;

import java.util.Objects;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import net.shibboleth.utilities.java.support.logic.Constraint;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import net.shibboleth.utilities.java.support.xml.QNameSupport;
import org.w3c.dom.DOMException;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public final class NamespaceSupport {
    private NamespaceSupport() {
    }

    public static void appendNamespaceDeclaration(@Nonnull Element element, @Nonnull String namespaceURI, @Nullable String prefix) {
        String attributeName;
        Constraint.isNotNull(element, "Element may not be null");
        String nsURI = StringSupport.trimOrNull(namespaceURI);
        String nsPrefix = StringSupport.trimOrNull(prefix);
        Constraint.isNotNull(nsURI, "namespace may not be null or empty");
        if (nsPrefix == null) {
            if (null == element.getPrefix() && !namespaceURI.equals(element.getNamespaceURI())) {
                throw new DOMException(15, "Cannot replace an element's default namespace");
            }
            attributeName = "xmlns";
        } else {
            if (nsPrefix.equals(element.getPrefix()) && !namespaceURI.equals(element.getNamespaceURI())) {
                throw new DOMException(15, "Cannot replace an element's default namespace");
            }
            attributeName = "xmlns:" + nsPrefix;
        }
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", attributeName, nsURI);
    }

    @Nullable
    public static String lookupNamespaceURI(@Nonnull Element startingElement, @Nullable Element stoppingElement, @Nonnull String prefix) {
        Element ancestor;
        Constraint.isNotNull(startingElement, "Starting element may not be null");
        if (startingElement.hasAttributes()) {
            NamedNodeMap map = startingElement.getAttributes();
            int length = map.getLength();
            for (int i = 0; i < length; ++i) {
                Node attr = map.item(i);
                String value = attr.getNodeValue();
                if (!Objects.equals(attr.getNamespaceURI(), "http://www.w3.org/2000/xmlns/")) continue;
                if (Objects.equals(attr.getLocalName(), "xmlns") && prefix == null) {
                    return value;
                }
                if (!Objects.equals(attr.getPrefix(), "xmlns") || !Objects.equals(attr.getLocalName(), prefix)) continue;
                return value;
            }
        }
        if (startingElement != stoppingElement && (ancestor = ElementSupport.getElementAncestor(startingElement)) != null) {
            return NamespaceSupport.lookupNamespaceURI(ancestor, stoppingElement, prefix);
        }
        return null;
    }

    @Nullable
    public static String lookupPrefix(@Nonnull Element startingElement, @Nullable Element stopingElement, @Nullable String namespaceURI) {
        Element ancestor;
        Constraint.isNotNull(startingElement, "Starting element may not be null");
        if (null == namespaceURI) {
            return null;
        }
        if (startingElement.hasAttributes()) {
            NamedNodeMap map = startingElement.getAttributes();
            int length = map.getLength();
            for (int i = 0; i < length; ++i) {
                String localName;
                String foundNamespace;
                Node attr = map.item(i);
                if (!Objects.equals(attr.getNamespaceURI(), "http://www.w3.org/2000/xmlns/") || !Objects.equals(attr.getPrefix(), "xmlns") || !Objects.equals(attr.getNodeValue(), namespaceURI) || !Objects.equals(foundNamespace = startingElement.lookupNamespaceURI(localName = attr.getLocalName()), namespaceURI)) continue;
                return localName;
            }
        }
        if (startingElement != stopingElement && (ancestor = ElementSupport.getElementAncestor(startingElement)) != null) {
            return NamespaceSupport.lookupPrefix(ancestor, stopingElement, namespaceURI);
        }
        return null;
    }

    public static void rootNamespaces(@Nullable Element domElement) {
        NamespaceSupport.rootNamespaces(domElement, domElement);
    }

    private static void rootNamespaces(@Nullable Element domElement, @Nullable Element upperNamespaceSearchBound) {
        if (domElement == null) {
            return;
        }
        String namespaceURI = null;
        String namespacePrefix = domElement.getPrefix();
        boolean nsDeclaredOnElement = false;
        nsDeclaredOnElement = namespacePrefix == null ? domElement.hasAttributeNS(null, "xmlns") : domElement.hasAttributeNS("http://www.w3.org/2000/xmlns/", namespacePrefix);
        if (!nsDeclaredOnElement && (namespaceURI = NamespaceSupport.lookupNamespaceURI(domElement, upperNamespaceSearchBound, namespacePrefix)) == null) {
            namespaceURI = NamespaceSupport.lookupNamespaceURI(upperNamespaceSearchBound, null, namespacePrefix);
            if (namespaceURI != null) {
                NamespaceSupport.appendNamespaceDeclaration(domElement, namespaceURI, namespacePrefix);
            } else if (namespacePrefix != null) {
                throw new DOMException(14, "Unable to resolve namespace prefix " + namespacePrefix + " found on element " + QNameSupport.getNodeQName(domElement));
            }
        }
        NamedNodeMap attributes = domElement.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            namespacePrefix = null;
            namespaceURI = null;
            Node attributeNode = attributes.item(i);
            if (attributeNode.getNodeType() != 2 || (namespacePrefix = StringSupport.trimOrNull(attributeNode.getPrefix())) == null || namespacePrefix.equals("xmlns") || namespacePrefix.equals("xml") || (namespaceURI = NamespaceSupport.lookupNamespaceURI(domElement, upperNamespaceSearchBound, namespacePrefix)) != null) continue;
            namespaceURI = NamespaceSupport.lookupNamespaceURI(upperNamespaceSearchBound, null, namespacePrefix);
            if (namespaceURI == null) {
                throw new DOMException(14, "Unable to resolve namespace prefix " + namespacePrefix + " found on attribute " + QNameSupport.getNodeQName(attributeNode) + " found on element " + QNameSupport.getNodeQName(domElement));
            }
            NamespaceSupport.appendNamespaceDeclaration(domElement, namespaceURI, namespacePrefix);
        }
        Element childNode = ElementSupport.getFirstChildElement(domElement);
        while (childNode != null) {
            NamespaceSupport.rootNamespaces(childNode, upperNamespaceSearchBound);
            childNode = ElementSupport.getNextSiblingElement(childNode);
        }
    }
}

