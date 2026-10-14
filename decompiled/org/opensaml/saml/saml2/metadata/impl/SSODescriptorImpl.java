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
import org.opensaml.saml.metadata.support.SAML2MetadataSupport;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.Endpoint;
import org.opensaml.saml.saml2.metadata.ManageNameIDService;
import org.opensaml.saml.saml2.metadata.NameIDFormat;
import org.opensaml.saml.saml2.metadata.SSODescriptor;
import org.opensaml.saml.saml2.metadata.SingleLogoutService;
import org.opensaml.saml.saml2.metadata.impl.RoleDescriptorImpl;

public abstract class SSODescriptorImpl
extends RoleDescriptorImpl
implements SSODescriptor {
    private final XMLObjectChildrenList<ArtifactResolutionService> artifactResolutionServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<SingleLogoutService> singleLogoutServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<ManageNameIDService> manageNameIDServices = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<NameIDFormat> nameIDFormats = new XMLObjectChildrenList(this);

    protected SSODescriptorImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public List<ArtifactResolutionService> getArtifactResolutionServices() {
        return this.artifactResolutionServices;
    }

    @Override
    public ArtifactResolutionService getDefaultArtifactResolutionService() {
        return SAML2MetadataSupport.getDefaultIndexedEndpoint(this.artifactResolutionServices);
    }

    @Override
    public List<SingleLogoutService> getSingleLogoutServices() {
        return this.singleLogoutServices;
    }

    @Override
    public List<ManageNameIDService> getManageNameIDServices() {
        return this.manageNameIDServices;
    }

    @Override
    public List<NameIDFormat> getNameIDFormats() {
        return this.nameIDFormats;
    }

    @Override
    public List<Endpoint> getEndpoints() {
        ArrayList<Endpoint> endpoints = new ArrayList<Endpoint>();
        endpoints.addAll(this.artifactResolutionServices);
        endpoints.addAll(this.singleLogoutServices);
        endpoints.addAll(this.manageNameIDServices);
        return Collections.unmodifiableList(endpoints);
    }

    @Override
    public List<Endpoint> getEndpoints(QName type) {
        if (type.equals(ArtifactResolutionService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<ArtifactResolutionService>(this.artifactResolutionServices));
        }
        if (type.equals(SingleLogoutService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<SingleLogoutService>(this.singleLogoutServices));
        }
        if (type.equals(ManageNameIDService.DEFAULT_ELEMENT_NAME)) {
            return Collections.unmodifiableList(new ArrayList<ManageNameIDService>(this.manageNameIDServices));
        }
        return Collections.EMPTY_LIST;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.addAll(super.getOrderedChildren());
        children.addAll(this.artifactResolutionServices);
        children.addAll(this.singleLogoutServices);
        children.addAll(this.manageNameIDServices);
        children.addAll(this.nameIDFormats);
        return Collections.unmodifiableList(children);
    }
}

