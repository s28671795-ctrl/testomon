/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.X509CRL;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.X509CRLImpl;

public class X509CRLBuilder
extends AbstractXMLObjectBuilder<X509CRL>
implements XMLSignatureBuilder<X509CRL> {
    @Override
    public X509CRL buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new X509CRLImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public X509CRL buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "X509CRL", "ds");
    }
}

