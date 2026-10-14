/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.ctx.impl.RequestTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class RequestTypeImplBuilder
extends AbstractXACMLObjectBuilder<RequestType> {
    @Override
    public RequestType buildObject() {
        return (RequestType)this.buildObject(RequestType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public RequestType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RequestTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

