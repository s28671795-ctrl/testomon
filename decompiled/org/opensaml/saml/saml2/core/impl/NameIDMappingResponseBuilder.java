/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.NameIDMappingResponse;
import org.opensaml.saml.saml2.core.impl.NameIDMappingResponseImpl;

public class NameIDMappingResponseBuilder
extends AbstractSAMLObjectBuilder<NameIDMappingResponse> {
    @Override
    public NameIDMappingResponse buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "NameIDMappingResponse", "saml2p");
    }

    @Override
    public NameIDMappingResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NameIDMappingResponseImpl(namespaceURI, localName, namespacePrefix);
    }
}

