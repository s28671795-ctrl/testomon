/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.ctx.ResponseType;
import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class ResponseTypeImpl
extends AbstractXACMLObject
implements ResponseType {
    private final XMLObjectChildrenList<ResultType> results = new XMLObjectChildrenList(this);

    protected ResponseTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<ResultType> children = new ArrayList<ResultType>();
        children.addAll(this.results);
        return Collections.unmodifiableList(children);
    }

    @Override
    public List<ResultType> getResults() {
        return this.results;
    }
}

