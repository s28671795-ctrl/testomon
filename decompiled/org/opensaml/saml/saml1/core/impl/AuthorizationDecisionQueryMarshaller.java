/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml1.core.AuthorizationDecisionQuery;
import org.opensaml.saml.saml1.core.impl.SubjectQueryMarshaller;
import org.w3c.dom.Element;

public class AuthorizationDecisionQueryMarshaller
extends SubjectQueryMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        AuthorizationDecisionQuery authorizationDecisionQuery = (AuthorizationDecisionQuery)samlElement;
        if (authorizationDecisionQuery.getResource() != null) {
            domElement.setAttributeNS(null, "Resource", authorizationDecisionQuery.getResource());
        }
    }
}

