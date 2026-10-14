/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPublicKeyParameters;
import org.bouncycastle.util.Arrays;

public class KyberPrivateKeyParameters
extends KyberKeyParameters {
    final byte[] s;
    final byte[] hpk;
    final byte[] nonce;
    final byte[] t;
    final byte[] rho;

    public KyberPrivateKeyParameters(KyberParameters kyberParameters, byte[] byArray, byte[] byArray2, byte[] byArray3, byte[] byArray4, byte[] byArray5) {
        super(true, kyberParameters);
        this.s = Arrays.clone(byArray);
        this.hpk = Arrays.clone(byArray2);
        this.nonce = Arrays.clone(byArray3);
        this.t = Arrays.clone(byArray4);
        this.rho = Arrays.clone(byArray5);
    }

    public KyberPrivateKeyParameters(KyberParameters kyberParameters, byte[] byArray) {
        super(true, kyberParameters);
        KyberEngine kyberEngine = kyberParameters.getEngine();
        int n = 0;
        this.s = Arrays.copyOfRange(byArray, 0, kyberEngine.getKyberIndCpaSecretKeyBytes());
        this.t = Arrays.copyOfRange(byArray, n += kyberEngine.getKyberIndCpaSecretKeyBytes(), n + kyberEngine.getKyberIndCpaPublicKeyBytes() - 32);
        this.rho = Arrays.copyOfRange(byArray, n += kyberEngine.getKyberIndCpaPublicKeyBytes() - 32, n + 32);
        this.hpk = Arrays.copyOfRange(byArray, n += 32, n + 32);
        this.nonce = Arrays.copyOfRange(byArray, n += 32, n + 32);
    }

    public byte[] getEncoded() {
        return Arrays.concatenate(new byte[][]{this.s, this.t, this.rho, this.hpk, this.nonce});
    }

    public byte[] getHPK() {
        return Arrays.clone(this.hpk);
    }

    public byte[] getNonce() {
        return Arrays.clone(this.nonce);
    }

    public byte[] getPrivateKey() {
        return this.getEncoded();
    }

    public byte[] getPublicKey() {
        return KyberPublicKeyParameters.getEncoded(this.t, this.rho);
    }

    public KyberPublicKeyParameters getPublicKeyParameters() {
        return new KyberPublicKeyParameters(this.getParameters(), this.t, this.rho);
    }

    public byte[] getRho() {
        return Arrays.clone(this.rho);
    }

    public byte[] getS() {
        return Arrays.clone(this.s);
    }

    public byte[] getT() {
        return Arrays.clone(this.t);
    }
}

