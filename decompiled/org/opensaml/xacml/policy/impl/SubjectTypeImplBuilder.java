/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectType;
import org.opensaml.xacml.policy.impl.SubjectTypeImpl;

public class SubjectTypeImplBuilder
extends AbstractXACMLObjectBuilder<SubjectType> {
    @Override
    public SubjectType buildObject() {
        return (SubjectType)this.buildObject(SubjectType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public SubjectType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

