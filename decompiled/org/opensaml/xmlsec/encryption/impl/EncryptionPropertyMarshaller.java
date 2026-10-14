/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xmlsec.encryption.impl;

import java.util.Map;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xmlsec.encryption.EncryptionProperty;
import org.opensaml.xmlsec.encryption.impl.AbstractXMLEncryptionMarshaller;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class EncryptionPropertyMarshaller
extends AbstractXMLEncryptionMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        EncryptionProperty ep = (EncryptionProperty)xmlObject;
        if (ep.getID() != null) {
            domElement.setAttributeNS(null, "Id", ep.getID());
            domElement.setIdAttributeNS(null, "Id", true);
        }
        if (ep.getTarget() != null) {
            domElement.setAttributeNS(null, "Target", ep.getTarget());
        }
        for (Map.Entry<QName, String> entry : ep.getUnknownAttributes().entrySet()) {
            Attr attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (!XMLObjectProviderRegistrySupport.isIDAttribute(entry.getKey()) && !ep.getUnknownAttributes().isIDAttribute(entry.getKey())) continue;
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
    }
}

