/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CipherValue;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.CipherValueImpl;

public class CipherValueBuilder
extends AbstractXMLObjectBuilder<CipherValue>
implements XMLEncryptionBuilder<CipherValue> {
    @Override
    public CipherValue buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CipherValueImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public CipherValue buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherValue", "xenc");
    }
}

