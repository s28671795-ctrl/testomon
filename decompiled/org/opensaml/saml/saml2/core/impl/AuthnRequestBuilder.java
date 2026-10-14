/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.opensaml.saml.saml2.core.impl.AuthnRequestImpl;

public class AuthnRequestBuilder
extends AbstractSAMLObjectBuilder<AuthnRequest> {
    @Override
    public AuthnRequest buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AuthnRequest", "saml2p");
    }

    @Override
    public AuthnRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnRequestImpl(namespaceURI, localName, namespacePrefix);
    }
}

