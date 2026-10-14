/*
 * Decompiled with CFR 0.152.
 */
package org.apache.wss4j.common.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.sax.SAXSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.CDATASection;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;

public final class XMLUtils {
    public static final String XMLNS_NS = "http://www.w3.org/2000/xmlns/";
    public static final String XML_NS = "http://www.w3.org/XML/1998/namespace";
    public static final String WSU_NS = "http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd";
    private static final Logger LOG = LoggerFactory.getLogger(XMLUtils.class);

    private XMLUtils() {
    }

    public static Element getDirectChildElement(Node parentNode, String localName, String namespace) {
        if (parentNode == null) {
            return null;
        }
        for (Node currentChild = parentNode.getFirstChild(); currentChild != null; currentChild = currentChild.getNextSibling()) {
            if (1 != currentChild.getNodeType() || !localName.equals(currentChild.getLocalName()) || !namespace.equals(currentChild.getNamespaceURI())) continue;
            return (Element)currentChild;
        }
        return null;
    }

    public static String getElementText(Element e) {
        if (e != null) {
            StringBuilder builder = new StringBuilder();
            boolean found = false;
            for (Node node = e.getFirstChild(); node != null; node = node.getNextSibling()) {
                if (3 == node.getNodeType()) {
                    found = true;
                    builder.append(((Text)node).getData());
                    continue;
                }
                if (4 != node.getNodeType()) continue;
                found = true;
                builder.append(((CDATASection)node).getData());
            }
            if (!found) {
                return null;
            }
            return builder.toString();
        }
        return null;
    }

    public static String getNamespace(String prefix, Node e) {
        while (e != null && e.getNodeType() == 1) {
            Attr attr = null;
            attr = prefix == null ? ((Element)e).getAttributeNode("xmlns") : ((Element)e).getAttributeNodeNS(XMLNS_NS, prefix);
            if (attr != null) {
                return attr.getValue();
            }
            e = e.getParentNode();
        }
        return null;
    }

