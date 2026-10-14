/*
 * Decompiled with CFR 0.152.
 */
package org.bouncycastle.pqc.crypto.crystals.kyber;

import java.security.SecureRandom;
import org.bouncycastle.crypto.EncapsulatedSecretGenerator;
import org.bouncycastle.crypto.SecretWithEncapsulation;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberEngine;
import org.bouncycastle.pqc.crypto.crystals.kyber.KyberPublicKeyParameters;
import org.bouncycastle.pqc.crypto.util.SecretWithEncapsulationImpl;

public class KyberKEMGenerator
implements EncapsulatedSecretGenerator {
    private final SecureRandom sr;

    public KyberKEMGenerator(SecureRandom secureRandom) {
        this.sr = secureRandom;
    }

    @Override
    public SecretWithEncapsulation generateEncapsulated(AsymmetricKeyParameter asymmetricKeyParameter) {
        KyberPublicKeyParameters kyberPublicKeyParameters = (KyberPublicKeyParameters)asymmetricKeyParameter;
        KyberEngine kyberEngine = kyberPublicKeyParameters.getParameters().getEngine();
        kyberEngine.init(this.sr);
        byte[][] byArray = kyberEngine.kemEncrypt(kyberPublicKeyParameters.getEncoded());
        return new SecretWithEncapsulationImpl(byArray[0], byArray[1]);
    }
}

