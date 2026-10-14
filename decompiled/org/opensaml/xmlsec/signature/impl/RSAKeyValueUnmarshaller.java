/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.Exponent;
import org.opensaml.xmlsec.signature.Modulus;
import org.opensaml.xmlsec.signature.RSAKeyValue;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureUnmarshaller;

public class RSAKeyValueUnmarshaller
extends AbstractXMLSignatureUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        RSAKeyValue keyValue = (RSAKeyValue)parentXMLObject;
        if (childXMLObject instanceof Modulus) {
            keyValue.setModulus((Modulus)childXMLObject);
        } else if (childXMLObject instanceof Exponent) {
            keyValue.setExponent((Exponent)childXMLObject);
        } else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}

