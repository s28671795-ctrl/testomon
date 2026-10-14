/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.KeyName;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.KeyNameImpl;

public class KeyNameBuilder
extends AbstractXMLObjectBuilder<KeyName>
implements XMLSignatureBuilder<KeyName> {
    @Override
    public KeyName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KeyNameImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public KeyName buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyName", "ds");
    }
}

