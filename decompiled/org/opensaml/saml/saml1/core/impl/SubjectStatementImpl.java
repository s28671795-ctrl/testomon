/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectStatement;

public abstract class SubjectStatementImpl
extends AbstractSAMLObject
implements SubjectStatement {
    private Subject subject;

    protected SubjectStatementImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Subject getSubject() {
        return this.subject;
    }

    @Override
    public void setSubject(Subject sub) {
        this.subject = this.prepareForAssignment(this.subject, sub);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        if (this.subject == null) {
            return null;
        }
        ArrayList<Subject> children = new ArrayList<Subject>();
        children.add(this.subject);
        return Collections.unmodifiableList(children);
    }
}

