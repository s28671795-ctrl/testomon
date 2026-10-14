/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ArtifactResponse;
import org.opensaml.saml.saml2.core.impl.ArtifactResponseImpl;

public class ArtifactResponseBuilder
extends AbstractSAMLObjectBuilder<ArtifactResponse> {
    @Override
    public ArtifactResponse buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResponse", "saml2p");
    }

    @Override
    public ArtifactResponse buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ArtifactResponseImpl(namespaceURI, localName, namespacePrefix);
    }
}

