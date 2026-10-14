/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.saml2.core.impl.AttributeImpl;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;

public class RequestedAttributeImpl
extends AttributeImpl
implements RequestedAttribute {
    private XSBooleanValue isRequired;

    protected RequestedAttributeImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public Boolean isRequired() {
        if (this.isRequired != null) {
            return this.isRequired.getValue();
        }
        return Boolean.FALSE;
    }

    @Override
    public XSBooleanValue isRequiredXSBoolean() {
        return this.isRequired;
    }

    @Override
    public void setIsRequired(Boolean newIsRequired) {
        this.isRequired = newIsRequired != null ? this.prepareForAssignment(this.isRequired, new XSBooleanValue(newIsRequired, false)) : (XSBooleanValue)this.prepareForAssignment(this.isRequired, null);
    }

    @Override
    public void setIsRequired(XSBooleanValue newIsRequired) {
        this.isRequired = this.prepareForAssignment(this.isRequired, newIsRequired);
    }
}

