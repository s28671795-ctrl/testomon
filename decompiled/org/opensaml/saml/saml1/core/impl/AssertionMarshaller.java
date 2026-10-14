/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.joda.time.format.ISODateTimeFormat;
import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml1.core.Assertion;
import org.w3c.dom.Element;

public class AssertionMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        Assertion assertion = (Assertion)samlElement;
        if (assertion.getID() != null) {
            domElement.setAttributeNS(null, "AssertionID", assertion.getID());
            if (assertion.getMinorVersion() != 0) {
                domElement.setIdAttributeNS(null, "AssertionID", true);
            }
        }
        if (assertion.getIssuer() != null) {
            domElement.setAttributeNS(null, "Issuer", assertion.getIssuer());
        }
        if (assertion.getIssueInstant() != null) {
            String date = ISODateTimeFormat.dateTime().print(assertion.getIssueInstant());
            domElement.setAttributeNS(null, "IssueInstant", date);
        }
        domElement.setAttributeNS(null, "MajorVersion", "1");
        if (assertion.getMinorVersion() == 0) {
            domElement.setAttributeNS(null, "MinorVersion", "0");
        } else {
            domElement.setAttributeNS(null, "MinorVersion", "1");
        }
    }
}

