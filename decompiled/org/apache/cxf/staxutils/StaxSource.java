/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.sax.SAXSource;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.DTDHandler;
import org.xml.sax.EntityResolver;
import org.xml.sax.ErrorHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.SAXNotRecognizedException;
import org.xml.sax.SAXNotSupportedException;
import org.xml.sax.SAXParseException;
import org.xml.sax.XMLReader;
import org.xml.sax.ext.LexicalHandler;
import org.xml.sax.helpers.AttributesImpl;

public class StaxSource
extends SAXSource
implements XMLReader {
    private XMLStreamReader streamReader;
    private ContentHandler contentHandler;
    private LexicalHandler lexicalHandler;

    public StaxSource(XMLStreamReader streamReader) {
        this.streamReader = streamReader;
        this.setInputSource(new InputSource());
    }

    @Override
    public XMLReader getXMLReader() {
        return this;
    }

    public XMLStreamReader getXMLStreamReader() {
        return this.streamReader;
    }

    protected void parse() throws SAXException {
        try {
            while (true) {
                switch (this.streamReader.getEventType()) {
                    case 10: {
                        break;
                    }
                    case 12: {
                        if (this.lexicalHandler != null) {
                            this.lexicalHandler.startCDATA();
                        }
                        int length = this.streamReader.getTextLength();
                        int start2 = this.streamReader.getTextStart();
                        char[] chars = this.streamReader.getTextCharacters();
                        this.contentHandler.characters(chars, start2, length);
                        if (this.lexicalHandler == null) break;
                        this.lexicalHandler.endCDATA();
                        break;
                    }
                    case 4: {
                        int length = this.streamReader.getTextLength();
                        int start3 = this.streamReader.getTextStart();
                        char[] chars = this.streamReader.getTextCharacters();
                        this.contentHandler.characters(chars, start3, length);
                        break;
                    }
                    case 6: {
                        int length = this.streamReader.getTextLength();
                        int start4 = this.streamReader.getTextStart();
                        char[] chars = this.streamReader.getTextCharacters();
                        this.contentHandler.ignorableWhitespace(chars, start4, length);
                        break;
                    }
                    case 5: {
                        if (this.lexicalHandler == null) break;
                        int length = this.streamReader.getTextLength();
                        int start5 = this.streamReader.getTextStart();
                        char[] chars = this.streamReader.getTextCharacters();
                        this.lexicalHandler.comment(chars, start5, length);
                        break;
                    }
                    case 11: {
                        break;
                    }
                    case 8: {
                        this.contentHandler.endDocument();
                        return;
                    }
                    case 2: {
                        int i;
                        String uri = this.streamReader.getNamespaceURI();
                        String localName = this.streamReader.getLocalName();
                        String prefix = this.streamReader.getPrefix();
                        String qname = prefix != null && !prefix.isEmpty() ? prefix + ':' + localName : localName;
                        this.contentHandler.endElement(uri, localName, qname);
                        for (i = 0; i < this.streamReader.getNamespaceCount(); ++i) {
                            this.contentHandler.endPrefixMapping(this.streamReader.getNamespacePrefix(i));
                        }
                        break;
                    }
                    case 9: 
                    case 13: 
                    case 14: 
                    case 15: {
                        break;
                    }
                    case 3: {
                        break;
                    }
                    case 7: {
                        this.contentHandler.startDocument();
                        break;
                    }
                    case 1: {
                        int i;
                        String uri = this.streamReader.getNamespaceURI();
                        String localName = this.streamReader.getLocalName();
                        String prefix = this.streamReader.getPrefix();
                        String qname = prefix != null && !prefix.isEmpty() ? prefix + ':' + localName : localName;
                        for (i = 0; i < this.streamReader.getNamespaceCount(); ++i) {
                            String nsPrefix = this.streamReader.getNamespacePrefix(i);
                            String nsUri = this.streamReader.getNamespaceURI(i);
                            if (nsUri == null) {
                                nsUri = "";
                            }
                            this.contentHandler.startPrefixMapping(nsPrefix, nsUri);
                        }
                        this.contentHandler.startElement(uri == null ? "" : uri, localName, qname, this.getAttributes());
                        break;
                    }
                }
                if (!this.streamReader.hasNext()) {
                    return;
                }
                this.streamReader.next();
            }
        }
        catch (XMLStreamException e) {
            SAXParseException spe = e.getLocation() != null ? new SAXParseException(e.getMessage(), null, null, e.getLocation().getLineNumber(), e.getLocation().getColumnNumber(), e) : new SAXParseException(e.getMessage(), null, null, -1, -1, e);
            spe.initCause(e);
            throw spe;
        }
    }

    protected String getQualifiedName() {
        String prefix = this.streamReader.getPrefix();
        if (prefix != null && !prefix.isEmpty()) {
            return prefix + ':' + this.streamReader.getLocalName();
        }
        return this.streamReader.getLocalName();
    }

    protected Attributes getAttributes() {
        AttributesImpl attrs = new AttributesImpl();
        for (int i = 0; i < this.streamReader.getAttributeCount(); ++i) {
            String uri = this.streamReader.getAttributeNamespace(i);
            String localName = this.streamReader.getAttributeLocalName(i);
            String prefix = this.streamReader.getAttributePrefix(i);
            String qName = prefix != null && prefix.length() > 0 ? prefix + ':' + localName : localName;
            String type = this.streamReader.getAttributeType(i);
            String value = this.streamReader.getAttributeValue(i);
            if (value == null) {
                value = "";
            }
            attrs.addAttribute(uri == null ? "" : uri, localName, qName, type, value);
        }
        return attrs;
    }

    @Override
    public boolean getFeature(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
        return false;
    }

    @Override
    public void setFeature(String name, boolean value) throws SAXNotRecognizedException, SAXNotSupportedException {
    }

    @Override
    public Object getProperty(String name) throws SAXNotRecognizedException, SAXNotSupportedException {
        return null;
    }

    @Override
    public void setProperty(String name, Object value) throws SAXNotRecognizedException, SAXNotSupportedException {
        if (!"http://xml.org/sax/properties/lexical-handler".equals(name)) {
            throw new SAXNotRecognizedException(name);
        }
        this.lexicalHandler = (LexicalHandler)value;
    }

    @Override
    public void setEntityResolver(EntityResolver resolver2) {
    }

    @Override
    public EntityResolver getEntityResolver() {
        return null;
    }

    @Override
    public void setDTDHandler(DTDHandler handler) {
    }

    @Override
    public DTDHandler getDTDHandler() {
        return null;
    }

    @Override
    public void setContentHandler(ContentHandler handler) {
        this.contentHandler = handler;
        if (handler instanceof LexicalHandler && this.lexicalHandler == null) {
            this.lexicalHandler = (LexicalHandler)((Object)handler);
        }
    }

    @Override
    public ContentHandler getContentHandler() {
        return this.contentHandler;
    }

    @Override
    public void setErrorHandler(ErrorHandler handler) {
    }

    @Override
    public ErrorHandler getErrorHandler() {
        return null;
    }

    @Override
    public void parse(InputSource input) throws SAXException {
        this.parse();
    }

    @Override
    public void parse(String systemId) throws SAXException {
        this.parse();
    }
}

