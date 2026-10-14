/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.signature.ECKeyValue;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureMarshaller;
import org.w3c.dom.Element;

public class ECKeyValueMarshaller
extends AbstractXMLSignatureMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        ECKeyValue ec = (ECKeyValue)xmlObject;
        if (ec.getID() != null) {
            domElement.setAttributeNS(null, "Id", ec.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
    }
}

