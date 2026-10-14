/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Public;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.PublicImpl;

public class PublicBuilder
extends AbstractXMLObjectBuilder<Public>
implements XMLEncryptionBuilder<Public> {
    @Override
    public Public buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PublicImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Public buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Public", "xenc");
    }
}

