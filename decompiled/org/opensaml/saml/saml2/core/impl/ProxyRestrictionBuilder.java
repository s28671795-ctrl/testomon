/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ProxyRestriction;
import org.opensaml.saml.saml2.core.impl.ProxyRestrictionImpl;

public class ProxyRestrictionBuilder
extends AbstractSAMLObjectBuilder<ProxyRestriction> {
    @Override
    public ProxyRestriction buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "ProxyRestriction", "saml2");
    }

    @Override
    public ProxyRestriction buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ProxyRestrictionImpl(namespaceURI, localName, namespacePrefix);
    }
}

