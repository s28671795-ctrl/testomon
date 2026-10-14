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
import org.opensaml.xacml.policy.AttributeAssignmentType;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.ObligationType;

public class ObligationTypeImpl
extends AbstractXACMLObject
implements ObligationType {
    private XMLObjectChildrenList<AttributeAssignmentType> attributeAssignments = new XMLObjectChildrenList(this);
    private EffectType fulFillOn;
    private String obligationId;

    protected ObligationTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AttributeAssignmentType> getAttributeAssignments() {
        return this.attributeAssignments;
    }

    @Override
    public EffectType getFulfillOn() {
        return this.fulFillOn;
    }

    @Override
    public String getObligationId() {
        return this.obligationId;
    }

    @Override
    public void setFulfillOn(EffectType newFulfillOn) {
        this.fulFillOn = this.prepareForAssignment(this.fulFillOn, newFulfillOn);
    }

    @Override
    public void setObligationId(String newObligationId) {
        this.obligationId = this.prepareForAssignment(this.obligationId, newObligationId);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<AttributeAssignmentType> children = new ArrayList<AttributeAssignmentType>();
        if (!this.attributeAssignments.isEmpty()) {
            children.addAll(this.attributeAssignments);
        }
        return Collections.unmodifiableList(children);
    }
}

