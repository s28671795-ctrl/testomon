/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.AttributeSelectorType;
import org.opensaml.xacml.policy.impl.AttributeSelectorTypeImpl;

public class AttributeSelectorTypeImplBuilder
extends AbstractXACMLObjectBuilder<AttributeSelectorType> {
    @Override
    public AttributeSelectorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeSelectorTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public AttributeSelectorType buildObject() {
        return (AttributeSelectorType)this.buildObject(AttributeSelectorType.DEFAULT_ELEMENT_NAME);
    }
}

