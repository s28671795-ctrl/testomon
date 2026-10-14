/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.impl.SubjectStatementMarshaller;
import org.w3c.dom.Element;

public class AuthenticationStatementMarshaller
extends SubjectStatementMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement)samlElement;
        if (authenticationStatement.getAuthenticationMethod() != null) {
            domElement.setAttributeNS(null, "AuthenticationMethod", authenticationStatement.getAuthenticationMethod());
        }
        if (authenticationStatement.getAuthenticationInstant() != null) {
            String value = SAMLConfigurationSupport.getSAMLDateFormatter().print(authenticationStatement.getAuthenticationInstant());
            domElement.setAttributeNS(null, "AuthenticationInstant", value);
        }
    }
}

