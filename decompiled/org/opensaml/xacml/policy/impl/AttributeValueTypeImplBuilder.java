/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeValueType;
import org.opensaml.xacml.policy.impl.AttributeValueTypeImpl;

public class AttributeValueTypeImplBuilder
extends AbstractXACMLObjectBuilder<AttributeValueType> {
    @Override
    public AttributeValueType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeValueTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public AttributeValueType buildObject() {
        return (AttributeValueType)this.buildObject(AttributeValueType.DEFAULT_ELEMENT_NAME);
    }
}

