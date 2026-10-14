/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.Action;
import org.opensaml.saml.saml2.core.AuthzDecisionQuery;
import org.opensaml.saml.saml2.core.Evidence;
import org.opensaml.saml.saml2.core.impl.SubjectQueryImpl;

public class AuthzDecisionQueryImpl
extends SubjectQueryImpl
implements AuthzDecisionQuery {
    private String resource;
    private Evidence evidence;
    private final XMLObjectChildrenList<Action> actions = new XMLObjectChildrenList(this);

    protected AuthzDecisionQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public void setResource(String newResource) {
        this.resource = this.prepareForAssignment(this.resource, newResource);
    }

    @Override
    public List<Action> getActions() {
        return this.actions;
    }

    @Override
    public Evidence getEvidence() {
        return this.evidence;
    }

    @Override
    public void setEvidence(Evidence newEvidence) {
        this.evidence = this.prepareForAssignment(this.evidence, newEvidence);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        children.addAll(this.actions);
        if (this.evidence != null) {
            children.add(this.evidence);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

