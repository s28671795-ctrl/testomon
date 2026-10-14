/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.impl.SubjectStatementMarshaller;
import org.w3c.dom.Element;

public class AuthorizationDecisionStatementMarshaller
extends SubjectStatementMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement)samlElement;
        if (authorizationDecisionStatement.getResource() != null) {
            domElement.setAttributeNS(null, "Resource", authorizationDecisionStatement.getResource());
        }
        if (authorizationDecisionStatement.getDecision() != null) {
            domElement.setAttributeNS(null, "Decision", authorizationDecisionStatement.getDecision().toString());
        }
    }
}

