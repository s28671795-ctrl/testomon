/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.opensaml.xmlsec.signature.Signature;

public abstract class ResponseAbstractTypeImpl
extends AbstractSignableSAMLObject
implements ResponseAbstractType {
    private String id;
    private SAMLVersion version = SAMLVersion.VERSION_11;
    private String inResponseTo;
    private DateTime issueInstant;
    private String recipient;

    protected ResponseAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
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
    public String getInResponseTo() {
        return this.inResponseTo;
    }

    @Override
    public void setInResponseTo(String to) {
        this.inResponseTo = this.prepareForAssignment(this.inResponseTo, to);
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
    public void setIssueInstant(DateTime date) {
        this.issueInstant = this.prepareForAssignment(this.issueInstant, date);
    }

    @Override
    public String getRecipient() {
        return this.recipient;
    }

    @Override
    public void setRecipient(String recip) {
        this.recipient = this.prepareForAssignment(this.recipient, recip);
    }

    @Override
    public String getSignatureReferenceID() {
        return this.id;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<Signature> children = new ArrayList<Signature>();
        if (this.getSignature() != null) {
            children.add(this.getSignature());
        }
        return Collections.unmodifiableList(children);
    }
}

