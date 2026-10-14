/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Transforms;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.TransformsImpl;

public class TransformsBuilder
extends AbstractXMLObjectBuilder<Transforms>
implements XMLSignatureBuilder<Transforms> {
    @Override
    public Transforms buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new TransformsImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Transforms buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Transforms", "ds");
    }
}

