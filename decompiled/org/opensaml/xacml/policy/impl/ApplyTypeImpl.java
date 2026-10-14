/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ApplyType;
import org.opensaml.xacml.policy.ExpressionType;

public class ApplyTypeImpl
extends AbstractXACMLObject
implements ApplyType {
    private XMLObjectChildrenList<ExpressionType> expressions = new XMLObjectChildrenList(this);
    private String functionId;

    protected ApplyTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ExpressionType> getExpressions() {
        return this.expressions;
    }

    @Override
    public String getFunctionId() {
        return this.functionId;
    }

    @Override
    public void setFunctionId(String id) {
        this.functionId = this.prepareForAssignment(this.functionId, id);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<ExpressionType> children = new ArrayList<ExpressionType>();
        if (!this.expressions.isEmpty()) {
            children.addAll(this.expressions);
        }
        return Collections.unmodifiableList(children);
    }
}

