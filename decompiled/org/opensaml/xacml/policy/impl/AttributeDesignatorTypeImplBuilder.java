/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.opensaml.xacml.policy.impl.AttributeDesignatorTypeImpl;

public class AttributeDesignatorTypeImplBuilder
extends AbstractXACMLObjectBuilder<AttributeDesignatorType> {
    @Override
    public AttributeDesignatorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeDesignatorTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public AttributeDesignatorType buildObject() {
        return (AttributeDesignatorType)this.buildObject(AttributeDesignatorType.DEFAULT_ELEMENT_NAME_XACML20);
    }
}

