/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.AbstractXMLObjectBuilder;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.XMLSignatureBuilder;
import org.opensaml.xmlsec.signature.impl.TransformImpl;

public class TransformBuilder
extends AbstractXMLObjectBuilder<Transform>
implements XMLSignatureBuilder<Transform> {
    @Override
    public Transform buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new TransformImpl(namespaceURI, localName, namespacePrefix);
    }

    @Override
    public Transform buildObject() {
        return this.buildObject("http://www.w3.org/2000/09/xmldsig#", "Transform", "ds");
    }
}

