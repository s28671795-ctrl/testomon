/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml.schema.impl;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.AbstractXMLObjectMarshaller;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.schema.XSAny;
import org.w3c.dom.Attr;
import org.w3c.dom.Element;

public class XSAnyMarshaller
extends AbstractXMLObjectMarshaller {
    @Override
    protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
        XSAny xsAny = (XSAny)xmlObject;
        for (Map.Entry<QName, String> entry : xsAny.getUnknownAttributes().entrySet()) {
            Attr attribute = AttributeSupport.constructAttribute(domElement.getOwnerDocument(), entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (!XMLObjectProviderRegistrySupport.isIDAttribute(entry.getKey()) && !xsAny.getUnknownAttributes().isIDAttribute(entry.getKey())) continue;
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
    }

    @Override
    protected void marshallElementContent(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
        XSAny xsAny = (XSAny)xmlObject;
        if (xsAny.getTextContent() != null) {
            ElementSupport.appendTextContent(domElement, xsAny.getTextContent());
        }
    }
}

