/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;
import org.opensaml.xacml.policy.SubjectMatchType;
import org.opensaml.xacml.policy.impl.SubjectMatchTypeImpl;

public class SubjectMatchTypeImplBuilder
extends AbstractXACMLObjectBuilder<SubjectMatchType> {
    @Override
    public SubjectMatchType buildObject() {
        return (SubjectMatchType)this.buildObject(SubjectMatchType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public SubjectMatchType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SubjectMatchTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

