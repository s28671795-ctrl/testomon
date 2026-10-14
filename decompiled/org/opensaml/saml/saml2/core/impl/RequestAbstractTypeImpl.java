/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.saml2.core.Extensions;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.RequestAbstractType;

public abstract class RequestAbstractTypeImpl
extends AbstractSignableSAMLObject
implements RequestAbstractType {
    private SAMLVersion version = SAMLVersion.VERSION_20;
    private String id;
    private DateTime issueInstant;
    private String destination;
    private String consent;
    private Issuer issuer;
    private Extensions extensions;

    protected RequestAbstractTypeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
    public DateTime getIssueInstant() {
        return this.issueInstant;
    }

    @Override
    public void setIssueInstant(DateTime newIssueInstant) {
        this.issueInstant = this.prepareForAssignment(this.issueInstant, newIssueInstant);
    }

    @Override
    public String getDestination() {
        return this.destination;
    }

    @Override
    public void setDestination(String newDestination) {
        this.destination = this.prepareForAssignment(this.destination, newDestination);
    }

    @Override
    public String getConsent() {
        return this.consent;
    }

    @Override
    public void setConsent(String newConsent) {
        this.consent = this.prepareForAssignment(this.consent, newConsent);
    }

    @Override
    public Issuer getIssuer() {
        return this.issuer;
    }

    @Override
    public void setIssuer(Issuer newIssuer) {
        this.issuer = this.prepareForAssignment(this.issuer, newIssuer);
    }

    @Override
    public Extensions getExtensions() {
        return this.extensions;
    }

    @Override
    public void setExtensions(Extensions newExtensions) {
        this.extensions = this.prepareForAssignment(this.extensions, newExtensions);
    }

    @Override
    public String getSignatureReferenceID() {
        return this.id;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.issuer != null) {
            children.add(this.issuer);
        }
        if (this.getSignature() != null) {
            children.add(this.getSignature());
        }
        if (this.extensions != null) {
            children.add(this.extensions);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

