/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.core.xml.schema.XSBooleanValue;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.opensaml.saml.saml2.metadata.ServiceDescription;
import org.opensaml.saml.saml2.metadata.ServiceName;
import org.w3c.dom.Attr;

public class AttributeConsumingServiceUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        AttributeConsumingService service2 = (AttributeConsumingService)parentSAMLObject;
        if (childSAMLObject instanceof ServiceName) {
            service2.getNames().add((ServiceName)childSAMLObject);
        } else if (childSAMLObject instanceof ServiceDescription) {
            service2.getDescriptions().add((ServiceDescription)childSAMLObject);
        } else if (childSAMLObject instanceof RequestedAttribute) {
            service2.getRequestAttributes().add((RequestedAttribute)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        AttributeConsumingService service2 = (AttributeConsumingService)samlObject;
        if (attribute.getNamespaceURI() == null) {
            if (attribute.getLocalName().equals("index")) {
                service2.setIndex(Integer.valueOf(attribute.getValue()));
            } else if (attribute.getLocalName().equals("isDefault")) {
                service2.setIsDefault(XSBooleanValue.valueOf(attribute.getValue()));
            } else {
                super.processAttribute(samlObject, attribute);
            }
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}

