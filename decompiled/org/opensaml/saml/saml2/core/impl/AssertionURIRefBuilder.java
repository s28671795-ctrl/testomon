/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AssertionURIRef;
import org.opensaml.saml.saml2.core.impl.AssertionURIRefImpl;

public class AssertionURIRefBuilder
extends AbstractSAMLObjectBuilder<AssertionURIRef> {
    @Override
    public AssertionURIRef buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionURIRef", "saml2");
    }

    @Override
    public AssertionURIRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionURIRefImpl(namespaceURI, localName, namespacePrefix);
    }
}

