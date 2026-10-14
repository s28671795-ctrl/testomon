/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AuthenticationQuery;
import org.opensaml.saml.saml1.core.impl.AuthenticationQueryImpl;

public class AuthenticationQueryBuilder
extends AbstractSAMLObjectBuilder<AuthenticationQuery> {
    @Override
    public AuthenticationQuery buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "AuthenticationQuery", "saml1p");
    }

    @Override
    public AuthenticationQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthenticationQueryImpl(namespaceURI, localName, namespacePrefix);
    }
}

