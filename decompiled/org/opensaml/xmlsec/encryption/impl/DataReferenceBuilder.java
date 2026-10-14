/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.DataReferenceImpl;

public class DataReferenceBuilder
extends AbstractXMLObjectBuilder<DataReference>
implements XMLEncryptionBuilder<DataReference> {
    @Override
    public DataReference buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new DataReferenceImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public DataReference buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "DataReference", "xenc");
    }
}

