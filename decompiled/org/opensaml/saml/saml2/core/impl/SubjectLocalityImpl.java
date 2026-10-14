/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml2.core.SubjectLocality;

public class SubjectLocalityImpl
extends AbstractSAMLObject
implements SubjectLocality {
    private String address;
    private String dnsName;

    protected SubjectLocalityImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getAddress() {
        return this.address;
    }

    @Override
    public void setAddress(String newAddress) {
        this.address = this.prepareForAssignment(this.address, newAddress);
    }

    @Override
    public String getDNSName() {
        return this.dnsName;
    }

    @Override
    public void setDNSName(String newDNSName) {
        this.dnsName = this.prepareForAssignment(this.dnsName, newDNSName);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

