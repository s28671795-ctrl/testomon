/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.Attribute;
import org.opensaml.saml.saml1.core.impl.AttributeDesignatorImpl;

public class AttributeImpl
extends AttributeDesignatorImpl
implements Attribute {
    private final XMLObjectChildrenList<XMLObject> attributeValues = new XMLObjectChildrenList(this);

    protected AttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getAttributeValues() {
        return this.attributeValues;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return Collections.unmodifiableList(this.attributeValues);
    }
}

