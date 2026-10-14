/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.NameIDMappingService;
import org.opensaml.saml.saml2.metadata.impl.NameIDMappingServiceImpl;

public class NameIDMappingServiceBuilder
extends AbstractSAMLObjectBuilder<NameIDMappingService> {
    @Override
    public NameIDMappingService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "NameIDMappingService", "md");
    }

    @Override
    public NameIDMappingService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new NameIDMappingServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

