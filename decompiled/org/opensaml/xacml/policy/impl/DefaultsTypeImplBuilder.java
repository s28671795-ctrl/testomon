/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.DefaultsType;
import org.opensaml.xacml.policy.impl.DefaultsTypeImpl;

public class DefaultsTypeImplBuilder
extends AbstractXACMLObjectBuilder<DefaultsType> {
    @Override
    public DefaultsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DefaultsTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public DefaultsType buildObject() {
        return null;
    }
}

