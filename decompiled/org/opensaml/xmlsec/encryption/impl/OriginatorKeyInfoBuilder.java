/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.OriginatorKeyInfo;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.OriginatorKeyInfoImpl;

public class OriginatorKeyInfoBuilder
extends AbstractXMLObjectBuilder<OriginatorKeyInfo>
implements XMLEncryptionBuilder<OriginatorKeyInfo> {
    @Override
    public OriginatorKeyInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new OriginatorKeyInfoImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public OriginatorKeyInfo buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "OriginatorKeyInfo", "xenc");
    }
}

