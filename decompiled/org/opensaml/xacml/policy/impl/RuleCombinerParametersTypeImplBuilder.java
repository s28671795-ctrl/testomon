/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.opensaml.xacml.policy.impl.RuleCombinerParametersTypeImpl;

public class RuleCombinerParametersTypeImplBuilder
extends AbstractXACMLObjectBuilder<RuleCombinerParametersType> {
    @Override
    public RuleCombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RuleCombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public RuleCombinerParametersType buildObject() {
        return (RuleCombinerParametersType)this.buildObject(RuleCombinerParametersType.DEFAULT_ELEMENT_NAME);
    }
}

