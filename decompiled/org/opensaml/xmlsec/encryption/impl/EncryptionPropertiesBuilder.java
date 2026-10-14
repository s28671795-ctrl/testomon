/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.EncryptionPropertiesImpl;

public class EncryptionPropertiesBuilder
extends AbstractXMLObjectBuilder<EncryptionProperties>
implements XMLEncryptionBuilder<EncryptionProperties> {
    @Override
    public EncryptionProperties buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptionPropertiesImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public EncryptionProperties buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "EncryptionProperties", "xenc");
    }
}

