/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.xml;

import java.util.Iterator;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.Attribute;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.Comment;
import javax.xml.stream.events.Namespace;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;
import javax.xml.transform.dom.DOMSource;
import org.apache.neethi.All;
import org.apache.neethi.Assertion;
import org.apache.neethi.ExactlyOne;
import org.apache.neethi.Policy;
import org.apache.neethi.PolicyComponent;
import org.apache.neethi.builders.PrimitiveAssertion;
import org.apache.neethi.builders.xml.XMLPrimitiveAssertionBuilder;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class XmlPrimitiveAssertion
extends PrimitiveAssertion
implements Assertion {
    protected Element element;

    public XmlPrimitiveAssertion(Element element) {
        super(new QName(element.getNamespaceURI(), element.getLocalName()), XMLPrimitiveAssertionBuilder.isOptional(element), XMLPrimitiveAssertionBuilder.isIgnorable(element));
        this.element = element;
    }

    public void setValue(Element el) {
        this.element = el;
    }

    public Element getValue() {
        return this.element;
    }

    @Override
    public PolicyComponent normalize() {
        if (this.optional) {
            Policy policy = new Policy();
            ExactlyOne exactlyOne = new ExactlyOne();
            All all = new All();
            Element el = (Element)this.element.cloneNode(true);
            Attr attr = el.getAttributeNodeNS("http://schemas.xmlsoap.org/ws/2004/09/policy", "Optional");
            if (attr != null) {
                el.removeAttributeNode(attr);
            }
            if ((attr = el.getAttributeNodeNS("http://www.w3.org/ns/ws-policy", "Optional")) != null) {
                el.removeAttributeNode(attr);
            }
            all.addPolicyComponent(new XmlPrimitiveAssertion(el));
            exactlyOne.addPolicyComponent(all);
            exactlyOne.addPolicyComponent(new All());
            policy.addPolicyComponent(exactlyOne);
            return policy;
        }
        return this;
    }

    @Override
    public void serialize(XMLStreamWriter writer) throws XMLStreamException {
        if (this.element == null) {
            throw new RuntimeException("Wrapped Element is not set");
        }
        this.copyEvents(XMLInputFactory.newInstance().createXMLEventReader(new DOMSource(this.element)), writer);
    }

    @Override
    public final short getType() {
        return 5;
    }

    @Override
    public boolean equal(PolicyComponent policyComponent) {
        if (policyComponent.getType() != 5) {
            return false;
        }
        return this.getName().equals(((Assertion)policyComponent).getName());
    }

    private void copyEvents(XMLEventReader reader, XMLStreamWriter writer) throws XMLStreamException {
        while (reader.hasNext()) {
            XMLEvent event = reader.nextEvent();
            switch (event.getEventType()) {
                case 10: {
                    Attribute attr = (Attribute)event;
                    QName name = attr.getName();
                    writer.writeAttribute(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), attr.getValue());
                    break;
                }
                case 7: 
                case 8: {
                    break;
                }
                case 2: {
                    writer.writeEndElement();
                    break;
                }
                case 13: {
                    Namespace ns = (Namespace)event;
                    writer.writeNamespace(ns.getPrefix(), ns.getNamespaceURI());
                    break;
                }
                case 1: {
                    StartElement se = event.asStartElement();
                    QName n = se.getName();
                    writer.writeStartElement(n.getPrefix(), n.getLocalPart(), n.getNamespaceURI());
                    Iterator it = se.getNamespaces();
                    while (it.hasNext()) {
                        Namespace ns = (Namespace)it.next();
                        writer.writeNamespace(ns.getPrefix(), ns.getNamespaceURI());
                    }
                    it = se.getAttributes();
                    while (it.hasNext()) {
                        Attribute attr = (Attribute)it.next();
                        QName name = attr.getName();
                        writer.writeAttribute(name.getPrefix(), name.getNamespaceURI(), name.getLocalPart(), attr.getValue());
                    }
                    if (n.getNamespaceURI().equals(writer.getNamespaceContext().getNamespaceURI(n.getPrefix()))) break;
                    writer.writeNamespace(n.getPrefix(), n.getNamespaceURI());
                    break;
                }
                case 4: {
                    Characters ch = event.asCharacters();
                    String text = ch.getData();
                    if (ch.isCData()) {
                        writer.writeCData(text);
                        break;
                    }
                    writer.writeCharacters(text);
                    break;
                }
                case 12: {
                    writer.writeCData(event.asCharacters().getData());
                    break;
                }
                case 5: {
                    writer.writeComment(((Comment)event).getText());
                    break;
                }
            }
        }
    }
}

