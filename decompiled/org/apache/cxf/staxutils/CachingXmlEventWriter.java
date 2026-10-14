/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLEventFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import javax.xml.stream.events.XMLEvent;
import org.apache.cxf.common.util.StringUtils;
import org.apache.cxf.staxutils.StaxUtils;

public class CachingXmlEventWriter
implements XMLStreamWriter {
    protected XMLEventFactory factory;
    List<XMLEvent> events = new ArrayList<XMLEvent>(1000);
    final Deque<NSContext> contexts = new ArrayDeque<NSContext>();
    final Deque<QName> elNames = new ArrayDeque<QName>();
    QName lastStart = new QName("");
    NSContext curContext = new NSContext(null);

    public CachingXmlEventWriter() {
        this.factory = XMLEventFactory.newInstance();
    }

    protected void addEvent(XMLEvent event) {
        this.events.add(event);
    }

    public List<XMLEvent> getEvents() {
        return this.events;
    }

    @Override
    public void close() throws XMLStreamException {
    }

    @Override
    public void flush() throws XMLStreamException {
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.curContext;
    }

    @Override
    public String getPrefix(String ns) throws XMLStreamException {
        return this.curContext.getPrefix(ns);
    }

    @Override
    public Object getProperty(String name) {
        return null;
    }

    @Override
    public void setNamespaceContext(NamespaceContext arg0) throws XMLStreamException {
        this.curContext = new NSContext(arg0);
    }

    @Override
    public void writeAttribute(String name, String value) throws XMLStreamException {
        this.addEvent(this.factory.createAttribute(name, value));
    }

    @Override
    public void writeAttribute(String pfx, String uri, String name, String value) throws XMLStreamException {
        if ("http://www.w3.org/2000/xmlns/".equals(uri)) {
            if (StringUtils.isEmpty(name)) {
                this.writeDefaultNamespace(value);
            } else {
                this.writeNamespace(name, value);
            }
        } else {
            this.addEvent(this.factory.createAttribute(pfx, uri, name, value));
        }
    }

    @Override
    public void writeCData(String arg0) throws XMLStreamException {
        this.addEvent(this.factory.createCData(arg0));
    }

    @Override
    public void writeCharacters(String arg0) throws XMLStreamException {
        this.addEvent(this.factory.createCharacters(arg0));
    }

    @Override
    public void writeCharacters(char[] arg0, int arg1, int arg2) throws XMLStreamException {
        this.addEvent(this.factory.createCharacters(new String(arg0, arg1, arg2)));
    }

    @Override
    public void writeComment(String arg0) throws XMLStreamException {
        this.addEvent(this.factory.createComment(arg0));
    }

    @Override
    public void writeDTD(String arg0) throws XMLStreamException {
        this.addEvent(this.factory.createDTD(arg0));
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        this.addEvent(this.factory.createEndDocument());
    }

    @Override
    public void writeEntityRef(String arg0) throws XMLStreamException {
        this.addEvent(this.factory.createEntityReference(arg0, null));
    }

    @Override
    public void writeProcessingInstruction(String arg0) throws XMLStreamException {
        this.addEvent(this.factory.createProcessingInstruction(arg0, null));
    }

    @Override
    public void writeProcessingInstruction(String arg0, String arg1) throws XMLStreamException {
        this.addEvent(this.factory.createProcessingInstruction(arg0, arg1));
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
        this.addEvent(this.factory.createStartDocument());
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        this.addEvent(this.factory.createStartDocument(null, version));
    }

    @Override
    public void writeStartDocument(String arg0, String arg1) throws XMLStreamException {
        this.addEvent(this.factory.createStartDocument(arg0, arg1));
    }

    @Override
    public void setDefaultNamespace(String ns) throws XMLStreamException {
        this.curContext.addNs("", ns);
    }

    @Override
    public void writeNamespace(String pfx, String ns) throws XMLStreamException {
        this.curContext.addNs(pfx, ns);
        if (StringUtils.isEmpty(pfx)) {
            this.addEvent(this.factory.createNamespace(ns));
        } else {
            this.addEvent(this.factory.createNamespace(pfx, ns));
        }
    }

    @Override
    public void writeAttribute(String uri, String name, String value) throws XMLStreamException {
        if (!StringUtils.isEmpty(uri)) {
            String pfx = StaxUtils.getUniquePrefix(this, uri, false);
            this.addEvent(this.factory.createAttribute(pfx, uri, name, value));
        } else {
            this.addEvent(this.factory.createAttribute(name, value));
        }
    }

    @Override
    public void setPrefix(String pfx, String uri) throws XMLStreamException {
        this.curContext.addNs(pfx, uri);
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        this.addEvent(this.factory.createEndElement(this.lastStart, Collections.emptyList().iterator()));
        this.curContext = this.contexts.pop();
        this.lastStart = this.elNames.pop();
    }

    @Override
    public void writeDefaultNamespace(String ns) throws XMLStreamException {
        this.writeNamespace("", ns);
    }

    @Override
    public void writeEmptyElement(String name) throws XMLStreamException {
        this.writeStartElement(name);
        this.writeEndElement();
    }

    @Override
    public void writeEmptyElement(String name, String ns) throws XMLStreamException {
        this.writeStartElement(name, ns);
        this.writeEndElement();
    }

    @Override
    public void writeEmptyElement(String pfx, String name, String ns) throws XMLStreamException {
        this.writeStartElement(pfx, name, ns);
        this.writeEndElement();
    }

    @Override
    public void writeStartElement(String name) throws XMLStreamException {
        this.elNames.push(this.lastStart);
        this.contexts.push(this.curContext);
        this.curContext = new NSContext(this.curContext);
        this.lastStart = new QName(name);
        this.addEvent(this.factory.createStartElement(this.lastStart, Collections.EMPTY_SET.iterator(), Collections.EMPTY_SET.iterator()));
    }

    @Override
    public void writeStartElement(String name, String ns) throws XMLStreamException {
        this.elNames.push(this.lastStart);
        this.contexts.push(this.curContext);
        this.curContext = new NSContext(this.curContext);
        this.lastStart = new QName(ns, name);
        this.addEvent(this.factory.createStartElement(this.lastStart, Collections.EMPTY_SET.iterator(), Collections.EMPTY_SET.iterator()));
    }

    @Override
    public void writeStartElement(String pfx, String name, String ns) throws XMLStreamException {
        this.elNames.push(this.lastStart);
        this.contexts.push(this.curContext);
        this.curContext = new NSContext(this.curContext);
        this.lastStart = new QName(ns, name, pfx);
        this.addEvent(this.factory.createStartElement(this.lastStart, Collections.EMPTY_SET.iterator(), Collections.EMPTY_SET.iterator()));
    }

    public static class NSContext
    implements NamespaceContext {
        NamespaceContext parent;
        Map<String, String> map = new HashMap<String, String>();

        public NSContext(NamespaceContext p) {
            this.parent = p;
        }

        public void addNs(String pfx, String ns) {
            this.map.put(pfx, ns);
        }

        @Override
        public String getNamespaceURI(String prefix) {
            String ret = this.map.get(prefix);
            if (ret == null && this.parent != null) {
                return this.parent.getNamespaceURI(prefix);
            }
            return ret;
        }

        @Override
        public String getPrefix(String namespaceURI) {
            for (Map.Entry<String, String> e : this.map.entrySet()) {
                if (!e.getValue().equals(namespaceURI)) continue;
                return e.getKey();
            }
            if (this.parent != null) {
                return this.parent.getPrefix(namespaceURI);
            }
            return null;
        }

        @Override
        public Iterator<String> getPrefixes(String namespaceURI) {
            List<Object> l = new ArrayList<String>();
            for (Map.Entry<String, String> e : this.map.entrySet()) {
                if (!e.getValue().equals(namespaceURI)) continue;
                l.add(e.getKey());
            }
            if (l.isEmpty()) {
                String pfx = this.getPrefix(namespaceURI);
                if (pfx == null) {
                    l = Collections.emptyList();
                    return l.iterator();
                }
                return Collections.singleton(pfx).iterator();
            }
            return l.iterator();
        }
    }
}

