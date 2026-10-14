/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.saml2.metadata.AffiliateMember;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;

public class AffiliationDescriptorImpl
extends AbstractSignableSAMLObject
implements AffiliationDescriptor {
    private String ownerID;
    private String id;
    private DateTime validUntil;
    private Long cacheDuration;
    private Extensions extensions;
    private final AttributeMap unknownAttributes = new AttributeMap(this);
    private final XMLObjectChildrenList<AffiliateMember> members = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<KeyDescriptor> keyDescriptors = new XMLObjectChildrenList(this);

    protected AffiliationDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getOwnerID() {
        return this.ownerID;
    }

    @Override
    public void setOwnerID(String newOwnerID) {
        if (newOwnerID != null && newOwnerID.length() > 1024) {
            throw new IllegalArgumentException("Owner ID can not exceed 1024 characters in length");
        }
        this.ownerID = this.prepareForAssignment(this.ownerID, newOwnerID);
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
    public boolean isValid() {
        if (null == this.validUntil) {
            return true;
        }
        DateTime now = new DateTime();
        return now.isBefore(this.validUntil);
    }

    @Override
    public DateTime getValidUntil() {
        return this.validUntil;
    }

    @Override
    public void setValidUntil(DateTime theValidUntil) {
        this.validUntil = this.prepareForAssignment(this.validUntil, theValidUntil);
    }

    @Override
    public Long getCacheDuration() {
        return this.cacheDuration;
    }

    @Override
    public void setCacheDuration(Long duration) {
        this.cacheDuration = this.prepareForAssignment(this.cacheDuration, duration);
    }

    @Override
    public Extensions getExtensions() {
        return this.extensions;
    }

    @Override
    public void setExtensions(Extensions theExtensions) {
        this.extensions = this.prepareForAssignment(this.extensions, theExtensions);
    }

    @Override
    public List<AffiliateMember> getMembers() {
        return this.members;
    }

    @Override
    public List<KeyDescriptor> getKeyDescriptors() {
        return this.keyDescriptors;
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }

    @Override
    public String getSignatureReferenceID() {
        return this.id;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.getSignature() != null) {
            children.add(this.getSignature());
        }
        children.add(this.getExtensions());
        children.addAll(this.getMembers());
        children.addAll(this.getKeyDescriptors());
        return Collections.unmodifiableList(children);
    }
}

