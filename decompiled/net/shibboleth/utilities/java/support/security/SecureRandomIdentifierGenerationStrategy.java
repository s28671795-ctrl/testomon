/*
 * Decompiled with CFR 0.152.
 */
package net.shibboleth.utilities.java.support.security;

import java.security.SecureRandom;
import javax.annotation.Nonnull;
import net.shibboleth.utilities.java.support.security.RandomIdentifierGenerationStrategy;
import org.apache.commons.codec.BinaryEncoder;

public class SecureRandomIdentifierGenerationStrategy
extends RandomIdentifierGenerationStrategy {
    public SecureRandomIdentifierGenerationStrategy() {
    }

    public SecureRandomIdentifierGenerationStrategy(int identifierSize) {
        super(identifierSize);
    }

    public SecureRandomIdentifierGenerationStrategy(@Nonnull SecureRandom source2, int identifierSize, @Nonnull BinaryEncoder identifierEncoder) {
        super(source2, identifierSize, identifierEncoder);
    }
}

