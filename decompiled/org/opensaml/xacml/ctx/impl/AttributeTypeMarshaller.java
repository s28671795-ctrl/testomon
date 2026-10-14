/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.ctx.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.ctx.AttributeType;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.w3c.dom.Element;

public class AttributeTypeMarshaller
extends AbstractXACMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        AttributeType attribute = (AttributeType)samlElement;
        if (attribute.getIssuer() != null) {
            domElement.setAttributeNS(null, "Issuer", attribute.getIssuer());
        }
        if (attribute.getDataType() != null) {
            domElement.setAttributeNS(null, "DataType", attribute.getDataType());
        }
        if (attribute.getAttributeId() != null) {
            domElement.setAttributeNS(null, "AttributeId", attribute.getAttributeId());
        }
    }
}

