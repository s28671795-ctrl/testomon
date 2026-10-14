/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.J;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.JImpl;

public class JBuilder
extends AbstractXMLObjectBuilder<J>
implements XMLSignatureBuilder<J> {
    @Override
    public J buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new JImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public J buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "J", "ds");
    }
}

