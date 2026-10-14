/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AuthzService;
import org.opensaml.saml.saml2.metadata.impl.AuthzServiceImpl;

public class AuthzServiceBuilder
extends AbstractSAMLObjectBuilder<AuthzService> {
    @Override
    public AuthzService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AuthzService", "md");
    }

    @Override
    public AuthzService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthzServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

