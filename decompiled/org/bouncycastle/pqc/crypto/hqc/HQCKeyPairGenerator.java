/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.hqc;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCEngine;
import org.bouncycastle.pqc.crypto.hqc.HQCKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.hqc.HQCPublicKeyParameters;

public class HQCKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private int n;
    private int k;
    private int delta;
    private int w;
    private int wr;
    private int we;
    private int N_BYTE;
    private HQCKeyGenerationParameters hqcKeyGenerationParameters;
    private SecureRandom random;

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.hqcKeyGenerationParameters = (HQCKeyGenerationParameters)keyGenerationParameters;
        this.random = keyGenerationParameters.getRandom();
        this.n = this.hqcKeyGenerationParameters.getParameters().getN();
        this.k = this.hqcKeyGenerationParameters.getParameters().getK();
        this.delta = this.hqcKeyGenerationParameters.getParameters().getDelta();
        this.w = this.hqcKeyGenerationParameters.getParameters().getW();
        this.wr = this.hqcKeyGenerationParameters.getParameters().getWr();
        this.we = this.hqcKeyGenerationParameters.getParameters().getWe();
        this.N_BYTE = (this.n + 7) / 8;
    }

    private AsymmetricCipherKeyPair genKeyPair(byte[] byArray) {
        HQCEngine hQCEngine = this.hqcKeyGenerationParameters.getParameters().getEngine();
        byte[] byArray2 = new byte[40 + this.N_BYTE];
        byte[] byArray3 = new byte[80 + this.N_BYTE];
        hQCEngine.genKeyPair(byArray2, byArray3, byArray);
        HQCPublicKeyParameters hQCPublicKeyParameters = new HQCPublicKeyParameters(this.hqcKeyGenerationParameters.getParameters(), byArray2);
        HQCPrivateKeyParameters hQCPrivateKeyParameters = new HQCPrivateKeyParameters(this.hqcKeyGenerationParameters.getParameters(), byArray3);
        return new AsymmetricCipherKeyPair(hQCPublicKeyParameters, hQCPrivateKeyParameters);
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        byte[] byArray = new byte[48];
        this.random.nextBytes(byArray);
        return this.genKeyPair(byArray);
    }

    public AsymmetricCipherKeyPair generateKeyPairWithSeed(byte[] byArray) {
        return this.genKeyPair(byArray);
    }
}

