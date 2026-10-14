/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.xwing;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.generators.X25519KeyPairGenerator;
import org.bouncycastle.crypto.params.X25519KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyPairGenerator;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.pqc.crypto.xwing.XWingPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.xwing.XWingPublicKeyParameters;

public class XWingKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private SecureRandom random;

    private void initialize(KeyGenerationParameters keyGenerationParameters) {
        this.random = keyGenerationParameters.getRandom();
    }

    private AsymmetricCipherKeyPair genKeyPair() {
        KyberKeyPairGenerator kyberKeyPairGenerator = new KyberKeyPairGenerator();
        kyberKeyPairGenerator.init(new KyberKeyGenerationParameters(this.random, KyberParameters.kyber768));
        X25519KeyPairGenerator x25519KeyPairGenerator = new X25519KeyPairGenerator();
        x25519KeyPairGenerator.init(new X25519KeyGenerationParameters(this.random));
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = kyberKeyPairGenerator.generateKeyPair();
        AsymmetricCipherKeyPair asymmetricCipherKeyPair2 = x25519KeyPairGenerator.generateKeyPair();
        return new AsymmetricCipherKeyPair(new XWingPublicKeyParameters(asymmetricCipherKeyPair.getPublic(), asymmetricCipherKeyPair2.getPublic()), new XWingPrivateKeyParameters(asymmetricCipherKeyPair.getPrivate(), asymmetricCipherKeyPair2.getPrivate()));
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

