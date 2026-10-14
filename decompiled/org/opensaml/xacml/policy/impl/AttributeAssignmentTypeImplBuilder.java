/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeAssignmentType;
import org.opensaml.xacml.policy.impl.AttributeAssignmentTypeImpl;

public class AttributeAssignmentTypeImplBuilder
extends AbstractXACMLObjectBuilder<AttributeAssignmentType> {
    @Override
    public AttributeAssignmentType buildObject() {
        return (AttributeAssignmentType)this.buildObject(AttributeAssignmentType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public AttributeAssignmentType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeAssignmentTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

