/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameIDMappingRequest;
import org.opensaml.saml.saml2.core.impl.NameIDMappingRequestImpl;

public class NameIDMappingRequestBuilder
extends AbstractSAMLObjectBuilder<NameIDMappingRequest> {
    @Override
    public NameIDMappingRequest buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingRequest", "saml2p");
    }

    @Override
    public NameIDMappingRequest buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NameIDMappingRequestImpl(namespaceURI, localName, namespacePrefix);
    }
}

