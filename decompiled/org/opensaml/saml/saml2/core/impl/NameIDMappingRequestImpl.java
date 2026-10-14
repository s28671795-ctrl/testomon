/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml.saml2.core.NameIDPolicy;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;

public class NameIDMappingRequestImpl
extends RequestAbstractTypeImpl
implements NameIDMappingRequest {
    private BaseID baseID;
    private NameID nameID;
    private EncryptedID encryptedID;
    private NameIDPolicy nameIDPolicy;

    protected NameIDMappingRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public NameIDPolicy getNameIDPolicy() {
        return this.nameIDPolicy;
    }

    @Override
    public void setNameIDPolicy(NameIDPolicy newNameIDPolicy) {
        this.nameIDPolicy = this.prepareForAssignment(this.nameIDPolicy, newNameIDPolicy);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.baseID != null) {
            children.add(this.baseID);
        }
        if (this.nameID != null) {
            children.add(this.nameID);
        }
        if (this.encryptedID != null) {
            children.add(this.encryptedID);
        }
        if (this.nameIDPolicy != null) {
            children.add(this.nameIDPolicy);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

