/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.NamedCurve;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureUnmarshaller;
import org.w3c.dom.Attr;

public class NamedCurveUnmarshaller
extends AbstractXMLSignatureUnmarshaller {
    @Override
    protected void processAttribute(XMLObject xmlObject, Attr attribute) throws UnmarshallingException {
        NamedCurve nc = (NamedCurve)xmlObject;
        if (attribute.getLocalName().equals("URI")) {
            nc.setURI(attribute.getValue());
        }
    }
}

