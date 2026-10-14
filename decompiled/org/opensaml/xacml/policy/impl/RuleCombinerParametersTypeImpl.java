/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.opensaml.xacml.policy.impl.CombinerParametersTypeImpl;

public class RuleCombinerParametersTypeImpl
extends CombinerParametersTypeImpl
implements RuleCombinerParametersType {
    private String ruleIdRef;

    protected RuleCombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getRuleIdRef() {
        return this.ruleIdRef;
    }

    @Override
    public void setRuleIdRef(String ref) {
        this.ruleIdRef = this.prepareForAssignment(this.ruleIdRef, ref);
    }
}

