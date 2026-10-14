/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.G;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.GImpl;

public class GBuilder
extends AbstractXMLObjectBuilder<G>
implements XMLSignatureBuilder<G> {
    @Override
    public G buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new GImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public G buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "G", "ds");
    }
}

