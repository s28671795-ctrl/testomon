/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.ctx.impl.ResultTypeImpl;
import org.opensaml.xacml.impl.AbstractXACMLObjectBuilder;

public class ResultTypeImplBuilder
extends AbstractXACMLObjectBuilder<ResultType> {
    @Override
    public ResultType buildObject() {
        return (ResultType)this.buildObject(ResultType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ResultType buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ResultTypeImpl(namespaceURI, localName, namespacePrefix);
    }
}

