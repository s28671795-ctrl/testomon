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
import org.opensaml.xacml.policy.ExpressionType;
import org.opensaml.xacml.policy.VariableReferenceType;

public class VariableReferenceTypeImpl
extends AbstractXACMLObject
implements VariableReferenceType {
    private XMLObjectChildrenList<ExpressionType> expressions = new XMLObjectChildrenList(this);
    private String valiableId;

    protected VariableReferenceTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ExpressionType> getExpressions() {
        return this.expressions;
    }

    @Override
    public String getVariableId() {
        return this.valiableId;
    }

    @Override
    public void setVariableId(String id) {
        this.valiableId = this.prepareForAssignment(this.valiableId, id);
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

