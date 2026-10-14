/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.impl.RuleTypeImpl;

public class RuleTypeImplBuilder
extends AbstractXACMLObjectBuilder<RuleType> {
    @Override
    public RuleType buildObject() {
        return (RuleType)this.buildObject(RuleType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public RuleType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RuleTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

