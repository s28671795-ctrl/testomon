/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.policy.AttributeAssignmentType;
import org.opensaml.xacml.policy.impl.AttributeValueTypeImpl;

public class AttributeAssignmentTypeImpl
extends AttributeValueTypeImpl
implements AttributeAssignmentType {
    private String attributeId;

    protected AttributeAssignmentTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAttributeId() {
        return this.attributeId;
    }

    @Override
    public void setAttributeId(String newAttributeID) {
        this.attributeId = this.prepareForAssignment(this.attributeId, newAttributeID);
    }
}

