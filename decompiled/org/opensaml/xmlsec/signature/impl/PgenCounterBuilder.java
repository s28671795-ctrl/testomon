/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.PgenCounter;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.PgenCounterImpl;

public class PgenCounterBuilder
extends AbstractXMLObjectBuilder<PgenCounter>
implements XMLSignatureBuilder<PgenCounter> {
    @Override
    public PgenCounter buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PgenCounterImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public PgenCounter buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "PgenCounter", "ds");
    }
}

