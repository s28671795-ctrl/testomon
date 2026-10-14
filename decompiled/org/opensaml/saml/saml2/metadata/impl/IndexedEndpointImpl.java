/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.metadata.IndexedEndpoint;
import org.opensaml.saml.saml2.metadata.impl.EndpointImpl;

public abstract class IndexedEndpointImpl
extends EndpointImpl
implements IndexedEndpoint {
    private Integer index;
    private XSBooleanValue isDefault;

    protected IndexedEndpointImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Integer getIndex() {
        return this.index;
    }

    @Override
    public void setIndex(Integer theIndex) {
        this.index = this.prepareForAssignment(this.index, theIndex);
    }

    @Override
    public Boolean isDefault() {
        if (this.isDefault == null) {
            return Boolean.FALSE;
        }
        return this.isDefault.getValue();
    }

    @Override
    public XSBooleanValue isDefaultXSBoolean() {
        return this.isDefault;
    }

    @Override
    public void setIsDefault(Boolean newIsDefault) {
        this.isDefault = newIsDefault != null ? this.prepareForAssignment(this.isDefault, new XSBooleanValue(newIsDefault, false)) : (XSBooleanValue)this.prepareForAssignment(this.isDefault, null);
    }

    @Override
    public void setIsDefault(XSBooleanValue theIsDefault) {
        this.isDefault = this.prepareForAssignment(this.isDefault, theIsDefault);
    }
}

