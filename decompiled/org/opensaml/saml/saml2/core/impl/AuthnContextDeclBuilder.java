/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnContextDecl;
import org.opensaml.saml.saml2.core.impl.AuthnContextDeclImpl;

public class AuthnContextDeclBuilder
extends AbstractSAMLObjectBuilder<AuthnContextDecl> {
    @Override
    public AuthnContextDecl buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnContextDecl", "saml2");
    }

    @Override
    public AuthnContextDecl buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnContextDeclImpl(namespaceURI, localName, namespacePrefix);
    }
}

