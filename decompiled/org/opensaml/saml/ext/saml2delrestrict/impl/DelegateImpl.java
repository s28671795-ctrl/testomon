/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2delrestrict.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.ext.saml2delrestrict.Delegate;
import org.opensaml.saml.saml2.core.BaseID;
import org.opensaml.saml.saml2.core.EncryptedID;
import org.opensaml.saml.saml2.core.NameID;

public class DelegateImpl
extends AbstractSAMLObject
implements Delegate {
    private BaseID baseID;
    private NameID nameID;
    private EncryptedID encryptedID;
    private DateTime delegationInstant;
    private String confirmationMethod;

    protected DelegateImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public BaseID getBaseID() {
        return this.baseID;
    }

    @Override
    public String getConfirmationMethod() {
        return this.confirmationMethod;
    }

    @Override
    public DateTime getDelegationInstant() {
        return this.delegationInstant;
    }

    @Override
    public EncryptedID getEncryptedID() {
        return this.encryptedID;
    }

    @Override
    public NameID getNameID() {
        return this.nameID;
    }

    @Override
    public void setBaseID(BaseID newBaseID) {
        this.baseID = this.prepareForAssignment(this.baseID, newBaseID);
    }

    @Override
    public void setConfirmationMethod(String newMethod) {
        this.confirmationMethod = this.prepareForAssignment(this.confirmationMethod, newMethod);
    }

    @Override
    public void setDelegationInstant(DateTime newInstant) {
        this.delegationInstant = this.prepareForAssignment(this.delegationInstant, newInstant);
    }

    @Override
    public void setEncryptedID(EncryptedID newEncryptedID) {
        this.encryptedID = this.prepareForAssignment(this.encryptedID, newEncryptedID);
    }

    @Override
    public void setNameID(NameID newNameID) {
        this.nameID = this.prepareForAssignment(this.nameID, newNameID);
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
        return Collections.unmodifiableList(children);
    }
}

