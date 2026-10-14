/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.EnvironmentType;
import org.opensaml.xacml.ctx.impl.EnvironmentTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class EnvironmentTypeImplBuilder
extends AbstractXACMLObjectBuilder<EnvironmentType> {
    @Override
    public EnvironmentType buildObject() {
        return (EnvironmentType)this.buildObject(EnvironmentType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public EnvironmentType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EnvironmentTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

