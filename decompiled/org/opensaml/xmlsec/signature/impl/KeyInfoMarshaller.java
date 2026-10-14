/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureMarshaller;
import org.w3c.dom.Element;

public class KeyInfoMarshaller
extends AbstractXMLSignatureMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        KeyInfo keyInfo = (KeyInfo)xmlObject;
        if (keyInfo.getID() != null) {
            domElement.setAttributeNS(null, "Id", keyInfo.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
    }
}

