/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

public class DepthXMLStreamReader
implements XMLStreamReader {
    protected XMLStreamReader reader;
    private int depth;

    public DepthXMLStreamReader(XMLStreamReader r) {
        this.reader = r;
    }

    public XMLStreamReader getReader() {
        return this.reader;
    }

    public int getDepth() {
        return this.depth;
    }

    @Override
    public void close() throws XMLStreamException {
        this.reader.close();
    }

    @Override
    public int getAttributeCount() {
        return this.reader.getAttributeCount();
    }

    @Override
    public String getAttributeLocalName(int arg0) {
        return this.reader.getAttributeLocalName(arg0);
    }

    @Override
    public QName getAttributeName(int arg0) {
        return this.reader.getAttributeName(arg0);
    }

    @Override
    public String getAttributeNamespace(int arg0) {
        return this.reader.getAttributeNamespace(arg0);
    }

    @Override
    public String getAttributePrefix(int arg0) {
        return this.reader.getAttributePrefix(arg0);
    }

    @Override
    public String getAttributeType(int arg0) {
        return this.reader.getAttributeType(arg0);
    }

    @Override
    public String getAttributeValue(int arg0) {
        return this.reader.getAttributeValue(arg0);
    }

    @Override
    public String getAttributeValue(String namespace, String localName) {
        return this.reader.getAttributeValue(namespace, localName);
    }

    @Override
    public String getCharacterEncodingScheme() {
        return this.reader.getCharacterEncodingScheme();
    }

    @Override
    public String getElementText() throws XMLStreamException {
        String ret = this.reader.getElementText();
        while (this.reader.getEventType() != 2) {
            this.reader.next();
        }
        --this.depth;
        return ret;
    }

    @Override
    public String getEncoding() {
        return this.reader.getEncoding();
    }

    @Override
    public int getEventType() {
        return this.reader.getEventType();
    }

    @Override
    public String getLocalName() {
        return this.reader.getLocalName();
    }

    @Override
    public Location getLocation() {
        return this.reader.getLocation();
    }

    @Override
    public QName getName() {
        return this.reader.getName();
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        return this.reader.getNamespaceContext();
    }

    @Override
    public int getNamespaceCount() {
        return this.reader.getNamespaceCount();
    }

    @Override
    public String getNamespacePrefix(int arg0) {
        return this.reader.getNamespacePrefix(arg0);
    }

    @Override
    public String getNamespaceURI() {
        return this.reader.getNamespaceURI();
    }

    @Override
    public String getNamespaceURI(int arg0) {
        return this.reader.getNamespaceURI(arg0);
    }

    @Override
    public String getNamespaceURI(String arg0) {
        return this.reader.getNamespaceURI(arg0);
    }

    @Override
    public String getPIData() {
        return this.reader.getPIData();
    }

    @Override
    public String getPITarget() {
        return this.reader.getPITarget();
    }

    @Override
    public String getPrefix() {
        return this.reader.getPrefix();
    }

    @Override
    public Object getProperty(String name) {
        return this.reader.getProperty(name);
    }

    @Override
    public String getText() {
        return this.reader.getText();
    }

    @Override
    public char[] getTextCharacters() {
        return this.reader.getTextCharacters();
    }

    @Override
    public int getTextCharacters(int arg0, char[] arg1, int arg2, int arg3) throws XMLStreamException {
        return this.reader.getTextCharacters(arg0, arg1, arg2, arg3);
    }

    @Override
    public int getTextLength() {
        return this.reader.getTextLength();
    }

    @Override
    public int getTextStart() {
        return this.reader.getTextStart();
    }

    @Override
    public String getVersion() {
        return this.reader.getVersion();
    }

    @Override
    public boolean hasName() {
        return this.reader.hasName();
    }

    @Override
    public boolean hasNext() throws XMLStreamException {
        return this.reader.hasNext();
    }

    @Override
    public boolean hasText() {
        return this.reader.hasText();
    }

    @Override
    public boolean isAttributeSpecified(int arg0) {
        return this.reader.isAttributeSpecified(arg0);
    }

    @Override
    public boolean isCharacters() {
        return this.reader.isCharacters();
    }

    @Override
    public boolean isEndElement() {
        return this.reader.isEndElement();
    }

    @Override
    public boolean isStandalone() {
        return this.reader.isStandalone();
    }

    @Override
    public boolean isStartElement() {
        return this.reader.isStartElement();
    }

    @Override
    public boolean isWhiteSpace() {
        return this.reader.isWhiteSpace();
    }

    @Override
    public int next() throws XMLStreamException {
        int next = this.reader.next();
        if (next == 1) {
            ++this.depth;
        } else if (next == 2) {
            --this.depth;
        }
        return next;
    }

    @Override
    public int nextTag() throws XMLStreamException {
        int eventType = this.next();
        while (eventType == 4 && this.isWhiteSpace() || eventType == 12 && this.isWhiteSpace() || eventType == 6 || eventType == 3 || eventType == 5) {
            eventType = this.next();
        }
        if (eventType != 1 && eventType != 2) {
            throw new XMLStreamException("expected start or end tag", this.getLocation());
        }
        return eventType;
    }

    @Override
    public void require(int arg0, String arg1, String arg2) throws XMLStreamException {
        this.reader.require(arg0, arg1, arg2);
    }

    @Override
    public boolean standaloneSet() {
        return this.reader.standaloneSet();
    }

    public int hashCode() {
        return this.reader.hashCode();
    }

    public boolean equals(Object arg0) {
        return this.reader.equals(arg0);
    }

    public String toString() {
        return this.reader.toString();
    }
}

