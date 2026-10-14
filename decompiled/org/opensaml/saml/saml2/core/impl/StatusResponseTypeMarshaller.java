/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.StatusResponseType;
import org.w3c.dom.Element;

public abstract class StatusResponseTypeMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        StatusResponseType sr = (StatusResponseType)samlObject;
        if (sr.getVersion() != null) {
            domElement.setAttributeNS(null, "Version", sr.getVersion().toString());
        }
        if (sr.getID() != null) {
            domElement.setAttributeNS(null, "ID", sr.getID());
            domElement.setIdAttributeNS(null, "ID", true);
        }
        if (sr.getInResponseTo() != null) {
            domElement.setAttributeNS(null, "InResponseTo", sr.getInResponseTo());
        }
        if (sr.getVersion() != null) {
            domElement.setAttributeNS(null, "Version", sr.getVersion().toString());
        }
        if (sr.getIssueInstant() != null) {
            String iiStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(sr.getIssueInstant());
            domElement.setAttributeNS(null, "IssueInstant", iiStr);
        }
        if (sr.getDestination() != null) {
            domElement.setAttributeNS(null, "Destination", sr.getDestination());
        }
        if (sr.getConsent() != null) {
            domElement.setAttributeNS(null, "Consent", sr.getConsent());
        }
    }
}

