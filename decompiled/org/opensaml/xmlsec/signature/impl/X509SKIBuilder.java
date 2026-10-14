/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509SKI;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.X509SKIImpl;

public class X509SKIBuilder
extends AbstractXMLObjectBuilder<X509SKI>
implements XMLSignatureBuilder<X509SKI> {
    @Override
    public X509SKI buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new X509SKIImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public X509SKI buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509SKI", "ds");
    }
}

