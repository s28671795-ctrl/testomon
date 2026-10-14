/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.axiom.om.OMElement
 *  org.apache.axiom.om.OMXMLBuilderFactory
 */
package org.apache.neethi.builders.converters;

import javax.xml.transform.Source;
import javax.xml.transform.dom.DOMSource;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMXMLBuilderFactory;
import org.apache.neethi.builders.converters.AbstractDOMConverter;
import org.apache.neethi.builders.converters.Converter;
import org.w3c.dom.Element;

public class DOMToOMConverter
extends AbstractDOMConverter
implements Converter<Element, OMElement> {
    @Override
    public OMElement convert(Element s2) {
        return OMXMLBuilderFactory.createOMBuilder((Source)new DOMSource(s2)).getDocumentElement();
    }
}

