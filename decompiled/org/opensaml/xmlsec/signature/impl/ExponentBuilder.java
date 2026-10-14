/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Exponent;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.ExponentImpl;

public class ExponentBuilder
extends AbstractXMLObjectBuilder<Exponent>
implements XMLSignatureBuilder<Exponent> {
    @Override
    public Exponent buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ExponentImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Exponent buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Exponent", "ds");
    }
}

