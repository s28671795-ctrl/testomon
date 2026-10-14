/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.Action;
import org.opensaml.saml.saml1.core.AuthorizationDecisionStatement;
import org.opensaml.saml.saml1.core.DecisionTypeEnumeration;
import org.opensaml.saml.saml1.core.Evidence;
import org.opensaml.saml.saml1.core.impl.SubjectStatementUnmarshaller;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;

public class AuthorizationDecisionStatementUnmarshaller
extends SubjectStatementUnmarshaller {
    private final Logger log = LoggerFactory.getLogger(AuthorizationDecisionStatementUnmarshaller.class);

    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement)parentSAMLObject;
        if (childSAMLObject instanceof Action) {
            authorizationDecisionStatement.getActions().add((Action)childSAMLObject);
        } else if (childSAMLObject instanceof Evidence) {
            authorizationDecisionStatement.setEvidence((Evidence)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        AuthorizationDecisionStatement authorizationDecisionStatement = (AuthorizationDecisionStatement)samlObject;
        if (attribute.getNamespaceURI() != null) {
            super.processAttribute(samlObject, attribute);
            return;
        }
        if ("Decision".equals(attribute.getLocalName())) {
            String value = attribute.getValue();
            if (value.equals(DecisionTypeEnumeration.PERMIT.toString())) {
                authorizationDecisionStatement.setDecision(DecisionTypeEnumeration.PERMIT);
                return;
            }
            if (value.equals(DecisionTypeEnumeration.DENY.toString())) {
                authorizationDecisionStatement.setDecision(DecisionTypeEnumeration.DENY);
                return;
            }
            if (value.equals(DecisionTypeEnumeration.INDETERMINATE.toString())) {
                authorizationDecisionStatement.setDecision(DecisionTypeEnumeration.INDETERMINATE);
                return;
            }
            this.log.error("Unknown value for DecisionType '" + value + "'");
            throw new UnmarshallingException("Unknown value for DecisionType '" + value + "'");
        }
        if ("Resource".equals(attribute.getLocalName())) {
            authorizationDecisionStatement.setResource(attribute.getValue());
            return;
        }
        super.processAttribute(samlObject, attribute);
    }
}

