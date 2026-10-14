/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.LogoutRequest;
import org.opensaml.saml.saml2.core.impl.RequestAbstractTypeMarshaller;
import org.w3c.dom.Element;

public class LogoutRequestMarshaller
extends RequestAbstractTypeMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        LogoutRequest req = (LogoutRequest)samlObject;
        if (req.getReason() != null) {
            domElement.setAttributeNS(null, "Reason", req.getReason());
        }
        if (req.getNotOnOrAfter() != null) {
            String noaStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(req.getNotOnOrAfter());
            domElement.setAttributeNS(null, "NotOnOrAfter", noaStr);
        }
        super.marshallAttributes(samlObject, domElement);
    }
}

