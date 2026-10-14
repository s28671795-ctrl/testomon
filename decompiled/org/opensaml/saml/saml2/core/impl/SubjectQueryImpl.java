/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectQuery;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;

public abstract class SubjectQueryImpl
extends RequestAbstractTypeImpl
implements SubjectQuery {
    private Subject subject;

    protected SubjectQueryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public void setSubject(Subject newSubject) {
        this.subject = this.prepareForAssignment(this.subject, newSubject);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.subject != null) {
            children.add(this.subject);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

