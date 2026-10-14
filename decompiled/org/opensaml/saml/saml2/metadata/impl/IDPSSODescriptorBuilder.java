/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.IDPSSODescriptor;
import org.opensaml.saml.saml2.metadata.impl.IDPSSODescriptorImpl;

public class IDPSSODescriptorBuilder
extends AbstractSAMLObjectBuilder<IDPSSODescriptor> {
    @Override
    public IDPSSODescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "IDPSSODescriptor", "md");
    }

    @Override
    public IDPSSODescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new IDPSSODescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

