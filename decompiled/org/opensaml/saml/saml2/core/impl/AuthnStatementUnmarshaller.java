/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import com.google.common.base.Strings;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthnContext;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.SubjectLocality;
import org.w3c.dom.Attr;

public class AuthnStatementUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentObject, XMLObject childObject) throws UnmarshallingException {
        AuthnStatement authnStatement = (AuthnStatement)parentObject;
        if (childObject instanceof SubjectLocality) {
            authnStatement.setSubjectLocality((SubjectLocality)childObject);
        } else if (childObject instanceof AuthnContext) {
            authnStatement.setAuthnContext((AuthnContext)childObject);
        } else {
            super.processChildElement(parentObject, childObject);
        }
    }

    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        AuthnStatement authnStatement = (AuthnStatement)samlObject;
        if (attribute.getNamespaceURI() == null) {
            if (attribute.getLocalName().equals("AuthnInstant") && !Strings.isNullOrEmpty(attribute.getValue())) {
                authnStatement.setAuthnInstant(new DateTime((Object)attribute.getValue(), (Chronology)ISOChronology.getInstanceUTC()));
            } else if (attribute.getLocalName().equals("SessionIndex")) {
                authnStatement.setSessionIndex(attribute.getValue());
            } else if (attribute.getLocalName().equals("SessionNotOnOrAfter") && !Strings.isNullOrEmpty(attribute.getValue())) {
                authnStatement.setSessionNotOnOrAfter(new DateTime((Object)attribute.getValue(), (Chronology)ISOChronology.getInstanceUTC()));
            } else {
                super.processAttribute(samlObject, attribute);
            }
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}

