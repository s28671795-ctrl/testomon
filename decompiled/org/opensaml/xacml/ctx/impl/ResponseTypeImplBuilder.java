/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.ctx.impl.ResponseTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ResponseTypeImplBuilder
extends AbstractXACMLObjectBuilder<ResponseType> {
    @Override
    public ResponseType buildObject() {
        return (ResponseType)this.buildObject(ResponseType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ResponseType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResponseTypeImpl(namespaceURI, localName, "xacml-context");
    }
}

