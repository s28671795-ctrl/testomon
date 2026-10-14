/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml1.core.NameIdentifier;
import org.opensaml.saml.saml1.core.Subject;
import org.opensaml.saml.saml1.core.SubjectConfirmation;

public class SubjectImpl
extends AbstractSAMLObject
implements Subject {
    private NameIdentifier nameIdentifier;
    private SubjectConfirmation subjectConfirmation;

    protected SubjectImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public NameIdentifier getNameIdentifier() {
        return this.nameIdentifier;
    }

    @Override
    public void setNameIdentifier(NameIdentifier name) {
        this.nameIdentifier = this.prepareForAssignment(this.nameIdentifier, name);
    }

    @Override
    public SubjectConfirmation getSubjectConfirmation() {
        return this.subjectConfirmation;
    }

    @Override
    public void setSubjectConfirmation(SubjectConfirmation conf) {
        this.subjectConfirmation = this.prepareForAssignment(this.subjectConfirmation, conf);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> list = new ArrayList<SAMLObject>(2);
        if (this.nameIdentifier != null) {
            list.add(this.nameIdentifier);
        }
        if (this.subjectConfirmation != null) {
            list.add(this.subjectConfirmation);
        }
        if (list.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(list);
    }
}

