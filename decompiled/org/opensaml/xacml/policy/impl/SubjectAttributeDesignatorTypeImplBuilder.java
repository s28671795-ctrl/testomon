/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectAttributeDesignatorType;
import org.opensaml.xacml.policy.impl.SubjectAttributeDesignatorTypeImpl;

public class SubjectAttributeDesignatorTypeImplBuilder
extends AbstractXACMLObjectBuilder<SubjectAttributeDesignatorType> {
    @Override
    public SubjectAttributeDesignatorType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectAttributeDesignatorTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public SubjectAttributeDesignatorType buildObject() {
        return (SubjectAttributeDesignatorType)this.buildObject(SubjectAttributeDesignatorType.DEFAULT_ELEMENT_QNAME);
    }
}

