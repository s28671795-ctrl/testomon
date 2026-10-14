/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.DecisionType;
import org.opensaml.xacml.ctx.ResultType;
import org.opensaml.xacml.ctx.StatusType;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ObligationsType;

public class ResultTypeImpl
extends AbstractXACMLObject
implements ResultType {
    private String resourceId;
    private DecisionType decision;
    private StatusType status;
    private ObligationsType obligations;

    protected ResultTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public DecisionType getDecision() {
        return this.decision;
    }

    @Override
    public ObligationsType getObligations() {
        return this.obligations;
    }

    @Override
    public void setObligations(ObligationsType obligationsIn) {
        this.obligations = this.prepareForAssignment(this.obligations, obligationsIn);
    }

    @Override
    public String getResourceId() {
        return this.resourceId;
    }

    @Override
    public StatusType getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(StatusType statusIn) {
        this.status = this.prepareForAssignment(this.status, statusIn);
    }

    @Override
    public void setDecision(DecisionType decisionIn) {
        this.decision = this.prepareForAssignment(this.decision, decisionIn);
    }

    @Override
    public void setResourceId(String newResourceId) {
        this.resourceId = this.prepareForAssignment(this.resourceId, newResourceId);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        if (this.decision != null) {
            children.add(this.decision);
        }
        if (this.status != null) {
            children.add(this.status);
        }
        if (this.obligations != null) {
            children.add(this.obligations);
        }
        return Collections.unmodifiableList(children);
    }
}

