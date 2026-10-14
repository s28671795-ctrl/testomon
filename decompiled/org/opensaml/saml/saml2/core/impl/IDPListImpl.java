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
import org.opensaml.saml.saml2.core.GetComplete;
import org.opensaml.saml.saml2.core.IDPEntry;
import org.opensaml.saml.saml2.core.IDPList;

public class IDPListImpl
extends AbstractSAMLObject
implements IDPList {
    private final XMLObjectChildrenList<IDPEntry> idpEntries = new XMLObjectChildrenList(this);
    private GetComplete getComplete;

    protected IDPListImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<IDPEntry> getIDPEntrys() {
        return this.idpEntries;
    }

    @Override
    public GetComplete getGetComplete() {
        return this.getComplete;
    }

    @Override
    public void setGetComplete(GetComplete newGetComplete) {
        this.getComplete = this.prepareForAssignment(this.getComplete, newGetComplete);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        children.addAll(this.idpEntries);
        children.add(this.getComplete);
        if (children.size() > 0) {
            return Collections.unmodifiableList(children);
        }
        return null;
    }
}

