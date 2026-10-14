/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.KeyReference;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.KeyReferenceImpl;

public class KeyReferenceBuilder
extends AbstractXMLObjectBuilder<KeyReference>
implements XMLEncryptionBuilder<KeyReference> {
    @Override
    public KeyReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KeyReferenceImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public KeyReference buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KeyReference", "xenc");
    }
}

