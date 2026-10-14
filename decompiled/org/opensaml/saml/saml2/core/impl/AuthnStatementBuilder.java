/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnStatement;
import org.opensaml.saml.saml2.core.impl.AuthnStatementImpl;

public class AuthnStatementBuilder
extends AbstractSAMLObjectBuilder<AuthnStatement> {
    @Override
    public AuthnStatement buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AuthnStatement", "saml2");
    }

    @Override
    public AuthnStatement buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnStatementImpl(namespaceURI, localName, namespacePrefix);
    }
}

