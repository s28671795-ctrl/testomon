/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.RetrievalMethod;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureMarshaller;
import org.w3c.dom.Element;

public class RetrievalMethodMarshaller
extends AbstractXMLSignatureMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        RetrievalMethod rm = (RetrievalMethod)xmlObject;
        if (rm.getURI() != null) {
            domElement.setAttributeNS(null, "URI", rm.getURI());
        }
        if (rm.getType() != null) {
            domElement.setAttributeNS(null, "Type", rm.getType());
        }
    }
}

