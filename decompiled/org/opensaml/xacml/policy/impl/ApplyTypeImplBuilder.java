/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ApplyType;
import org.opensaml.xacml.policy.impl.ApplyTypeImpl;

public class ApplyTypeImplBuilder
extends AbstractXACMLObjectBuilder<ApplyType> {
    @Override
    public ApplyType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ApplyTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public ApplyType buildObject() {
        return (ApplyType)this.buildObject(ApplyType.DEFAULT_ELEMENT_NAME);
    }
}

