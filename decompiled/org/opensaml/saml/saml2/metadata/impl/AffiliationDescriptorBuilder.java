/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AffiliationDescriptor;
import org.opensaml.saml.saml2.metadata.impl.AffiliationDescriptorImpl;

public class AffiliationDescriptorBuilder
extends AbstractSAMLObjectBuilder<AffiliationDescriptor> {
    @Override
    public AffiliationDescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AffiliationDescriptor", "md");
    }

    @Override
    public AffiliationDescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AffiliationDescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

