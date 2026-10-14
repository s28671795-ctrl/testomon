/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.ECKeyValueImpl;

public class ECKeyValueBuilder
extends AbstractXMLObjectBuilder<ECKeyValue>
implements XMLSignatureBuilder<ECKeyValue> {
    @Override
    public ECKeyValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ECKeyValueImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public ECKeyValue buildObject() {
        return this.buildObject("http://www.w3.org/2009/xmldsig11#", "ECKeyValue", "ds11");
    }
}

