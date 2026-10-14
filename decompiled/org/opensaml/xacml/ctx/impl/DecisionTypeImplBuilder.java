/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.impl.DecisionTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class DecisionTypeImplBuilder
extends AbstractXACMLObjectBuilder<DecisionType> {
    @Override
    public DecisionType buildObject() {
        return (DecisionType)this.buildObject(DecisionType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public DecisionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DecisionTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

