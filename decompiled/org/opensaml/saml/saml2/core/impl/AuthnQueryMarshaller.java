/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.core.xml.io.MarshallingException;
import org.opensaml.saml.saml2.core.AuthnQuery;
import org.opensaml.saml.saml2.core.impl.SubjectQueryMarshaller;
import org.w3c.dom.Element;

public class AuthnQueryMarshaller
extends SubjectQueryMarshaller {
    @Override
    protected void marshallAttributes(XMLObject samlObject, Element domElement) throws MarshallingException {
        AuthnQuery query = (AuthnQuery)samlObject;
        if (query.getSessionIndex() != null) {
            domElement.setAttributeNS(null, "SessionIndex", query.getSessionIndex());
        }
        super.marshallAttributes(samlObject, domElement);
    }
}

