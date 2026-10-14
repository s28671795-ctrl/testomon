/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.IdReferenceType;
import org.opensaml.xacml.policy.impl.IdReferenceTypeImpl;

public class IdReferenceTypeImplBuilder
extends AbstractXACMLObjectBuilder<IdReferenceType> {
    @Override
    public IdReferenceType buildObject() {
        return (IdReferenceType)this.buildObject(IdReferenceType.TYPE_NAME);
    }

    @Override
    public IdReferenceType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new IdReferenceTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

