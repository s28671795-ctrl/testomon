/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.DEREncodedKeyValue;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.DEREncodedKeyValueImpl;

public class DEREncodedKeyValueBuilder
extends AbstractXMLObjectBuilder<DEREncodedKeyValue>
implements XMLSignatureBuilder<DEREncodedKeyValue> {
    @Override
    public DEREncodedKeyValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DEREncodedKeyValueImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public DEREncodedKeyValue buildObject() {
        return this.buildObject("http://www.w3.org/2009/xmldsig11#", "DEREncodedKeyValue", "ds11");
    }
}

