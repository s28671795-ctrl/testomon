/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;

public class KyberKeyGenerationParameters
extends KeyGenerationParameters {
    private final KyberParameters params;

    public KyberKeyGenerationParameters(SecureRandom secureRandom, KyberParameters kyberParameters) {
        super(secureRandom, 256);
        this.params = kyberParameters;
    }

    public KyberParameters getParameters() {
        return this.params;
    }
}

