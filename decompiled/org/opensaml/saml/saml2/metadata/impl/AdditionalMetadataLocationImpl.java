/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;

public class AdditionalMetadataLocationImpl
extends AbstractSAMLObject
implements AdditionalMetadataLocation {
    private String location;
    private String namespace;

    protected AdditionalMetadataLocationImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getLocationURI() {
        return this.location;
    }

    @Override
    public void setLocationURI(String locationURI) {
        this.location = this.prepareForAssignment(this.location, locationURI);
    }

    @Override
    public String getNamespaceURI() {
        return this.namespace;
    }

    @Override
    public void setNamespaceURI(String namespaceURI) {
        this.namespace = this.prepareForAssignment(this.namespace, namespaceURI);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

