/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.xmlsec.encryption.DataReference;
import org.opensaml.xmlsec.encryption.KeyReference;
import org.opensaml.xmlsec.encryption.ReferenceList;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionUnmarshaller;

public class ReferenceListUnmarshaller
extends AbstractXMLEncryptionUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentXMLObject, XMLObject childXMLObject) throws UnmarshallingException {
        ReferenceList rl = (ReferenceList)parentXMLObject;
        if (childXMLObject instanceof DataReference) {
            rl.getReferences().add((DataReference)childXMLObject);
        } else if (childXMLObject instanceof KeyReference) {
            rl.getReferences().add((KeyReference)childXMLObject);
        } else {
            super.processChildElement(parentXMLObject, childXMLObject);
        }
    }
}

