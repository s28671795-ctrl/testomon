/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils;

import java.util.LinkedHashMap;
import java.util.Map;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.common.util.StringUtils;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.ext.LexicalHandler;

public class StreamWriterContentHandler
implements ContentHandler,
LexicalHandler {
    XMLStreamWriter writer;
    Map<String, String> mapping = new LinkedHashMap<String, String>();
    boolean inCDATA;

    public StreamWriterContentHandler(XMLStreamWriter w) {
        this.writer = w;
    }

    @Override
    public void endDocument() throws SAXException {
    }

    @Override
    public void startDocument() throws SAXException {
    }

    @Override
    public void characters(char[] ch, int start2, int length) throws SAXException {
        try {
            if (this.inCDATA) {
                this.writer.writeCData(new String(ch, start2, length));
            } else {
                this.writer.writeCharacters(ch, start2, length);
            }
        }
        catch (XMLStreamException e) {
            throw new SAXException(e);
        }
    }

    @Override
    public void ignorableWhitespace(char[] ch, int start2, int length) throws SAXException {
        try {
            this.writer.writeCharacters(ch, start2, length);
        }
        catch (XMLStreamException e) {
            throw new SAXException(e);
        }
    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {
    }

    @Override
    public void skippedEntity(String name) throws SAXException {
    }

    @Override
    public void setDocumentLocator(Locator locator) {
    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {
    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {
        this.mapping.put(prefix, uri);
        try {
            this.writer.setPrefix(prefix, uri);
        }
        catch (XMLStreamException e) {
            throw new SAXException(e);
        }
    }

    @Override
    public void endElement(String namespaceURI, String localName, String qName) throws SAXException {
        try {
            this.writer.writeEndElement();
        }
        catch (XMLStreamException e) {
            throw new SAXException(e);
        }
    }

    private String getPrefix(String ns, String namespaceURI) {
        int idx = ns.indexOf(58);
        if (idx != -1) {
            return ns.substring(0, idx);
        }
        if (namespaceURI != null && namespaceURI.length() > 0) {
            return "";
        }
        return null;
    }

    @Override
    public void startElement(String namespaceURI, String localName, String qName, Attributes atts) throws SAXException {
        try {
            String pfx;
            int i;
            String prefix = this.getPrefix(qName, namespaceURI);
            if (prefix == null) {
                this.writer.writeStartElement(namespaceURI, localName);
            } else {
                this.writer.writeStartElement(prefix, localName, namespaceURI);
            }
            if (!this.mapping.isEmpty() && atts != null) {
                int attCount = atts.getLength();
                for (i = 0; i < attCount; ++i) {
                    if (StringUtils.isEmpty(atts.getURI(i)) || !atts.getQName(i).startsWith("xmlns")) continue;
                    pfx = atts.getQName(i);
                    int idx = pfx.indexOf(58);
                    if (idx == -1) {
                        this.mapping.remove("");
                        this.writer.writeDefaultNamespace(atts.getValue(i));
                        continue;
                    }
                    pfx = pfx.substring(idx + 1);
                    this.mapping.remove(pfx);
                    this.writer.writeNamespace(pfx, atts.getValue(i));
                }
            }
            for (Map.Entry<String, String> e : this.mapping.entrySet()) {
                if ("".equals(e.getKey())) {
                    this.writer.writeDefaultNamespace(e.getValue());
                    continue;
                }
                this.writer.writeNamespace(e.getKey(), e.getValue());
            }
            this.mapping.clear();
            if (atts != null) {
                int attCount = atts.getLength();
                for (i = 0; i < attCount; ++i) {
                    if (StringUtils.isEmpty(atts.getURI(i))) {
                        String s2 = atts.getLocalName(i);
                        if (StringUtils.isEmpty(s2)) {
                            s2 = atts.getQName(i);
                        }
                        this.writer.writeAttribute(s2, atts.getValue(i));
                        continue;
                    }
                    pfx = atts.getQName(i);
                    if (pfx.startsWith("xmlns")) continue;
                    if (pfx.indexOf(58) != -1) {
                        pfx = pfx.substring(0, pfx.indexOf(58));
                        this.writer.writeAttribute(pfx, atts.getURI(i), atts.getLocalName(i), atts.getValue(i));
                        continue;
                    }
                    this.writer.writeAttribute(atts.getURI(i), atts.getLocalName(i), atts.getValue(i));
                }
            }
        }
        catch (XMLStreamException e) {
            throw new SAXException(e);
        }
    }

    @Override
    public void startDTD(String name, String publicId, String systemId) throws SAXException {
    }

    @Override
    public void endDTD() throws SAXException {
    }

    @Override
    public void startEntity(String name) throws SAXException {
    }

    @Override
    public void endEntity(String name) throws SAXException {
    }

    @Override
    public void startCDATA() throws SAXException {
        this.inCDATA = true;
    }

    @Override
    public void endCDATA() throws SAXException {
        this.inCDATA = false;
    }

    @Override
    public void comment(char[] ch, int start2, int length) throws SAXException {
        try {
            this.writer.writeComment(new String(ch, start2, length));
        }
        catch (XMLStreamException e) {
            throw new SAXException(e);
        }
    }
}

