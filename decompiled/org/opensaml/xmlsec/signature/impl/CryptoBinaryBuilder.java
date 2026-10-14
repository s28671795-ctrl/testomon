/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.core.xml.XMLObjectBuilder;
import org.opensaml.xmlsec.signature.CryptoBinary;
import org.opensaml.xmlsec.signature.impl.CryptoBinaryImpl;

public class CryptoBinaryBuilder
extends AbstractXMLObjectBuilder<CryptoBinary>
implements XMLObjectBuilder<CryptoBinary> {
    @Override
    public CryptoBinary buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CryptoBinaryImpl(namespaceURI, localName, namespacePrefix);
    }
}

