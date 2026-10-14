/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509SerialNumber;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.X509SerialNumberImpl;

public class X509SerialNumberBuilder
extends AbstractXMLObjectBuilder<X509SerialNumber>
implements XMLSignatureBuilder<X509SerialNumber> {
    @Override
    public X509SerialNumber buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new X509SerialNumberImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public X509SerialNumber buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SerialNumber", "ds");
    }
}

