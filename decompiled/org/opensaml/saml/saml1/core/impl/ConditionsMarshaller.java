/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml1.core.Conditions;
import org.w3c.dom.Element;

public class ConditionsMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlElement, Element domElement) throws MarshallingException {
        String date;
        Conditions conditions = (Conditions)samlElement;
        if (conditions.getNotBefore() != null) {
            date = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotBefore());
            domElement.setAttributeNS(null, "NotBefore", date);
        }
        if (conditions.getNotOnOrAfter() != null) {
            date = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotOnOrAfter());
            domElement.setAttributeNS(null, "NotOnOrAfter", date);
        }
    }
}

