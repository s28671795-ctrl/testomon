/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.EncryptionMethod;
import org.opensaml.xmlsec.encryption.KeySize;
import org.opensaml.xmlsec.encryption.OAEPparams;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionUnmarshaller;
import org.w3c.dom.Attr;

public class EncryptionMethodUnmarshaller
extends AbstractXMLEncryptionUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        EncryptionMethod em = (EncryptionMethod)xmlObject;
        if (attribute.getLocalName().equals("Algorithm")) {
            em.setAlgorithm(attribute.getValue());
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }

    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        EncryptionMethod em = (EncryptionMethod)parentXMLObject;
        if (childXMLObject instanceof KeySize) {
            em.setKeySize((KeySize)childXMLObject);
        } else if (childXMLObject instanceof OAEPparams) {
            em.setOAEPparams((OAEPparams)childXMLObject);
        } else {
            em.getUnknownXMLObjects().add(childXMLObject);
        }
    }
}

