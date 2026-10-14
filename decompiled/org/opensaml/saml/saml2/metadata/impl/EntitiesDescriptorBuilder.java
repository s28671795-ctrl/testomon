/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.EntitiesDescriptor;
import org.opensaml.saml.saml2.metadata.impl.EntitiesDescriptorImpl;

public class EntitiesDescriptorBuilder
extends AbstractSAMLObjectBuilder<EntitiesDescriptor> {
    @Override
    public EntitiesDescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "EntitiesDescriptor", "md");
    }

    @Override
    public EntitiesDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new EntitiesDescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

