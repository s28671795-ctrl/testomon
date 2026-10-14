/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.RSAKeyValue;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.RSAKeyValueImpl;

public class RSAKeyValueBuilder
extends AbstractXMLObjectBuilder<RSAKeyValue>
implements XMLSignatureBuilder<RSAKeyValue> {
    @Override
    public RSAKeyValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RSAKeyValueImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public RSAKeyValue buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "RSAKeyValue", "ds");
    }
}

