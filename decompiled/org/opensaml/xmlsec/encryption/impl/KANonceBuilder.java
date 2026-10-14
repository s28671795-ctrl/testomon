/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.KANonce;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.KANonceImpl;

public class KANonceBuilder
extends AbstractXMLObjectBuilder<KANonce>
implements XMLEncryptionBuilder<KANonce> {
    @Override
    public KANonce buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KANonceImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public KANonce buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "KA-Nonce", "xenc");
    }
}

