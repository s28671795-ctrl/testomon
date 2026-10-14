/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnContextClassRef;
import org.opensaml.saml.saml2.core.impl.AuthnContextClassRefImpl;

public class AuthnContextClassRefBuilder
extends AbstractSAMLObjectBuilder<AuthnContextClassRef> {
    @Override
    public AuthnContextClassRef buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextClassRef", "saml2");
    }

    @Override
    public AuthnContextClassRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnContextClassRefImpl(namespaceURI, localName, namespacePrefix);
    }
}

