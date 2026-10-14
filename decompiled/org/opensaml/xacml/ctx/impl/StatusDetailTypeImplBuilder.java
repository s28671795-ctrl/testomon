/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusDetailType;
import org.opensaml.xacml.ctx.impl.StatusDetailTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusDetailTypeImplBuilder
extends AbstractXACMLObjectBuilder<StatusDetailType> {
    @Override
    public StatusDetailType buildObject() {
        return (StatusDetailType)this.buildObject(StatusDetailType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public StatusDetailType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusDetailTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

