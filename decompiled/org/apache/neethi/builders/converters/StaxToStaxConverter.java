/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import javax.xml.stream.XMLStreamReader;
import org.apache.neethi.builders.converters.AbstractStaxConverter;
import org.apache.neethi.builders.converters.Converter;

public class StaxToStaxConverter
extends AbstractStaxConverter
implements Converter<XMLStreamReader, XMLStreamReader> {
    @Override
    public XMLStreamReader convert(XMLStreamReader s2) {
        return s2;
    }
}

