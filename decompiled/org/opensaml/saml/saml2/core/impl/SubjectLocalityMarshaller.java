/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.SubjectLocality;
import org.w3c.dom.Element;

public class SubjectLocalityMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        SubjectLocality subjectLocality = (SubjectLocality)samlObject;
        if (subjectLocality.getAddress() != null) {
            domElement.setAttributeNS(null, "Address", subjectLocality.getAddress());
        }
        if (subjectLocality.getDNSName() != null) {
            domElement.setAttributeNS(null, "DNSName", subjectLocality.getDNSName());
        }
    }
}

