/*
 * Decompiled with CFR 0.152.
 */
package com.sun.xml.messaging.saaj.util.stax;

import com.sun.xml.messaging.saaj.soap.impl.BodyImpl;
import com.sun.xml.messaging.saaj.soap.impl.EnvelopeImpl;
import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPException;
import javax.xml.stream.Location;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import org.jvnet.staxex.util.DOMStreamReader;
import org.w3c.dom.Node;

public class LazyEnvelopeStaxReader
extends DOMStreamReader {
    XMLStreamReader payloadReader = null;
    boolean usePayloadReaderDelegate = false;
    private QName bodyQName;

    public LazyEnvelopeStaxReader(EnvelopeImpl env) throws SOAPException, XMLStreamException {
        super(env);
        this.bodyQName = new QName(env.getNamespaceURI(), "Body");
        this.payloadReader = env.getStaxBridge().getPayloadReader();
        int eventType = this.getEventType();
        while (eventType != 1) {
            eventType = this.nextTag();
        }
    }

    @Override
    public Object getProperty(String name) throws IllegalArgumentException {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getProperty(name);
        }
        return super.getProperty(name);
    }

    @Override
    public int next() throws XMLStreamException {
        this.checkReaderStatus(true);
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getEventType();
        }
        return this.getEventType();
    }

    @Override
    public void require(int type, String namespaceURI, String localName) throws XMLStreamException {
        if (this.usePayloadReaderDelegate) {
            this.payloadReader.require(type, namespaceURI, localName);
        } else {
            super.require(type, namespaceURI, localName);
        }
    }

    @Override
    public String getElementText() throws XMLStreamException {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getElementText();
        }
        return super.getElementText();
    }

    @Override
    public int nextTag() throws XMLStreamException {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.nextTag();
        }
        return super.nextTag();
    }

    @Override
    public boolean hasNext() throws XMLStreamException {
        this.checkReaderStatus(false);
        boolean hasNext = this.usePayloadReaderDelegate ? this.payloadReader.hasNext() : super.hasNext();
        return hasNext;
    }

    private void checkReaderStatus(boolean advanceToNext) throws XMLStreamException {
        if (this.usePayloadReaderDelegate) {
            if (!this.payloadReader.hasNext()) {
                this.usePayloadReaderDelegate = false;
            }
        } else if (1 == this.getEventType() && this.bodyQName.equals(this.getName())) {
            this.usePayloadReaderDelegate = true;
            advanceToNext = false;
        }
        if (advanceToNext) {
            if (this.usePayloadReaderDelegate) {
                this.payloadReader.next();
            } else {
                super.next();
            }
        }
    }

    @Override
    public void close() throws XMLStreamException {
        if (this.usePayloadReaderDelegate) {
            this.payloadReader.close();
        } else {
            super.close();
        }
    }

    @Override
    public String getNamespaceURI(String prefix) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getNamespaceURI(prefix);
        }
        return super.getNamespaceURI(prefix);
    }

    @Override
    public boolean isStartElement() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.isStartElement();
        }
        return super.isStartElement();
    }

    @Override
    public boolean isEndElement() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.isEndElement();
        }
        return super.isEndElement();
    }

    @Override
    public boolean isCharacters() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.isCharacters();
        }
        return super.isEndElement();
    }

    @Override
    public boolean isWhiteSpace() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.isWhiteSpace();
        }
        return super.isWhiteSpace();
    }

    @Override
    public String getAttributeValue(String namespaceURI, String localName) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributeValue(namespaceURI, localName);
        }
        return super.getAttributeValue(namespaceURI, localName);
    }

    @Override
    public int getAttributeCount() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributeCount();
        }
        return super.getAttributeCount();
    }

    @Override
    public QName getAttributeName(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributeName(index);
        }
        return super.getAttributeName(index);
    }

    @Override
    public String getAttributeNamespace(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributeNamespace(index);
        }
        return super.getAttributeNamespace(index);
    }

    @Override
    public String getAttributeLocalName(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributeLocalName(index);
        }
        return super.getAttributeLocalName(index);
    }

    @Override
    public String getAttributePrefix(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributePrefix(index);
        }
        return super.getAttributePrefix(index);
    }

    @Override
    public String getAttributeType(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributeType(index);
        }
        return super.getAttributeType(index);
    }

    @Override
    public String getAttributeValue(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getAttributeValue(index);
        }
        return super.getAttributeValue(index);
    }

    @Override
    public boolean isAttributeSpecified(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.isAttributeSpecified(index);
        }
        return super.isAttributeSpecified(index);
    }

    @Override
    public int getNamespaceCount() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getNamespaceCount();
        }
        return super.getNamespaceCount();
    }

    @Override
    public String getNamespacePrefix(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getNamespacePrefix(index);
        }
        return super.getNamespacePrefix(index);
    }

    @Override
    public String getNamespaceURI(int index) {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getNamespaceURI(index);
        }
        return super.getNamespaceURI(index);
    }

    @Override
    public NamespaceContext getNamespaceContext() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getNamespaceContext();
        }
        return super.getNamespaceContext();
    }

    @Override
    public int getEventType() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getEventType();
        }
        return super.getEventType();
    }

    @Override
    public String getText() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getText();
        }
        return super.getText();
    }

    @Override
    public char[] getTextCharacters() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getTextCharacters();
        }
        return super.getTextCharacters();
    }

    @Override
    public int getTextCharacters(int sourceStart, char[] target, int targetStart, int length) throws XMLStreamException {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getTextCharacters(sourceStart, target, targetStart, length);
        }
        return super.getTextCharacters(sourceStart, target, targetStart, length);
    }

    @Override
    public int getTextStart() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getTextStart();
        }
        return super.getTextStart();
    }

    @Override
    public int getTextLength() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getTextLength();
        }
        return super.getTextLength();
    }

    @Override
    public String getEncoding() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getEncoding();
        }
        return super.getEncoding();
    }

    @Override
    public boolean hasText() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.hasText();
        }
        return super.hasText();
    }

    @Override
    public Location getLocation() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getLocation();
        }
        return super.getLocation();
    }

    @Override
    public QName getName() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getName();
        }
        return super.getName();
    }

    @Override
    public String getLocalName() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getLocalName();
        }
        return super.getLocalName();
    }

    @Override
    public boolean hasName() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.hasName();
        }
        return super.hasName();
    }

    @Override
    public String getNamespaceURI() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getNamespaceURI();
        }
        return super.getNamespaceURI();
    }

    @Override
    public String getPrefix() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getPrefix();
        }
        return super.getPrefix();
    }

    @Override
    public String getVersion() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getVersion();
        }
        return super.getVersion();
    }

    @Override
    public boolean isStandalone() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.isStandalone();
        }
        return super.isStandalone();
    }

    @Override
    public boolean standaloneSet() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.standaloneSet();
        }
        return super.standaloneSet();
    }

    @Override
    public String getCharacterEncodingScheme() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getCharacterEncodingScheme();
        }
        return super.getCharacterEncodingScheme();
    }

    @Override
    public String getPITarget() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getPITarget();
        }
        return super.getPITarget();
    }

    @Override
    public String getPIData() {
        if (this.usePayloadReaderDelegate) {
            return this.payloadReader.getPIData();
        }
        return super.getPIData();
    }

    protected Node getFirstChild(Node node) {
        if (node instanceof BodyImpl) {
            return ((BodyImpl)node).getFirstChildNoMaterialize();
        }
        return node.getFirstChild();
    }

    protected Node getNextSibling(Node node) {
        if (node instanceof BodyImpl) {
            return null;
        }
        return node.getNextSibling();
    }
}

