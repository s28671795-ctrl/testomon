/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.ActionsType;
import org.opensaml.xacml.policy.impl.ActionsTypeImpl;

public class ActionsTypeImplBuilder
extends AbstractXACMLObjectBuilder<ActionsType> {
    @Override
    public ActionsType buildObject() {
        return (ActionsType)this.buildObject(ActionsType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ActionsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ActionsTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

