/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.ReferenceType;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionMarshaller;
import org.w3c.dom.Element;

public class ReferenceTypeMarshaller
extends AbstractXMLEncryptionMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        ReferenceType rt = (ReferenceType)xmlObject;
        if (rt.getURI() != null) {
            domElement.setAttributeNS(null, "URI", rt.getURI());
        } else {
            super.marshallAttributes(xmlObject, domElement);
        }
    }
}

