/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.XMLEncryptionBuilder;
import org.opensaml.xmlsec.encryption.impl.AgreementMethodImpl;

public class AgreementMethodBuilder
extends AbstractXMLObjectBuilder<AgreementMethod>
implements XMLEncryptionBuilder<AgreementMethod> {
    @Override
    public AgreementMethod buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AgreementMethodImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public AgreementMethod buildObject() {
        return this.buildObject("http://www.w3.org/2001/04/xmlenc#", "AgreementMethod", "xenc");
    }
}

