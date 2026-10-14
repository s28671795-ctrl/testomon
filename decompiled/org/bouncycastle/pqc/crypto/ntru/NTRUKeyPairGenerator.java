/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.ntru;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.pqc.crypto.ntru.NTRUKeyGenerationParameters;
import org.bouncycastle.pqc.crypto.ntru.NTRUOWCPA;
import org.bouncycastle.pqc.crypto.ntru.NTRUPrivateKeyParameters;
import org.bouncycastle.pqc.crypto.ntru.NTRUPublicKeyParameters;
import org.bouncycastle.pqc.crypto.ntru.OWCPAKeyPair;
import org.bouncycastle.pqc.math.ntru.parameters.NTRUParameterSet;

public class NTRUKeyPairGenerator
implements AsymmetricCipherKeyPairGenerator {
    private NTRUKeyGenerationParameters params;
    private SecureRandom random;

    @Override
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.params = (NTRUKeyGenerationParameters)keyGenerationParameters;
        this.random = keyGenerationParameters.getRandom();
    }

    @Override
    public AsymmetricCipherKeyPair generateKeyPair() {
        NTRUParameterSet nTRUParameterSet = this.params.getParameters().parameterSet;
        byte[] byArray = new byte[nTRUParameterSet.sampleFgBytes()];
        this.random.nextBytes(byArray);
        NTRUOWCPA nTRUOWCPA = new NTRUOWCPA(nTRUParameterSet);
        OWCPAKeyPair oWCPAKeyPair = nTRUOWCPA.keypair(byArray);
        byte[] byArray2 = oWCPAKeyPair.publicKey;
        byte[] byArray3 = new byte[nTRUParameterSet.ntruSecretKeyBytes()];
        byte[] byArray4 = oWCPAKeyPair.privateKey;
        System.arraycopy(byArray4, 0, byArray3, 0, byArray4.length);
        byte[] byArray5 = new byte[nTRUParameterSet.prfKeyBytes()];
        this.random.nextBytes(byArray5);
        System.arraycopy(byArray5, 0, byArray3, nTRUParameterSet.owcpaSecretKeyBytes(), byArray5.length);
        return new AsymmetricCipherKeyPair(new NTRUPublicKeyParameters(this.params.getParameters(), byArray2), new NTRUPrivateKeyParameters(this.params.getParameters(), byArray3));
    }
}

