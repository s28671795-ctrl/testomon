/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.Assertion;
import org.opensaml.saml.saml1.core.impl.AssertionImpl;

public class AssertionBuilder
extends AbstractSAMLObjectBuilder<Assertion> {
    @Override
    public AssertionImpl buildObject() {
        return new AssertionImpl("urn:oasis:names:tc:SAML:1.0:assertion", "Assertion", "saml1");
    }

    @Override
    public AssertionImpl buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionImpl(namespaceURI, localName, namespacePrefix);
    }
}

