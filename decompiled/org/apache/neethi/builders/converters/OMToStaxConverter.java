/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.axiom.om.OMElement
 */
package org.apache.neethi.builders.converters;

import javax.xml.stream.XMLStreamReader;
import org.apache.axiom.om.OMElement;
import org.apache.neethi.builders.converters.AbstractOMConverter;
import org.apache.neethi.builders.converters.Converter;

public class OMToStaxConverter
extends AbstractOMConverter
implements Converter<OMElement, XMLStreamReader> {
    @Override
    public XMLStreamReader convert(OMElement s2) {
        return s2.getXMLStreamReader();
    }
}

