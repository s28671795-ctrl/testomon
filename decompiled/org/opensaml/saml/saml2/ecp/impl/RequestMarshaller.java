/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.ecp.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.core.xml.util.XMLObjectSupport;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.ecp.Request;
import org.w3c.dom.Element;

public class RequestMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject xmlObject, Element domElement) throws MarshallingException {
        Request request = (Request)xmlObject;
        if (request.getProviderName() != null) {
            domElement.setAttributeNS(null, "ProviderName", request.getProviderName());
        }
        if (request.isPassiveXSBoolean() != null) {
            domElement.setAttributeNS(null, "IsPassive", request.isPassiveXSBoolean().toString());
        }
        if (request.isSOAP11MustUnderstandXSBoolean() != null) {
            XMLObjectSupport.marshallAttribute(Request.SOAP11_MUST_UNDERSTAND_ATTR_NAME, request.isSOAP11MustUnderstandXSBoolean().toString(), domElement, false);
        }
        if (request.getSOAP11Actor() != null) {
            XMLObjectSupport.marshallAttribute(Request.SOAP11_ACTOR_ATTR_NAME, request.getSOAP11Actor(), domElement, false);
        }
    }
}

