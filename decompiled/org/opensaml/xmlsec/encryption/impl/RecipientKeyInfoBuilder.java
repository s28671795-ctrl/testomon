/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.RecipientKeyInfo;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.RecipientKeyInfoImpl;

public class RecipientKeyInfoBuilder
extends AbstractXMLObjectBuilder<RecipientKeyInfo>
implements XMLEncryptionBuilder<RecipientKeyInfo> {
    @Override
    public RecipientKeyInfo buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new RecipientKeyInfoImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public RecipientKeyInfo buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "RecipientKeyInfo", "xenc");
    }
}

