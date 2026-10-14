/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AttributeAuthorityDescriptor;
import org.opensaml.saml.saml2.metadata.impl.AttributeAuthorityDescriptorImpl;

public class AttributeAuthorityDescriptorBuilder
extends AbstractSAMLObjectBuilder<AttributeAuthorityDescriptor> {
    @Override
    public AttributeAuthorityDescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AttributeAuthorityDescriptor", "md");
    }

    @Override
    public AttributeAuthorityDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AttributeAuthorityDescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

