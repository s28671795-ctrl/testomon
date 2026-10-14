/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import java.math.BigInteger;
import java.util.List;
import org.opensaml.core.xml.AbstractXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.xmlsec.signature.X509SerialNumber;

public class X509SerialNumberImpl
extends AbstractXMLObject
implements X509SerialNumber {
    private BigInteger value;

    protected X509SerialNumberImpl(String namespaceURI, String elementLocalName, String namespacePrefix) {
        super(namespaceURI, elementLocalName, namespacePrefix);
    }

    @Override
    public BigInteger getValue() {
        return this.value;
    }

    @Override
    public void setValue(BigInteger newValue) {
        this.value = this.prepareForAssignment(this.value, newValue);
    }

    @Override
    public List<XMLObject> getOrderedChildren() {
        return null;
    }
}

