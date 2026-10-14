/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPublicKeyParameters;

public class KyberKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private KyberParameters kyberParams;
    private SecureRandom random;

    private void initialize(KeyGenerationParameters keyGenerationParameters) {
        this.kyberParams = ((KyberKeyGenerationParameters)keyGenerationParameters).getParameters();
        this.random = keyGenerationParameters.getRandom();
    }

    private AsymmetricCipherKeyPair genKeyPair() {
        KyberEngine kyberEngine = this.kyberParams.getEngine();
        kyberEngine.init(this.random);
        byte[][] byArray = kyberEngine.generateKemKeyPair();
        KyberPublicKeyParameters kyberPublicKeyParameters = new KyberPublicKeyParameters(this.kyberParams, byArray[0], byArray[1]);
        KyberPrivateKeyParameters kyberPrivateKeyParameters = new KyberPrivateKeyParameters(this.kyberParams, byArray[2], byArray[3], byArray[4], byArray[0], byArray[1]);
        return new AsymmetricCipherKeyPair(kyberPublicKeyParameters, kyberPrivateKeyParameters);
    }

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.initialize(keyGenerationParameters);
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        return this.genKeyPair();
    }
}

