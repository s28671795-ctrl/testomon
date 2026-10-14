/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.ctx.impl.AttributeTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class AttributeTypeImplBuilder
extends AbstractXACMLObjectBuilder<AttributeType> {
    @Override
    public AttributeType buildObject() {
        return (AttributeType)this.buildObject(AttributeType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public AttributeType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

