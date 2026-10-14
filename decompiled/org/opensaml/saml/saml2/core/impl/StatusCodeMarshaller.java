/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.StatusCode;
import org.w3c.dom.Element;

public class StatusCodeMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        StatusCode statusCode = (StatusCode)samlObject;
        if (statusCode.getValue() != null) {
            domElement.setAttributeNS(null, "Value", statusCode.getValue());
        }
    }
}

