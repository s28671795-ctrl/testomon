/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.AgreementMethod;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionMarshaller;
import org.w3c.dom.Element;

public class AgreementMethodMarshaller
extends AbstractXMLEncryptionMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        AgreementMethod am = (AgreementMethod)xmlObject;
        if (am.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", am.getAlgorithm());
        }
    }
}

