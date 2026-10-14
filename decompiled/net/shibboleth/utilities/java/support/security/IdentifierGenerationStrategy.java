/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.security;

import javax.annotation.Nonnull;

public interface IdentifierGenerationStrategy {
    @Nonnull
    public String generateIdentifier();

    @Nonnull
    public String generateIdentifier(boolean var1);
}

