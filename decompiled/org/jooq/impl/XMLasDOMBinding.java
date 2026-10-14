/*
 * Decompiled with CFR 0.152.
 */
package org.jooq.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.StringReader;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.jooq.Converter;
import org.jooq.XML;
import org.jooq.impl.AbstractXMLBinding;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class XMLasDOMBinding
extends AbstractXMLBinding<Node> {
    private static final Converter<XML, Node> CONVERTER = Converter.ofNullable(XML.class, Node.class, t2 -> XMLasDOMBinding.fromString(t2.data()), u -> XML.xml(XMLasDOMBinding.toString(u)));

    @Override
    public final Converter<XML, Node> converter() {
        return CONVERTER;
    }

    static final String toString(Node node) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty("omit-xml-declaration", "yes");
            DOMSource source2 = new DOMSource(node);
            StreamResult target = new StreamResult(out);
            transformer.transform(source2, target);
            return out.toString("UTF-8");
        }
        catch (Exception e) {
            return "[ ERROR IN toString() : " + e.getMessage() + " ]";
        }
    }

    public static Document fromString(String name) {
        Document document = XMLasDOMBinding.builder().newDocument();
        DocumentFragment fragment = XMLasDOMBinding.createContent(document, name);
        if (fragment != null) {
            document.appendChild(fragment);
        } else {
            document.appendChild(document.createElement(name));
        }
        return document;
    }

    public static DocumentBuilder builder() {
        try {
            DocumentBuilderFactory factory2 = DocumentBuilderFactory.newInstance();
            try {
                factory2.setFeature("http://apache.org/xml/features/disallow-doctype-decl", true);
            }
            catch (ParserConfigurationException parserConfigurationException) {
                // empty catch block
            }
            try {
                factory2.setFeature("http://xml.org/sax/features/external-general-entities", false);
            }
            catch (ParserConfigurationException parserConfigurationException) {
                // empty catch block
            }
            try {
                factory2.setFeature("http://xml.org/sax/features/external-parameter-entities", false);
            }
            catch (ParserConfigurationException parserConfigurationException) {
                // empty catch block
            }
            factory2.setXIncludeAware(false);
            factory2.setExpandEntityReferences(false);
            factory2.setNamespaceAware(true);
            DocumentBuilder builder = factory2.newDocumentBuilder();
            return builder;
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static final DocumentFragment createContent(Document doc, String text) {
        if (text != null && text.contains("<")) {
            DocumentBuilder builder = XMLasDOMBinding.builder();
            try {
                text = text.trim();
                if (text.startsWith("<?xml")) {
                    Document parsed = builder.parse(new InputSource(new StringReader(text)));
                    DocumentFragment fragment = parsed.createDocumentFragment();
                    fragment.appendChild(parsed.getDocumentElement());
                    return (DocumentFragment)doc.importNode(fragment, true);
                }
                String wrapped = "<dummy>" + text + "</dummy>";
                Document parsed = builder.parse(new InputSource(new StringReader(wrapped)));
                DocumentFragment fragment = parsed.createDocumentFragment();
                NodeList children = parsed.getDocumentElement().getChildNodes();
                while (children.getLength() > 0) {
                    fragment.appendChild(children.item(0));
                }
                return (DocumentFragment)doc.importNode(fragment, true);
            }
            catch (IOException | SAXException exception) {
                // empty catch block
            }
        }
        return null;
    }
}

