/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.ReferenceList;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.ReferenceListImpl;

public class ReferenceListBuilder
extends AbstractXMLObjectBuilder<ReferenceList>
implements XMLEncryptionBuilder<ReferenceList> {
    @Override
    public ReferenceList buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ReferenceListImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public ReferenceList buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "ReferenceList", "xenc");
    }
}

