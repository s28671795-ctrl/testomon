/*
 * Decompiled with CFR 0.152.
 */
package com.hierynomus.security;

import com.hierynomus.security.SecurityException;
import com.hierynomus.security.jce.derivationfunction.DerivationParameters;

public interface DerivationFunction {
    public void init(DerivationParameters var1) throws SecurityException;

    public int generateBytes(byte[] var1, int var2, int var3);
}

