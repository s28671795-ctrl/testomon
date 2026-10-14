/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.Issuer;
import org.opensaml.saml.saml2.core.impl.IssuerImpl;

public class IssuerBuilder
extends AbstractSAMLObjectBuilder<Issuer> {
    @Override
    public Issuer buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "Issuer", "saml2");
    }

    @Override
    public Issuer buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new IssuerImpl(namespaceURI, localName, namespacePrefix);
    }
}

