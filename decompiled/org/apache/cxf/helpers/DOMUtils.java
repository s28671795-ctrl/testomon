/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.helpers;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.cxf.common.logging.LogUtils;
import org.apache.cxf.common.util.ReflectionUtil;
import org.apache.cxf.common.util.StringUtils;
import org.w3c.dom.Attr;
import org.w3c.dom.Comment;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.EntityResolver;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public final class DOMUtils {
    private static boolean isJre9SAAJ;
    private static final Map<ClassLoader, DocumentBuilder> DOCUMENT_BUILDERS;
    private static final String XMLNAMESPACE = "xmlns";
    private static volatile Document emptyDocument;
    private static final ClassValue<Method> GET_DOM_ELEMENTS_METHODS;
    private static final ClassValue<Field> GET_DOCUMENT_FRAGMENT_FIELDS;

    private DOMUtils() {
    }

    private static DocumentBuilder getDocumentBuilder() throws ParserConfigurationException {
        ClassLoader loader = DOMUtils.getContextClassLoader();
        if (loader == null) {
            loader = DOMUtils.getClassLoader(DOMUtils.class);
        }
        if (loader == null) {
            return DOMUtils.createDocumentBuilder();
        }
        DocumentBuilder factory2 = DOCUMENT_BUILDERS.get(loader);
        if (factory2 == null) {
            factory2 = DOMUtils.createDocumentBuilder();
            DOCUMENT_BUILDERS.put(loader, factory2);
        }
        return factory2;
    }

    private static DocumentBuilder createDocumentBuilder() throws ParserConfigurationException {
        DocumentBuilderFactory f = DocumentBuilderFactory.newInstance();
        f.setNamespaceAware(true);
        f.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        f.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
        return f.newDocumentBuilder();
    }

    private static ClassLoader getContextClassLoader() {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return Thread.currentThread().getContextClassLoader();
                }
            });
        }
        return Thread.currentThread().getContextClassLoader();
    }

    private static ClassLoader getClassLoader(final Class<?> clazz) {
        SecurityManager sm = System.getSecurityManager();
        if (sm != null) {
            return AccessController.doPrivileged(new PrivilegedAction<ClassLoader>(){

                @Override
                public ClassLoader run() {
                    return clazz.getClassLoader();
                }
            });
        }
        return clazz.getClassLoader();
    }

    public static Document newDocument() {
        return DOMUtils.createDocument();
    }

    public static Document createDocument() {
        try {
            return DOMUtils.getDocumentBuilder().newDocument();
        }
        catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    private static synchronized Document createEmptyDocument() {
        if (emptyDocument == null) {
            emptyDocument = DOMUtils.createDocument();
        }
        return emptyDocument;
    }

    public static Document getEmptyDocument() {
        Document doc = emptyDocument;
        if (doc == null) {
            doc = DOMUtils.createEmptyDocument();
        }
        return doc;
    }

    public static String getAttributeValueEmptyNull(Element e, String attributeName) {
        Attr node = e.getAttributeNode(attributeName);
        if (node == null) {
            return null;
        }
        return node.getValue();
    }

    public static String getAllContent(Node n) {
        StringBuilder b = new StringBuilder();
        DOMUtils.getAllContent(n, b);
        return b.toString();
    }

    private static void getAllContent(Node n, StringBuilder b) {
        for (Node nd = n.getFirstChild(); nd != null; nd = nd.getNextSibling()) {
            if (nd instanceof Text && !(nd instanceof Comment)) {
                b.append(((Text)nd).getData());
                continue;
            }
            DOMUtils.getAllContent(nd, b);
        }
    }

    public static String getContent(Node n) {
        String s2 = DOMUtils.getRawContent(n);
        if (s2 != null) {
            s2 = s2.trim();
        }
        return s2;
    }

    public static String getRawContent(Node n) {
        if (n == null) {
            return null;
        }
        StringBuilder b = null;
        String s2 = null;
        for (Node n1 = n.getFirstChild(); n1 != null; n1 = n1.getNextSibling()) {
            if (n1.getNodeType() != 3 && n1.getNodeType() != 4) continue;
            if (b != null) {
                b.append(((Text)n1).getNodeValue());
                continue;
            }
            if (s2 == null) {
                s2 = ((Text)n1).getNodeValue();
                continue;
            }
            b = new StringBuilder(s2).append(((Text)n1).getNodeValue());
            s2 = null;
        }
        if (b != null) {
            return b.toString();
        }
        return s2;
    }

    public static Node getChild(Node parent, String name) {
        if (parent == null) {
            return null;
        }
        Node first = parent.getFirstChild();
        if (first == null) {
            return null;
        }
        for (Node node = first; node != null; node = node.getNextSibling()) {
            if (node.getNodeType() != 1) continue;
            if (name != null && name.equals(node.getNodeName())) {
                return node;
            }
            if (name != null) continue;
            return node;
        }
        return null;
    }

    public static boolean hasAttribute(Element element, String value) {
        NamedNodeMap attributes = element.getAttributes();
        for (int i = 0; i < attributes.getLength(); ++i) {
            Node node = attributes.item(i);
            if (!value.equals(node.getNodeValue())) continue;
            return true;
        }
        return false;
    }

    public static String getAttribute(Node element, String attName) {
        NamedNodeMap attrs = element.getAttributes();
        if (attrs == null) {
            return null;
        }
        Node attN = attrs.getNamedItem(attName);
        if (attN == null) {
            return null;
        }
        return attN.getNodeValue();
    }

    public static String getAttribute(Element element, QName attName) {
        Attr attr = StringUtils.isEmpty(attName.getNamespaceURI()) ? element.getAttributeNode(attName.getLocalPart()) : element.getAttributeNodeNS(attName.getNamespaceURI(), attName.getLocalPart());
        return attr == null ? null : attr.getValue();
    }

    public static void setAttribute(Node node, String attName, String val) {
        NamedNodeMap attributes = node.getAttributes();
        Attr attNode = node.getOwnerDocument().createAttributeNS(null, attName);
        attNode.setNodeValue(val);
        attributes.setNamedItem(attNode);
    }

    public static void removeAttribute(Node node, String attName) {
        NamedNodeMap attributes = node.getAttributes();
        attributes.removeNamedItem(attName);
    }

    public static void setText(Node node, String val) {
        Node chld = DOMUtils.getChild(node, 3);
        if (chld == null) {
            Text textN = node.getOwnerDocument().createTextNode(val);
            node.appendChild(textN);
            return;
        }
        chld.setNodeValue(val);
    }

    public static Element findChildWithAtt(Node parent, String elemName, String attName, String attVal) {
        Element child = (Element)DOMUtils.getChild(parent, 1);
        if (attVal == null) {
            while (child != null && (elemName == null || elemName.equals(child.getNodeName())) && DOMUtils.getAttribute((Node)child, attName) != null) {
                child = (Element)DOMUtils.getNext(child, elemName, 1);
            }
        } else {
            while (child != null && (elemName == null || elemName.equals(child.getNodeName())) && !attVal.equals(DOMUtils.getAttribute((Node)child, attName))) {
                child = (Element)DOMUtils.getNext(child, elemName, 1);
            }
        }
        return child;
    }

    public static String getChildContent(Node parent, String name) {
        Node first = parent.getFirstChild();
        if (first == null) {
            return null;
        }
        for (Node node = first; node != null; node = node.getNextSibling()) {
            if (!name.equals(node.getNodeName())) continue;
            return DOMUtils.getRawContent(node);
        }
        return null;
    }

    public static QName getElementQName(Element el) {
        return new QName(el.getNamespaceURI(), el.getLocalName());
    }

    public static QName createQName(String qualifiedName, Node node) {
        if (qualifiedName == null) {
            return null;
        }
        int index = qualifiedName.indexOf(58);
        if (index == -1) {
            return new QName(qualifiedName);
        }
        String prefix = qualifiedName.substring(0, index);
        String localName = qualifiedName.substring(index + 1);
        String ns = node.lookupNamespaceURI(prefix);
        if (ns == null) {
            throw new RuntimeException("Invalid QName in mapping: " + qualifiedName);
        }
        return new QName(ns, localName, prefix);
    }

    public static QName convertStringToQName(String expandedQName) {
        return DOMUtils.convertStringToQName(expandedQName, "");
    }

    public static QName convertStringToQName(String expandedQName, String prefix) {
        int ind1 = expandedQName.indexOf(123);
        if (ind1 != 0) {
            return new QName(expandedQName);
        }
        int ind2 = expandedQName.indexOf(125);
        if (ind2 <= ind1 + 1 || ind2 >= expandedQName.length() - 1) {
            return null;
        }
        String ns = expandedQName.substring(ind1 + 1, ind2);
        String localName = expandedQName.substring(ind2 + 1);
        return new QName(ns, localName, prefix);
    }

    public static Set<QName> convertStringsToQNames(List<String> expandedQNames) {
        Set<QName> dropElements = Collections.emptySet();
        if (expandedQNames != null) {
            dropElements = new LinkedHashSet<QName>(expandedQNames.size());
            for (String val : expandedQNames) {
                dropElements.add(DOMUtils.convertStringToQName(val));
            }
        }
        return dropElements;
    }

    public static Element getFirstElement(Node parent) {
        Node n;
        for (n = parent.getFirstChild(); n != null && 1 != n.getNodeType(); n = n.getNextSibling()) {
        }
        if (n == null) {
            return null;
        }
        return (Element)n;
    }

    public static Element getNextElement(Element el) {
        for (Node nd = el.getNextSibling(); nd != null; nd = nd.getNextSibling()) {
            if (nd.getNodeType() != 1) continue;
            return (Element)nd;
        }
        return null;
    }

    public static Element getFirstChildWithName(Element parent, QName q) {
        String ns = q.getNamespaceURI();
        String lp = q.getLocalPart();
        return DOMUtils.getFirstChildWithName(parent, ns, lp);
    }

    public static Element getFirstChildWithName(Element parent, String ns, String lp) {
        for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
            String ens;
            if (!(n instanceof Element)) continue;
            Element e = (Element)n;
            String string = ens = e.getNamespaceURI() == null ? "" : e.getNamespaceURI();
            if (!ns.equals(ens) || !lp.equals(e.getLocalName())) continue;
            return e;
        }
        return null;
    }

    public static List<Element> getChildrenWithName(Element parent, String ns, String localName) {
        ArrayList<Element> r = new ArrayList<Element>();
        for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
            String eNs;
            if (!(n instanceof Element)) continue;
            Element e = (Element)n;
            String string = eNs = e.getNamespaceURI() == null ? "" : e.getNamespaceURI();
            if (!ns.equals(eNs) || !localName.equals(e.getLocalName())) continue;
            r.add(e);
        }
        return r;
    }

    public static List<Element> getChildrenWithNamespace(Element parent, String ns) {
        ArrayList<Element> r = new ArrayList<Element>();
        for (Node n = parent.getFirstChild(); n != null; n = n.getNextSibling()) {
            String eNs;
            if (!(n instanceof Element)) continue;
            Element e = (Element)n;
            String string = eNs = e.getNamespaceURI() == null ? "" : e.getNamespaceURI();
            if (!ns.equals(eNs)) continue;
            r.add(e);
        }
        return r;
    }

    public static Node getChild(Node parent, int type) {
        Node n;
        for (n = parent.getFirstChild(); n != null && type != n.getNodeType(); n = n.getNextSibling()) {
        }
        if (n == null) {
            return null;
        }
        return n;
    }

    public static Node getNext(Node current) {
        String name = current.getNodeName();
        short type = current.getNodeType();
        return DOMUtils.getNext(current, name, type);
    }

    public static Node getNext(Node current, String name, int type) {
        Node first = current.getNextSibling();
        if (first == null) {
            return null;
        }
        for (Node node = first; node != null; node = node.getNextSibling()) {
            if (type >= 0 && node.getNodeType() != type) continue;
            if (name == null) {
                return node;
            }
            if (!name.equals(node.getNodeName())) continue;
            return node;
        }
        return null;
    }

    public static String getPrefixRecursive(Element el, String ns) {
        String prefix = DOMUtils.getPrefix(el, ns);
        if (prefix == null && el.getParentNode() instanceof Element) {
            prefix = DOMUtils.getPrefixRecursive((Element)el.getParentNode(), ns);
        }
        return prefix;
    }

    public static String getPrefix(Element el, String ns) {
        NamedNodeMap atts = el.getAttributes();
        for (int i = 0; i < atts.getLength(); ++i) {
            Node node = atts.item(i);
            String name = node.getNodeName();
            if (!ns.equals(node.getNodeValue()) || name == null || !XMLNAMESPACE.equals(name) && !name.startsWith("xmlns:")) continue;
            return node.getLocalName();
        }
        return null;
    }

    public static void getPrefixesRecursive(Element element, String namespaceUri, List<String> prefixes) {
        DOMUtils.getPrefixes(element, namespaceUri, prefixes);
        Node parent = element.getParentNode();
        if (parent instanceof Element) {
            DOMUtils.getPrefixesRecursive((Element)parent, namespaceUri, prefixes);
        }
    }

    public static void getPrefixes(Element element, String namespaceUri, List<String> prefixes) {
        NamedNodeMap atts = element.getAttributes();
        for (int i = 0; i < atts.getLength(); ++i) {
            Node node = atts.item(i);
            String name = node.getNodeName();
            if (!namespaceUri.equals(node.getNodeValue()) || name == null || !XMLNAMESPACE.equals(name) && !name.startsWith("xmlns:")) continue;
            prefixes.add(node.getPrefix());
        }
    }

    public static String createNamespace(Element el, String ns) {
        String p = "ns1";
        int i = 1;
        while (DOMUtils.getPrefix(el, ns) != null) {
            p = "ns" + i;
            ++i;
        }
        DOMUtils.addNamespacePrefix(el, ns, p);
        return p;
    }

    public static String getNamespace(Node node, String searchPrefix) {
        while (!(node instanceof Element)) {
            node = node.getParentNode();
        }
        Element el = (Element)node;
        NamedNodeMap atts = el.getAttributes();
        for (int i = 0; i < atts.getLength(); ++i) {
            Node currentAttribute = atts.item(i);
            String currentLocalName = currentAttribute.getLocalName();
            String currentPrefix = currentAttribute.getPrefix();
            if (searchPrefix.equals(currentLocalName) && XMLNAMESPACE.equals(currentPrefix)) {
                return currentAttribute.getNodeValue();
            }
            if (!StringUtils.isEmpty(searchPrefix) || !XMLNAMESPACE.equals(currentLocalName) || !StringUtils.isEmpty(currentPrefix)) continue;
            return currentAttribute.getNodeValue();
        }
        Node parent = el.getParentNode();
        if (parent instanceof Element) {
            return DOMUtils.getNamespace(parent, searchPrefix);
        }
        return null;
    }

    public static List<Element> findAllElementsByTagNameNS(Element elem, String nameSpaceURI, String localName) {
        LinkedList<Element> ret = new LinkedList<Element>();
        DOMUtils.findAllElementsByTagNameNS(elem, nameSpaceURI, localName, ret);
        return ret;
    }

    public static Node getDomElement(Node node) {
        Method method2;
        if (node != null && DOMUtils.isJava9SAAJ() && (method2 = GET_DOM_ELEMENTS_METHODS.get(node.getClass())) != null) {
            try {
                return (Node)ReflectionUtil.setAccessible(method2).invoke((Object)node, new Object[0]);
            }
            catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
                throw new RuntimeException(e);
            }
        }
        return node;
    }

    public static DocumentFragment getDomDocumentFragment(DocumentFragment fragment) {
        Field f;
        if (fragment != null && DOMUtils.isJava9SAAJ() && (f = GET_DOCUMENT_FRAGMENT_FIELDS.get(fragment.getClass())) != null) {
            return ReflectionUtil.accessDeclaredField(f, fragment, DocumentFragment.class);
        }
        return fragment;
    }

    private static void findAllElementsByTagNameNS(Element el, String nameSpaceURI, String localName, List<Element> elementList) {
        if (el.getNamespaceURI() != null && localName.equals(el.getLocalName()) && nameSpaceURI.contains(el.getNamespaceURI())) {
            elementList.add(el);
        }
        Element elem = DOMUtils.getFirstElement(el);
        while (elem != null) {
            DOMUtils.findAllElementsByTagNameNS(elem, nameSpaceURI, localName, elementList);
            elem = DOMUtils.getNextElement(elem);
        }
    }

    public static List<Element> findAllElementsByTagName(Element elem, String tagName) {
        LinkedList<Element> ret = new LinkedList<Element>();
        DOMUtils.findAllElementsByTagName(elem, tagName, ret);
        return ret;
    }

    private static void findAllElementsByTagName(Element el, String tagName, List<Element> elementList) {
        if (tagName.equals(el.getTagName())) {
            elementList.add(el);
        }
        Element elem = DOMUtils.getFirstElement(el);
        while (elem != null) {
            DOMUtils.findAllElementsByTagName(elem, tagName, elementList);
            elem = DOMUtils.getNextElement(elem);
        }
    }

    public static boolean hasElementWithName(Element el, String nameSpaceURI, String localName) {
        if (el.getNamespaceURI() != null && localName.equals(el.getLocalName()) && nameSpaceURI.contains(el.getNamespaceURI())) {
            return true;
        }
        Element elem = DOMUtils.getFirstElement(el);
        while (elem != null) {
            if (DOMUtils.hasElementWithName(elem, nameSpaceURI, localName)) {
                return true;
            }
            elem = DOMUtils.getNextElement(elem);
        }
        return false;
    }

    public static boolean hasElementInNS(Element el, String namespace) {
        if (namespace.equals(el.getNamespaceURI())) {
            return true;
        }
        Element elem = DOMUtils.getFirstElement(el);
        while (elem != null) {
            if (DOMUtils.hasElementInNS(elem, namespace)) {
                return true;
            }
            elem = DOMUtils.getNextElement(elem);
        }
        return false;
    }

    public static String setNamespace(Element element, String namespace, String prefix) {
        String pre = DOMUtils.getPrefixRecursive(element, namespace);
        if (pre != null) {
            return pre;
        }
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespace);
        return prefix;
    }

    public static void addNamespacePrefix(Element element, String namespaceUri, String prefix) {
        element.setAttributeNS("http://www.w3.org/2000/xmlns/", "xmlns:" + prefix, namespaceUri);
    }

    public static boolean isJava9SAAJ() {
        return isJre9SAAJ;
    }

    private static void setJava9SAAJ(boolean isJava9SAAJ) {
        isJre9SAAJ = isJava9SAAJ;
    }

    static {
        DOCUMENT_BUILDERS = Collections.synchronizedMap(new WeakHashMap());
        GET_DOM_ELEMENTS_METHODS = new ClassValue<Method>(){

            @Override
            protected Method computeValue(Class<?> type) {
                try {
                    return ReflectionUtil.getMethod(type, "getDomElement", new Class[0]);
                }
                catch (NoSuchMethodException e) {
                    return null;
                }
            }
        };
        GET_DOCUMENT_FRAGMENT_FIELDS = new ClassValue<Field>(){

            @Override
            protected Field computeValue(Class<?> type) {
                return ReflectionUtil.getDeclaredField(type, "documentFragment");
            }
        };
        try {
            Method[] methods;
            for (Method method2 : methods = DOMUtils.class.getClassLoader().loadClass("com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl").getMethods()) {
                if (!"register".equals(method2.getName())) continue;
                DOMUtils.setJava9SAAJ(true);
                break;
            }
        }
        catch (ClassNotFoundException cnfe) {
            LogUtils.getL7dLogger(DOMUtils.class).finest("can't load class com.sun.xml.messaging.saaj.soap.SOAPDocumentImpl");
            try {
                Method[] methods;
                for (Method method3 : methods = DOMUtils.class.getClassLoader().loadClass("com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl").getMethods()) {
                    if (!"register".equals(method3.getName())) continue;
                    DOMUtils.setJava9SAAJ(true);
                }
            }
            catch (ClassNotFoundException cnfe1) {
                LogUtils.getL7dLogger(DOMUtils.class).finest("can't load class com.sun.xml.internal.messaging.saaj.soap.SOAPDocumentImpl");
            }
        }
        catch (Throwable throwable) {
            LogUtils.getL7dLogger(DOMUtils.class).finest("Other JDK vendor");
        }
    }

    public static class NullResolver
    implements EntityResolver {
        @Override
        public InputSource resolveEntity(String publicId, String systemId) throws SAXException, IOException {
            return new InputSource(new StringReader(""));
        }
    }
}

