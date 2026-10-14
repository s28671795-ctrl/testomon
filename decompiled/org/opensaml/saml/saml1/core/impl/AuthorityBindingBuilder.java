/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AuthorityBinding;
import org.opensaml.saml.saml1.core.impl.AuthorityBindingImpl;

public class AuthorityBindingBuilder
extends AbstractSAMLObjectBuilder<AuthorityBinding> {
    @Override
    public AuthorityBinding buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:assertion", "AuthorityBinding", "saml1");
    }

    @Override
    public AuthorityBinding buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AuthorityBindingImpl(namespaceURI, localName, namespacePrefix);
    }
}

