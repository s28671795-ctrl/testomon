/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.xml.namespace.QName;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.saml2.core.Attribute;
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.AttributeProfile;
import org.opensaml.saml.saml2.metadata.AttributeService;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorImpl;

public class AttributeAuthorityDescriptorImpl
extends RoleDescriptorImpl
implements AttributeAuthorityDescriptor {
    private final XMLObjectChildrenList<AttributeService> attributeServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AssertionIDRequestService> assertionIDRequestServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<NameIDFormat> nameFormats;
    private final XMLObjectChildrenList<AttributeProfile> attributeProfiles = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<Attribute> attributes;

    protected AttributeAuthorityDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
        this.nameFormats = new XMLObjectChildrenList(this);
        this.attributes = new XMLObjectChildrenList(this);
    }

    @Override
    public List<AttributeService> getAttributeServices() {
        return this.attributeServices;
    }

    @Override
    public List<AssertionIDRequestService> getAssertionIDRequestServices() {
        return this.assertionIDRequestServices;
    }

    @Override
    public List<NameIDFormat> getNameIDFormats() {
        return this.nameFormats;
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
        endpoints.addAll(this.attributeServices);
        endpoints.addAll(this.assertionIDRequestServices);
        return Collections.unmodifiableList(endpoints);
    }

    @Override
    public List<Endpoint> getEndpoints(QName type) {
        if (type.equals(AttributeService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<AttributeService>(this.attributeServices));
        }
        if (type.equals(AssertionIDRequestService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<AssertionIDRequestService>(this.assertionIDRequestServices));
        }
        return null;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(super.getOrderedChildren());
        children.addAll(this.attributeServices);
        children.addAll(this.assertionIDRequestServices);
        children.addAll(this.nameFormats);
        children.addAll(this.attributeProfiles);
        children.addAll(this.attributes);
        return Collections.unmodifiableList(children);
    }
}

