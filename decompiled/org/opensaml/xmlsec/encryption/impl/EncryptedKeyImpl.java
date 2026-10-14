/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.encryption.CarriedKeyName;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.ReferenceList;
import org.opensaml.xmlsec.encryption.impl.EncryptedTypeImpl;

public class EncryptedKeyImpl
extends EncryptedTypeImpl
implements EncryptedKey {
    private String recipient;
    private CarriedKeyName carriedKeyName;
    private ReferenceList referenceList;

    protected EncryptedKeyImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getRecipient() {
        return this.recipient;
    }

    @Override
    public void setRecipient(String newRecipient) {
        this.recipient = this.prepareForAssignment(this.recipient, newRecipient);
    }

    @Override
    public ReferenceList getReferenceList() {
        return this.referenceList;
    }

    @Override
    public void setReferenceList(ReferenceList newReferenceList) {
        this.referenceList = this.prepareForAssignment(this.referenceList, newReferenceList);
    }

    @Override
    public CarriedKeyName getCarriedKeyName() {
        return this.carriedKeyName;
    }

    @Override
    public void setCarriedKeyName(CarriedKeyName newCarriedKeyName) {
        this.carriedKeyName = this.prepareForAssignment(this.carriedKeyName, newCarriedKeyName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (super.getOrderedChildren() != null) {
            children.addAll(super.getOrderedChildren());
        }
        if (this.referenceList != null) {
            children.add(this.referenceList);
        }
        if (this.carriedKeyName != null) {
            children.add(this.carriedKeyName);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

