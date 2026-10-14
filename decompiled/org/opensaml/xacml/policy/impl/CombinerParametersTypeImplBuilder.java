/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.CombinerParametersType;
import org.opensaml.xacml.policy.impl.CombinerParametersTypeImpl;

public class CombinerParametersTypeImplBuilder
extends AbstractXACMLObjectBuilder<CombinerParametersType> {
    @Override
    public CombinerParametersType buildObject() {
        return (CombinerParametersType)this.buildObject(CombinerParametersType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public CombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

