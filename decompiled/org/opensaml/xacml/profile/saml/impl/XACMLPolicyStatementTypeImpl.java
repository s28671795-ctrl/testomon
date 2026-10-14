/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;
import org.opensaml.xacml.profile.saml.XACMLPolicyStatementType;

public class XACMLPolicyStatementTypeImpl
extends AbstractSAMLObject
implements XACMLPolicyStatementType {
    private IndexedXMLObjectChildrenList<XACMLObject> choiceGroup = new IndexedXMLObjectChildrenList(this);
    private ReferencedPoliciesType referencedPolicies;

    protected XACMLPolicyStatementTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        children.addAll(this.choiceGroup);
        if (this.referencedPolicies != null) {
            children.add(this.referencedPolicies);
        }
        return Collections.unmodifiableList(children);
    }

    @Override
    public List<PolicyType> getPolicies() {
        return this.choiceGroup.subList(PolicyType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<PolicySetType> getPolicySets() {
        return this.choiceGroup.subList(PolicySetType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public ReferencedPoliciesType getReferencedPolicies() {
        return this.referencedPolicies;
    }

    @Override
    public void setReferencedPolicies(ReferencedPoliciesType policies) {
        this.referencedPolicies = this.prepareForAssignment(this.referencedPolicies, policies);
    }
}

