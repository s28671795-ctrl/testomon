/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptionProperty;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.EncryptionPropertyImpl;

public class EncryptionPropertyBuilder
extends AbstractXMLObjectBuilder<EncryptionProperty>
implements XMLEncryptionBuilder<EncryptionProperty> {
    @Override
    public EncryptionProperty buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptionPropertyImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public EncryptionProperty buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperty", "xenc");
    }
}

