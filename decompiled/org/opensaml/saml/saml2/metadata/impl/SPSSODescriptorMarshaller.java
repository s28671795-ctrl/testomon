/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.impl.SSODescriptorMarshaller;
import org.w3c.dom.Element;

public class SPSSODescriptorMarshaller
extends SSODescriptorMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        SPSSODescriptor descriptor = (SPSSODescriptor)samlObject;
        if (descriptor.isAuthnRequestsSignedXSBoolean() != null) {
            domElement.setAttributeNS(null, "AuthnRequestsSigned", descriptor.isAuthnRequestsSignedXSBoolean().toString());
        }
        if (descriptor.getWantAssertionsSignedXSBoolean() != null) {
            domElement.setAttributeNS(null, "WantAssertionsSigned", descriptor.getWantAssertionsSignedXSBoolean().toString());
        }
        super.marshallAttributes(samlObject, domElement);
    }
}

