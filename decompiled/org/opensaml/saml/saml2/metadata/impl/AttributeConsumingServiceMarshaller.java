/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.w3c.dom.Element;

public class AttributeConsumingServiceMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        AttributeConsumingService service2 = (AttributeConsumingService)samlObject;
        domElement.setAttributeNS(null, "index", Integer.toString(service2.getIndex()));
        if (service2.isDefaultXSBoolean() != null) {
            domElement.setAttributeNS(null, "isDefault", service2.isDefaultXSBoolean().toString());
        }
    }
}

