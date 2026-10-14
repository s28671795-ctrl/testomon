/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ConditionType;
import org.opensaml.xacml.policy.ExpressionType;

public class ConditionTypeImpl
extends AbstractXACMLObject
implements ConditionType {
    private ExpressionType expression;

    protected ConditionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public ExpressionType getExpression() {
        return this.expression;
    }

    @Override
    public void setExpression(ExpressionType exp) {
        this.expression = this.prepareForAssignment(this.expression, exp);
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

