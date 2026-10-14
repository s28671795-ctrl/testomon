/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AuthnAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.impl.AuthnAuthorityDescriptorImpl;

public class AuthnAuthorityDescriptorBuilder
extends AbstractSAMLObjectBuilder<AuthnAuthorityDescriptor> {
    @Override
    public AuthnAuthorityDescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AuthnAuthorityDescriptor", "md");
    }

    @Override
    public AuthnAuthorityDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthnAuthorityDescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

