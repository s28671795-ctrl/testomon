/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.AttributeValueType;
import org.opensaml.xacml.policy.CombinerParameterType;

public class CombinerParameterTypeImpl
extends AbstractXACMLObject
implements CombinerParameterType {
    private String name;
    private AttributeValueType value;

    protected CombinerParameterTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public AttributeValueType getAttributeValue() {
        return this.value;
    }

    @Override
    public String getParameterName() {
        return this.name;
    }

    @Override
    public void setAttributeValue(AttributeValueType newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    public void setParameterName(String newName) {
        this.name = this.prepareForAssignment(this.name, newName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<AttributeValueType> children = new ArrayList<AttributeValueType>();
        if (this.value != null) {
            children.add(this.value);
        }
        return Collections.unmodifiableList(children);
    }
}

