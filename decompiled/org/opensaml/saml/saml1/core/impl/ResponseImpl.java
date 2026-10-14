/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.Response;
import org.opensaml.saml.saml1.core.Status;
import org.opensaml.saml.saml1.core.impl.ResponseAbstractTypeImpl;

public class ResponseImpl
extends ResponseAbstractTypeImpl
implements Response {
    private Status status;
    private final XMLObjectChildrenList<Assertion> assertions = new XMLObjectChildrenList(this);

    protected ResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<Assertion> getAssertions() {
        return this.assertions;
    }

    @Override
    public Status getStatus() {
        return this.status;
    }

    @Override
    public void setStatus(Status s2) {
        this.status = this.prepareForAssignment(this.status, s2);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>(1 + this.assertions.size());
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.status != null) {
            children.add(this.status);
        }
        children.addAll(this.assertions);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

