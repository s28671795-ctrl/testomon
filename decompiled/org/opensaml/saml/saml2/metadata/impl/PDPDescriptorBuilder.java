/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.PDPDescriptor;
import org.opensaml.saml.saml2.metadata.impl.PDPDescriptorImpl;

public class PDPDescriptorBuilder
extends AbstractSAMLObjectBuilder<PDPDescriptor> {
    @Override
    public PDPDescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "PDPDescriptor", "md");
    }

    @Override
    public PDPDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new PDPDescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

