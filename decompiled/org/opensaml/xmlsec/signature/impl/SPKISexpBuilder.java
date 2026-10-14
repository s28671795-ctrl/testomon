/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.SPKISexp;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.SPKISexpImpl;

public class SPKISexpBuilder
extends AbstractXMLObjectBuilder<SPKISexp>
implements XMLSignatureBuilder<SPKISexp> {
    @Override
    public SPKISexp buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SPKISexpImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public SPKISexp buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "SPKISexp", "ds");
    }
}

