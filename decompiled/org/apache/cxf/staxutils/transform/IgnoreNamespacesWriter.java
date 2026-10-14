/*
 * Decompiled with CFR 0.152.
 */
package org.apache.cxf.staxutils.transform;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import org.apache.cxf.staxutils.DelegatingXMLStreamWriter;

public class IgnoreNamespacesWriter
extends DelegatingXMLStreamWriter {
    private static final String XSI_PREFIX = "xsi";
    private boolean ignoreXsiAttributes;

    public IgnoreNamespacesWriter(XMLStreamWriter writer) {
        this(writer, false);
    }

    public IgnoreNamespacesWriter(XMLStreamWriter writer, boolean ignoreXsiAttributes) {
        super(writer);
        this.ignoreXsiAttributes = ignoreXsiAttributes;
    }

    @Override
    public void writeAttribute(String prefix, String uri, String local, String value) throws XMLStreamException {
        if (this.ignoreXsiAttributes && XSI_PREFIX.equals(prefix) && ("type".equals(local) || "nil".equals(local))) {
            return;
        }
        super.writeAttribute(local, value);
    }

    @Override
    public void writeAttribute(String uri, String local, String value) throws XMLStreamException {
        super.writeAttribute(local, value);
    }

    @Override
    public void writeStartElement(String prefix, String local, String uri) throws XMLStreamException {
        super.writeStartElement(local);
    }

    @Override
    public void writeStartElement(String uri, String local) throws XMLStreamException {
        super.writeStartElement(local);
    }

    @Override
    public void setPrefix(String pfx, String uri) throws XMLStreamException {
    }

    @Override
    public void setDefaultNamespace(String uri) throws XMLStreamException {
    }
}

