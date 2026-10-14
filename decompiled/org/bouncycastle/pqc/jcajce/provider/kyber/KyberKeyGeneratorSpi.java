/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.jcajce.provider.kyber;

import java.security.InvalidAlgorithmParameterException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.KeyGeneratorSpi;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.security.auth.DestroyFailedException;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.jcajce.SecretKeyWithEncapsulation;
import org.bouncycastle.jcajce.spec.KEMExtractSpec;
import org.bouncycastle.jcajce.spec.KEMGenerateSpec;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKEMExtractor;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberKEMGenerator;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberParameters;
import org.bouncycastle.pqc.jcajce.provider.kyber.BCKyberPrivateKey;
import org.bouncycastle.pqc.jcajce.provider.kyber.BCKyberPublicKey;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

public class KyberKeyGeneratorSpi
extends KeyGeneratorSpi {
    private KEMGenerateSpec genSpec;
    private SecureRandom random;
    private KEMExtractSpec extSpec;
    private KyberParameters kyberParameters;

    public KyberKeyGeneratorSpi() {
        this(null);
    }

    protected KyberKeyGeneratorSpi(KyberParameters kyberParameters) {
        this.kyberParameters = kyberParameters;
    }

    @Override
    protected void engineInit(SecureRandom secureRandom) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.random = secureRandom;
        if (algorithmParameterSpec instanceof KEMGenerateSpec) {
            String string;
            this.genSpec = (KEMGenerateSpec)algorithmParameterSpec;
            this.extSpec = null;
            if (this.kyberParameters != null && !(string = Strings.toUpperCase(this.kyberParameters.getName())).equals(this.genSpec.getPublicKey().getAlgorithm())) {
                throw new InvalidAlgorithmParameterException("key generator locked to " + string);
            }
        } else if (algorithmParameterSpec instanceof KEMExtractSpec) {
            String string;
            this.genSpec = null;
            this.extSpec = (KEMExtractSpec)algorithmParameterSpec;
            if (this.kyberParameters != null && !(string = Strings.toUpperCase(this.kyberParameters.getName())).equals(this.extSpec.getPrivateKey().getAlgorithm())) {
                throw new InvalidAlgorithmParameterException("key generator locked to " + string);
            }
        } else {
            throw new InvalidAlgorithmParameterException("unknown spec");
        }
    }

    @Override
    protected void engineInit(int n, SecureRandom secureRandom) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    protected SecretKey engineGenerateKey() {
        if (this.genSpec != null) {
            BCKyberPublicKey bCKyberPublicKey = (BCKyberPublicKey)this.genSpec.getPublicKey();
            KyberKEMGenerator kyberKEMGenerator = new KyberKEMGenerator(this.random);
            SecretWithEncapsulation secretWithEncapsulation = kyberKEMGenerator.generateEncapsulated(bCKyberPublicKey.getKeyParams());
            byte[] byArray = secretWithEncapsulation.getSecret();
            byte[] byArray2 = Arrays.copyOfRange(byArray, 0, (this.genSpec.getKeySize() + 7) / 8);
            Arrays.clear(byArray);
            SecretKeyWithEncapsulation secretKeyWithEncapsulation = new SecretKeyWithEncapsulation(new SecretKeySpec(byArray2, this.genSpec.getKeyAlgorithmName()), secretWithEncapsulation.getEncapsulation());
            try {
                secretWithEncapsulation.destroy();
            }
            catch (DestroyFailedException destroyFailedException) {
                throw new IllegalStateException("key cleanup failed");
            }
            return secretKeyWithEncapsulation;
        }
        BCKyberPrivateKey bCKyberPrivateKey = (BCKyberPrivateKey)this.extSpec.getPrivateKey();
        KyberKEMExtractor kyberKEMExtractor = new KyberKEMExtractor(bCKyberPrivateKey.getKeyParams());
        byte[] byArray = this.extSpec.getEncapsulation();
        byte[] byArray3 = kyberKEMExtractor.extractSecret(byArray);
        byte[] byArray4 = Arrays.copyOfRange(byArray3, 0, (this.extSpec.getKeySize() + 7) / 8);
        Arrays.clear(byArray3);
        SecretKeyWithEncapsulation secretKeyWithEncapsulation = new SecretKeyWithEncapsulation(new SecretKeySpec(byArray4, this.extSpec.getKeyAlgorithmName()), byArray);
        Arrays.clear(byArray4);
        return secretKeyWithEncapsulation;
    }

    public static class Kyber1024
    extends KyberKeyGeneratorSpi {
        public Kyber1024() {
            super(KyberParameters.kyber1024);
        }
    }

    public static class Kyber512
    extends KyberKeyGeneratorSpi {
        public Kyber512() {
            super(KyberParameters.kyber512);
        }
    }

    public static class Kyber768
    extends KyberKeyGeneratorSpi {
        public Kyber768() {
            super(KyberParameters.kyber768);
        }
    }
}

