/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.impl.PolicySetTypeImpl;

public class PolicySetTypeImplBuilder
extends AbstractXACMLObjectBuilder<PolicySetType> {
    @Override
    public PolicySetType buildObject() {
        return (PolicySetType)this.buildObject(PolicySetType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public PolicySetType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PolicySetTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