    public static String prettyDocumentToString(Document doc) throws IOException, TransformerException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
            XMLUtils.elementToStream(doc.getDocumentElement(), baos);
            String string = new String(baos.toByteArray(), StandardCharsets.UTF_8);
            return string;
        }
    }

    public static void elementToStream(Element element, OutputStream out) throws TransformerException {
        DOMSource source2 = new DOMSource(element);
        StreamResult result = new StreamResult(out);
        TransformerFactory transFactory = TransformerFactory.newInstance();
        transFactory.setFeature("http://javax.xml.XMLConstants/feature/secure-processing", true);
        Transformer transformer = transFactory.newTransformer();
        transformer.transform(source2, result);
    }

    public static InputSource sourceToInputSource(Source source2) throws IOException, TransformerException {
        if (source2 instanceof SAXSource) {
            return ((SAXSource)source2).getInputSource();
        }
        if (source2 instanceof DOMSource) {
            Node node = ((DOMSource)source2).getNode();
            if (node instanceof Document) {
                node = ((Document)node).getDocumentElement();
            }
            Element domElement = (Element)node;
            try (ByteArrayOutputStream baos = new ByteArrayOutputStream();){
                XMLUtils.elementToStream(domElement, baos);
                InputSource isource = new InputSource(source2.getSystemId());
                isource.setByteStream(new ByteArrayInputStream(baos.toByteArray()));
                InputSource inputSource = isource;
                return inputSource;
            }
        }
        if (source2 instanceof StreamSource) {
            StreamSource ss = (StreamSource)source2;
            InputSource isource = new InputSource(ss.getSystemId());
            isource.setByteStream(ss.getInputStream());
            isource.setCharacterStream(ss.getReader());
            isource.setPublicId(ss.getPublicId());
            return isource;
        }
        return XMLUtils.getInputSourceFromURI(source2.getSystemId());
    }

    public static InputSource getInputSourceFromURI(String uri) {
        return new InputSource(uri);
    }

    public static String setNamespace(Element element, String namespace, String prefix) {
        String pre = XMLUtils.getPrefixNS(namespace, element);
        if (pre != null) {
            return pre;
        }
        element.setAttributeNS(XMLNS_NS, "xmlns:" + prefix, namespace);
        return prefix;
    }

    public static String getPrefixNS(String uri, Node e) {
        while (e != null && e.getNodeType() == 1) {
            NamedNodeMap attrs = e.getAttributes();
            int length = attrs.getLength();
            for (int n = 0; n < length; ++n) {
                Attr a = (Attr)attrs.item(n);
                String name = a.getName();
                if (!name.startsWith("xmlns:") || !a.getNodeValue().equals(uri)) continue;
                return name.substring("xmlns:".length());
            }
            e = e.getParentNode();
        }
        return null;
    }

    public static String getIDFromReference(String ref) {
        if (ref == null) {
            return null;
        }
        String id = ref.trim();
        if (id.length() == 0) {
            return null;
        }
        if (id.charAt(0) == '#') {
            id = id.substring(1);
        }
        return id;
    }

    public static Element findElementById(Node startNode, String value, boolean checkMultipleElements) {
        if (startNode == null) {
            return null;
        }
        Node startParent = startNode.getParentNode();
        Node processedNode = null;
        Element foundElement = null;
        String id = XMLUtils.getIDFromReference(value);
        while (startNode != null) {
            if (startNode.getNodeType() == 1) {
                Element se = (Element)startNode;
                String attributeNS = se.getAttributeNS(WSU_NS, "Id");
                if ("".equals(attributeNS) || !id.equals(attributeNS)) {
                    attributeNS = se.getAttributeNS(null, "Id");
                }
                if (!"".equals(attributeNS) && id.equals(attributeNS)) {
                    if (!checkMultipleElements) {
                        return se;
                    }
                    if (foundElement == null) {
                        foundElement = se;
                    } else {
                        LOG.warn("Multiple elements with the same 'Id' attribute value!");
                        return null;
                    }
                }
            }
            processedNode = startNode;
            if ((startNode = startNode.getFirstChild()) == null) {
                startNode = processedNode.getNextSibling();
            }
            while (startNode == null) {
                if ((processedNode = processedNode.getParentNode()) == startParent) {
                    return foundElement;
                }
                startNode = processedNode.getNextSibling();
            }
        }
        return foundElement;
    }

    public static Element findElement(Node startNode, String name, String namespace) {
        if (startNode == null) {
            return null;
        }
        Node startParent = startNode.getParentNode();
        Node processedNode = null;
        while (startNode != null) {
            if (startNode.getNodeType() == 1 && startNode.getLocalName().equals(name)) {
                String ns = startNode.getNamespaceURI();
                if (ns != null && ns.equals(namespace)) {
                    return (Element)startNode;
                }
                if (!(namespace != null && namespace.length() != 0 || ns != null && ns.length() != 0)) {
                    return (Element)startNode;
                }
            }
            processedNode = startNode;
            if ((startNode = startNode.getFirstChild()) == null) {
                startNode = processedNode.getNextSibling();
            }
            while (startNode == null) {
                if ((processedNode = processedNode.getParentNode()) == startParent) {
                    return null;
                }
                startNode = processedNode.getNextSibling();
            }
        }
        return null;
    }

    public static List<Element> findElements(Node startNode, String name, String namespace) {
        if (startNode == null) {
            return null;
        }
        Node startParent = startNode.getParentNode();
        Node processedNode = null;
        ArrayList<Element> foundNodes = new ArrayList<Element>();
        while (startNode != null) {
            if (startNode.getNodeType() == 1 && startNode.getLocalName().equals(name)) {
                String ns = startNode.getNamespaceURI();
                if (ns != null && ns.equals(namespace)) {
                    foundNodes.add((Element)startNode);
                }
                if (!(namespace != null && namespace.length() != 0 || ns != null && ns.length() != 0)) {
                    foundNodes.add((Element)startNode);
                }
            }
            processedNode = startNode;
            if ((startNode = startNode.getFirstChild()) == null) {
                startNode = processedNode.getNextSibling();
            }
            while (startNode == null) {
                if ((processedNode = processedNode.getParentNode()) == startParent) {
                    return foundNodes;
                }
                startNode = processedNode.getNextSibling();
            }
        }
        return foundNodes;
    }

    public static Element findSAMLAssertionElementById(Node startNode, String value) {
        Element foundElement = null;
        if (startNode == null) {
            return null;
        }
        Node startParent = startNode.getParentNode();
        Node processedNode = null;
        while (startNode != null) {
            Element se;
            if (startNode.getNodeType() == 1 && ((se = (Element)startNode).hasAttributeNS(null, "ID") && value.equals(se.getAttributeNS(null, "ID")) || se.hasAttributeNS(null, "AssertionID") && value.equals(se.getAttributeNS(null, "AssertionID")))) {
                if (foundElement == null) {
                    foundElement = se;
                } else {
                    LOG.warn("Multiple elements with the same 'ID' attribute value!");
                    return null;
                }
            }
            processedNode = startNode;
            if ((startNode = startNode.getFirstChild()) == null) {
                startNode = processedNode.getNextSibling();
            }
            while (startNode == null) {
                if ((processedNode = processedNode.getParentNode()) == startParent) {
                    return foundElement;
                }
                startNode = processedNode.getNextSibling();
            }
        }
        return foundElement;
    }
}

