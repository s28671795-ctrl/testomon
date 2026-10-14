/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeConsumingService;
import org.opensaml.saml.saml2.metadata.impl.AttributeConsumingServiceImpl;

public class AttributeConsumingServiceBuilder
extends AbstractSAMLObjectBuilder<AttributeConsumingService> {
    @Override
    public AttributeConsumingService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeConsumingService", "md");
    }

    @Override
    public AttributeConsumingService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeConsumingServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

