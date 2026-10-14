/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.axiom.om.OMAbstractFactory
 *  org.apache.axiom.om.OMElement
 *  org.apache.axiom.om.OMFactory
 *  org.apache.axiom.om.OMXMLBuilderFactory
 */
package org.apache.neethi.builders.converters;

import javax.xml.stream.XMLStreamReader;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.neethi.builders.converters.AbstractStaxConverter;
import org.apache.neethi.builders.converters.Converter;

public class StaxToOMConverter
extends AbstractStaxConverter
implements Converter<XMLStreamReader, OMElement> {
    @Override
    public OMElement convert(XMLStreamReader s2) {
        return OMXMLBuilderFactory.createStAXOMBuilder((OMFactory)OMAbstractFactory.getOMFactory(), (XMLStreamReader)s2).getDocumentElement();
    }
}

