/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AssertionIDRef;
import org.opensaml.saml.saml2.core.impl.AssertionIDRefImpl;

public class AssertionIDRefBuilder
extends AbstractSAMLObjectBuilder<AssertionIDRef> {
    @Override
    public AssertionIDRef buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "AssertionIDRef", "saml2");
    }

    @Override
    public AssertionIDRef buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionIDRefImpl(namespaceURI, localName, namespacePrefix);
    }
}

