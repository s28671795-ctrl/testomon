/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusCodeType;
import org.opensaml.xacml.ctx.impl.StatusCodeTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusCodeTypeImplBuilder
extends AbstractXACMLObjectBuilder<StatusCodeType> {
    @Override
    public StatusCodeType buildObject() {
        return (StatusCodeType)this.buildObject(StatusCodeType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public StatusCodeType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusCodeTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

