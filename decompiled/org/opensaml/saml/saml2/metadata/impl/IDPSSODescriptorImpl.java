/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AttributeProfile;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.NameIDMappingService;
import org.opensaml.saml.saml2.metadata.SingleSignOnService;
import org.opensaml.saml.saml2.metadata.impl.SSODescriptorImpl;

public class IDPSSODescriptorImpl
extends SSODescriptorImpl
implements IDPSSODescriptor {
    private XSBooleanValue wantAuthnRequestsSigned;
    private final XMLObjectChildrenList<SingleSignOnService> singleSignOnServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<NameIDMappingService> nameIDMappingServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AssertionIDRequestService> assertionIDRequestServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AttributeProfile> attributeProfiles = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<Attribute> attributes = new XMLObjectChildrenList(this);

    protected IDPSSODescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Boolean getWantAuthnRequestsSigned() {
        if (this.wantAuthnRequestsSigned != null) {
            return this.wantAuthnRequestsSigned.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue getWantAuthnRequestsSignedXSBoolean() {
        return this.wantAuthnRequestsSigned;
    }

    @Override
    public void setWantAuthnRequestsSigned(Boolean newWantSigned) {
        this.wantAuthnRequestsSigned = newWantSigned != null ? this.prepareForAssignment(this.wantAuthnRequestsSigned, new XSBooleanValue(newWantSigned, false)) : (XSBooleanValue)this.prepareForAssignment(this.wantAuthnRequestsSigned, null);
    }

    @Override
    public void setWantAuthnRequestsSigned(XSBooleanValue wantSigned) {
        this.wantAuthnRequestsSigned = this.prepareForAssignment(this.wantAuthnRequestsSigned, wantSigned);
    }

    @Override
    public List<SingleSignOnService> getSingleSignOnServices() {
        return this.singleSignOnServices;
    }

    @Override
    public List<NameIDMappingService> getNameIDMappingServices() {
        return this.nameIDMappingServices;
    }

    @Override
    public List<AssertionIDRequestService> getAssertionIDRequestServices() {
        return this.assertionIDRequestServices;
    }

    @Override
    public List<AttributeProfile> getAttributeProfiles() {
        return this.attributeProfiles;
    }

    @Override
    public List<Attribute> getAttributes() {
        return this.attributes;
    }

    @Override
    public List<Endpoint> getEndpoints() {
        ArrayList<Endpoint> endpoints = new ArrayList<Endpoint>();
        endpoints.addAll(super.getEndpoints());
        endpoints.addAll(this.singleSignOnServices);
        endpoints.addAll(this.nameIDMappingServices);
        endpoints.addAll(this.assertionIDRequestServices);
        return Collections.unmodifiableList(endpoints);
    }

    @Override
    public List<Endpoint> getEndpoints(QName type) {
        if (type.equals(SingleSignOnService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<SingleSignOnService>(this.singleSignOnServices));
        }
        if (type.equals(NameIDMappingService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<NameIDMappingService>(this.nameIDMappingServices));
        }
        if (type.equals(AssertionIDRequestService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<AssertionIDRequestService>(this.assertionIDRequestServices));
        }
        return super.getEndpoints(type);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(super.getOrderedChildren());
        children.addAll(this.singleSignOnServices);
        children.addAll(this.nameIDMappingServices);
        children.addAll(this.assertionIDRequestServices);
        children.addAll(this.attributeProfiles);
        children.addAll(this.attributes);
        return Collections.unmodifiableList(children);
    }
}

