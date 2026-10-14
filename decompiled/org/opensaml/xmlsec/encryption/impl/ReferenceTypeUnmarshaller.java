/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.ReferenceType;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionUnmarshaller;
import org.w3c.dom.Attr;

public class ReferenceTypeUnmarshaller
extends AbstractXMLEncryptionUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        ReferenceType rt = (ReferenceType)xmlObject;
        if (attribute.getLocalName().equals("URI")) {
            rt.setURI(attribute.getValue());
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }

    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        ReferenceType rt = (ReferenceType)parentXMLObject;
        rt.getUnknownXMLObjects().add(childXMLObject);
    }
}

