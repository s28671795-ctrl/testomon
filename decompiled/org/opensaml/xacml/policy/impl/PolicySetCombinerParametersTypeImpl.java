/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;
import org.opensaml.xacml.policy.impl.CombinerParametersTypeImpl;

public class PolicySetCombinerParametersTypeImpl
extends CombinerParametersTypeImpl
implements PolicySetCombinerParametersType {
    private String policySetIdRef;

    protected PolicySetCombinerParametersTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getPolicySetIdRef() {
        return this.policySetIdRef;
    }

    @Override
    public void setPolicySetIdRef(String ref) {
        this.policySetIdRef = this.prepareForAssignment(this.policySetIdRef, ref);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return super.getOrderedChildren();
    }
}

