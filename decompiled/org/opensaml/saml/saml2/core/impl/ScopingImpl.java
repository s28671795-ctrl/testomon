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
import org.opensaml.saml.saml2.core.IDPList;
import org.opensaml.saml.saml2.core.RequesterID;
import org.opensaml.saml.saml2.core.Scoping;

public class ScopingImpl
extends AbstractSAMLObject
implements Scoping {
    private IDPList idpList;
    private final XMLObjectChildrenList<RequesterID> requesterIDs = new XMLObjectChildrenList(this);
    private Integer proxyCount;

    protected ScopingImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Integer getProxyCount() {
        return this.proxyCount;
    }

    @Override
    public void setProxyCount(Integer newProxyCount) {
        this.proxyCount = this.prepareForAssignment(this.proxyCount, newProxyCount);
    }

    @Override
    public IDPList getIDPList() {
        return this.idpList;
    }

    @Override
    public void setIDPList(IDPList newIDPList) {
        this.idpList = this.prepareForAssignment(this.idpList, newIDPList);
    }

    @Override
    public List<RequesterID> getRequesterIDs() {
        return this.requesterIDs;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<SAMLObject> children = new ArrayList<SAMLObject>();
        if (this.idpList != null) {
            children.add(this.idpList);
        }
        children.addAll(this.requesterIDs);
        if (children.size() > 0) {
            return Collections.unmodifiableList(children);
        }
        return null;
    }
}

