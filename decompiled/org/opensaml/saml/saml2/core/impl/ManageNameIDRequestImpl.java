/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.ManageNameIDRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NewEncryptedID;
import org.opensaml.saml.saml2.core.NewID;
import org.opensaml.saml.saml2.core.Terminate;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;

public class ManageNameIDRequestImpl
extends RequestAbstractTypeImpl
implements ManageNameIDRequest {
    private NameID nameID;
    private EncryptedID encryptedID;
    private NewID newID;
    private NewEncryptedID newEncryptedID;
    private Terminate terminate;

    protected ManageNameIDRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
    public void setEncryptedID(EncryptedID newEncID) {
        this.encryptedID = this.prepareForAssignment(this.encryptedID, newEncID);
    }

    @Override
    public NewID getNewID() {
        return this.newID;
    }

    @Override
    public void setNewID(NewID newNewID) {
        this.newID = this.prepareForAssignment(this.newID, newNewID);
    }

    @Override
    public NewEncryptedID getNewEncryptedID() {
        return this.newEncryptedID;
    }

    @Override
    public void setNewEncryptedID(NewEncryptedID newNewEncryptedID) {
        this.newEncryptedID = this.prepareForAssignment(this.newEncryptedID, newNewEncryptedID);
    }

    @Override
    public Terminate getTerminate() {
        return this.terminate;
    }

    @Override
    public void setTerminate(Terminate newTerminate) {
        this.terminate = this.prepareForAssignment(this.terminate, newTerminate);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.nameID != null) {
            children.add(this.nameID);
        }
        if (this.encryptedID != null) {
            children.add(this.encryptedID);
        }
        if (this.newID != null) {
            children.add(this.newID);
        }
        if (this.newEncryptedID != null) {
            children.add(this.newEncryptedID);
        }
        if (this.terminate != null) {
            children.add(this.terminate);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

