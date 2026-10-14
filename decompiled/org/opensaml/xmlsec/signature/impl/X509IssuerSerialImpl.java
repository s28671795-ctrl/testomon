/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.X509IssuerName;
import org.opensaml.xmlsec.signature.X509IssuerSerial;
import org.opensaml.xmlsec.signature.X509SerialNumber;

public class X509IssuerSerialImpl
extends AbstractXMLObject
implements X509IssuerSerial {
    private X509IssuerName issuerName;
    private X509SerialNumber serialNumber;

    protected X509IssuerSerialImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public X509IssuerName getX509IssuerName() {
        return this.issuerName;
    }

    @Override
    public void setX509IssuerName(X509IssuerName newX509IssuerName) {
        this.issuerName = this.prepareForAssignment(this.issuerName, newX509IssuerName);
    }

    @Override
    public X509SerialNumber getX509SerialNumber() {
        return this.serialNumber;
    }

    @Override
    public void setX509SerialNumber(X509SerialNumber newX509SerialNumber) {
        this.serialNumber = this.prepareForAssignment(this.serialNumber, newX509SerialNumber);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        ArrayList<XMLObject> children = new ArrayList<XMLObject>();
        if (this.issuerName != null) {
            children.add(this.issuerName);
        }
        if (this.serialNumber != null) {
            children.add(this.serialNumber);
        }
        if (children.size() == 0) {
            return null;
        }
        return Collections.unmodifiableList(children);
    }
}

