/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.X509Data;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureUnmarshaller;

public class X509DataUnmarshaller
extends AbstractXMLSignatureUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        X509Data x509Data = (X509Data)parentXMLObject;
        x509Data.getXMLObjects().add(childXMLObject);
    }
}

