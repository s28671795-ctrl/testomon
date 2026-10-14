/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptedData;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.EncryptedDataImpl;

public class EncryptedDataBuilder
extends AbstractXMLObjectBuilder<EncryptedData>
implements XMLEncryptionBuilder<EncryptedData> {
    @Override
    public EncryptedData buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptedData", "xenc");
    }

    @Override
    public EncryptedData buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptedDataImpl(namespaceURI, localName, namespacePrefix);
    }
}

