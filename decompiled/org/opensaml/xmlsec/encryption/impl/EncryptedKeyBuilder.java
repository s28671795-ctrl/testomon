/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.EncryptedKeyImpl;

public class EncryptedKeyBuilder
extends AbstractXMLObjectBuilder<EncryptedKey>
implements XMLEncryptionBuilder<EncryptedKey> {
    @Override
    public EncryptedKey buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptedKey", "xenc");
    }

    @Override
    public EncryptedKey buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptedKeyImpl(namespaceURI, localName, namespacePrefix);
    }
}

