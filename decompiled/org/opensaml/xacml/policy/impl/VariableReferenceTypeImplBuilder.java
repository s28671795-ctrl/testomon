/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.VariableReferenceType;
import org.opensaml.xacml.policy.impl.VariableReferenceTypeImpl;

public class VariableReferenceTypeImplBuilder
extends AbstractXACMLObjectBuilder<VariableReferenceType> {
    @Override
    public VariableReferenceType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new VariableReferenceTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public VariableReferenceType buildObject() {
        return (VariableReferenceType)this.buildObject(VariableReferenceType.DEFAULT_ELEMENT_NAME_XACML20);
    }
}

