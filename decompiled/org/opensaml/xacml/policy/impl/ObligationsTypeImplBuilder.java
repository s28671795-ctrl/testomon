/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.impl.ObligationsTypeImpl;

public class ObligationsTypeImplBuilder
extends AbstractXACMLObjectBuilder<ObligationsType> {
    @Override
    public ObligationsType buildObject() {
        return (ObligationsType)this.buildObject(ObligationsType.DEFAULT_ELEMENT_QNAME);
    }

    @Override
    public ObligationsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ObligationsTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

