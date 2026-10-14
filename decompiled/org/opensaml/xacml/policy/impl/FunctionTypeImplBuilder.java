/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.FunctionType;
import org.opensaml.xacml.policy.impl.FunctionTypeImpl;

public class FunctionTypeImplBuilder
extends AbstractXACMLObjectBuilder<FunctionType> {
    @Override
    public FunctionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new FunctionTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public FunctionType buildObject() {
        return (FunctionType)this.buildObject(FunctionType.DEFAULT_ELEMENT_NAME);
    }
}

