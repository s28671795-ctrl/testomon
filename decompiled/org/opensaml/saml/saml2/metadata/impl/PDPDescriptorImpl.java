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
import org.opensaml.saml.saml2.metadata.AssertionIDRequestService;
import org.opensaml.saml.saml2.metadata.AuthzService;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorImpl;

public class PDPDescriptorImpl
extends RoleDescriptorImpl
implements PDPDescriptor {
    private final XMLObjectChildrenList<AuthzService> authzServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<AssertionIDRequestService> assertionIDRequestServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<NameIDFormat> nameIDFormats = new XMLObjectChildrenList(this);

    protected PDPDescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<AuthzService> getAuthzServices() {
        return this.authzServices;
    }

    @Override
    public List<AssertionIDRequestService> getAssertionIDRequestServices() {
        return this.assertionIDRequestServices;
    }

    @Override
    public List<NameIDFormat> getNameIDFormats() {
        return this.nameIDFormats;
    }

    @Override
    public List<Endpoint> getEndpoints() {
        ArrayList<Endpoint> endpoints = new ArrayList<Endpoint>();
        endpoints.addAll(this.authzServices);
        endpoints.addAll(this.assertionIDRequestServices);
        return Collections.unmodifiableList(endpoints);
    }

    @Override
    public List<Endpoint> getEndpoints(QName type) {
        if (type.equals(AuthzService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<AuthzService>(this.authzServices));
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
        children.addAll(this.authzServices);
        children.addAll(this.assertionIDRequestServices);
        children.addAll(this.nameIDFormats);
        return children;
    }
}

