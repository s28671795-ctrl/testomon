/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingResponse;
import org.opensaml.saml.saml2.core.impl.StatusResponseTypeImpl;

public class NameIDMappingResponseImpl
extends StatusResponseTypeImpl
implements NameIDMappingResponse {
    private NameID nameID;
    private EncryptedID encryptedID;

    protected NameIDMappingResponseImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public void setEncryptedID(EncryptedID newEncryptedID) {
        this.encryptedID = this.prepareForAssignment(this.encryptedID, newEncryptedID);
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
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

