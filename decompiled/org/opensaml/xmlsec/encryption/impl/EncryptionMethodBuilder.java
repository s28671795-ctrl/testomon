/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.EncryptionMethodImpl;

public class EncryptionMethodBuilder
extends AbstractXMLObjectBuilder<EncryptionMethod>
implements XMLEncryptionBuilder<EncryptionMethod> {
    @Override
    public EncryptionMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptionMethodImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public EncryptionMethod buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionMethod", "xenc");
    }
}

