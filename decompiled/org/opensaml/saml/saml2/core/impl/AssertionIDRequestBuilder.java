/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.AssertionIDRequest;
import org.opensaml.saml.saml2.core.impl.AssertionIDRequestImpl;

public class AssertionIDRequestBuilder
extends AbstractSAMLObjectBuilder<AssertionIDRequest> {
    @Override
    public AssertionIDRequest buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "AssertionIDRequest", "saml2p");
    }

    @Override
    public AssertionIDRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionIDRequestImpl(namespaceURI, localName, namespacePrefix);
    }
}

