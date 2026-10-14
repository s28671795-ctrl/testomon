/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnQuery;
import org.opensaml.saml.saml2.core.impl.AuthnQueryImpl;

public class AuthnQueryBuilder
extends AbstractSAMLObjectBuilder<AuthnQuery> {
    @Override
    public AuthnQuery buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnQuery", "saml2p");
    }

    @Override
    public AuthnQuery buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnQueryImpl(namespaceURI, localName, namespacePrefix);
    }
}

