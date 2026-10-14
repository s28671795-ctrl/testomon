/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ConditionType;
import org.opensaml.xacml.policy.impl.ConditionTypeImpl;

public class ConditionTypeImplBuilder
extends AbstractXACMLObjectBuilder<ConditionType> {
    @Override
    public ConditionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ConditionTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public ConditionType buildObject() {
        return (ConditionType)this.buildObject(ConditionType.DEFAULT_ELEMENT_NAME);
    }
}

