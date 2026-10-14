/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.ext.saml2mdrpi.impl;

import java.util.ArrayList;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.ext.saml2mdrpi.PublicationInfo;
import org.opensaml.saml.ext.saml2mdrpi.UsagePolicy;

public class PublicationInfoImpl
extends AbstractSAMLObject
implements PublicationInfo {
    private XMLObjectChildrenList<UsagePolicy> usagePolicies = new IndexedXMLObjectChildrenList<UsagePolicy>(this);
    private String publisher;
    private DateTime creationInstant;
    private String publicationId;

    protected PublicationInfoImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getPublisher() {
        return this.publisher;
    }

    @Override
    public void setPublisher(String thePublisher) {
        this.publisher = this.prepareForAssignment(this.publisher, thePublisher);
    }

    @Override
    public DateTime getCreationInstant() {
        return this.creationInstant;
    }

    @Override
    public void setCreationInstant(DateTime dateTime) {
        this.creationInstant = this.prepareForAssignment(this.creationInstant, dateTime);
    }

    @Override
    public String getPublicationId() {
        return this.publicationId;
    }

    @Override
    public void setPublicationId(String id) {
        this.publicationId = this.prepareForAssignment(this.publicationId, id);
    }

    @Override
    public List<UsagePolicy> getUsagePolicies() {
        return this.usagePolicies;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(this.usagePolicies);
        return children;
    }
}

