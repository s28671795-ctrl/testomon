/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import java.util.Stack;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.apache.neethi.builders.converters.AbstractStaxConverter;
import org.apache.neethi.builders.converters.Converter;
import org.apache.neethi.builders.converters.ConverterException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

public class StaxToDOMConverter
extends AbstractStaxConverter
implements Converter<XMLStreamReader, Element> {
    @Override
    public Element convert(XMLStreamReader reader) {
        try {
            Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder().newDocument();
            StaxToDOMConverter.readDocElements(doc, doc, reader);
            return doc.getDocumentElement();
        }
        catch (ParserConfigurationException ex) {
            throw new ConverterException(ex);
        }
        catch (XMLStreamException ex) {
            throw new ConverterException(ex);
        }
    }

    public static void readDocElements(Document doc, Node parent, XMLStreamReader reader) throws XMLStreamException {
        Stack<Node> stack = new Stack<Node>();
        int event = reader.getEventType();
        while (reader.hasNext()) {
            switch (event) {
                case 1: {
                    String prefix;
                    Element e = doc.createElementNS(reader.getNamespaceURI(), reader.getLocalName());
                    if (reader.getPrefix() != null) {
                        e.setPrefix(reader.getPrefix());
                    }
                    e = (Element)parent.appendChild(e);
                    for (int ns = 0; ns < reader.getNamespaceCount(); ++ns) {
                        String uri = reader.getNamespaceURI(ns);
                        prefix = reader.getNamespacePrefix(ns);
                        StaxToDOMConverter.declare(e, uri, prefix);
                    }
                    for (int att = 0; att < reader.getAttributeCount(); ++att) {
                        String name = reader.getAttributeLocalName(att);
                        prefix = reader.getAttributePrefix(att);
                        if (prefix != null && prefix.length() > 0) {
                            name = prefix + ":" + name;
                        }
                        Attr attr = doc.createAttributeNS(reader.getAttributeNamespace(att), name);
                        attr.setValue(reader.getAttributeValue(att));
                        e.setAttributeNode(attr);
                    }
                    stack.push(parent);
                    parent = e;
                    break;
                }
                case 2: {
                    if (stack.isEmpty()) {
                        return;
                    }
                    parent = (Node)stack.pop();
                    if (!(parent instanceof Document)) break;
                    return;
                }
                case 13: {
                    break;
                }
                case 10: {
                    break;
                }
                case 4: {
                    if (parent == null) break;
                    parent.appendChild(doc.createTextNode(reader.getText()));
                    break;
                }
                case 5: {
                    if (parent == null) break;
                    parent.appendChild(doc.createComment(reader.getText()));
                    break;
                }
                case 12: {
                    parent.appendChild(doc.createCDATASection(reader.getText()));
                    break;
                }
                case 3: {
                    parent.appendChild(doc.createProcessingInstruction(reader.getPITarget(), reader.getPIData()));
                    break;
                }
                case 9: {
                    parent.appendChild(doc.createProcessingInstruction(reader.getPITarget(), reader.getPIData()));
                    break;
                }
            }
            if (!reader.hasNext()) continue;
            event = reader.next();
        }
    }

    private static void declare(Element node, String uri, String prefix) {
        String qualname = prefix != null && prefix.length() > 0 ? "xmlns:" + prefix : "xmlns";
        Attr attr = node.getOwnerDocument().createAttributeNS("http://www.w3.org/2000/xmlns/", qualname);
        attr.setValue(uri);
        node.setAttributeNodeNS(attr);
    }
}

