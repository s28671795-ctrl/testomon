/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.AssertionArtifact;
import org.opensaml.saml.saml1.core.AssertionIDReference;
import org.opensaml.saml.saml1.core.AttributeQuery;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.Query;
import org.opensaml.saml.saml1.core.Request;
import org.opensaml.saml.saml1.core.SubjectQuery;
import org.opensaml.saml.saml1.core.impl.RequestAbstractTypeImpl;

public class RequestImpl
extends RequestAbstractTypeImpl
implements Request {
    private Query query;
    private final XMLObjectChildrenList<AssertionIDReference> assertionIDReferences = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AssertionArtifact> assertionArtifacts = new XMLObjectChildrenList(this);

    protected RequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Query getQuery() {
        return this.query;
    }

    @Override
    public SubjectQuery getSubjectQuery() {
        return this.query instanceof SubjectQuery ? (SubjectQuery)this.query : null;
    }

    @Override
    public AttributeQuery getAttributeQuery() {
        return this.query instanceof AttributeQuery ? (AttributeQuery)this.query : null;
    }

    @Override
    public AuthenticationQuery getAuthenticationQuery() {
        return this.query instanceof AuthenticationQuery ? (AuthenticationQuery)this.query : null;
    }

    @Override
    public AuthorizationDecisionQuery getAuthorizationDecisionQuery() {
        return this.query instanceof AuthorizationDecisionQuery ? (AuthorizationDecisionQuery)this.query : null;
    }

    @Override
    public void setQuery(Query q) {
        this.query = this.prepareForAssignment(this.query, q);
    }

    @Override
    public List<AssertionIDReference> getAssertionIDReferences() {
        return this.assertionIDReferences;
    }

    @Override
    public List<AssertionArtifact> getAssertionArtifacts() {
        return this.assertionArtifacts;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> list = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            list.addAll(super.getOrderedChildren());
        }
        if (this.query != null) {
            list.add(this.query);
        }
        if (this.assertionIDReferences.size() != 0) {
            list.addAll(this.assertionIDReferences);
        }
        if (this.assertionArtifacts.size() != 0) {
            list.addAll(this.assertionArtifacts);
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}

