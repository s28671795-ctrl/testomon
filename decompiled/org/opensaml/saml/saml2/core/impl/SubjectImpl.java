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
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.Subject;
import org.opensaml.saml.saml2.core.SubjectConfirmation;

public class SubjectImpl
extends AbstractSAMLObject
implements Subject {
    private BaseID baseID;
    private NameID nameID;
    private EncryptedID encryptedID;
    private final XMLObjectChildrenList<SubjectConfirmation> subjectConfirmations = new XMLObjectChildrenList(this);

    protected SubjectImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public BaseID getBaseID() {
        return this.baseID;
    }

    @Override
    public void setBaseID(BaseID newBaseID) {
        this.baseID = this.prepareForAssignment(this.baseID, newBaseID);
    }

    @Override
    public NameID getNameID() {
        return this.nameID;
    }

    @Override
    public void setNameID(NameID newNameID) {
        this.nameID = this.prepareForAssignment(this.nameID, newNameID);
    }

    @Override
    public EncryptedID getEncryptedID() {
        return this.encryptedID;
    }

    @Override
    public void setEncryptedID(EncryptedID newEncryptedID) {
        this.encryptedID = this.prepareForAssignment(this.encryptedID, newEncryptedID);
    }

    @Override
    public List<SubjectConfirmation> getSubjectConfirmations() {
        return this.subjectConfirmations;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        if (this.baseID != null) {
            children.add(this.baseID);
        }
        if (this.nameID != null) {
            children.add(this.nameID);
        }
        if (this.encryptedID != null) {
            children.add(this.encryptedID);
        }
        children.addAll(this.subjectConfirmations);
        return Collections.unmodifiableList(children);
    }
}

