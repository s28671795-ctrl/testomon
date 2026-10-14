/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.core.xml;

import java.util.Map;
import javax.annotation.Nonnull;
import javax.xml.namespace.QName;
import net.shibboleth.utilities.java.support.xml.AttributeSupport;
import org.opensaml.core.xml.AbstractElementExtensibleXMLObjectMarshaller;
import org.opensaml.core.xml.AttributeExtensibleXMLObject;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.config.XMLObjectProviderRegistrySupport;
import org.opensaml.core.xml.io.MarshallingException;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

public abstract class AbstractExtensibleXMLObjectMarshaller
extends AbstractElementExtensibleXMLObjectMarshaller {
    @Override
    protected void marshallAttributes(@Nonnull XMLObject xmlObject, @Nonnull Element domElement) throws MarshallingException {
        AttributeExtensibleXMLObject anyAttribute = (AttributeExtensibleXMLObject)xmlObject;
        Document document = domElement.getOwnerDocument();
        for (Map.Entry<QName, String> entry : anyAttribute.getUnknownAttributes().entrySet()) {
            Attr attribute = AttributeSupport.constructAttribute(document, entry.getKey());
            attribute.setValue(entry.getValue());
            domElement.setAttributeNodeNS(attribute);
            if (!XMLObjectProviderRegistrySupport.isIDAttribute(entry.getKey()) && !anyAttribute.getUnknownAttributes().isIDAttribute(entry.getKey())) continue;
            attribute.getOwnerElement().setIdAttributeNode(attribute, true);
        }
    }
}

