/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding.artifact;

import javax.annotation.Nonnull;
import org.opensaml.saml.common.binding.artifact.SAMLArtifact;

public interface SAMLSourceLocationArtifact
extends SAMLArtifact {
    @Nonnull
    public String getSourceLocation();
}

