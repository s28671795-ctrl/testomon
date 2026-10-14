/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.P;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.PImpl;

public class PBuilder
extends AbstractXMLObjectBuilder<P>
implements XMLEncryptionBuilder<P> {
    @Override
    public P buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public P buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "P", "xenc");
    }
}

