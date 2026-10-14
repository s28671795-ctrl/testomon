/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.impl.AttributeMarshaller;
import org.opensaml.saml.saml2.metadata.RequestedAttribute;
import org.w3c.dom.Element;

public class RequestedAttributeMarshaller
extends AttributeMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        RequestedAttribute requestedAttribute = (RequestedAttribute)samlObject;
        if (requestedAttribute.isRequiredXSBoolean() != null) {
            domElement.setAttributeNS(null, "isRequired", requestedAttribute.isRequiredXSBoolean().toString());
        }
        super.marshallAttributes(samlObject, domElement);
    }
}

