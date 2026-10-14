/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.metadata.EmailAddress;

public class EmailAddressImpl
extends AbstractSAMLObject
implements EmailAddress {
    private String address;

    protected EmailAddressImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(String addr) {
        this.address = this.prepareForAssignment(this.address, addr);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

