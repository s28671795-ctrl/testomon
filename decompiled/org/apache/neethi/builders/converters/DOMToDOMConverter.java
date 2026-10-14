/*
 * Decompiled with CFR 0.152.
 */
package org.apache.neethi.builders.converters;

import org.apache.neethi.builders.converters.AbstractDOMConverter;
import org.apache.neethi.builders.converters.Converter;
import org.w3c.dom.Element;

public class DOMToDOMConverter
extends AbstractDOMConverter
implements Converter<Element, Element> {
    @Override
    public Element convert(Element s2) {
        return s2;
    }
}

