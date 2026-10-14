/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.profile.saml.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;
import org.opensaml.xacml.XACMLObject;
import org.opensaml.xacml.ctx.RequestType;
import org.opensaml.xacml.policy.IdReferenceType;
import org.opensaml.xacml.profile.saml.XACMLPolicyQueryType;

public class XACMLPolicyQueryTypeImpl
extends RequestAbstractTypeImpl
implements XACMLPolicyQueryType {
    private IndexedXMLObjectChildrenList<XACMLObject> choiceGroup;

    public XACMLPolicyQueryTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.setElementNamespacePrefix(namespacePrefix);
        this.choiceGroup = new IndexedXMLObjectChildrenList(this);
    }

    @Override
    public List<RequestType> getRequests() {
        return this.choiceGroup.subList(RequestType.DEFAULT_ELEMENT_NAME);
    }

    @Override
    public List<IdReferenceType> getPolicySetIdReferences() {
        return this.choiceGroup.subList(IdReferenceType.POLICY_SET_ID_REFERENCE_ELEMENT_NAME);
    }

    @Override
    public List<IdReferenceType> getPolicyIdReferences() {
        return this.choiceGroup.subList(IdReferenceType.POLICY_ID_REFERENCE_ELEMENT_NAME);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        children.addAll(this.choiceGroup);
        return Collections.unmodifiableList(children);
    }
}

