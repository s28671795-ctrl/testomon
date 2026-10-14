/*
 * Decompiled with CFR 0.152.
 */
package org.conscrypt;

import java.io.Serializable;
import java.security.SecureRandomSpi;
import org.conscrypt.NativeCrypto;

public final class OpenSSLRandom
extends SecureRandomSpi
implements Serializable {
    private static final long serialVersionUID = 8506210602917522861L;

    @Override
    protected void engineSetSeed(byte[] seed) {
        if (seed == null) {
            throw new NullPointerException("seed == null");
        }
    }

    @Override
    protected void engineNextBytes(byte[] bytes) {
        NativeCrypto.RAND_bytes(bytes);
    }

    @Override
    protected byte[] engineGenerateSeed(int numBytes) {
        byte[] output = new byte[numBytes];
        NativeCrypto.RAND_bytes(output);
        return output;
    }
}

