/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml1.core.impl;

import org.opensaml.saml.common.AbstractSAMLObjectBuilder;
import org.opensaml.saml.saml1.core.AssertionArtifact;
import org.opensaml.saml.saml1.core.impl.AssertionArtifactImpl;

public class AssertionArtifactBuilder
extends AbstractSAMLObjectBuilder<AssertionArtifact> {
    @Override
    public AssertionArtifact buildObject() {
        return this.buildObject("urn:oasis:names:tc:SAML:1.0:protocol", "AssertionArtifact", "saml1p");
    }

    @Override
    public AssertionArtifact buildObject(String namespaceURI, String localName, String namespacePrefix) {
        return new AssertionArtifactImpl(namespaceURI, localName, namespacePrefix);
    }
}

