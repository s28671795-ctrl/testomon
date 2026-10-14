/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.metadata.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.metadata.ArtifactResolutionService;
import org.opensaml.saml.saml2.metadata.impl.ArtifactResolutionServiceImpl;

public class ArtifactResolutionServiceBuilder
extends AbstractSAMLObjectBuilder<ArtifactResolutionService> {
    @Override
    public ArtifactResolutionService buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:metadata", "ArtifactResolutionService", "md");
    }

    @Override
    public ArtifactResolutionService buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ArtifactResolutionServiceImpl(namespaceURI, localName, namespacePrefix);
    }
}

