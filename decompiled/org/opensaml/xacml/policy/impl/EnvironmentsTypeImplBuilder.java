/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.EnvironmentsType;
import org.opensaml.xacml.policy.impl.EnvironmentsTypeImpl;

public class EnvironmentsTypeImplBuilder
extends AbstractXACMLObjectBuilder<EnvironmentsType> {
    @Override
    public EnvironmentsType buildObject() {
        return (EnvironmentsType)this.buildObject(EnvironmentsType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public EnvironmentsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EnvironmentsTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

