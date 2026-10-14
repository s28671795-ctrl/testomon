/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PGPKeyID;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.PGPKeyIDImpl;

public class PGPKeyIDBuilder
extends AbstractXMLObjectBuilder<PGPKeyID>
implements XMLSignatureBuilder<PGPKeyID> {
    @Override
    public PGPKeyID buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PGPKeyIDImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public PGPKeyID buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPKeyID", "ds");
    }
}

