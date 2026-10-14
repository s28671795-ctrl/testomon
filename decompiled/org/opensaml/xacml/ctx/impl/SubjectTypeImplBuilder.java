/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.SubjectType;
import org.opensaml.xacml.ctx.impl.SubjectTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

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

