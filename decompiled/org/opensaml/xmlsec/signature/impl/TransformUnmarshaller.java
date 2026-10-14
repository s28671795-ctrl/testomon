/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.Transform;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureUnmarshaller;
import org.w3c.dom.Attr;

public class TransformUnmarshaller
extends AbstractXMLSignatureUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        Transform transform = (Transform)xmlObject;
        if (attribute.getLocalName().equals("Algorithm")) {
            transform.setAlgorithm(attribute.getValue());
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }

    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        Transform transform = (Transform)parentXMLObject;
        transform.getAllChildren().add(childXMLObject);
    }
}

