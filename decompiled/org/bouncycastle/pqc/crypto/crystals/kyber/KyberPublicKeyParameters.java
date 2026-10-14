/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.util.Arrays;

public class KyberPublicKeyParameters
extends KyberKeyParameters {
    final byte[] t;
    final byte[] rho;

    static byte[] getEncoded(byte[] byArray, byte[] byArray2) {
        return Arrays.concatenate(byArray, byArray2);
    }

    public KyberPublicKeyParameters(KyberParameters kyberParameters, byte[] byArray, byte[] byArray2) {
        super(false, kyberParameters);
        this.t = Arrays.clone(byArray);
        this.rho = Arrays.clone(byArray2);
    }

    public KyberPublicKeyParameters(KyberParameters kyberParameters, byte[] byArray) {
        super(false, kyberParameters);
        this.t = Arrays.copyOfRange(byArray, 0, byArray.length - 32);
        this.rho = Arrays.copyOfRange(byArray, byArray.length - 32, byArray.length);
    }

    public byte[] getEncoded() {
        return KyberPublicKeyParameters.getEncoded(this.t, this.rho);
    }

    public byte[] getPublicKey() {
        return this.getEncoded();
    }

    public byte[] getRho() {
        return Arrays.clone(this.rho);
    }

    public byte[] getT() {
        return Arrays.clone(this.t);
    }
}

