/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Q;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.QImpl;

public class QBuilder
extends AbstractXMLObjectBuilder<Q>
implements XMLEncryptionBuilder<Q> {
    @Override
    public Q buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new QImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Q buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Q", "xenc");
    }
}

