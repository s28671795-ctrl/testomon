/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.XPath;
import org.opensaml.xmlsec.signature.impl.XPathImpl;

public class XPathBuilder
extends AbstractXMLObjectBuilder<XPath>
implements XMLSignatureBuilder<XPath> {
    @Override
    public XPath buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new XPathImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public XPath buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "XPath", "ds");
    }
}

