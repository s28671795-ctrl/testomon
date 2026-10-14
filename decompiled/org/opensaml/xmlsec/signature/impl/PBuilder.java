/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.P;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.PImpl;

public class PBuilder
extends AbstractXMLObjectBuilder<P>
implements XMLSignatureBuilder<P> {
    @Override
    public P buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public P buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "P", "ds");
    }
}

