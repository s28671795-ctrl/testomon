/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.impl.AbstractXACMLObject;
import org.opensaml.xacml.policy.CombinerParametersType;
import org.opensaml.xacml.policy.DefaultsType;
import org.opensaml.xacml.policy.DescriptionType;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.policy.RuleCombinerParametersType;
import org.opensaml.xacml.policy.RuleType;
import org.opensaml.xacml.policy.TargetType;
import org.opensaml.xacml.policy.VariableDefinitionType;

public class PolicyTypeImpl
extends AbstractXACMLObject
implements PolicyType {
    private DescriptionType description;
    private DefaultsType policyDefaults;
    private TargetType target;
    private IndexedXMLObjectChildrenList<? extends XACMLObject> choiceGroup = new IndexedXMLObjectChildrenList(this);
    private ObligationsType obligations;
    private String policyId;
    private String version = "1.0";
    private String ruleCombiningAlgo;

    protected PolicyTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<CombinerParametersType> getCombinerParameters() {
        return this.choiceGroup.subList(CombinerParametersType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public DescriptionType getDescription() {
        return this.description;
    }

    @Override
    public ObligationsType getObligations() {
        return this.obligations;
    }

    @Override
    public DefaultsType getPolicyDefaults() {
        return this.policyDefaults;
    }

    @Override
    public String getPolicyId() {
        return this.policyId;
    }

    @Override
    public List<RuleCombinerParametersType> getRuleCombinerParameters() {
        return this.choiceGroup.subList(RuleCombinerParametersType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public String getRuleCombiningAlgoId() {
        return this.ruleCombiningAlgo;
    }

    @Override
    public List<RuleType> getRules() {
        return this.choiceGroup.subList(RuleType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public TargetType getTarget() {
        return this.target;
    }

    @Override
    public List<VariableDefinitionType> getVariableDefinitions() {
        return this.choiceGroup.subList(VariableDefinitionType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public String getVersion() {
        return this.version;
    }

    @Override
    public void setDescription(DescriptionType newDescription) {
        this.description = this.prepareForAssignment(this.description, newDescription);
    }

    @Override
    public void setObligations(ObligationsType newObligations) {
        this.obligations = this.prepareForAssignment(this.obligations, newObligations);
    }

    @Override
    public void setPolicyDefaults(DefaultsType defaults) {
        this.policyDefaults = this.prepareForAssignment(this.policyDefaults, defaults);
    }

    @Override
    public void setPolicyId(String id) {
        this.policyId = this.prepareForAssignment(this.policyId, id);
    }

    @Override
    public void setRuleCombiningAlgoId(String id) {
        this.ruleCombiningAlgo = this.prepareForAssignment(this.ruleCombiningAlgo, id);
    }

    @Override
    public void setTarget(TargetType newTarget) {
        this.target = this.prepareForAssignment(this.target, newTarget);
    }

    @Override
    public void setVersion(String newVersion) {
        this.version = this.prepareForAssignment(this.version, newVersion);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.description != null) {
            children.add(this.description);
        }
        if (this.policyDefaults != null) {
            children.add(this.policyDefaults);
        }
        children.add(this.target);
        if (!this.choiceGroup.isEmpty()) {
            children.addAll(this.choiceGroup);
        }
        if (this.obligations != null) {
            children.add(this.obligations);
        }
        return children;
    }
}

