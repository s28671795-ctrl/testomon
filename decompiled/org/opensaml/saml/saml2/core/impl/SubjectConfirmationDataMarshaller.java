/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.config.SAMLConfigurationSupport;
import org.opensaml.saml.saml2.core.SubjectConfirmationData;
import org.w3c.dom.Element;

public class SubjectConfirmationDataMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        SubjectConfirmationData subjectCD = (SubjectConfirmationData)samlObject;
        if (subjectCD.getNotBefore() != null) {
            String notBeforeStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(subjectCD.getNotBefore());
            domElement.setAttributeNS(null, "NotBefore", notBeforeStr);
        }
        if (subjectCD.getNotOnOrAfter() != null) {
            String notOnOrAfterStr = SAMLConfigurationSupport.getSAMLDateFormatter().print(subjectCD.getNotOnOrAfter());
            domElement.setAttributeNS(null, "NotOnOrAfter", notOnOrAfterStr);
        }
        if (subjectCD.getRecipient() != null) {
            domElement.setAttributeNS(null, "Recipient", subjectCD.getRecipient());
        }
        if (subjectCD.getInResponseTo() != null) {
            domElement.setAttributeNS(null, "InResponseTo", subjectCD.getInResponseTo());
        }
        if (subjectCD.getAddress() != null) {
            domElement.setAttributeNS(null, "Address", subjectCD.getAddress());
        }
        this.marshallUnknownAttributes(subjectCD, domElement);
    }
}

