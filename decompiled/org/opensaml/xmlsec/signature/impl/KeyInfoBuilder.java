/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.KeyInfoImpl;

public class KeyInfoBuilder
extends AbstractXMLObjectBuilder<KeyInfo>
implements XMLSignatureBuilder<KeyInfo> {
    @Override
    public KeyInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KeyInfoImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public KeyInfo buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "KeyInfo", "ds");
    }
}

