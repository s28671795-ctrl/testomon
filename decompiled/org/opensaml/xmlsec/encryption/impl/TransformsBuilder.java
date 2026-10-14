/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Transforms;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.TransformsImpl;

public class TransformsBuilder
extends AbstractXMLObjectBuilder<Transforms>
implements XMLEncryptionBuilder<Transforms> {
    @Override
    public Transforms buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new TransformsImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Transforms buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Transforms", "xenc");
    }
}

