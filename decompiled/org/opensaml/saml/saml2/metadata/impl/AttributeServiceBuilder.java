/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeService;
import org.opensaml.saml.saml2.metadata.impl.AttributeServiceImpl;

public class AttributeServiceBuilder
extends AbstractSAMLObjectBuilder<AttributeService> {
    @Override
    public AttributeService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeService", "md");
    }

    @Override
    public AttributeService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

