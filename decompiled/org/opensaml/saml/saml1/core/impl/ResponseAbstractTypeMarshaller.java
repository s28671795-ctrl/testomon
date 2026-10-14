/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.common.SAMLVersion;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.core.ResponseAbstractType;
import org.w3c.dom.Element;

public abstract class ResponseAbstractTypeMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        ResponseAbstractType response = (ResponseAbstractType)samlElement;
        if (response.getID() != null) {
            domElement.setAttributeNS(null, "ResponseID", response.getID());
            if (response.getVersion() != SAMLVersion.VERSION_10) {
                domElement.setIdAttributeNS(null, "ResponseID", true);
            }
        }
        if (response.getInResponseTo() != null) {
            domElement.setAttributeNS(null, "InResponseTo", response.getInResponseTo());
        }
        if (response.getIssueInstant() != null) {
            String date = SAMLConfigurationSupport.getSAMLDateFormatter().print(response.getIssueInstant());
            domElement.setAttributeNS(null, "IssueInstant", date);
        }
        domElement.setAttributeNS(null, "MajorVersion", Integer.toString(response.getVersion().getMajorVersion()));
        domElement.setAttributeNS(null, "MinorVersion", Integer.toString(response.getVersion().getMinorVersion()));
        if (response.getRecipient() != null) {
            domElement.setAttributeNS(null, "Recipient", response.getRecipient());
        }
    }
}

