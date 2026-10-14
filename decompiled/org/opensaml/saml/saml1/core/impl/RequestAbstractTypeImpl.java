/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.opensaml.saml.saml1.core.RespondWith;

public abstract class RequestAbstractTypeImpl
extends AbstractSignableSAMLObject
implements RequestAbstractType {
    private String id;
    private DateTime issueInstant;
    private SAMLVersion version = SAMLVersion.VERSION_11;
    private final XMLObjectChildrenList<RespondWith> respondWiths = new XMLObjectChildrenList(this);

    protected RequestAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getID() {
        return this.id;
    }

    @Override
    public void setID(String newID) {
        String oldID = this.id;
        this.id = this.prepareForAssignment(this.id, newID);
        this.registerOwnID(oldID, this.id);
    }

    @Override
    public SAMLVersion getVersion() {
        return this.version;
    }

    @Override
    public void setVersion(SAMLVersion newVersion) {
        this.version = this.prepareForAssignment(this.version, newVersion);
    }

    @Override
    public DateTime getIssueInstant() {
        return this.issueInstant;
    }

    @Override
    public void setIssueInstant(DateTime instant) {
        this.issueInstant = this.prepareForAssignment(this.issueInstant, instant);
    }

    @Override
    public List<RespondWith> getRespondWiths() {
        return this.respondWiths;
    }

    @Override
    public String getSignatureReferenceID() {
        return this.id;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.respondWiths);
        if (this.getSignature() != null) {
            children.add(this.getSignature());
        }
        return Collections.unmodifiableList(children);
    }
}

