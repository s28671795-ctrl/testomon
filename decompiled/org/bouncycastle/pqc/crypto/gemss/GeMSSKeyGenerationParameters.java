/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.gemss;

import java.security.SecureRandom;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.gemss.GeMSSParameters;

public class GeMSSKeyGenerationParameters
extends KeyGenerationParameters {
    final GeMSSParameters parameters;

    public GeMSSKeyGenerationParameters(SecureRandom secureRandom, GeMSSParameters geMSSParameters) {
        super(secureRandom, -1);
        this.parameters = geMSSParameters;
    }

    public GeMSSParameters getParameters() {
        return this.parameters;
    }
}

