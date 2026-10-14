/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.policy.PolicySetType;
import org.opensaml.xacml.policy.PolicyType;
import org.opensaml.xacml.profile.saml.ReferencedPoliciesType;
import org.opensaml.xacml.profile.saml.XACMLAuthzDecisionQueryType;

public class XACMLAuthzDecisionQueryTypeImpl
extends RequestAbstractTypeImpl
implements XACMLAuthzDecisionQueryType {
    private List<PolicyType> policies;
    private List<PolicySetType> policySets;
    private ReferencedPoliciesType referencedPolicies;
    private RequestType request;
    private XSBooleanValue inputContextOnly;
    private XSBooleanValue returnContext;
    private XSBooleanValue combinePolicies;

    protected XACMLAuthzDecisionQueryTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.setElementNamespacePrefix(namespacePrefix);
        this.policies = new XMLObjectChildrenList<PolicyType>(this);
        this.policySets = new XMLObjectChildrenList<PolicySetType>(this);
    }

    @Override
    public XSBooleanValue getCombinePoliciesXSBooleanValue() {
        return this.combinePolicies;
    }

    @Override
    public XSBooleanValue getInputContextOnlyXSBooleanValue() {
        return this.inputContextOnly;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.request != null) {
            children.add(this.request);
        }
        if (!this.policies.isEmpty()) {
            children.addAll(this.policies);
        }
        if (!this.policySets.isEmpty()) {
            children.addAll(this.policySets);
        }
        if (this.referencedPolicies != null) {
            children.add(this.referencedPolicies);
        }
        return Collections.unmodifiableList(children);
    }

    @Override
    public RequestType getRequest() {
        return this.request;
    }

    @Override
    public XSBooleanValue getReturnContextXSBooleanValue() {
        return this.returnContext;
    }

    @Override
    public Boolean isCombinePolicies() {
        if (this.combinePolicies != null) {
            return this.combinePolicies.getValue();
        }
        return Boolean.TRUE;
    }

    @Override
    public Boolean isInputContextOnly() {
        if (this.inputContextOnly != null) {
            return this.inputContextOnly.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public Boolean isReturnContext() {
        if (this.returnContext != null) {
            return this.returnContext.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public void setCombinePolicies(XSBooleanValue combine) {
        this.combinePolicies = this.prepareForAssignment(this.combinePolicies, combine);
    }

    @Override
    public void setCombinePolicies(Boolean combine) {
        this.combinePolicies = combine != null ? this.prepareForAssignment(this.combinePolicies, new XSBooleanValue(combine, false)) : (XSBooleanValue)this.prepareForAssignment(this.combinePolicies, null);
    }

    @Override
    public void setInputContextOnly(XSBooleanValue flag) {
        this.inputContextOnly = this.prepareForAssignment(this.inputContextOnly, flag);
    }

    @Override
    public void setInputContextOnly(Boolean flag) {
        this.inputContextOnly = flag != null ? this.prepareForAssignment(this.inputContextOnly, new XSBooleanValue(flag, false)) : (XSBooleanValue)this.prepareForAssignment(this.inputContextOnly, null);
    }

    @Override
    public void setRequest(RequestType req) {
        this.request = this.prepareForAssignment(this.request, req);
    }

    @Override
    public void setReturnContext(XSBooleanValue flag) {
        this.returnContext = this.prepareForAssignment(this.returnContext, flag);
    }

    @Override
    public void setReturnContext(Boolean flag) {
        this.returnContext = flag != null ? this.prepareForAssignment(this.returnContext, new XSBooleanValue(flag, false)) : (XSBooleanValue)this.prepareForAssignment(this.returnContext, null);
    }

    @Override
    public List<PolicyType> getPolicies() {
        return this.policies;
    }

    @Override
    public List<PolicySetType> getPolicySets() {
        return this.policySets;
    }

    @Override
    public ReferencedPoliciesType getReferencedPolicies() {
        return this.referencedPolicies;
    }

    @Override
    public void setReferencedPolicies(ReferencedPoliciesType pols) {
        this.referencedPolicies = this.prepareForAssignment(this.referencedPolicies, pols);
    }
}

