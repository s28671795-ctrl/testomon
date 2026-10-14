/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AuthnQueryService;
import org.opensaml.saml.saml2.metadata.impl.AuthnQueryServiceImpl;

public class AuthnQueryServiceBuilder
extends AbstractSAMLObjectBuilder<AuthnQueryService> {
    @Override
    public AuthnQueryService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AuthnQueryService", "md");
    }

    @Override
    public AuthnQueryService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnQueryServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

