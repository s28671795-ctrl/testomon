/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.signature.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.signature.SPKIData;
import org.opensaml.xmlsec.signature.impl.AbstractXMLSignatureUnmarshaller;

public class SPKIDataUnmarshaller
extends AbstractXMLSignatureUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        SPKIData spkiData = (SPKIData)parentXMLObject;
        spkiData.getXMLObjects().add(childXMLObject);
    }
}

