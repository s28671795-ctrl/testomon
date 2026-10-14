/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.AlgorithmIdentifierType;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionMarshaller;
import org.w3c.dom.Element;

public class AlgorithmIdentifierTypeMarshaller
extends AbstractXMLEncryptionMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        AlgorithmIdentifierType algoIdType = (AlgorithmIdentifierType)xmlObject;
        if (algoIdType.getAlgorithm() != null) {
            domElement.setAttributeNS(null, "Algorithm", algoIdType.getAlgorithm());
        }
    }
}

