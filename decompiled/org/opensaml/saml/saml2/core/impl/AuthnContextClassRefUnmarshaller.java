/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.core.xml.XMLObject;
import org.opensaml.saml.common.AbstractSAMLObjectUnmarshaller;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;

public class AuthnContextClassRefUnmarshaller
extends AbstractSAMLObjectUnmarshaller {
    @Override
    protected void processElementContent(XMLObject samlObject, String elementContent) {
        AuthnContextClassRef authnContextClassRef = (AuthnContextClassRef)samlObject;
        authnContextClassRef.setAuthnContextClassRef(elementContent);
    }
}

