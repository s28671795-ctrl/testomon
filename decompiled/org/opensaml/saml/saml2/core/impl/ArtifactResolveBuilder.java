/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml2.core.ArtifactResolve;
import org.opensaml.saml.saml2.core.impl.ArtifactResolveImpl;

public class ArtifactResolveBuilder
extends AbstractSAMLObjectBuilder<ArtifactResolve> {
    @Override
    public ArtifactResolve buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:2.0:protocol", "ArtifactResolve", "saml2p");
    }

    @Override
    public ArtifactResolve buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new ArtifactResolveImpl(namespaceURI, localName, namespacePrefix);
    }
}

