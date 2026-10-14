/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeMarshaller;
import org.w3c.dom.Element;

public class AuthnRequestMarshaller
extends RequestAbstractTypeMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        AuthnRequest req = (AuthnRequest)samlObject;
        if (req.isForceAuthnXSBoolean() != null) {
            domElement.setAttributeNS(null, "ForceAuthn", req.isForceAuthnXSBoolean().toString());
        }
        if (req.isPassiveXSBoolean() != null) {
            domElement.setAttributeNS(null, "IsPassive", req.isPassiveXSBoolean().toString());
        }
        if (req.getProtocolBinding() != null) {
            domElement.setAttributeNS(null, "ProtocolBinding", req.getProtocolBinding());
        }
        if (req.getAssertionConsumerServiceIndex() != null) {
            domElement.setAttributeNS(null, "AssertionConsumerServiceIndex", req.getAssertionConsumerServiceIndex().toString());
        }
        if (req.getAssertionConsumerServiceURL() != null) {
            domElement.setAttributeNS(null, "AssertionConsumerServiceURL", req.getAssertionConsumerServiceURL());
        }
        if (req.getAttributeConsumingServiceIndex() != null) {
            domElement.setAttributeNS(null, "AttributeConsumingServiceIndex", req.getAttributeConsumingServiceIndex().toString());
        }
        if (req.getProviderName() != null) {
            domElement.setAttributeNS(null, "ProviderName", req.getProviderName());
        }
        super.marshallAttributes(samlObject, domElement);
    }
}

