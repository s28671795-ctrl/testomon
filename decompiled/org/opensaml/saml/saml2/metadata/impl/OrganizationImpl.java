/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.ArrayList;
import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.util.AttributeMap;
import org.opensaml.core.xml.util.XMLObjectChildrenList;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.Extensions;
import org.opensaml.saml.saml2.metadata.Organization;
import org.opensaml.saml.saml2.metadata.OrganizationDisplayName;
import org.opensaml.saml.saml2.metadata.OrganizationName;
import org.opensaml.saml.saml2.metadata.OrganizationURL;

public class OrganizationImpl
extends AbstractSAMLObject
implements Organization {
    private Extensions extensions;
    private final XMLObjectChildrenList<OrganizationName> names = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<OrganizationDisplayName> displayNames = new XMLObjectChildrenList(this);
    private final XMLObjectChildrenList<OrganizationURL> urls = new XMLObjectChildrenList(this);
    private final AttributeMap unknownAttributes = new AttributeMap(this);

    protected OrganizationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
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
    public List<OrganizationName> getOrganizationNames() {
        return this.names;
    }

    @Override
    public List<OrganizationDisplayName> getDisplayNames() {
        return this.displayNames;
    }

    @Override
    public List<OrganizationURL> getURLs() {
        return this.urls;
    }

    @Override
    public AttributeMap getUnknownAttributes() {
        return this.unknownAttributes;
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        children.add(this.extensions);
        children.addAll(this.names);
        children.addAll(this.displayNames);
        children.addAll(this.urls);
        return children;
    }
}

