/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.saml2.binding.artifact;

import javax.annotation.Nonnull;
import org.opensaml.saml.common.binding.artifact.SAMLArtifact;

public interface SAML2Artifact
extends SAMLArtifact {
    @Nonnull
    public byte[] getEndpointIndex();
}

