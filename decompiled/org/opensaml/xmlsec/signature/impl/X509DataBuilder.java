/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.X509DataImpl;

public class X509DataBuilder
extends AbstractXMLObjectBuilder<X509Data>
implements XMLSignatureBuilder<X509Data> {
    @Override
    public X509Data buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new X509DataImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public X509Data buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509Data", "ds");
    }
}

