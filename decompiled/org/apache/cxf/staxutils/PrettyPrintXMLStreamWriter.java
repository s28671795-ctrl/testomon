/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.ArrayDeque;
import java.util.Deque;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;

public class PrettyPrintXMLStreamWriter
implements XMLStreamWriter {
    static final int DEFAULT_INDENT_LEVEL = 2;
    XMLStreamWriter baseWriter;
    int curIndent;
    int indentAmount = 2;
    final Deque<CurrentElement> elems = new ArrayDeque<CurrentElement>();

    public PrettyPrintXMLStreamWriter(XMLStreamWriter writer, int indentAmount) {
        this(writer, indentAmount, 0);
    }

    public PrettyPrintXMLStreamWriter(XMLStreamWriter writer, int indentAmount, int initialLevel) {
        this.baseWriter = writer;
        this.curIndent = initialLevel;
        this.indentAmount = indentAmount;
    }

    public void writeSpaces() throws XMLStreamException {
        for (int i = 0; i < this.curIndent; ++i) {
            this.baseWriter.writeCharacters(" ");
        }
    }

    public void indentWithSpaces() throws XMLStreamException {
        this.writeSpaces();
        this.indent();
    }

    public void indent() {
        this.curIndent += this.indentAmount;
    }

    public void unindent() {
        this.curIndent -= this.indentAmount;
    }

    @Override
    public void close() throws XMLStreamException {
        this.baseWriter.close();
    }

    @Override
    public void flush() throws XMLStreamException {
        this.baseWriter.flush();
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.baseWriter.getNamespaceContext();
    }

    @Override
    public String getPrefix(String uri) throws XMLStreamException {
        return this.baseWriter.getPrefix(uri);
    }

    @Override
    public Object getProperty(String name) {
        return this.baseWriter.getProperty(name);
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
        this.baseWriter.setDefaultNamespace(uri);
    }

    @Override
    public void setNamespaceContext(NamespaceContext context) throws XMLStreamException {
        this.baseWriter.setNamespaceContext(context);
    }

    @Override
    public void setPrefix(String prefix, String uri) throws XMLStreamException {
        this.baseWriter.setPrefix(prefix, uri);
    }

    @Override
    public void writeAttribute(String localName, String value) throws XMLStreamException {
        this.baseWriter.writeAttribute(localName, value);
    }

    @Override
    public void writeAttribute(String namespaceURI, String localName, String value) throws XMLStreamException {
        this.baseWriter.writeAttribute(namespaceURI, localName, value);
    }

    @Override
    public void writeAttribute(String prefix, String namespaceURI, String localName, String value) throws XMLStreamException {
        this.baseWriter.writeAttribute(prefix, namespaceURI, localName, value);
    }

    @Override
    public void writeCData(String data) throws XMLStreamException {
        this.baseWriter.writeCData(data);
    }

    @Override
    public void writeCharacters(char[] text, int start2, int len) throws XMLStreamException {
        this.baseWriter.writeCharacters(text, start2, len);
    }

    @Override
    public void writeCharacters(String text) throws XMLStreamException {
        this.baseWriter.writeCharacters(text);
    }

    @Override
    public void writeComment(String data) throws XMLStreamException {
        this.baseWriter.writeComment(data);
    }

    @Override
    public void writeDefaultNamespace(String namespaceURI) throws XMLStreamException {
        this.baseWriter.writeDefaultNamespace(namespaceURI);
    }

    @Override
    public void writeDTD(String dtd) throws XMLStreamException {
        this.baseWriter.writeDTD(dtd);
    }

    @Override
    public void writeEmptyElement(String localName) throws XMLStreamException {
        this.baseWriter.writeEmptyElement(localName);
    }

    @Override
    public void writeEmptyElement(String namespaceURI, String localName) throws XMLStreamException {
        this.baseWriter.writeEmptyElement(localName, namespaceURI);
    }

    @Override
    public void writeEmptyElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        this.baseWriter.writeEmptyElement(prefix, localName, namespaceURI);
    }

    @Override
    public void writeEndDocument() throws XMLStreamException {
        this.baseWriter.writeEndDocument();
    }

    @Override
    public void writeEndElement() throws XMLStreamException {
        CurrentElement elem = this.elems.pop();
        this.unindent();
        if (elem.hasChildElements()) {
            this.baseWriter.writeCharacters("\n");
            this.writeSpaces();
        }
        this.baseWriter.writeEndElement();
        if (this.elems.isEmpty()) {
            this.baseWriter.writeCharacters("\n");
        }
    }

    @Override
    public void writeEntityRef(String name) throws XMLStreamException {
        this.baseWriter.writeEntityRef(name);
    }

    @Override
    public void writeNamespace(String prefix, String namespaceURI) throws XMLStreamException {
        this.baseWriter.writeNamespace(prefix, namespaceURI);
    }

    @Override
    public void writeProcessingInstruction(String target) throws XMLStreamException {
        this.baseWriter.writeProcessingInstruction(target);
    }

    @Override
    public void writeProcessingInstruction(String target, String data) throws XMLStreamException {
        this.baseWriter.writeProcessingInstruction(target, data);
    }

    @Override
    public void writeStartDocument() throws XMLStreamException {
        this.baseWriter.writeStartDocument();
    }

    @Override
    public void writeStartDocument(String version) throws XMLStreamException {
        this.baseWriter.writeStartDocument(version);
    }

    @Override
    public void writeStartDocument(String encoding, String version) throws XMLStreamException {
        this.baseWriter.writeStartDocument(encoding, version);
    }

    @Override
    public void writeStartElement(String localName) throws XMLStreamException {
        this.writeStartElement(null, localName, null);
    }

    @Override
    public void writeStartElement(String namespaceURI, String localName) throws XMLStreamException {
        this.writeStartElement(null, localName, namespaceURI);
    }

    @Override
    public void writeStartElement(String prefix, String localName, String namespaceURI) throws XMLStreamException {
        QName currElemName = new QName(namespaceURI, localName);
        if (this.elems.isEmpty()) {
            this.indentWithSpaces();
        } else {
            this.baseWriter.writeCharacters("\n");
            this.indentWithSpaces();
            CurrentElement elem = this.elems.peek();
            elem.setChildElements(true);
        }
        if (prefix == null && namespaceURI == null) {
            this.baseWriter.writeStartElement(localName);
        } else if (prefix == null) {
            this.baseWriter.writeStartElement(namespaceURI, localName);
        } else {
            this.baseWriter.writeStartElement(prefix, localName, namespaceURI);
        }
        this.elems.push(new CurrentElement(currElemName));
    }

    static class CurrentElement {
        private QName name;
        private boolean hasChildElements;

        CurrentElement(QName qname) {
            this.name = qname;
        }

        public QName getQName() {
            return this.name;
        }

        public boolean hasChildElements() {
            return this.hasChildElements;
        }

        public void setChildElements(boolean childElements) {
            this.hasChildElements = childElements;
        }
    }
}

