/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.PgenCounter;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.PgenCounterImpl;

public class PgenCounterBuilder
extends AbstractXMLObjectBuilder<PgenCounter>
implements XMLEncryptionBuilder<PgenCounter> {
    @Override
    public PgenCounter buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PgenCounterImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public PgenCounter buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "pgenCounter", "xenc");
    }
}

