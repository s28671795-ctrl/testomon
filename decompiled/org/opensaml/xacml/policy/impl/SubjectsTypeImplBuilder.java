/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectsType;
import org.opensaml.xacml.policy.impl.SubjectsTypeImpl;

public class SubjectsTypeImplBuilder
extends AbstractXACMLObjectBuilder<SubjectsType> {
    @Override
    public SubjectsType buildObject() {
        return (SubjectsType)this.buildObject(SubjectsType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public SubjectsType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectsTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

