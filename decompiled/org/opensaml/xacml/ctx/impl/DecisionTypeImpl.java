/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.impl.AbstractXACMLObject;

public class DecisionTypeImpl
extends AbstractXACMLObject
implements DecisionType {
    private DecisionType.DECISION decision;

    protected DecisionTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return new ArrayList<XMLObject>();
    }

    @Override
    public DecisionType.DECISION getDecision() {
        return this.decision;
    }

    @Override
    public void setDecision(DecisionType.DECISION dec) {
        this.decision = this.prepareForAssignment(this.decision, dec);
    }
}

