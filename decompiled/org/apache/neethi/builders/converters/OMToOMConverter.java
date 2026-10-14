/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.apache.axiom.om.OMElement
 */
package org.apache.neethi.builders.converters;

import org.apache.axiom.om.OMElement;
import org.apache.neethi.builders.converters.AbstractOMConverter;
import org.apache.neethi.builders.converters.Converter;

public class OMToOMConverter
extends AbstractOMConverter
implements Converter<OMElement, OMElement> {
    @Override
    public OMElement convert(OMElement s2) {
        return s2;
    }
}

