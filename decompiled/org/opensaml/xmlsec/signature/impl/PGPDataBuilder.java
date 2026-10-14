/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PGPData;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.PGPDataImpl;

public class PGPDataBuilder
extends AbstractXMLObjectBuilder<PGPData>
implements XMLSignatureBuilder<PGPData> {
    @Override
    public PGPData buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PGPDataImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public PGPData buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PGPData", "ds");
    }
}

