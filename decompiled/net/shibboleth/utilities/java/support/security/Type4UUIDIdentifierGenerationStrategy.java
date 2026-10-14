/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.security;

import java.util.UUID;
import javax.annotation.Nonnull;
import javax.annotation.concurrent.ThreadSafe;
import net.shibboleth.utilities.java.support.security.IdentifierGenerationStrategy;

@ThreadSafe
public class Type4UUIDIdentifierGenerationStrategy
implements IdentifierGenerationStrategy {
    @Override
    @Nonnull
    public String generateIdentifier() {
        return this.generateIdentifier(true);
    }

    @Override
    public String generateIdentifier(boolean xmlSafe) {
        if (xmlSafe) {
            return "_" + UUID.randomUUID().toString();
        }
        return UUID.randomUUID().toString();
    }
}

