/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.KeyInfoReference;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureMarshaller;
import org.w3c.dom.Element;

public class KeyInfoReferenceMarshaller
extends AbstractXMLSignatureMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        KeyInfoReference ref = (KeyInfoReference)xmlObject;
        if (ref.getID() != null) {
            domElement.setAttributeNS(null, "Id", ref.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
        if (ref.getURI() != null) {
            domElement.setAttributeNS(null, "URI", ref.getURI());
        }
    }
}

