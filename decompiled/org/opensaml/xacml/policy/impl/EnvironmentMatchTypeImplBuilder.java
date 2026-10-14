/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.EnvironmentMatchType;
import org.opensaml.xacml.policy.impl.EnvironmentMatchTypeImpl;

public class EnvironmentMatchTypeImplBuilder
extends AbstractXACMLObjectBuilder<EnvironmentMatchType> {
    @Override
    public EnvironmentMatchType buildObject() {
        return (EnvironmentMatchType)this.buildObject(EnvironmentMatchType.DEFAULT_ELEMENT_QNAME);
    }

    @Override
    public EnvironmentMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EnvironmentMatchTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

