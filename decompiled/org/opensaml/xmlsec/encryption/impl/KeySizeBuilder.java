/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.KeySize;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.KeySizeImpl;

public class KeySizeBuilder
extends AbstractXMLObjectBuilder<KeySize>
implements XMLEncryptionBuilder<KeySize> {
    @Override
    public KeySize buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KeySizeImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public KeySize buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KeySize", "xenc");
    }
}

