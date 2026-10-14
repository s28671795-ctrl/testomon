/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security.jce;

import com.hierynomus.security.DerivationFunction;
import com.hierynomus.security.SecurityException;
import com.hierynomus.security.jce.derivationfunction.DerivationParameters;

public class JceDerivationFunction
implements DerivationFunction {
    @Override
    public void init(DerivationParameters parameters) throws SecurityException {
        throw new UnsupportedOperationException();
    }

    @Override
    public int generateBytes(byte[] out, int outOff, int len) {
        throw new UnsupportedOperationException();
    }
}

