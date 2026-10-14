/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.NamedCurveImpl;

public class NamedCurveBuilder
extends AbstractXMLObjectBuilder<NamedCurve>
implements XMLSignatureBuilder<NamedCurve> {
    @Override
    public NamedCurve buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NamedCurveImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public NamedCurve buildObject() {
        return this.buildObject("http://www.w3.org/2009/xmldsig11#", "NamedCurve", "ds11");
    }
}

