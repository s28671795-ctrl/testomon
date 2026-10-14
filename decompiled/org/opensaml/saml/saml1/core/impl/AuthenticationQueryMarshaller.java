/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.opensaml.saml.saml1.core.impl.SubjectQueryMarshaller;
import org.w3c.dom.Element;

public class AuthenticationQueryMarshaller
extends SubjectQueryMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        AuthenticationQuery authenticationQuery = (AuthenticationQuery)samlObject;
        if (authenticationQuery.getAuthenticationMethod() != null) {
            domElement.setAttributeNS(null, "AuthenticationMethod", authenticationQuery.getAuthenticationMethod());
        }
    }
}

