/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.KeyInfo;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureUnmarshaller;
import org.w3c.dom.Attr;

public class KeyInfoUnmarshaller
extends AbstractXMLSignatureUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        KeyInfo keyInfo = (KeyInfo)xmlObject;
        if (attribute.getLocalName().equals("Id")) {
            keyInfo.setID(attribute.getValue());
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }

    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        KeyInfo keyInfo = (KeyInfo)parentXMLObject;
        keyInfo.getXMLObjects().add(childXMLObject);
    }
}

