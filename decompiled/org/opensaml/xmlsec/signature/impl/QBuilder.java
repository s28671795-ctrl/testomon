/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Q;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.QImpl;

public class QBuilder
extends AbstractXMLObjectBuilder<Q>
implements XMLSignatureBuilder<Q> {
    @Override
    public Q buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new QImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Q buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Q", "ds");
    }
}

