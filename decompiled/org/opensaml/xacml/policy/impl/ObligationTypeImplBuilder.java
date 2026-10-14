/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ObligationType;
import org.opensaml.xacml.policy.impl.ObligationTypeImpl;

public class ObligationTypeImplBuilder
extends AbstractXACMLObjectBuilder<ObligationType> {
    @Override
    public ObligationType buildObject() {
        return (ObligationType)this.buildObject(ObligationType.DEFAULT_ELEMENT_QNAME);
    }

    @Override
    public ObligationType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ObligationTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

