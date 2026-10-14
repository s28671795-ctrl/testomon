/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.AttributeValueType;
import org.opensaml.xacml.ctx.impl.AttributeValueTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class AttributeValueTypeImplBuilder
extends AbstractXACMLObjectBuilder<AttributeValueType> {
    @Override
    public AttributeValueType buildObject() {
        return (AttributeValueType)this.buildObject(AttributeValueType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public AttributeValueType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeValueTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

