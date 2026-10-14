/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.jcajce.provider.hqc;

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
import org.bouncycastle.pqc.crypto.hqc.HQCKEMExtractor;
import org.bouncycastle.pqc.crypto.hqc.HQCKEMGenerator;
import org.bouncycastle.pqc.jcajce.provider.hqc.BCHQCPrivateKey;
import org.bouncycastle.pqc.jcajce.provider.hqc.BCHQCPublicKey;
import org.bouncycastle.util.Arrays;

public class HQCKeyGeneratorSpi
extends KeyGeneratorSpi {
    private KEMGenerateSpec genSpec;
    private SecureRandom random;
    private KEMExtractSpec extSpec;

    @Override
    protected void engineInit(SecureRandom secureRandom) {
        throw new UnsupportedOperationException("Operation not supported");
    }

    @Override
    protected void engineInit(AlgorithmParameterSpec algorithmParameterSpec, SecureRandom secureRandom) throws InvalidAlgorithmParameterException {
        this.random = secureRandom;
        if (algorithmParameterSpec instanceof KEMGenerateSpec) {
            this.genSpec = (KEMGenerateSpec)algorithmParameterSpec;
            this.extSpec = null;
        } else if (algorithmParameterSpec instanceof KEMExtractSpec) {
            this.genSpec = null;
            this.extSpec = (KEMExtractSpec)algorithmParameterSpec;
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
            BCHQCPublicKey bCHQCPublicKey = (BCHQCPublicKey)this.genSpec.getPublicKey();
            HQCKEMGenerator hQCKEMGenerator = new HQCKEMGenerator(this.random);
            SecretWithEncapsulation secretWithEncapsulation = hQCKEMGenerator.generateEncapsulated(bCHQCPublicKey.getKeyParams());
            SecretKeyWithEncapsulation secretKeyWithEncapsulation = new SecretKeyWithEncapsulation(new SecretKeySpec(secretWithEncapsulation.getSecret(), this.genSpec.getKeyAlgorithmName()), secretWithEncapsulation.getEncapsulation());
            try {
                secretWithEncapsulation.destroy();
            }
            catch (DestroyFailedException destroyFailedException) {
                throw new IllegalStateException("key cleanup failed");
            }
            return secretKeyWithEncapsulation;
        }
        BCHQCPrivateKey bCHQCPrivateKey = (BCHQCPrivateKey)this.extSpec.getPrivateKey();
        HQCKEMExtractor hQCKEMExtractor = new HQCKEMExtractor(bCHQCPrivateKey.getKeyParams());
        byte[] byArray = this.extSpec.getEncapsulation();
        byte[] byArray2 = hQCKEMExtractor.extractSecret(byArray);
        SecretKeyWithEncapsulation secretKeyWithEncapsulation = new SecretKeyWithEncapsulation(new SecretKeySpec(byArray2, this.extSpec.getKeyAlgorithmName()), byArray);
        Arrays.clear(byArray2);
        return secretKeyWithEncapsulation;
    }
}

