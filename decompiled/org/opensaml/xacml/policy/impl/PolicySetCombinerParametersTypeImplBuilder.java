/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;
import org.opensaml.xacml.policy.impl.PolicySetCombinerParametersTypeImpl;

public class PolicySetCombinerParametersTypeImplBuilder
extends AbstractXACMLObjectBuilder<PolicySetCombinerParametersType> {
    @Override
    public PolicySetCombinerParametersType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PolicySetCombinerParametersTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public PolicySetCombinerParametersType buildObject() {
        return (PolicySetCombinerParametersType)this.buildObject(PolicySetCombinerParametersType.DEFAULT_ELEMENT_NAME);
    }
}

