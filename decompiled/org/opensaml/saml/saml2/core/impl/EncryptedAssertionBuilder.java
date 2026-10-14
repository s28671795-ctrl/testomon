/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.EncryptedAssertion;
import org.opensaml.saml.saml2.core.impl.EncryptedAssertionImpl;

public class EncryptedAssertionBuilder
extends AbstractSAMLObjectBuilder<EncryptedAssertion> {
    @Override
    public EncryptedAssertion buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:assertion", "EncryptedAssertion", "saml2");
    }

    @Override
    public EncryptedAssertion buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EncryptedAssertionImpl(namespaceURI, localName, namespacePrefix);
    }
}

