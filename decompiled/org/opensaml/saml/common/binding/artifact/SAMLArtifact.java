/*
 * Decompiled with CFR 0.152.
 */
package org.opensaml.saml.common.binding.artifact;

import javax.annotation.Nonnull;

public interface SAMLArtifact {
    @Nonnull
    public byte[] getArtifactBytes();

    @Nonnull
    public byte[] getTypeCode();
}

