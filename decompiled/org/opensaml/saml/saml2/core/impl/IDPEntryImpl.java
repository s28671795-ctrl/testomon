/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.IDPEntry;

public class IDPEntryImpl
extends AbstractSAMLObject
implements IDPEntry {
    private String providerID;
    private String name;
    private String loc;

    protected IDPEntryImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getProviderID() {
        return this.providerID;
    }

    @Override
    public void setProviderID(String newProviderID) {
        this.providerID = this.prepareForAssignment(this.providerID, newProviderID);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public void setName(String newName) {
        this.name = this.prepareForAssignment(this.name, newName);
    }

    @Override
    public String getLoc() {
        return this.loc;
    }

    @Override
    public void setLoc(String newLoc) {
        this.loc = this.prepareForAssignment(this.loc, newLoc);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

