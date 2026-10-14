/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.CombinerParameterType;
import org.opensaml.xacml.policy.impl.CombinerParameterTypeImpl;

public class CombinerParameterTypeImplBuilder
extends AbstractXACMLObjectBuilder<CombinerParameterType> {
    @Override
    public CombinerParameterType buildObject() {
        return (CombinerParameterType)this.buildObject(CombinerParameterType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public CombinerParameterType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CombinerParameterTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

