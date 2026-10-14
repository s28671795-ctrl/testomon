/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.SPSSODescriptor;
import org.opensaml.saml.saml2.metadata.impl.SPSSODescriptorImpl;

public class SPSSODescriptorBuilder
extends AbstractSAMLObjectBuilder<SPSSODescriptor> {
    @Override
    public SPSSODescriptor buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "SPSSODescriptor", "md");
    }

    @Override
    public SPSSODescriptor buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new SPSSODescriptorImpl(namespaceURI, localName, namespacePrefix);
    }
}

