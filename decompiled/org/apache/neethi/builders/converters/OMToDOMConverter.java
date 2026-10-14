/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.axiom.om.OMAbstractFactory
 *  org.apache.axiom.om.OMElement
 *  org.apache.axiom.om.OMException
 *  org.apache.axiom.om.OMFactory
 *  org.apache.axiom.om.OMXMLBuilderFactory
 */
package org.apache.neethi.builders.converters;

import javax.xml.stream.XMLStreamReader;
import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMException;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.neethi.builders.converters.AbstractOMConverter;
import org.apache.neethi.builders.converters.Converter;
import org.apache.neethi.builders.converters.StaxToDOMConverter;
import org.w3c.dom.Element;

public class OMToDOMConverter
extends AbstractOMConverter
implements Converter<OMElement, Element> {
    @Override
    public Element convert(OMElement s2) {
        try {
            return (Element)OMXMLBuilderFactory.createStAXOMBuilder((OMFactory)OMAbstractFactory.getMetaFactory((String)"dom").getOMFactory(), (XMLStreamReader)s2.getXMLStreamReader()).getDocumentElement();
        }
        catch (OMException err) {
            return new StaxToDOMConverter().convert(s2.getXMLStreamReader());
        }
    }
}

