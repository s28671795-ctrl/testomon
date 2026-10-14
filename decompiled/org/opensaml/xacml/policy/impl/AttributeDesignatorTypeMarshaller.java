/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.xacml.policy.impl;

import com.google.common.base.Strings;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.xacml.impl.AbstractXACMLObjectMarshaller;
import org.opensaml.xacml.policy.AttributeDesignatorType;
import org.w3c.dom.Element;

public class AttributeDesignatorTypeMarshaller
extends AbstractXACMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        AttributeDesignatorType attributeDesignatorType = (AttributeDesignatorType)xmlObject;
        if (!Strings.isNullOrEmpty(attributeDesignatorType.getAttributeId())) {
            domElement.setAttributeNS(null, "AttributeId", attributeDesignatorType.getAttributeId());
        }
        if (!Strings.isNullOrEmpty(attributeDesignatorType.getDataType())) {
            domElement.setAttributeNS(null, "DataType", attributeDesignatorType.getDataType());
        }
        if (!Strings.isNullOrEmpty(attributeDesignatorType.getIssuer())) {
            domElement.setAttributeNS(null, "Issuer", attributeDesignatorType.getIssuer());
        }
        if (attributeDesignatorType.getMustBePresentXSBoolean() != null) {
            domElement.setAttributeNS(null, "MustBePresent", Boolean.toString(attributeDesignatorType.getMustBePresentXSBoolean().getValue()));
        }
    }
}

