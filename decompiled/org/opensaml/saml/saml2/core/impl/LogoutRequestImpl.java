/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.NameID;
import org.opensaml.saml.saml2.core.SessionIndex;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeImpl;

public class LogoutRequestImpl
extends RequestAbstractTypeImpl
implements LogoutRequest {
    private String reason;
    private DateTime notOnOrAfter;
    private BaseID baseID;
    private NameID nameID;
    private EncryptedID encryptedID;
    private final XMLObjectChildrenList<SessionIndex> sessionIndexes = new XMLObjectChildrenList(this);

    protected LogoutRequestImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getReason() {
        return this.reason;
    }

    @Override
    public void setReason(String newReason) {
        this.reason = this.prepareForAssignment(this.reason, newReason);
    }

    @Override
    public DateTime getNotOnOrAfter() {
        return this.notOnOrAfter;
    }

    @Override
    public void setNotOnOrAfter(DateTime newNotOnOrAfter) {
        this.notOnOrAfter = this.prepareForAssignment(this.notOnOrAfter, newNotOnOrAfter);
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
    public List<SessionIndex> getSessionIndexes() {
        return this.sessionIndexes;
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
        children.addAll(this.sessionIndexes);
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

