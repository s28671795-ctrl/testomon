/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.dom.DOMSource;
import org.apache.neethi.builders.converters.AbstractDOMConverter;
import org.apache.neethi.builders.converters.Converter;
import org.apache.neethi.builders.converters.ConverterException;
import org.w3c.dom.Element;

public class DOMToStaxConverter
extends AbstractDOMConverter
implements Converter<Element, XMLStreamReader> {
    @Override
    public XMLStreamReader convert(Element s2) {
        try {
            return XMLInputFactory.newInstance().createXMLStreamReader(new DOMSource(s2));
        }
        catch (XMLStreamException ex) {
            throw new ConverterException(ex);
        }
    }
}

