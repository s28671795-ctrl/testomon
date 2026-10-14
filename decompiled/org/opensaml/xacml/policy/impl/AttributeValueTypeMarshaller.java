/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import net.shibboleth.utilities.java.support.xml.ElementSupport;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.AttributeValueType;
import org.w3c.dom.Element;

public class AttributeValueTypeMarshaller
extends AbstractXACMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        AttributeValueType attributeValue = (AttributeValueType)xmlObject;
        if (!Strings.isNullOrEmpty(attributeValue.getDataType())) {
            domElement.setAttributeNS(null, "DataType", attributeValue.getDataType());
        }
        this.marshallUnknownAttributes(attributeValue, domElement);
    }

    @Override
    protected void marshallElementContent(XMLObject xmlObject, Element domElement) throws MarshallingException {
        AttributeValueType attributeValue = (AttributeValueType)xmlObject;
        if (attributeValue.getValue() != null) {
            ElementSupport.appendTextContent(domElement, attributeValue.getValue());
        }
    }
}

