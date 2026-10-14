/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ExpressionType;
import org.opensaml.xacml.policy.VariableDefinitionType;

public class VariableDefinitionTypeImpl
extends AbstractXACMLObject
implements VariableDefinitionType {
    private ExpressionType expression;
    private String variableId;

    protected VariableDefinitionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public ExpressionType getExpression() {
        return this.expression;
    }

    @Override
    public void setExpression(ExpressionType newExpression) {
        this.expression = this.prepareForAssignment(this.expression, newExpression);
    }

    @Override
    public String getVariableId() {
        return this.variableId;
    }

    @Override
    public void setVariableId(String id) {
        this.variableId = this.prepareForAssignment(this.variableId, id);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<ExpressionType> children = new ArrayList<ExpressionType>();
        if (this.expression != null) {
            children.add(this.expression);
        }
        return Collections.unmodifiableList(children);
    }
}

