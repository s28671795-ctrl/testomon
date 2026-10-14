/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.common.SAMLObject;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;

public class EntitiesDescriptorImpl
extends AbstractSignableSAMLObject
implements EntitiesDescriptor {
    private String name;
    private String id;
    private DateTime validUntil;
    private Long cacheDuration;
    private Extensions extensions;
    private final IndexedXMLObjectChildrenList<SAMLObject> orderedDescriptors = new IndexedXMLObjectChildrenList(this);

    protected EntitiesDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
    public void setValidUntil(DateTime newValidUntil) {
        this.validUntil = this.prepareForAssignment(this.validUntil, newValidUntil);
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
    public void setExtensions(Extensions newExtensions) {
        this.extensions = this.prepareForAssignment(this.extensions, newExtensions);
    }

    @Override
    public List<EntitiesDescriptor> getEntitiesDescriptors() {
        return this.orderedDescriptors.subList(EntitiesDescriptor.ELEMENT_QNAME);
    }

    @Override
    public List<EntityDescriptor> getEntityDescriptors() {
        return this.orderedDescriptors.subList(EntityDescriptor.ELEMENT_QNAME);
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
        children.addAll(this.orderedDescriptors);
        return Collections.unmodifiableList(children);
    }
}

