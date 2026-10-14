/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.Conditions;
import org.w3c.dom.Element;

public class ConditionsMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        Conditions conditions = (Conditions)samlObject;
        if (conditions.getNotBefore() != null) {
            String notBeforeStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotBefore());
            domElement.setAttributeNS(null, "NotBefore", notBeforeStr);
        }
        if (conditions.getNotOnOrAfter() != null) {
            String notOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(conditions.getNotOnOrAfter());
            domElement.setAttributeNS(null, "NotOnOrAfter", notOnOrAfterStr);
        }
    }
}

