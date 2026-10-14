/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.joda.time.DateTime;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.IndexedXMLObjectChildrenList;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSignableSAMLObject;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.ContactPerson;
import org.opensaml.saml.saml2.metadata.EntityDescriptor;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;
import org.opensaml.saml.saml2.metadata.RoleDescriptor;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;

public class EntityDescriptorImpl
extends AbstractSignableSAMLObject
implements EntityDescriptor {
    private String entityID;
    private String id;
    private DateTime validUntil;
    private Long cacheDuration;
    private Extensions extensions;
    private final IndexedXMLObjectChildrenList<RoleDescriptor> roleDescriptors = new IndexedXMLObjectChildrenList(this);
    private AffiliationDescriptor affiliationDescriptor;
    private Organization organization;
    private final XMLObjectChildrenList<ContactPerson> contactPersons = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AdditionalMetadataLocation> additionalMetadata = new XMLObjectChildrenList(this);
    private final AttributeMap unknownAttributes = new AttributeMap(this);

    protected EntityDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getEntityID() {
        return this.entityID;
    }

    @Override
    public void setEntityID(String newId) {
        if (newId != null && newId.length() > 1024) {
            throw new IllegalArgumentException("Entity ID can not exceed 1024 characters in length");
        }
        this.entityID = this.prepareForAssignment(this.entityID, newId);
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
    public List<RoleDescriptor> getRoleDescriptors() {
        return this.roleDescriptors;
    }

    @Override
    public List<RoleDescriptor> getRoleDescriptors(QName typeOrName) {
        return this.roleDescriptors.subList(typeOrName);
    }

    @Override
    public List<RoleDescriptor> getRoleDescriptors(QName type, String supportedProtocol) {
        ArrayList<RoleDescriptor> supportingRoleDescriptors = new ArrayList<RoleDescriptor>();
        for (RoleDescriptor descriptor : this.roleDescriptors.subList(type)) {
            if (!descriptor.isSupportedProtocol(supportedProtocol)) continue;
            supportingRoleDescriptors.add(descriptor);
        }
        return supportingRoleDescriptors;
    }

    @Override
    public IDPSSODescriptor getIDPSSODescriptor(String supportedProtocol) {
        List<RoleDescriptor> descriptors = this.getRoleDescriptors(IDPSSODescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
        if (descriptors.size() > 0) {
            return (IDPSSODescriptor)descriptors.get(0);
        }
        return null;
    }

    @Override
    public SPSSODescriptor getSPSSODescriptor(String supportedProtocol) {
        List<RoleDescriptor> descriptors = this.getRoleDescriptors(SPSSODescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
        if (descriptors.size() > 0) {
            return (SPSSODescriptor)descriptors.get(0);
        }
        return null;
    }

    @Override
    public AuthnAuthorityDescriptor getAuthnAuthorityDescriptor(String supportedProtocol) {
        List<RoleDescriptor> descriptors = this.getRoleDescriptors(AuthnAuthorityDescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
        if (descriptors.size() > 0) {
            return (AuthnAuthorityDescriptor)descriptors.get(0);
        }
        return null;
    }

    @Override
    public AttributeAuthorityDescriptor getAttributeAuthorityDescriptor(String supportedProtocol) {
        List<RoleDescriptor> descriptors = this.getRoleDescriptors(AttributeAuthorityDescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
        if (descriptors.size() > 0) {
            return (AttributeAuthorityDescriptor)descriptors.get(0);
        }
        return null;
    }

    @Override
    public PDPDescriptor getPDPDescriptor(String supportedProtocol) {
        List<RoleDescriptor> descriptors = this.getRoleDescriptors(PDPDescriptor.DEFAULT_ELEMENT_NAME, supportedProtocol);
        if (descriptors.size() > 0) {
            return (PDPDescriptor)descriptors.get(0);
        }
        return null;
    }

    @Override
    public AffiliationDescriptor getAffiliationDescriptor() {
        return this.affiliationDescriptor;
    }

    @Override
    public void setAffiliationDescriptor(AffiliationDescriptor descriptor) {
        this.affiliationDescriptor = this.prepareForAssignment(this.affiliationDescriptor, descriptor);
    }

    @Override
    public Organization getOrganization() {
        return this.organization;
    }

    @Override
    public void setOrganization(Organization newOrganization) {
        this.organization = this.prepareForAssignment(this.organization, newOrganization);
    }

    @Override
    public List<ContactPerson> getContactPersons() {
        return this.contactPersons;
    }

    @Override
    public List<AdditionalMetadataLocation> getAdditionalMetadataLocations() {
        return this.additionalMetadata;
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
        children.addAll(this.roleDescriptors);
        children.add(this.getAffiliationDescriptor());
        children.add(this.getOrganization());
        children.addAll(this.contactPersons);
        children.addAll(this.additionalMetadata);
        return Collections.unmodifiableList(children);
    }
}

