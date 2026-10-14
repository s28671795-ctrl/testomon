/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security.jce.derivationfunction;

import com.hierynomus.security.jce.derivationfunction.DerivationParameters;
import java.util.Arrays;

public class CounterDerivationParameters
implements DerivationParameters {
    private byte[] seed;
    private byte[] fixedCounterSuffix;
    private int counterLength;

    public CounterDerivationParameters(byte[] seed, byte[] fixedCounterSuffix, int counterLength) {
        if (seed == null || seed.length == 0) {
            throw new IllegalArgumentException("Missing Seed for KDF");
        }
        this.seed = Arrays.copyOf(seed, seed.length);
        this.fixedCounterSuffix = fixedCounterSuffix == null ? new byte[0] : Arrays.copyOf(fixedCounterSuffix, fixedCounterSuffix.length);
        this.counterLength = counterLength;
    }

    public byte[] getSeed() {
        return this.seed;
    }

    public byte[] getFixedCounterSuffix() {
        return this.fixedCounterSuffix;
    }

    public int getCounterLength() {
        return this.counterLength;
    }
}

