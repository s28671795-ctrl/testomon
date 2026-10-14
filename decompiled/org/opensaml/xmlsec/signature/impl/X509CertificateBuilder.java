/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509Certificate;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.X509CertificateImpl;

public class X509CertificateBuilder
extends AbstractXMLObjectBuilder<X509Certificate>
implements XMLSignatureBuilder<X509Certificate> {
    @Override
    public X509Certificate buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new X509CertificateImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public X509Certificate buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509Certificate", "ds");
    }
}

