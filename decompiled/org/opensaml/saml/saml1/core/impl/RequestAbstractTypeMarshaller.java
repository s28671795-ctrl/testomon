/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.core.RequestAbstractType;
import org.w3c.dom.Element;

public class RequestAbstractTypeMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        RequestAbstractType request = (RequestAbstractType)samlElement;
        if (request.getID() != null) {
            domElement.setAttributeNS(null, "RequestID", request.getID());
            if (request.getVersion() != SAMLVersion.VERSION_10) {
                domElement.setIdAttributeNS(null, "RequestID", true);
            }
        }
        if (request.getIssueInstant() != null) {
            String date = SAMLConfigurationSupport.getSAMLDateFormatter().print(request.getIssueInstant());
            domElement.setAttributeNS(null, "IssueInstant", date);
        }
        domElement.setAttributeNS(null, "MajorVersion", Integer.toString(request.getVersion().getMajorVersion()));
        domElement.setAttributeNS(null, "MinorVersion", Integer.toString(request.getVersion().getMinorVersion()));
    }
}

