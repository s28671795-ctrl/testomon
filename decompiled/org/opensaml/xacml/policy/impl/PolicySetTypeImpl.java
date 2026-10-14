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
import org.opensaml.xacml.policy.IdReferenceType;
import org.opensaml.xacml.policy.ObligationsType;
import org.opensaml.xacml.policy.PolicyCombinerParametersType;
import org.opensaml.xacml.policy.PolicySetCombinerParametersType;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.policy.TargetType;

public class PolicySetTypeImpl
extends AbstractXACMLObject
implements PolicySetType {
    private DescriptionType description;
    private DefaultsType policySetDefaults;
    private TargetType target;
    private IndexedXMLObjectChildrenList<? extends XACMLObject> choiceGroup = new IndexedXMLObjectChildrenList(this);
    private ObligationsType obligations;
    private String policySetId;
    private String version = "1.0";
    private String combiningAlgo;

    protected PolicySetTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.description != null) {
            children.add(this.description);
        }
        if (this.policySetDefaults != null) {
            children.add(this.policySetDefaults);
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

    @Override
    public List<PolicyType> getPolicies() {
        return this.choiceGroup.subList(PolicyType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<PolicyCombinerParametersType> getPolicyCombinerParameters() {
        return this.choiceGroup.subList(PolicyCombinerParametersType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public String getPolicyCombiningAlgoId() {
        return this.combiningAlgo;
    }

    @Override
    public List<IdReferenceType> getPolicyIdReferences() {
        return this.choiceGroup.subList(IdReferenceType.POLICY_ID_REFERENCE_ELEMENT_NAME);
    }

    @Override
    public List<PolicySetCombinerParametersType> getPolicySetCombinerParameters() {
        return this.choiceGroup.subList(PolicySetCombinerParametersType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public DefaultsType getPolicySetDefaults() {
        return this.policySetDefaults;
    }

    @Override
    public String getPolicySetId() {
        return this.policySetId;
    }

    @Override
    public List<IdReferenceType> getPolicySetIdReferences() {
        return this.choiceGroup.subList(IdReferenceType.POLICY_SET_ID_REFERENCE_ELEMENT_NAME);
    }

    @Override
    public List<PolicySetType> getPolicySets() {
        return this.choiceGroup.subList(PolicySetType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public TargetType getTarget() {
        return this.target;
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
    public void setPolicyCombiningAlgoId(String id) {
        this.combiningAlgo = this.prepareForAssignment(this.combiningAlgo, id);
    }

    @Override
    public void setPolicySetDefaults(DefaultsType defaults) {
        this.policySetDefaults = this.prepareForAssignment(this.policySetDefaults, defaults);
    }

    @Override
    public void setPolicySetId(String id) {
        this.policySetId = this.prepareForAssignment(this.policySetId, id);
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
    public IndexedXMLObjectChildrenList<XACMLObject> getPolicyChoiceGroup() {
        return this.choiceGroup;
    }
}

