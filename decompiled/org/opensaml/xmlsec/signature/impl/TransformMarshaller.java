/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureMarshaller;
import org.w3c.dom.Element;

public class TransformMarshaller
extends AbstractXMLSignatureMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        Transform transform = (Transform)xmlObject;
        if (transform.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", transform.getAlgorithm());
        }
    }
}

