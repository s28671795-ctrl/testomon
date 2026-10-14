/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.CipherReference;
import org.opensaml.xmlsec.encryption.Transforms;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionUnmarshaller;
import org.w3c.dom.Attr;

public class CipherReferenceUnmarshaller
extends AbstractXMLEncryptionUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        CipherReference cr = (CipherReference)xmlObject;
        if (attribute.getLocalName().equals("URI")) {
            cr.setURI(attribute.getValue());
        } else {
            super.processAttribute(xmlObject, attribute);
        }
    }

    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        CipherReference cr = (CipherReference)parentXMLObject;
        if (childXMLObject instanceof Transforms) {
            cr.setTransforms((Transforms)childXMLObject);
        } else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}

