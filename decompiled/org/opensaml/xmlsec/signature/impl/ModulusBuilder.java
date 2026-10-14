/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Modulus;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.ModulusImpl;

public class ModulusBuilder
extends AbstractXMLObjectBuilder<Modulus>
implements XMLSignatureBuilder<Modulus> {
    @Override
    public Modulus buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ModulusImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Modulus buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Modulus", "ds");
    }
}

