/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.ctx.impl.StatusTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class StatusTypeImplBuilder
extends AbstractXACMLObjectBuilder<StatusType> {
    @Override
    public StatusType buildObject() {
        return (StatusType)this.buildObject(StatusType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public StatusType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new StatusTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

