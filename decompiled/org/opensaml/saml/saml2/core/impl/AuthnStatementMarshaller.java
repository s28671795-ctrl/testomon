/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.w3c.dom.Element;

public class AuthnStatementMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        AuthnStatement authnStatement = (AuthnStatement)samlObject;
        if (authnStatement.getAuthnInstant() != null) {
            String authnInstantStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(authnStatement.getAuthnInstant());
            domElement.setAttributeNS(null, "AuthnInstant", authnInstantStr);
        }
        if (authnStatement.getSessionIndex() != null) {
            domElement.setAttributeNS(null, "SessionIndex", authnStatement.getSessionIndex());
        }
        if (authnStatement.getSessionNotOnOrAfter() != null) {
            String sessionNotOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(authnStatement.getSessionNotOnOrAfter());
            domElement.setAttributeNS(null, "SessionNotOnOrAfter", sessionNotOnOrAfterStr);
        }
    }
}

