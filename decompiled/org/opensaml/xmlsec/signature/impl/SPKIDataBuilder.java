/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.SPKIData;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.SPKIDataImpl;

public class SPKIDataBuilder
extends AbstractXMLObjectBuilder<SPKIData>
implements XMLSignatureBuilder<SPKIData> {
    @Override
    public SPKIData buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SPKIDataImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public SPKIData buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "SPKIData", "ds");
    }
}

