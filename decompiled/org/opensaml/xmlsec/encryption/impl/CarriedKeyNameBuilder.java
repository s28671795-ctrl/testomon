/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.CarriedKeyName;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.CarriedKeyNameImpl;

public class CarriedKeyNameBuilder
extends AbstractXMLObjectBuilder<CarriedKeyName>
implements XMLEncryptionBuilder<CarriedKeyName> {
    @Override
    public CarriedKeyName buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new CarriedKeyNameImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public CarriedKeyName buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "CarriedKeyName", "xenc");
    }
}

