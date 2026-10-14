/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.AdditionalMetadataLocation;
import org.opensaml.saml.saml2.metadata.impl.AdditionalMetadataLocationImpl;

public class AdditionalMetadataLocationBuilder
extends AbstractSAMLObjectBuilder<AdditionalMetadataLocation> {
    @Override
    public AdditionalMetadataLocation buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "AdditionalMetadataLocation", "md");
    }

    @Override
    public AdditionalMetadataLocation buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AdditionalMetadataLocationImpl(namespaceURI, localName, namespacePrefix);
    }
}

