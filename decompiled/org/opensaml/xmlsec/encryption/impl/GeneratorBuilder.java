/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.Generator;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.GeneratorImpl;

public class GeneratorBuilder
extends AbstractXMLObjectBuilder<Generator>
implements XMLEncryptionBuilder<Generator> {
    @Override
    public Generator buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new GeneratorImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Generator buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "Generator", "xenc");
    }
}

