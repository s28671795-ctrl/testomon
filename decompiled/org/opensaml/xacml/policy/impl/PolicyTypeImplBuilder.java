/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.policy.impl.PolicyTypeImpl;

public class PolicyTypeImplBuilder
extends AbstractXACMLObjectBuilder<PolicyType> {
    @Override
    public PolicyType buildObject() {
        return (PolicyType)this.buildObject(PolicyType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public PolicyType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PolicyTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

