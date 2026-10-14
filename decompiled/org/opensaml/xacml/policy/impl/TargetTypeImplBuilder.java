/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.TargetType;
import org.opensaml.xacml.policy.impl.TargetTypeImpl;

public class TargetTypeImplBuilder
extends AbstractXACMLObjectBuilder<TargetType> {
    @Override
    public TargetType buildObject() {
        return (TargetType)this.buildObject(TargetType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public TargetType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new TargetTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

