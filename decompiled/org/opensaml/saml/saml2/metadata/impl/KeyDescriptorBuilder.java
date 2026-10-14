/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.KeyDescriptor;
import org.opensaml.saml.saml2.metadata.impl.KeyDescriptorImpl;

public class KeyDescriptorBuilder
extends AbstractSAMLObjectBuilder<KeyDescriptor> {
    @Override
    public KeyDescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "KeyDescriptor", "md");
    }

    @Override
    public KeyDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new KeyDescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

