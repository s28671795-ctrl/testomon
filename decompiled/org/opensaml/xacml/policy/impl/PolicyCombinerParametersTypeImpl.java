/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.policy.PolicyCombinerParametersType;
import org.opensaml.xacml.policy.impl.CombinerParametersTypeImpl;

public class PolicyCombinerParametersTypeImpl
extends CombinerParametersTypeImpl
implements PolicyCombinerParametersType {
    private String policyIdRef;

    protected PolicyCombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getPolicyIdRef() {
        return this.policyIdRef;
    }

    @Override
    public void setPolicyIdRef(String ref) {
        this.policyIdRef = this.prepareForAssignment(this.policyIdRef, ref);
    }
}

