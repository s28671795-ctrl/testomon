/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.OAEPparams;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.OAEPparamsImpl;

public class OAEPparamsBuilder
extends AbstractXMLObjectBuilder<OAEPparams>
implements XMLEncryptionBuilder<OAEPparams> {
    @Override
    public OAEPparams buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new OAEPparamsImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public OAEPparams buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "OAEPparams", "xenc");
    }
}

