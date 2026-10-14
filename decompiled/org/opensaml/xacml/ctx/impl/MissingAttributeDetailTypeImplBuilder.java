/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.MissingAttributeDetailType;
import org.opensaml.xacml.ctx.impl.MissingAttributeDetailTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class MissingAttributeDetailTypeImplBuilder
extends AbstractXACMLObjectBuilder<MissingAttributeDetailType> {
    @Override
    public MissingAttributeDetailType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new MissingAttributeDetailTypeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public MissingAttributeDetailType buildObject() {
        return (MissingAttributeDetailType)this.buildObject(MissingAttributeDetailType.DEFAULT_ELEMENT_NAME);
    }
}

