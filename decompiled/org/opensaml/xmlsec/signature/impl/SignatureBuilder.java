/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.impl.SignatureImpl;

public class SignatureBuilder
extends AbstractXMLObjectBuilder<SignatureImpl> {
    public SignatureImpl buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Signature", "ds");
    }

    @Override
    public SignatureImpl buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SignatureImpl(namespaceURI, localName, namespacePrefix);
    }
}

