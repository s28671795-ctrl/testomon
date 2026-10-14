/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import com.google.common.base.Strings;
import org.joda.time.Chronology;
import org.joda.time.DateTime;
import org.joda.time.chrono.ISOChronology;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.UnmarshallingException;
import org.opensaml.saml.saml1.core.AuthenticationStatement;
import org.opensaml.saml.saml1.core.AuthorityBinding;
import org.opensaml.saml.saml1.core.SubjectLocality;
import org.opensaml.saml.saml1.core.impl.SubjectStatementUnmarshaller;
import org.w3c.dom.Attr;

public class AuthenticationStatementUnmarshaller
extends SubjectStatementUnmarshaller {
    @Override
    protected void processChildElement(XMLObject parentSAMLObject, XMLObject childSAMLObject) throws UnmarshallingException {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement)parentSAMLObject;
        if (childSAMLObject instanceof SubjectLocality) {
            authenticationStatement.setSubjectLocality((SubjectLocality)childSAMLObject);
        } else if (childSAMLObject instanceof AuthorityBinding) {
            authenticationStatement.getAuthorityBindings().add((AuthorityBinding)childSAMLObject);
        } else {
            super.processChildElement(parentSAMLObject, childSAMLObject);
        }
    }

    @Override
    protected void processAttribute(XMLObject samlObject, Attr attribute) throws UnmarshallingException {
        AuthenticationStatement authenticationStatement = (AuthenticationStatement)samlObject;
        if (attribute.getNamespaceURI() == null) {
            if ("AuthenticationInstant".equals(attribute.getLocalName()) && !Strings.isNullOrEmpty(attribute.getValue())) {
                DateTime value = new DateTime((Object)attribute.getValue(), (Chronology)ISOChronology.getInstanceUTC());
                authenticationStatement.setAuthenticationInstant(value);
            } else if ("AuthenticationMethod".equals(attribute.getLocalName())) {
                authenticationStatement.setAuthenticationMethod(attribute.getValue());
            } else {
                super.processAttribute(samlObject, attribute);
            }
        } else {
            super.processAttribute(samlObject, attribute);
        }
    }
}

