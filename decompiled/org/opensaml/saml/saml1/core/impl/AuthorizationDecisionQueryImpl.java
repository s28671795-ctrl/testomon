/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.Evidence;
import org.opensaml.saml.saml1.core.impl.SubjectQueryImpl;

public class AuthorizationDecisionQueryImpl
extends SubjectQueryImpl
implements AuthorizationDecisionQuery {
    private String resource;
    private final XMLObjectChildrenList<Action> actions;
    private Evidence evidence;

    protected AuthorizationDecisionQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.setElementNamespacePrefix("saml1p");
        this.actions = new XMLObjectChildrenList(this);
    }

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public void setResource(String res) {
        this.resource = this.prepareForAssignment(this.resource, res);
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
    public void setEvidence(Evidence ev) {
        this.evidence = this.prepareForAssignment(this.evidence, ev);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>(this.actions.size() + 2);
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        list.addAll(this.actions);
        if (this.evidence != null) {
            list.add(this.evidence);
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}

