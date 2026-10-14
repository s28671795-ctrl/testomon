/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.CipherReferenceImpl;

public class CipherReferenceBuilder
extends AbstractXMLObjectBuilder<CipherReference>
implements XMLEncryptionBuilder<CipherReference> {
    @Override
    public CipherReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CipherReferenceImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public CipherReference buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CipherReference", "xenc");
    }
}

