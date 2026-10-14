/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptedType;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionMarshaller;
import org.w3c.dom.Element;

public abstract class EncryptedTypeMarshaller
extends AbstractXMLEncryptionMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        EncryptedType et = (EncryptedType)xmlObject;
        if (et.getID() != null) {
            domElement.setAttributeNS(null, "Id", et.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
        if (et.getType() != null) {
            domElement.setAttributeNS(null, "Type", et.getType());
        }
        if (et.getMimeType() != null) {
            domElement.setAttributeNS(null, "MimeType", et.getMimeType());
        }
        if (et.getEncoding() != null) {
            domElement.setAttributeNS(null, "Encoding", et.getEncoding());
        }
    }
}

