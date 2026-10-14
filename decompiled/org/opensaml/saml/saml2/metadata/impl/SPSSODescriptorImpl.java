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
import org.opensaml.saml.metadata.support.AttributeConsumingServiceSelector;
import org.opensaml.saml.metadata.support.SAML2MetadataSupport;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.impl.SSODescriptorImpl;

public class SPSSODescriptorImpl
extends SSODescriptorImpl
implements SPSSODescriptor {
    private XSBooleanValue authnRequestSigned;
    private XSBooleanValue assertionSigned;
    private final XMLObjectChildrenList<AssertionConsumerService> assertionConsumerServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AttributeConsumingService> attributeConsumingServices = new XMLObjectChildrenList(this);

    protected SPSSODescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Boolean isAuthnRequestsSigned() {
        if (this.authnRequestSigned == null) {
            return Boolean.FALSE;
        }
        return this.authnRequestSigned.getValue();
    }

    @Override
    public XSBooleanValue isAuthnRequestsSignedXSBoolean() {
        return this.authnRequestSigned;
    }

    @Override
    public void setAuthnRequestsSigned(Boolean newIsSigned) {
        this.authnRequestSigned = newIsSigned != null ? this.prepareForAssignment(this.authnRequestSigned, new XSBooleanValue(newIsSigned, false)) : (XSBooleanValue)this.prepareForAssignment(this.authnRequestSigned, null);
    }

    @Override
    public void setAuthnRequestsSigned(XSBooleanValue isSigned) {
        this.authnRequestSigned = this.prepareForAssignment(this.authnRequestSigned, isSigned);
    }

    @Override
    public Boolean getWantAssertionsSigned() {
        if (this.assertionSigned == null) {
            return Boolean.FALSE;
        }
        return this.assertionSigned.getValue();
    }

    @Override
    public XSBooleanValue getWantAssertionsSignedXSBoolean() {
        return this.assertionSigned;
    }

    @Override
    public void setWantAssertionsSigned(Boolean wantAssestionSigned) {
        this.assertionSigned = wantAssestionSigned != null ? this.prepareForAssignment(this.assertionSigned, new XSBooleanValue(wantAssestionSigned, false)) : (XSBooleanValue)this.prepareForAssignment(this.assertionSigned, null);
    }

    @Override
    public void setWantAssertionsSigned(XSBooleanValue wantAssestionSigned) {
        this.assertionSigned = this.prepareForAssignment(this.assertionSigned, wantAssestionSigned);
    }

    @Override
    public List<AssertionConsumerService> getAssertionConsumerServices() {
        return this.assertionConsumerServices;
    }

    @Override
    public AssertionConsumerService getDefaultAssertionConsumerService() {
        return SAML2MetadataSupport.getDefaultIndexedEndpoint(this.assertionConsumerServices);
    }

    @Override
    public List<AttributeConsumingService> getAttributeConsumingServices() {
        return this.attributeConsumingServices;
    }

    @Override
    public AttributeConsumingService getDefaultAttributeConsumingService() {
        AttributeConsumingServiceSelector selector = new AttributeConsumingServiceSelector();
        selector.setRoleDescriptor(this);
        return selector.selectService();
    }

    @Override
    public List<Endpoint> getEndpoints() {
        ArrayList<Endpoint> endpoints = new ArrayList<Endpoint>();
        endpoints.addAll(super.getEndpoints());
        endpoints.addAll(this.assertionConsumerServices);
        return Collections.unmodifiableList(endpoints);
    }

    @Override
    public List<Endpoint> getEndpoints(QName type) {
        if (type.equals(AssertionConsumerService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<AssertionConsumerService>(this.assertionConsumerServices));
        }
        return super.getEndpoints(type);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(super.getOrderedChildren());
        children.addAll(this.assertionConsumerServices);
        children.addAll(this.attributeConsumingServices);
        return Collections.unmodifiableList(children);
    }
}

