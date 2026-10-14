/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.ConditionType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.EffectType;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.TargetType;

public class RuleTypeImpl
extends AbstractXACMLObject
implements RuleType {
    private ConditionType condition;
    private TargetType target;
    private DescriptionType description;
    private EffectType effectType;
    private String ruleId;

    protected RuleTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public ConditionType getCondition() {
        return this.condition;
    }

    @Override
    public DescriptionType getDescription() {
        return this.description;
    }

    @Override
    public EffectType getEffect() {
        return this.effectType;
    }

    @Override
    public String getRuleId() {
        return this.ruleId;
    }

    @Override
    public TargetType getTarget() {
        return this.target;
    }

    @Override
    public void setCondition(ConditionType newCondition) {
        this.condition = this.prepareForAssignment(this.condition, newCondition);
    }

    @Override
    public void setDescription(DescriptionType newDescription) {
        this.description = this.prepareForAssignment(this.description, newDescription);
    }

    @Override
    public void setEffect(EffectType type) {
        this.effectType = this.prepareForAssignment(this.effectType, type);
    }

    @Override
    public void setRuleId(String id) {
        this.ruleId = this.prepareForAssignment(this.ruleId, id);
    }

    @Override
    public void setTarget(TargetType newTarget) {
        this.target = this.prepareForAssignment(this.target, newTarget);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        if (this.description != null) {
            children.add(this.description);
        }
        if (this.target != null) {
            children.add(this.target);
        }
        if (this.condition != null) {
            children.add(this.condition);
        }
        return Collections.unmodifiableList(children);
    }
}

