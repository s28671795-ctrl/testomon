/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptedKey;
import org.opensaml.xmlsec.encryption.impl.EncryptedTypeMarshaller;
import org.w3c.dom.Element;

public class EncryptedKeyMarshaller
extends EncryptedTypeMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        EncryptedKey ek = (EncryptedKey)xmlObject;
        if (ek.getRecipient() != null) {
            domElement.setAttributeNS(null, "Recipient", ek.getRecipient());
        }
        super.marshallAttributes(xmlObject, domElement);
    }
}

