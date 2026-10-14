/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.common.AbstractSAMLObjectMarshaller;
import org.opensaml.saml.saml2.core.RequestedAuthnContext;
import org.w3c.dom.Element;

public class RequestedAuthnContextMarshaller
extends AbstractSAMLObjectMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        RequestedAuthnContext rac = (RequestedAuthnContext)samlObject;
        if (rac.getComparison() != null) {
            domElement.setAttributeNS(null, "Comparison", rac.getComparison().toString());
        }
    }
}

