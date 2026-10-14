/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import java.util.List;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObject;
import org.opensaml.saml.saml1.core.SubjectLocality;

public class SubjectLocalityImpl
extends AbstractSAMLObject
implements SubjectLocality {
    private String ipAddress;
    private String dnsAddress;

    protected SubjectLocalityImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public String getIPAddress() {
        return this.ipAddress;
    }

    @Override
    public void setIPAddress(String address) {
        this.ipAddress = this.prepareForAssignment(this.ipAddress, address);
    }

    @Override
    public String getDNSAddress() {
        return this.dnsAddress;
    }

    @Override
    public void setDNSAddress(String address) {
        this.dnsAddress = this.prepareForAssignment(this.dnsAddress, address);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

