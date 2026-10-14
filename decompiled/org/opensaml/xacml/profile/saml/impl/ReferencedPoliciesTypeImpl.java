/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;

public class ReferencedPoliciesTypeImpl
extends AbstractSAMLObject
implements ReferencedPoliciesType {
    private XMLObjectChildrenList<PolicyType> policies = new XMLObjectChildrenList(this);
    private XMLObjectChildrenList<PolicySetType> policieSets = new XMLObjectChildrenList(this);

    protected ReferencedPoliciesTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<PolicySetType> getPolicySets() {
        return this.policieSets;
    }

    @Override
    public List<PolicyType> getPolicies() {
        return this.policies;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XACMLObject> children = new ArrayList<XACMLObject>();
        if (!this.policies.isEmpty()) {
            children.addAll(this.policies);
        }
        if (!this.policieSets.isEmpty()) {
            children.addAll(this.policieSets);
        }
        return Collections.unmodifiableList(children);
    }
}

