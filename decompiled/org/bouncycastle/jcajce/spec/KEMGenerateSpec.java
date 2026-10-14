/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.jcajce.spec;

import java.security.PublicKey;
import java.security.spec.AlgorithmParameterSpec;

public class KEMGenerateSpec
implements AlgorithmParameterSpec {
    private final PublicKey publicKey;
    private final String keyAlgorithmName;
    private final int keySizeInBits;

    public KEMGenerateSpec(PublicKey publicKey, String string) {
        this(publicKey, string, 256);
    }

    public KEMGenerateSpec(PublicKey publicKey, String string, int n) {
        this.publicKey = publicKey;
        this.keyAlgorithmName = string;
        this.keySizeInBits = n;
    }

    public PublicKey getPublicKey() {
        return this.publicKey;
    }

    public String getKeyAlgorithmName() {
        return this.keyAlgorithmName;
    }

    public int getKeySize() {
        return this.keySizeInBits;
    }
}

