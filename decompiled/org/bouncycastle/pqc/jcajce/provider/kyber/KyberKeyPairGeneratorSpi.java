/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.jcajce.provider.kyber;

import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKeyPairGenerator;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPublicKeyParameters;
import org.bouncycastle.pqc.jcajce.provider.kyber.BCKyberPrivateKey;
import org.bouncycastle.pqc.jcajce.provider.kyber.BCKyberPublicKey;
import org.bouncycastle.pqc.jcajce.provider.util.SpecUtil;
import org.bouncycastle.pqc.jcajce.spec.KyberParameterSpec;
import org.bouncycastle.util.Strings;

public class KyberKeyPairGeneratorSpi
extends KeyPairGenerator {
    private static Map parameters = new HashMap();
    KyberKeyGenerationParameters param;
    KyberKeyPairGenerator engine = new KyberKeyPairGenerator();
    SecureRandom random = CryptoServicesRegistrar.getSecureRandom();
    boolean initialised = false;
    private KyberParameters kyberParameters;

    public KyberKeyPairGeneratorSpi() {
        super("KYBER");
        this.kyberParameters = null;
    }

    protected KyberKeyPairGeneratorSpi(KyberParameters kyberParameters) {
        super(Strings.toUpperCase(kyberParameters.getName()));
        this.kyberParameters = kyberParameters;
    }

    @Override
    public void initialize(int n, SecureRandom secureRandom) {
        throw new IllegalArgumentException("use AlgorithmParameterSpec");
    }

    @Override
    public void initialize(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        String string = KyberKeyPairGeneratorSpi.getNameFromParams(algorithmParameterSpec);
        if (string != null && parameters.containsKey(string)) {
            KyberParameters kyberParameters = (KyberParameters)parameters.get(string);
            this.param = new KyberKeyGenerationParameters(secureRandom, kyberParameters);
            if (this.kyberParameters != null && !kyberParameters.getName().equals(this.kyberParameters.getName())) {
                throw new InvalidAlgorithmParameterException("key pair generator locked to " + Strings.toUpperCase(this.kyberParameters.getName()));
            }
        } else {
            throw new InvalidAlgorithmParameterException("invalid ParameterSpec: " + algorithmParameterSpec);
        }
        this.engine.init(this.param);
        this.initialised = true;
    }

    private static String getNameFromParams(AlgorithmParameterSpec algorithmParameterSpec) {
        if (algorithmParameterSpec instanceof KyberParameterSpec) {
            KyberParameterSpec kyberParameterSpec = (KyberParameterSpec)algorithmParameterSpec;
            return kyberParameterSpec.getName();
        }
        return Strings.toLowerCase(SpecUtil.getNameFrom(algorithmParameterSpec));
    }

    @Override
    public KeyPair generateKeyPair() {
        if (!this.initialised) {
            this.param = this.kyberParameters != null ? new KyberKeyGenerationParameters(this.random, this.kyberParameters) : new KyberKeyGenerationParameters(this.random, KyberParameters.kyber1024);
            this.engine.init(this.param);
            this.initialised = true;
        }
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = this.engine.generateKeyPair();
        KyberPublicKeyParameters kyberPublicKeyParameters = (KyberPublicKeyParameters)asymmetricCipherKeyPair.getPublic();
        KyberPrivateKeyParameters kyberPrivateKeyParameters = (KyberPrivateKeyParameters)asymmetricCipherKeyPair.getPrivate();
        return new KeyPair(new BCKyberPublicKey(kyberPublicKeyParameters), new BCKyberPrivateKey(kyberPrivateKeyParameters));
    }

    static {
        parameters.put(KyberParameterSpec.kyber512.getName(), KyberParameters.kyber512);
        parameters.put(KyberParameterSpec.kyber768.getName(), KyberParameters.kyber768);
        parameters.put(KyberParameterSpec.kyber1024.getName(), KyberParameters.kyber1024);
    }

    public static class Kyber1024
    extends KyberKeyPairGeneratorSpi {
        public Kyber1024() {
            super(KyberParameters.kyber1024);
        }
    }

    public static class Kyber512
    extends KyberKeyPairGeneratorSpi {
        public Kyber512() {
            super(KyberParameters.kyber512);
        }
    }

    public static class Kyber768
    extends KyberKeyPairGeneratorSpi {
        public Kyber768() {
            super(KyberParameters.kyber768);
        }
    }
}

