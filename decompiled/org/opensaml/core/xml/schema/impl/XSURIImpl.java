/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSURI;

public class XSURIImpl
extends AbstractXMLObject
implements XSURI {
    private String value;

    protected XSURIImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getValue() {
        return this.value;
    }

    @Override
    public void setValue(String newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(new LinkedList());
    }
}

