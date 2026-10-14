/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CipherData;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.CipherDataImpl;

public class CipherDataBuilder
extends AbstractXMLObjectBuilder<CipherData>
implements XMLEncryptionBuilder<CipherData> {
    @Override
    public CipherData buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CipherDataImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public CipherData buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherData", "xenc");
    }
}

