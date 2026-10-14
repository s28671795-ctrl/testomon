/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptionProperties;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionMarshaller;
import org.w3c.dom.Element;

public class EncryptionPropertiesMarshaller
extends AbstractXMLEncryptionMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        EncryptionProperties ep = (EncryptionProperties)xmlObject;
        if (ep.getID() != null) {
            domElement.setAttributeNS(null, "Id", ep.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
    }
}

