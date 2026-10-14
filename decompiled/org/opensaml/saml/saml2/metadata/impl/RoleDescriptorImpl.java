/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import net.shibboleth.utilities.java.support.collection.LazyList;
import net.shibboleth.utilities.java.support.primitive.StringSupport;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;

public abstract class RoleDescriptorImpl
extends AbstractSignableSAMLObject
implements RoleDescriptor {
    private String id;
    private DateTime validUntil;
    private Long cacheDuration;
    private final List<String> supportedProtocols;
    private String errorURL;
    private Extensions extensions;
    private Organization organization;
    private final AttributeMap unknownAttributes = new AttributeMap(this);
    private final XMLObjectChildrenList<ContactPerson> contactPersons;
    private final XMLObjectChildrenList<KeyDescriptor> keyDescriptors;

    protected RoleDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.supportedProtocols = new LazyList<String>();
        this.contactPersons = new XMLObjectChildrenList(this);
        this.keyDescriptors = new XMLObjectChildrenList(this);
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
    public void setValidUntil(DateTime dt) {
        this.validUntil = this.prepareForAssignment(this.validUntil, dt);
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
    public List<String> getSupportedProtocols() {
        return Collections.unmodifiableList(this.supportedProtocols);
    }

    @Override
    public boolean isSupportedProtocol(String protocol) {
        return this.supportedProtocols.contains(protocol);
    }

    @Override
    public void addSupportedProtocol(String protocol) {
        String trimmed = StringSupport.trimOrNull(protocol);
        if (trimmed != null && !this.supportedProtocols.contains(trimmed)) {
            this.releaseThisandParentDOM();
            this.supportedProtocols.add(trimmed);
        }
    }

    @Override
    public void removeSupportedProtocol(String protocol) {
        String trimmed = StringSupport.trimOrNull(protocol);
        if (trimmed != null && this.supportedProtocols.contains(trimmed)) {
            this.releaseThisandParentDOM();
            this.supportedProtocols.remove(trimmed);
        }
    }

    @Override
    public void removeSupportedProtocols(Collection<String> protocols) {
        for (String protocol : protocols) {
            this.removeSupportedProtocol(protocol);
        }
    }

    @Override
    public void removeAllSupportedProtocols() {
        this.supportedProtocols.clear();
    }

    @Override
    public String getErrorURL() {
        return this.errorURL;
    }

    @Override
    public void setErrorURL(String url2) {
        this.errorURL = this.prepareForAssignment(this.errorURL, url2);
    }

    @Override
    public Extensions getExtensions() {
        return this.extensions;
    }

    @Override
    public void setExtensions(Extensions ext) {
        this.extensions = this.prepareForAssignment(this.extensions, ext);
    }

    @Override
    public Organization getOrganization() {
        return this.organization;
    }

    @Override
    public void setOrganization(Organization org) {
        this.organization = this.prepareForAssignment(this.organization, org);
    }

    @Override
    public List<ContactPerson> getContactPersons() {
        return this.contactPersons;
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
        if (this.extensions != null) {
            children.add(this.getExtensions());
        }
        children.addAll(this.getKeyDescriptors());
        if (this.organization != null) {
            children.add(this.getOrganization());
        }
        children.addAll(this.getContactPersons());
        return Collections.unmodifiableList(children);
    }
}

