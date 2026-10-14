/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Seed;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.SeedImpl;

public class SeedBuilder
extends AbstractXMLObjectBuilder<Seed>
implements XMLEncryptionBuilder<Seed> {
    @Override
    public Seed buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SeedImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Seed buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "seed", "xenc");
    }
}

