/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnContextDeclRef;
import org.opensaml.saml.saml2.core.impl.AuthnContextDeclRefImpl;

public class AuthnContextDeclRefBuilder
extends AbstractSAMLObjectBuilder<AuthnContextDeclRef> {
    @Override
    public AuthnContextDeclRef buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDeclRef", "saml2");
    }

    @Override
    public AuthnContextDeclRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnContextDeclRefImpl(namespaceURI, localName, namespacePrefix);
    }
}

