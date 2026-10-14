/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicyCombinerParametersType;
import org.opensaml.xacml.policy.impl.PolicyCombinerParametersTypeImpl;

public class PolicyCombinerParametersTypeImplBuilder
extends AbstractXACMLObjectBuilder<PolicyCombinerParametersType> {
    @Override
    public PolicyCombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PolicyCombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public PolicyCombinerParametersType buildObject() {
        return (PolicyCombinerParametersType)this.buildObject(PolicyCombinerParametersType.DEFAULT_ELEMENT_NAME);
    }
}

