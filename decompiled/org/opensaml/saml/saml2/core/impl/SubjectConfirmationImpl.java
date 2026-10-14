/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SubjectConfirmation;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;

public class SubjectConfirmationImpl
extends AbstractSAMLObject
implements SubjectConfirmation {
    private BaseID baseID;
    private NameID nameID;
    private EncryptedID encryptedID;
    private SubjectConfirmationData subjectConfirmationData;
    private String method;

    protected SubjectConfirmationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public SubjectConfirmationData getSubjectConfirmationData() {
        return this.subjectConfirmationData;
    }

    @Override
    public void setSubjectConfirmationData(SubjectConfirmationData newSubjectConfirmationData) {
        this.subjectConfirmationData = this.prepareForAssignment(this.subjectConfirmationData, newSubjectConfirmationData);
    }

    @Override
    public String getMethod() {
        return this.method;
    }

    @Override
    public void setMethod(String newMethod) {
        this.method = this.prepareForAssignment(this.method, newMethod);
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
        children.add(this.subjectConfirmationData);
        return Collections.unmodifiableList(children);
    }
}

