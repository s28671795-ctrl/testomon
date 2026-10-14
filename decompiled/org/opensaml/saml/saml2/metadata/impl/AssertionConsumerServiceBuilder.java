/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AssertionConsumerService;
import org.opensaml.saml.saml2.metadata.impl.AssertionConsumerServiceImpl;

public class AssertionConsumerServiceBuilder
extends AbstractSAMLObjectBuilder<AssertionConsumerService> {
    @Override
    public AssertionConsumerService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AssertionConsumerService", "md");
    }

    @Override
    public AssertionConsumerService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionConsumerServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

