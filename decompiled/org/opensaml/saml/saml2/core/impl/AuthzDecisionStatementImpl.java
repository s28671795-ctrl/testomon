/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.Action;
import org.opensaml.saml.saml2.core.AuthzDecisionStatement;
import org.opensaml.saml.saml2.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml2.core.Evidence;

public class AuthzDecisionStatementImpl
extends AbstractSAMLObject
implements AuthzDecisionStatement {
    private String resource;
    private DecisionTypeEnumeration decision;
    private final XMLObjectChildrenList<Action> actions = new XMLObjectChildrenList(this);
    private Evidence evidence;

    protected AuthzDecisionStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getResource() {
        return this.resource;
    }

    @Override
    public void setResource(String newResourceURI) {
        this.resource = this.prepareForAssignment(this.resource, newResourceURI, false);
    }

    @Override
    public DecisionTypeEnumeration getDecision() {
        return this.decision;
    }

    @Override
    public void setDecision(DecisionTypeEnumeration newDecision) {
        this.decision = this.prepareForAssignment(this.decision, newDecision);
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
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.addAll(this.actions);
        children.add(this.evidence);
        return Collections.unmodifiableList(children);
    }
}

