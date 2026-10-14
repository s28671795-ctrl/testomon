/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSInteger;

public class XSIntegerImpl
extends AbstractXMLObject
implements XSInteger {
    private Integer value;

    protected XSIntegerImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public void setValue(Integer newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

