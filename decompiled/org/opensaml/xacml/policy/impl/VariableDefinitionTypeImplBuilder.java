/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.VariableDefinitionType;
import org.opensaml.xacml.policy.impl.VariableDefinitionTypeImpl;

public class VariableDefinitionTypeImplBuilder
extends AbstractXACMLObjectBuilder<VariableDefinitionType> {
    @Override
    public VariableDefinitionType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new VariableDefinitionTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public VariableDefinitionType buildObject() {
        return (VariableDefinitionType)this.buildObject(VariableDefinitionType.DEFAULT_ELEMENT_NAME);
    }
}

